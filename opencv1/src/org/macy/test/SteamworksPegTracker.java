package org.macy.test;

import java.util.ArrayList;
import java.util.Iterator;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
//import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

//import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 * @author Elijah Kaufman
 * @version 1.0
 * @description Uses opencv and network table 3.0 to detect the vision targets
 *
 */
public class SteamworksPegTracker implements IObjectTracker {

  /**
   * static method to load opencv and networkTables
   */
  static {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    // NetworkTable.setClientMode();
    // NetworkTable.setIPAddress("roborio-3335.local");
  }

  // constants for the color bgr values
  public static final Scalar RED = new Scalar(0, 0, 255);
  public static final Scalar BLUE = new Scalar(255, 0, 0);
  public static final Scalar GREEN = new Scalar(0, 255, 0);
  public static final Scalar BLACK = new Scalar(0, 0, 0);
  public static final Scalar YELLOW = new Scalar(0, 255, 255);
  // these are the threshold values in order
  // public static final Scalar LOWER_BOUNDS = new Scalar(58, 0, 109);
  // public static final Scalar UPPER_BOUNDS = new Scalar(93, 255, 240);
  // public static final Scalar LOWER_BOUNDS = new Scalar(63, 168, 55);
  // public static final Scalar UPPER_BOUNDS = new Scalar(96, 255, 161);
  // White light
  // public static final Scalar LOWER_BOUNDS = new Scalar(0, 0, 124);
  // public static final Scalar UPPER_BOUNDS = new Scalar(180, 255, 255);
  public static final Scalar LOWER_BOUNDS = new Scalar(0, 0, 124);
  public static final Scalar UPPER_BOUNDS = new Scalar(180, 255, 255);
  // "Green" light
  // public static final Scalar LOWER_BOUNDS = new Scalar(20, 0, 124);
  // public static final Scalar UPPER_BOUNDS = new Scalar(100, 255, 255);

  // the size for resizing the image
  public static final Size resize = new Size(320, 240);

  private final VideoSource videoSource;

  private Mat matOriginal, matHSV, matThresh, matHierarchy;

  // Constants for known variables
  // the height to the top of the target in first stronghold is 97 inches
  public static final int TOP_TARGET_HEIGHT = 97;
  // the physical height of the camera lens
  public static final int TOP_CAMERA_HEIGHT = 32;

  // camera details, can usually be found on the datasheets of the camera
  public static final double VERTICAL_FOV = 51;
  public static final double HORIZONTAL_FOV = 67;
  public static final double CAMERA_ANGLE = 10;

  private final ImagePanel imagePanel;

  private boolean verboseLogging = true;
  private final TargetInfo targetInfoNone;
  private TargetInfo targetInfo;
  private volatile boolean stopTracker = false;
  private long frameCount = -1;
  private long timeStart = -1;

  public SteamworksPegTracker() {
    this(new VideoSource(), null);
  }

  public SteamworksPegTracker(int cameraDeviceID, ImagePanel panel) {
    this(new VideoSource(cameraDeviceID), panel);
  }

  public SteamworksPegTracker(VideoSource videoSource, ImagePanel panel) {
    this.videoSource = videoSource;
    imagePanel = panel;
    targetInfoNone = new TargetInfo(0, 0, 0, 0, 0, 0, 0, 0, 0);
    targetInfo = targetInfoNone;
  }

  /**
   *
   * @param args
   *          command line arguments just the main loop for the program and the
   *          entry points
   */
  public static void main(String[] args) {
    // ImagePanel panel = ImagePanel.createDisplayWindow();
    // new TowerTrackerNew(1, panel).run();
    SteamworksPegTracker tracker = new SteamworksPegTracker(0, null);
    tracker.setVerbose(false);
    new Thread(tracker).start();
    int count = 0;
    while (true) {
      TargetInfo info = tracker.getTargetInfo();
      System.out.println("info: " + count++ + " distance " + info.getDistance() + " azimuth " + info.getAzimuth());
    }
  }

  @Override
  public void run() {
    matOriginal = new Mat();
    matHSV = new Mat();
    matThresh = new Mat();
    matHierarchy = new Mat();
    // NetworkTable table = NetworkTable.getTable("SmartDashboard");
    try {
      videoSource.setDefaultProperties();
      // time to actually process the acquired images
      processImage();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      videoSource.close();
    }
  }

  @Override
  public void stopTracker() {
    stopTracker = true;
  }

