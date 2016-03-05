package org.usfirst.frc.team3335.robot;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotPreferences {

  private Preferences prefs;

  public final static String JOYSTICK_SCALAR = "JoystickScaler";

  public final static String JOYSTICK_POWER_SCALAR = "JoystickPowerScaler";

  public final static String ACCEL_LIMIT = "AccelLimit";

  public final static String JOYSTICK_DRIVER_PORT = "JoystickDriverPort";

  public final static String JOYSTICK_SHOOTER_PORT = "JoystickShooterPort";

  private double joystickScalar;

  private double joystickPowerScalar;

  private double accelLimit;

  private int joystickDriverPort;

  private int joystickShooterPort;

  public RobotPreferences() {
    prefs = Preferences.getInstance();

    joystickScalar = prefs.getDouble(JOYSTICK_SCALAR, -0.9);
    if (prefs.containsKey(JOYSTICK_SCALAR)) {
      prefs.putDouble(JOYSTICK_SCALAR, joystickScalar);
    }

    joystickPowerScalar = prefs.getDouble(JOYSTICK_POWER_SCALAR, 2);
    if (prefs.containsKey(JOYSTICK_POWER_SCALAR)) {
      prefs.putDouble(JOYSTICK_POWER_SCALAR, joystickPowerScalar);
    }

    accelLimit = prefs.getDouble(ACCEL_LIMIT, 0.5);
    if (prefs.containsKey(ACCEL_LIMIT)) {
      prefs.putDouble(ACCEL_LIMIT, accelLimit);
    }

    joystickDriverPort = prefs.getInt(JOYSTICK_DRIVER_PORT, 0);
    if (prefs.containsKey(JOYSTICK_DRIVER_PORT)) {
      prefs.putInt(JOYSTICK_DRIVER_PORT, joystickDriverPort);
    }

    joystickShooterPort = prefs.getInt(JOYSTICK_SHOOTER_PORT, 2);
    if (prefs.containsKey(JOYSTICK_SHOOTER_PORT)) {
      prefs.putInt(JOYSTICK_SHOOTER_PORT, joystickShooterPort);
    }

    // Put preferences on Smart Dashboard
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

  public int getJoystickDriverPort() {
    return joystickDriverPort;
  }

  public int getJoystickShooterPort() {
    return joystickShooterPort;
  }
}
