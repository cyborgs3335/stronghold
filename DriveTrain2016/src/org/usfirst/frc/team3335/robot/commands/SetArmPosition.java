package org.usfirst.frc.team3335.robot.commands;

import org.usfirst.frc.team3335.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SetArmPosition extends Command {
  private double position;

  public SetArmPosition(double position) {
    this.position = position;
    requires(Robot.armPID);
  }

  @Override
  protected void initialize() {
    Robot.armPID.enable();
    Robot.armPID.setSetpoint(position);

  }

  @Override
  protected void execute() {
    // TODO Auto-generated method stub

  }

  @Override
  protected boolean isFinished() {
    return Robot.armPID.onTarget();
  }

  @Override
  protected void end() {
    // TODO Auto-generated method stub

  }

  @Override
  protected void interrupted() {
    // TODO Auto-generated method stub

  }

}
