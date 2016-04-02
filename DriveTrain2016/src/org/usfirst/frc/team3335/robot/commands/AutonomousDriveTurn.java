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
public class AutonomousDriveTurn extends Command {
  // private PIDController pid;

  private final double turnAngle;

  // "Speed"
  private final double valueDefault = -0.8;

  // Time out
  private final double timeOut;

  /**
   * Constructor specifying angle to turn in degrees
   *
   * @param angle
   *          angle to turn in degrees
   */
  public AutonomousDriveTurn(double angle, double timeOut) {
    requires(Robot.drivetrain);
    this.turnAngle = angle;
    this.timeOut = timeOut;
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
    if (turnAngle >= 0) {
      Robot.drivetrain.driveScaled(-valueDefault, valueDefault);
    } else {
      Robot.drivetrain.driveScaled(valueDefault, -valueDefault);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (timeSinceInitialized() > timeOut) {
      return true;
    }
    double currentHeading = Robot.drivetrain.getHeading();
    if (turnAngle >= 0 && currentHeading > turnAngle) {
      return true;
    } else if (turnAngle < 0 && currentHeading < turnAngle) {
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
