package org.macy.test;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.objdetect.CascadeClassifier;

//
// Detects faces in an image, draws boxes around them, and writes the results
// to "faceDetection.png".
//
public class DetectFaceDemo {
  // private final int CAMERA_DEVICE_ID = 0;
  private final int CAMERA_DEVICE_ID = 1;

  static {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
  }

  private final String imagePath;

  public DetectFaceDemo(String imagePath) {
    this.imagePath = imagePath;
  }

  public void run() {
    System.out.println("\nRunning DetectFaceDemo");

    // Create a face detector from the cascade file in the resources
    // directory.
    System.out.println("xml: " + getClass().getResource("/lbpcascade_frontalface.xml").getPath());
    CascadeClassifier faceDetector = new CascadeClassifier(
        getClass().getResource("/lbpcascade_frontalface.xml").getPath());
    // System.out.println("png: " +
    // getClass().getResource("/lena.png").getPath());
    // Mat image =
    // Highgui.imread(getClass().getResource("/lena.png").getPath());
    System.out.println("image: " + imagePath);
    Mat image = Highgui.imread(imagePath);

    // Detect faces in the image.
    // MatOfRect is a special container class for Rect.
    MatOfRect faceDetections = new MatOfRect();
    faceDetector.detectMultiScale(image, faceDetections);

    System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));

    // Draw a bounding box around each face.
    for (Rect rect : faceDetections.toArray()) {
      Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
          new Scalar(0, 255, 0));
    }

    // Save the visualized detection.
    String filename = "faceDetection.png";
    System.out.println(String.format("Writing %s", filename));
    Highgui.imwrite(filename, image);
  }

  public void runVideo() {
    System.out.println("\nRunning DetectFaceDemo");

    // Create a face detector from the cascade file in the resources
    // directory.
    System.out.println("xml: " + getClass().getResource("/lbpcascade_frontalface.xml").getPath());
    CascadeClassifier faceDetector = new CascadeClassifier(
        getClass().getResource("/lbpcascade_frontalface.xml").getPath());
    // System.out.println("png: " +
    // getClass().getResource("/lena.png").getPath());
    // Mat image =
    // Highgui.imread(getClass().getResource("/lena.png").getPath());
    // System.out.println("image: " + imagePath);
    // Mat image = Highgui.imread(imagePath);
    VideoCapture videoCapture;

    // opens up the camera stream and tries to load it
    videoCapture = new VideoCapture();
    // replaces the ##.## with your team number
    // videoCapture.open("http://10.33.35.11/mjpg/video.mjpg");
    // opens default camera on device
    videoCapture.open(CAMERA_DEVICE_ID);
    // Example
    // cap.open("http://10.30.19.11/mjpg/video.mjpg");
    // wait until it is opened
    while (!videoCapture.isOpened()) {
    }
    // Mat image = new Mat();
    // videoCapture.read(image);
    // time to actually process the acquired images
    processImages(videoCapture, faceDetector);
    // processImage(faceDetector, image);
    videoCapture.release();
  }

  public void processImages(VideoCapture videoCapture, CascadeClassifier faceDetector) {
    // Create named window
    ImagePanel panel = ImagePanel.createDisplayWindow();

    Mat image = new Mat();
    int frameCount = 0;
    long timeBefore = System.currentTimeMillis();
    while (frameCount < 1000000) {
      videoCapture.read(image);
      processImage(faceDetector, image);
      log(timeBefore, System.currentTimeMillis(), frameCount, "finished processing frame");
      panel.drawNewImage(image);
      frameCount++;
    }
  }

  public void log(long timeBefore, long timeAfter, int frameCount, String message) {
    double elapsedTimeSec = (timeAfter - timeBefore) / 1000.0;
    double fps = (frameCount + 1) / elapsedTimeSec;
    System.out.println("[iter=" + frameCount + " time=" + elapsedTimeSec + " fps=" + fps + "] " + message);
  }

  public void processImage(CascadeClassifier faceDetector, Mat image) {
    // Detect faces in the image.
    // MatOfRect is a special container class for Rect.
    MatOfRect faceDetections = new MatOfRect();
    faceDetector.detectMultiScale(image, faceDetections);

    System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));

    // Draw a bounding box around each face.
    for (Rect rect : faceDetections.toArray()) {
      Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
          new Scalar(0, 255, 0), 5);
    }

    // Save the visualized detection.
    // String filename = "faceDetection.png";
    // System.out.println(String.format("Writing %s", filename));
    // Highgui.imwrite(filename, image);
  }

  public static void main(String[] args) {
    System.out.println("Welcome to OpenCV " + Core.VERSION);
    String imagePath = "resources/lena.png";
    if (args.length == 1) {
      imagePath = args[0];
    }
    new DetectFaceDemo(imagePath).runVideo();
  }

}