  /**
   *
   * reads an image from a live image capture and outputs information to the
   * SmartDashboard or a file
   */
  public void processImage() {
    // ImagePanel panel = ImagePanel.createDisplayWindow();
    // ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
    GripPipelineOne grip = new GripPipelineOne();
    // frame counter
    frameCount = 0;
    timeStart = System.currentTimeMillis();
    while (!stopTracker) {
      // contours.clear();
      // capture from the axis camera
      boolean imageRead = videoSource.read(matOriginal);
      if (!imageRead) {
        break;
      }

      // Grip
      grip.process(matOriginal);
      ArrayList<MatOfPoint> contours = grip.filterContoursOutput();
      if (contours.size() == 2) {
        MatOfPoint mop1 = contours.get(0);
        Rect rec1 = Imgproc.boundingRect(mop1);
        MatOfPoint mop2 = contours.get(1);
        Rect rec2 = Imgproc.boundingRect(mop2);
        int x = Math.min(rec1.x, rec2.x);
        int y = Math.min(rec1.y, rec2.y);
        int width = Math.max(rec1.x + rec1.width, rec2.x + rec2.width) - x;
        int height = Math.max(rec1.y + rec1.height, rec2.y + rec2.height) - y;
        Rect recCombine = new Rect(x, y, width, height);

        double[] distAzim = computeDistanceAzimuthNew(recCombine);
        double distance = distAzim[0];
        double azimuth = distAzim[1];
        setTargetInfo(recCombine, distance, azimuth, mop1);
        if (verboseLogging) {
          logRect(recCombine, distance, azimuth, mop1, logPrefix(timeStart, System.currentTimeMillis(), frameCount));
          // logRect(rec, logPrefix(before, System.currentTimeMillis(),
          // FrameCount));
        }
        Imgproc.drawContours(matOriginal, contours, -1, BLUE);
        Core.rectangle(matOriginal, new Point(recCombine.x, recCombine.y),
            new Point(recCombine.x + recCombine.width, recCombine.y + recCombine.height), new Scalar(0, 255, 0), 5);
      } else {
        targetInfo = targetInfoNone;
        if (verboseLogging) {
          System.out.println(
              logPrefix(timeStart, System.currentTimeMillis(), frameCount) + " found " + contours.size() + " contours");
        }
      }
      // output an image for debugging
      // TODO FIXME
      // Imgcodecs.imwrite("output.png", matOriginal);
      if (imagePanel != null) {
        imagePanel.drawNewImage(matOriginal);
      }
      frameCount++;
    }

  }

