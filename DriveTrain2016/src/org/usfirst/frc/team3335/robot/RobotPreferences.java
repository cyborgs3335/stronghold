package org.usfirst.frc.team3335.robot;

import edu.wpi.first.wpilibj.Preferences;

public class RobotPreferences {
	private Preferences prefs;
	private double joystickScalar;
	private double joystickPowerScalar;
	private double accelLimit;
	public RobotPreferences() {
		prefs = Preferences.getInstance();
		joystickScalar = prefs.getDouble("JoystickScalar", -0.8);
		joystickPowerScalar = prefs.getDouble("JoystickPowerScalar", 2);
		accelLimit = prefs.getDouble("AccelLimit", 0.1);
	}

	public double getJoystickScalar() {
		return joystickScalar;
	}
	
	public double getJoystickPowerScalar() {
		return joystickPowerScalar;
	}
	
	public double getAccelLimit() {
		return accelLimit;
	}
}
