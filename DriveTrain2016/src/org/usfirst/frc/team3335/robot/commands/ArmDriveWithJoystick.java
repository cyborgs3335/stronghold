/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc.team3335.robot.commands;

import org.usfirst.frc.team3335.robot.Robot;
import org.usfirst.frc.team3335.robot.subsystems.Arm;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Have the robot drive tank style using the PS3 Joystick until interrupted.
 */
public class ArmDriveWithJoystick extends Command {

  private final Arm arm;

  public ArmDriveWithJoystick(Arm arm) {
    this.arm = arm;
    requires(arm);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    // System.out.println(this.getClass().getName() + ": initialize");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // System.out.println(this.getClass().getName() + ": execute");
    arm.rotate(Robot.oi.getJoystickShooter().getRawAxis(3)); // TODO Verify if
                                                             // this is correct
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false; // Runs until interrupted
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    arm.rotate(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
