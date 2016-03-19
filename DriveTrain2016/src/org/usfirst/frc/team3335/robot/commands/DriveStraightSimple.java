/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc.team3335.robot.commands;

import org.usfirst.frc.team3335.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Drive the given distance straight (negative values go backwards). Uses a
 * local PID controller to run a simple PID loop that is only enabled while this
 * command is running. The input is the averaged values of the left and right
 * encoders.
 */
public class DriveStraightSimple extends Command {
  // private PIDController pid;

  private final double kp = 0.1;

  public DriveStraightSimple() {
    requires(Robot.drivetrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    // Get everything in a safe starting state.
    Robot.drivetrain.reset();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // double value = -0.95; // old setting
    double value = -1;
    double heading = Robot.drivetrain.getHeading();
    double leftValue = value;
    double rightValue = value;
    double scalar = kp * Math.cos(Math.toRadians(heading));
    if (heading < 0) {
      leftValue *= scalar;
    } else {
      rightValue *= scalar;
    }
    Robot.drivetrain.driveScaled(leftValue, rightValue);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (timeSinceInitialized() > Robot.robotPreferences.getDriveTimer()) {
      return true;
    }
    return false;

  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    // Stop the wheels
    Robot.drivetrain.drive(0, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
