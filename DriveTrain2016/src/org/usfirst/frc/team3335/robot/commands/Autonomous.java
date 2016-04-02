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
    addSequential(new DriveStraightSimple());
    // addSequential(new SetDistanceToBox(0.10));
    // // addSequential(new DriveStraight(4)); // Use Encoders if ultrasonic is
    // broken
    // addSequential(new SetDistanceToBox(0.60));
    // // addSequential(new DriveStraight(-2)); // Use Encoders if ultrasonic is
    // broken

    // Test for low bar: drive straight, turn right(?) 15 degrees, drive
    // straight
    // addSequential(new AutonomousDriveStraight(1));
    // addSequential(new AutonomousDriveTurn(15, 1));
    // addSequential(new AutonomousDriveStraight(1));
  }
}
