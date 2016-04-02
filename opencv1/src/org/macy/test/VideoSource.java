package org.macy.test;

import java.util.logging.Logger;

import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;
//import org.opencv.videoio.VideoCapture;

public class VideoSource {
  private static final Logger LOG = Logger.getLogger(VideoSource.class.getName());

  // From /usr/include/opencv2/highgui/highgui_c.h:
  public static final int CV_CAP_PROP_FPS = 5;
  public static final int CV_CAP_PROP_BRIGHTNESS = 10;
  public static final int CV_CAP_PROP_CONTRAST = 11;
  public static final int CV_CAP_PROP_SATURATION = 12;
  public static final int CV_CAP_PROP_HUE = 13;

  private static final int DEFAULT_CAMERA_DEVICE_ID = 0;

  // Video properties
  private double videoBrightnessDefault = 0.01;// 0.1;
  private double videoContrastDefault = 0.5; // 0.0;
  private double videoSaturationDefault = 1.0;
  private double videoHueDefault = 0.5;
  private double videoFpsDefault = 30.0;

  private final VideoCapture videoCapture;

  public VideoSource() {
    this(DEFAULT_CAMERA_DEVICE_ID);
  }

  public VideoSource(int cameraDeviceId) {
    // opens up the camera stream and tries to load it
    videoCapture = new VideoCapture();
    // replaces the ##.## with your team number
    // videoCapture.open("http://10.33.35.11/mjpg/video.mjpg");
    // opens default camera on device
    videoCapture.open(cameraDeviceId);
    // Example
    // cap.open("http://10.30.19.11/mjpg/video.mjpg");
    // wait until it is opened
    int openTry = 0;
    while (!videoCapture.isOpened() && openTry < 10) {
      try {
        Thread.sleep(250);
      } catch (InterruptedException e) {
        // do nothing
        // e.printStackTrace();
      }
      videoCapture.open(cameraDeviceId);
      openTry++;
    }
    if (!videoCapture.isOpened()) {
      throw new IllegalStateException("Unable to open video capture device id " + cameraDeviceId);
    }
  }

  public boolean read(Mat image) {
    return videoCapture.read(image);
  }

  public void close() {
    videoCapture.release();
  }

  public double getProperty(int propertyId) {
    return videoCapture.get(propertyId);
  }

  public boolean setProperty(int propertyId, double value) {
    return videoCapture.set(propertyId, value);
  }

  public void setDefaultProperties() {
    try {
      LOG.info("camera properties: \n" + "  brightness = " + getProperty(CV_CAP_PROP_BRIGHTNESS) + "  contrast = "
          + getProperty(CV_CAP_PROP_CONTRAST) + "  saturation = " + getProperty(CV_CAP_PROP_SATURATION) + "  hue = "
          + getProperty(CV_CAP_PROP_HUE) + "  fps = " + getProperty(CV_CAP_PROP_FPS));
    } catch (Exception e) {
      e.printStackTrace();
    }
    boolean error;
    error = setProperty(CV_CAP_PROP_BRIGHTNESS, videoBrightnessDefault);
    if (error) {
      LOG.warning("Unable to set BRIGHTNESS property!");
    }
    error = setProperty(CV_CAP_PROP_CONTRAST, videoContrastDefault);
    if (error) {
      LOG.warning("Unable to set CONTRAST property!");
    }
    error = setProperty(CV_CAP_PROP_SATURATION, videoSaturationDefault);
    if (error) {
      LOG.warning("Unable to set SATURATION property!");
    }
    error = setProperty(CV_CAP_PROP_HUE, videoHueDefault);
    if (error) {
      LOG.warning("Unable to set HUE property!");
    }
    error = setProperty(CV_CAP_PROP_FPS, videoFpsDefault);
    if (error) {
      LOG.warning("Unable to set FPS property!");
    }
    LOG.info("camera properties: \n" + "  brightness = " + getProperty(CV_CAP_PROP_BRIGHTNESS) + "  contrast = "
        + getProperty(CV_CAP_PROP_CONTRAST) + "  saturation = " + getProperty(CV_CAP_PROP_SATURATION) + "  hue = "
        + getProperty(CV_CAP_PROP_HUE) + "  fps = " + getProperty(CV_CAP_PROP_FPS));
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#finalize()
   */
  @Override
  protected void finalize() throws Throwable {
    if (videoCapture.isOpened()) {
      videoCapture.release();
    }
    super.finalize();
  }
}
