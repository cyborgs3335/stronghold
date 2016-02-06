package org.usfirst.frc.team3335.robot.subsystems;

import edu.wpi.first.wpilibj.AccumulatorResult;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Turret extends Subsystem {

	private AnalogInput turretEncoder;

	public Turret() {
		turretEncoder = new AnalogInput(0);

		// Let's show everything on the LiveWindow
		LiveWindow.addActuator("Turret", "Turret_Encoder", turretEncoder);
	}

	@Override
	protected void initDefaultCommand() {
	}

	/**
	 * The log method puts interesting information to the SmartDashboard.
	 */
	public void log() {
		SmartDashboard.putNumber("Turret Position", getPosition());
	}

	/**
	 * Return the angular position in degrees.
	 * @return angular position in degrees
	 */
	public float getPosition() {
		AccumulatorResult result = new AccumulatorResult();
		//result.count;
		turretEncoder.getAccumulatorOutput(result);
		return 360f * turretEncoder.getAverageValue()/4096;
	}
}
