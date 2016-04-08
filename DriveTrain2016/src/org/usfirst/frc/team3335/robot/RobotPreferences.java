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

  public final static String DRIVE_TIMER = "DriveTimer";

  public final static String HOOD_DRIVE_SCALAR = "HoodDriveScalar";

  public final static String HOOD_DRIVE_TARGET_POSITION = "HoodDriveTargetPosition";

  public final static String FULL_SHOOTER_INTAKE_DELAY = "FullShooterIntakeDelay";

  private double joystickScalar;

  private double joystickPowerScalar;

  private double accelLimit;

  private int joystickDriverPort;

  private int joystickShooterPort;

  private double drivetimer;

  private double hoodDriveScalar;

  private double hoodDriveTargetPosition;

  private long fullShooterIntakeDelay;

  public RobotPreferences() {
    prefs = Preferences.getInstance();

    joystickScalar = prefs.getDouble(JOYSTICK_SCALAR, -1); // -0.9 old value
    if (!prefs.containsKey(JOYSTICK_SCALAR)) {
      prefs.putDouble(JOYSTICK_SCALAR, joystickScalar);
    }

    joystickPowerScalar = prefs.getDouble(JOYSTICK_POWER_SCALAR, 2);
    if (!prefs.containsKey(JOYSTICK_POWER_SCALAR)) {
      prefs.putDouble(JOYSTICK_POWER_SCALAR, joystickPowerScalar);
    }

    accelLimit = prefs.getDouble(ACCEL_LIMIT, 0.5);
    if (!prefs.containsKey(ACCEL_LIMIT)) {
      prefs.putDouble(ACCEL_LIMIT, accelLimit);
    }

    joystickDriverPort = prefs.getInt(JOYSTICK_DRIVER_PORT, 0);
    if (!prefs.containsKey(JOYSTICK_DRIVER_PORT)) {
      prefs.putInt(JOYSTICK_DRIVER_PORT, joystickDriverPort);
    }

    joystickShooterPort = prefs.getInt(JOYSTICK_SHOOTER_PORT, 2);
    if (!prefs.containsKey(JOYSTICK_SHOOTER_PORT)) {
      prefs.putInt(JOYSTICK_SHOOTER_PORT, joystickShooterPort);
    }

    drivetimer = prefs.getDouble(DRIVE_TIMER, 4);
    if (!prefs.containsKey(DRIVE_TIMER)) {
      prefs.putDouble(DRIVE_TIMER, drivetimer);
    }

    // is this the correct default????
    hoodDriveScalar = prefs.getDouble(HOOD_DRIVE_SCALAR, 0.3);
    if (!prefs.containsKey(HOOD_DRIVE_SCALAR)) {
      prefs.putDouble(HOOD_DRIVE_SCALAR, hoodDriveScalar);
    }

    // hood takes about 0.5 degrees to fully stop
    hoodDriveTargetPosition = prefs.getDouble(HOOD_DRIVE_TARGET_POSITION, 19.5); // degrees
    if (!prefs.containsKey(HOOD_DRIVE_TARGET_POSITION)) {
      prefs.putDouble(HOOD_DRIVE_TARGET_POSITION, hoodDriveTargetPosition);
    }

    fullShooterIntakeDelay = prefs.getLong(FULL_SHOOTER_INTAKE_DELAY, 1000); // milliseconds
    if (!prefs.containsKey(FULL_SHOOTER_INTAKE_DELAY)) {
      prefs.putLong(FULL_SHOOTER_INTAKE_DELAY, fullShooterIntakeDelay);
    }

    // Put preferences on Smart Dashboard
    SmartDashboard.putNumber("Joystick Scalar", joystickScalar);
    SmartDashboard.putNumber("Joystick Power Scalar", joystickPowerScalar);
    SmartDashboard.putNumber("Acceleration Limit", accelLimit);
    SmartDashboard.putNumber("Joystick Driver Port", joystickDriverPort);
    SmartDashboard.putNumber("Joystick Shooter Port", joystickShooterPort);
    SmartDashboard.putNumber("Drive Timer", drivetimer);
    SmartDashboard.putNumber("Hood Drive Scalar", hoodDriveScalar);
    SmartDashboard.putNumber("Hood Target Position", hoodDriveTargetPosition);
    SmartDashboard.putNumber("Full Shooter Intake Delay (millisec)", fullShooterIntakeDelay);
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

  public double getDriveTimer() {
    return drivetimer;
  }

  public double getHoodDriveScalar() {
    return hoodDriveScalar;
  }

  public double getHoodTargetPosition() {
    return hoodDriveTargetPosition;
  }

  public long getFullShooterIntakeDelay() {
    return fullShooterIntakeDelay;
  }
}
