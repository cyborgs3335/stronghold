package org.usfirst.frc.team3335.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * The main autonomous command to pickup and deliver the soda to the box.
 */
public class Autonomous extends CommandGroup {
  public Autonomous() {
    this(true);
  }

  public Autonomous(boolean runAutonomous) {
    if (!runAutonomous) {
      return;
    }
    // Lower arm to reset position, then fully raise
    // addSequential(new MoveArm(Arm.Direction.DOWN, 0));
    // addSequential(new MoveArm(Arm.Direction.UP, Arm.MAX_POSITION, 1));
    // addSequential(new MoveArm(Arm.Direction.UP, 120, 1));
    // addSequential(new TimeDelay(), 0.5);
    // Drive straight
    addSequential(new DriveStraightSimple(2.5));
    // addSequential(new SetDistanceToBox(0.10));
    // // addSequential(new DriveStraight(4)); // Use Encoders if ultrasonic is
    // broken
    // addSequential(new SetDistanceToBox(0.60));
    // // addSequential(new DriveStraight(-2)); // Use Encoders if ultrasonic is
    // broken

    // Test for low bar: drive straight for 1 sec, turn right(?) 15 degrees,
    // drive
    // straight for 1 sec
    // addSequential(new AutonomousDriveStraight(1));
    // addSequential(new AutonomousDriveTurn(15, 2));
    // addSequential(new AutonomousDriveStraight(1));
  }
}
