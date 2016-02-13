package org.usfirst.frc.team3335.robot;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotPreferences {

  private Preferences prefs;

  public static String JOYSTICK_SCALAR = "JoystickScaler";

  public static String JOYSTICK_POWER_SCALAR = "JoystickPowerScaler";

  public static String ACCEL_LIMIT = "AccelLimit";

  private double joystickScalar;

  private double joystickPowerScalar;

  private double accelLimit;

  public RobotPreferences() {
    prefs = Preferences.getInstance();
    joystickScalar = prefs.getDouble(JOYSTICK_SCALAR, -0.8);
    if (prefs.containsKey(JOYSTICK_SCALAR)) {
      prefs.putDouble(JOYSTICK_SCALAR, joystickScalar);
    }
    joystickPowerScalar = prefs.getDouble(JOYSTICK_POWER_SCALAR, 2);
    accelLimit = prefs.getDouble(ACCEL_LIMIT, 0.5);
    SmartDashboard.putNumber("Joystick Scalar", joystickScalar);
    SmartDashboard.putNumber("Joystick Power Scalar", joystickPowerScalar);
    SmartDashboard.putNumber("Acceleration Limit", accelLimit);
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