  /**
   *
   * reads an image from a live image capture and outputs information to the
   * SmartDashboard or a file
   */
  public void processImageTowerTracker() {
    // ImagePanel panel = ImagePanel.createDisplayWindow();
    ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
    // frame counter
    frameCount = 0;
    timeStart = System.currentTimeMillis();
    while (!stopTracker) {
      contours.clear();
      // capture from the axis camera
      boolean imageRead = videoSource.read(matOriginal);
      if (!imageRead) {
        break;
      }
      // captures from a static file for testing
      // matOriginal = Imgcodecs.imread("someFile.png");
      Imgproc.cvtColor(matOriginal, matHSV, Imgproc.COLOR_BGR2HSV);
      Core.inRange(matHSV, LOWER_BOUNDS, UPPER_BOUNDS, matThresh);
      Imgproc.findContours(matThresh, contours, matHierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
      // make sure the contours that are detected are at least 20x20
      // pixels with an area of 400 and an aspect ratio greater than 1 and less
      // than 2.5
      ArrayList<Double> contourScore = new ArrayList<Double>(contours.size());
      MatOfPoint maxMop = null;
      double maxScore = 0;
      for (Iterator<MatOfPoint> iterator = contours.iterator(); iterator.hasNext();) {
        MatOfPoint matOfPoint = iterator.next();
        double score = 0;
        // Remove contours with width or height less than 25 pixels
        Rect rec = Imgproc.boundingRect(matOfPoint);
        if (rec.height < 25 || rec.width < 25) {
          // iterator.remove();
          contourScore.add(0.0);
          continue;
        } else {
          score += 100;
        }
        // Remove contours with aspect ratio less than one or greater than 2.5
        float aspect = (float) rec.width / (float) rec.height;
        if (aspect < 1.0 || aspect > 2.5) {
          // iterator.remove();
          contourScore.add(0.0);
          continue;
        } else {
          if (aspect < 1.6) {
            double a = (1.6 - aspect) / (1.6 - 1);
            score += 100 * (1 - a);
          } else {
            double a = (aspect - 1.6) / (2.5 - 1.6);
            score += 100 * (1 - a);
          }
        }
        // Remove contours that have area greater than 50% of bounding rectangle
        // area
        double contourArea = Imgproc.contourArea(matOfPoint);
        if (contourArea > 0.5 * rec.height * rec.width) {
          // iterator.remove();
          contourScore.add(0.0);
          continue;
        } else {
          score += 100;
        }
        // Remove contours with moment of inertia less than 0.5 (0.82 seems to
        // be about right)
        Moments m = Imgproc.moments(matOfPoint);
        double momentOfInertia = m.get_nu02() + m.get_nu20();
        if (momentOfInertia < /* 0.5 */ 0.75) {
          // iterator.remove();
          contourScore.add(0.0);
          continue;
        } else {
          score += 100;
        }
        contourScore.add(score);
        // TODO score each contour based on how close it is to ideal, then
        // choose the best one
        if (score > maxScore) {
          maxScore = score;
          maxMop = matOfPoint;
        }
      }
      // if there is only 1 target, then we have found the target we want
      if (contours.size() >= 1 && maxMop != null) {
        // Rect rec = Imgproc.boundingRect(contours.get(0));
        int contourIdx = 0;
        // if (contours.size() > 1) {
        // System.out.println("Warning: more than one contour!");
        // }
        for (MatOfPoint mop : contours) {
          if (!mop.equals(maxMop)) {
            continue;
          }
          // Moments m = Imgproc.moments(mop);
          Rect rec = Imgproc.boundingRect(mop);
          // TODO FIXME Imgproc.rectangle(matOriginal, rec.br(), rec.tl(),
          // BLACK);
          // drawing info on target
          // Point center = new Point(rec.br().x - rec.width / 2 - 15,
          // rec.br().y - rec.height / 2);
          // Point centerw = new Point(rec.br().x - rec.width / 2 - 15,
          // rec.br().y - rec.height / 2 - 20);
          // TODO FIXME
          // Imgproc.putText(matOriginal, "" + (int) distance, center,
          // Core.FONT_HERSHEY_PLAIN, 1, BLACK);
          // Imgproc.putText(matOriginal, "" + (int) azimuth, centerw,
          // Core.FONT_HERSHEY_PLAIN, 1, BLACK);
          double[] distAzim = computeDistanceAzimuthNew(rec);
          double distance = distAzim[0];
          double azimuth = distAzim[1];
          setTargetInfo(rec, distance, azimuth, mop);
          if (verboseLogging) {
            logRect(rec, distance, azimuth, mop, logPrefix(timeStart, System.currentTimeMillis(), frameCount));
            // logRect(rec, logPrefix(before, System.currentTimeMillis(),
            // FrameCount));
          }
          Imgproc.drawContours(matOriginal, contours, contourIdx, BLUE);
          Core.rectangle(matOriginal, new Point(rec.x, rec.y), new Point(rec.x + rec.width, rec.y + rec.height),
              new Scalar(0, 255, 0), 5);
          contourIdx++;
        }
      } else {
        targetInfo = targetInfoNone;
        if (verboseLogging) {
          System.out.println(
              logPrefix(timeStart, System.currentTimeMillis(), frameCount) + " found " + contours.size() + " contours");
        }
      }
      // output an image for debugging
      // TODO FIXME
      // Imgcodecs.imwrite("output.png", matOriginal);
      if (imagePanel != null) {
        imagePanel.drawNewImage(matOriginal);
      }
      frameCount++;
    }
  }

  @Override
  public void setVerbose(boolean verbose) {
    this.verboseLogging = verbose;
  }

  public double[] computeDistanceAzimuthOriginal(Rect rec) {
    // "fun" math brought to you by miss daisy (team 341)!
    double y = rec.br().y + rec.height / 2;
    y = -(2 * (y / matOriginal.height()) - 1);
    double distance = (TOP_TARGET_HEIGHT - TOP_CAMERA_HEIGHT)
        / Math.tan((y * VERTICAL_FOV / 2.0 + CAMERA_ANGLE) * Math.PI / 180);
    // angle to target...would not rely on this
    double targetX = rec.tl().x + rec.width / 2;
    targetX = 2 * (targetX / matOriginal.width()) - 1;
    double azimuth = normalize360(targetX * HORIZONTAL_FOV / 2.0 + 0);
    return new double[] { distance, azimuth };
  }

  public double[] computeDistanceAzimuthNew(Rect rec) {
    double pixelFOV = matOriginal.width();
    double targetFeet = 20.0 / 12.0;
    double diagonalFOVDegrees = 68.5; // 68.5 deg for Microsoft LifeCam HD-3000
    diagonalFOVDegrees /= 1.5; // Empirically determined; approximate
    // d = Tft*FOVpixel/(2*Tpixel*tanΘ);
    double distance = targetFeet * pixelFOV / (2 * rec.width * Math.tan(Math.toRadians(diagonalFOVDegrees / 2)));
    double targetCx = rec.x + rec.width / 2;
    double width = (targetCx - pixelFOV / 2) * targetFeet / rec.width;
    double azimuth = Math.toDegrees(Math.atan2(width, distance));
    return new double[] { distance, azimuth };
  }

  @Override
  public double getFramesPerSec() {
    if (timeStart < 0) {
      return 0;
    }
    return (frameCount + 1) / ((System.currentTimeMillis() - timeStart) / 1000.0);
  }

  public String logPrefix(long timeBefore, long timeAfter, long frameCount) {
    double elapsedTimeSec = (timeAfter - timeBefore) / 1000.0;
    double fps = (frameCount + 1) / elapsedTimeSec;
    return "[iter=" + frameCount + " time=" + elapsedTimeSec + String.format(" fps=%.2f", fps) + "]";
  }

  public void logRect(Rect rec, double distance, double azimuth, MatOfPoint mop, String prefix) {
    Moments m = Imgproc.moments(mop);
    double momentOfInertia = m.get_nu02() + m.get_nu20();
    // System.out.println("Moments: m00 " + m.get_m00() + " m01 " + m.get_m01()
    // + " m02 " + m.get_m02() + " m03 "
    // + m.get_m03() + " m10 " + m.get_m10() + " m11 " + m.get_m11() + " m12 " +
    // m.get_m12() + " m20 " + m.get_m20()
    // + " m21 " + m.get_m21() + " m30 " + m.get_m30());
    // System.out.println("Moments: mu02 " + m.get_mu02() + " mu03 " +
    // m.get_mu03() + " mu11 " + m.get_mu11() + " mu12 "
    // + m.get_mu12() + " mu20 " + m.get_mu20() + " mu21 " + m.get_mu21() + "
    // mu30 " + m.get_mu30());
    // System.out.println("Moments: nu02 " + m.get_nu02() + " nu03 " +
    // m.get_nu03() + " nu11 " + m.get_nu11() + " nu12 "
    // + m.get_nu12() + " nu20 " + m.get_nu20() + " nu21 " + m.get_nu21() + "
    // nu30 " + m.get_nu30());
    // System.out.println("Centroid cx " + m.get_m10() / m.get_m00() + " cy " +
    // m.get_m01() / m.get_m00()
    // + " moment-of-inertia " + (m.get_nu02() + m.get_nu20()));
    double area = rec.height * rec.width;
    double contourArea = Imgproc.contourArea(mop);
    double aspectRatio = (float) rec.width / rec.height;
    System.out.println(prefix + String.format(" Distance %.2f azimuth %.2f", distance, azimuth) + " center x "
        + 0.5 * (rec.tl().x + rec.br().x) + " center y " + 0.5 * (rec.tl().y + rec.br().y) + " area " + area
        + " ctr-area " + contourArea + String.format(" area-ratio %.4f moment-of-inertia %.4f aspect ratio %.3f ",
            contourArea / area, momentOfInertia, aspectRatio));
  }

  public void logRect(Rect rec, String prefix) {
    double[] distAzimOrig = computeDistanceAzimuthOriginal(rec);
    double[] distAzimNew = computeDistanceAzimuthNew(rec);
    System.out.println(prefix + String.format("odist %.2f azim %.2f ", distAzimOrig[0], distAzimOrig[1])
        + String.format("ndist %.2f azim %.2f ", distAzimNew[0], distAzimNew[1]) + " cx "
        + 0.5 * (rec.tl().x + rec.br().x) + " cy " + 0.5 * (rec.tl().y + rec.br().y) + " area " + rec.height * rec.width
        + " aspect ratio " + (float) rec.width / rec.height);
  }

  private void setTargetInfo(Rect rec, double distance, double azimuth, MatOfPoint mop) {
    Moments m = Imgproc.moments(mop);
    double momentOfInertia = m.get_nu02() + m.get_nu20();
    double area = rec.height * rec.width;
    double contourArea = Imgproc.contourArea(mop);
    double aspectRatio = (float) rec.width / rec.height;
    double cx = 0.5 * (rec.tl().x + rec.br().x);
    double cy = 0.5 * (rec.tl().y + rec.br().y);
    targetInfo = new TargetInfo(distance, azimuth, cx, cy, area, contourArea, contourArea / area, momentOfInertia,
        aspectRatio);
  }

  @Override
  public TargetInfo getTargetInfo() {
    return targetInfo;
  }

  /**
   * @param angle
   *          a nonnormalized angle
   */
  public double normalize360(double angle) {
    while (angle >= 360.0) {
      angle -= 360.0;
    }
    while (angle < 0.0) {
      angle += 360.0;
    }
    return angle;
  }
}
