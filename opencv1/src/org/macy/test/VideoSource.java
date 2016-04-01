package org.macy.test;

import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;
//import org.opencv.videoio.VideoCapture;

public class VideoSource {

  // From /usr/include/opencv2/highgui/highgui_c.h:
  public static final int CV_CAP_PROP_BRIGHTNESS = 10;
  public static final int CV_CAP_PROP_CONTRAST = 11;
  public static final int CV_CAP_PROP_SATURATION = 12;
  public static final int CV_CAP_PROP_HUE = 13;

  private static final int DEFAULT_CAMERA_DEVICE_ID = 0;

  // Video properties
  private double videoBrightness = 0.01;// 0.1;
  private double videoContrast = 0.5; // 0.0;
  private double videoSaturation = 1.0;
  private double videoHue = 0.5;

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

  public void setVideoProperties() {
    System.out.println("camera properties: \n" + "  brightness = " + videoCapture.get(CV_CAP_PROP_BRIGHTNESS)
        + "  contrast   = " + videoCapture.get(CV_CAP_PROP_CONTRAST) + "  saturation = "
        + videoCapture.get(CV_CAP_PROP_SATURATION) + "  hue        = " + videoCapture.get(CV_CAP_PROP_HUE));
    // try {
    // Thread.sleep(1000);
    // } catch (InterruptedException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    videoCapture.set(CV_CAP_PROP_BRIGHTNESS, videoBrightness);
    videoCapture.set(CV_CAP_PROP_CONTRAST, videoContrast);
    videoCapture.set(CV_CAP_PROP_SATURATION, videoSaturation);
    videoCapture.set(CV_CAP_PROP_HUE, videoHue);
    System.out.println("camera properties: \n" + "  brightness = " + videoCapture.get(CV_CAP_PROP_BRIGHTNESS)
        + "  contrast   = " + videoCapture.get(CV_CAP_PROP_CONTRAST) + "  saturation = "
        + videoCapture.get(CV_CAP_PROP_SATURATION) + "  hue        = " + videoCapture.get(CV_CAP_PROP_HUE));
    // try {
    // Thread.sleep(1000);
    // } catch (InterruptedException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
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
