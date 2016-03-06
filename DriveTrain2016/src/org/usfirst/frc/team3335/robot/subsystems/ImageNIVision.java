package org.usfirst.frc.team3335.robot.subsystems;

import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ImageNIVision extends Subsystem implements LoggableSubsystem {
  private int session;
  private Image frame;
  private CameraServer server;

  /**
   * Simple vision
   *
   * @param cameraName
   *          name of camera, as found on roborio web page; e.g., "cam0"
   */
  public ImageNIVision(String cameraName) {

    // frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
    //
    // // the camera name (ex "cam0") can be found through the roborio web
    // // interface
    // session = NIVision.IMAQdxOpenCamera("cam0",
    // NIVision.IMAQdxCameraControlMode.CameraControlModeController);
    // NIVision.IMAQdxConfigureGrab(session);

    server = CameraServer.getInstance();
    server.setQuality(50);
    // the camera name (ex "cam0") can be found through the roborio web
    // interface
    server.startAutomaticCapture(cameraName);
  }

  // public void init() {
  // NIVision.IMAQdxStartAcquisition(session);
  //
  // }
  //
  // public void processImage() {
  //
  // /**
  // * grab an image, draw the circle, and provide it for the camera server
  // * which will in turn send it to the dashboard.
  // */
  // NIVision.Rect rect = new NIVision.Rect(10, 10, 100, 100);
  //
  // NIVision.IMAQdxGrab(session, frame, 1);
  // NIVision.imaqDrawShapeOnImage(frame, frame, rect, DrawMode.DRAW_VALUE,
  // ShapeMode.SHAPE_OVAL, 0.0f);
  //
  // CameraServer.getInstance().setImage(frame);
  //
  // }
  //
  // public void stop() {
  // NIVision.IMAQdxStopAcquisition(session);
  // }

  @Override
  protected void initDefaultCommand() {
    // No default command
  }

  @Override
  public void log() {
    // Nothing to log right now
  }
}
