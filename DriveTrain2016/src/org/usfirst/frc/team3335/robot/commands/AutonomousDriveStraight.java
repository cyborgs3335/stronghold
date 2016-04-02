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
public class AutonomousDriveStraight extends Command {
  // private PIDController pid;

  // Scalar for gyro correction
  private final double kp = 0.1; // tested value
  // private final double kp = -0.5;

  private final double driveTime;

  // "Speed"
  // private double valueStartDefault = -0.85; // tested
  private double valueStartDefault = -0.75;
  private double valueDefault = -0.95; // old setting; tested
  // private double valueDefault = -1.0; // full power
  // private double valueDefault = -0.7; // temporary for testing

  // private double rampUpTime = 0.25; // seconds; tested
  private double rampUpTime = 0.50; // seconds

  public AutonomousDriveStraight(double time) {
    requires(Robot.drivetrain);
    this.driveTime = time;
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
    double value = valueDefault;
    double timeRunning = timeSinceInitialized();
    if (timeRunning < rampUpTime) {
      // value = valueStartDefault; // tested
      // ramp up instead
      if (rampUpTime > 0) {
        value = (valueDefault - valueStartDefault) / rampUpTime * timeRunning + valueStartDefault;
      }
    }
    double heading = Robot.drivetrain.getHeading();
    double leftValue = value;
    double rightValue = value;
    if (heading < 0) {
      leftValue = computeScalar2(leftValue, heading);
    } else {
      rightValue = computeScalar2(rightValue, heading);
    }
    Robot.drivetrain.driveScaled(leftValue, rightValue);
  }

  /**
   * Provided input speed and heading, compute adjusted speed.
   *
   * @param speed
   *          speed before adjustment due to gyro heading
   * @param heading
   *          heading from gyro, in degrees
   * @return adjusted speed
   */
  private double computeScalar1(double speed, double heading) {
    double scalar = Math.cos(kp * Math.toRadians(heading));
    return speed * scalar;
  }

  /**
   * Provided input speed and heading, compute adjusted speed.
   *
   * @param speed
   *          speed before adjustment due to gyro heading
   * @param heading
   *          heading from gyro, in degrees
   * @return adjusted speed
   */
  private double computeScalar2(double speed, double heading) {
    double scalar = speed * kp * Math.abs(Math.sin(Math.toRadians(heading)));
    return speed - scalar;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (timeSinceInitialized() > driveTime) {
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
