package org.usfirst.frc.team3335.robot.commands;

import org.usfirst.frc.team3335.robot.Robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class ProcessImage extends Command {

	private final NetworkTable grip = NetworkTable.getTable("grip");
	int session;
    Image frame;

	public ProcessImage() {
		requires(Robot.imageProcessor);
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

        // the camera name (ex "cam0") can be found through the roborio web interface
        session = NIVision.IMAQdxOpenCamera("cam0",
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        NIVision.IMAQdxConfigureGrab(session);
	}

	@Override
	protected void initialize() {
		// Do nothing
	}

	@Override
	protected void execute() {
		// Get published values from GRIP using NetworkTables
		double[] areas = grip.getNumberArray("myContoursReport", new double[0]);
		for (double area: areas) {
			System.out.println("Got contour with area = " + area);
		}
		if (areas.length > 0) {
			SmartDashboard.putNumber("Image area 0", areas[0]);
		}
		//Return the image shown by the roboRIO
		NIVision.IMAQdxStartAcquisition(session);

		/**
		 * grab an image, draw the circle, and provide it for the camera server
		 * which will in turn send it to the dashboard.
		 */
		NIVision.Rect rect = new NIVision.Rect(10, 10, 100, 100);

		NIVision.IMAQdxGrab(session, frame, 1);
		NIVision.imaqDrawShapeOnImage(frame, frame, rect,
				DrawMode.DRAW_VALUE, ShapeMode.SHAPE_OVAL, 0.0f);

		CameraServer.getInstance().setImage(frame);

		/** robot code here! **/
		Timer.delay(0.005);		// wait for a motor update time
		NIVision.IMAQdxStopAcquisition(session);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.imageProcessor.terminate();
	}

	@Override
	protected void interrupted() {
		end();
	}

}
