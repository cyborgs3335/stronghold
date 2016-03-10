package org.usfirst.frc.team3335.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;

  //
  // ===== Competition Robot =====
  //
  // Drive train motors for competition robot
  // public static final int DRIVE_TRAIN_FRONT_LEFT_MOTOR = 1;
  // public static final int DRIVE_TRAIN_BACK_LEFT_MOTOR = 7;
  // public static final int DRIVE_TRAIN_FRONT_RIGHT_MOTOR = 8;
  // public static final int DRIVE_TRAIN_BACK_RIGHT_MOTOR = 5;

  // Other motors for competition robot
  // public static final int ARM_MOTOR = 6;
  // public static final int FLYWHEEL_MOTOR = 2;
  // public static final int HOOD_MOTOR = 4;
  // public static final int INTAKE_MOTOR = 9;
  // public static final int TURRET_MOTOR = 3;

  //
  // ===== Practice Robot =====
  //
  // Drive train motors for practice robot
  public static final int DRIVE_TRAIN_FRONT_LEFT_MOTOR = 6;
  public static final int DRIVE_TRAIN_BACK_LEFT_MOTOR = 3;
  public static final int DRIVE_TRAIN_FRONT_RIGHT_MOTOR = 4;
  public static final int DRIVE_TRAIN_BACK_RIGHT_MOTOR = 1;

  // Other motors for practice robot
  public static final int ARM_MOTOR = 2;
  public static final int FLYWHEEL_MOTOR = 7;
  public static final int HOOD_MOTOR = 9;
  public static final int INTAKE_MOTOR = 5;
  public static final int TURRET_MOTOR = 8;

  // Digital inputs ports for practice robot
  public static final int ARM_ENCODER_A = 9;
  public static final int ARM_ENCODER_B = 7;
  public static final int FLYWHEEL_ENCODER_A = 15; // was 4???
  public static final int FLYWHEEL_ENCODER_B = 2;
  public static final int HOOD_ENCODER_A = 14; // 4
  public static final int HOOD_ENCODER_B = 12; // 2
  public static final int HOOD_SWITCH = 4; // temporarily???
  public static final int INTAKE_SWITCH = 6;
  public static final int TURRET_CW_SWITCH = 0;
  public static final int TURRET_CCW_SWITCH = 1;
  public static final int TURRET_CENTER_SWITCH = 16;
  public static final int TURRET_ENCODER_A = 5;
  public static final int TURRET_ENCODER_B = 3;

  // Relay channels for practice robot
  public static final int CAMERA_LIGHT_SWITCH = 0;
}
