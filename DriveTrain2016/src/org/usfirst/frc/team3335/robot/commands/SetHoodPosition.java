package org.usfirst.frc.team3335.robot.commands;

import org.usfirst.frc.team3335.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SetHoodPosition extends Command {

  private double position;

  public SetHoodPosition(double position) {
    this.position = position;
    requires(Robot.hood);
  }

  @Override
  protected void initialize() {
    Robot.hood.enable();
    Robot.hood.setSetpoint(position);
  }

  @Override
  protected void execute() {
    // TODO Auto-generated method stub
  }

  @Override
  protected boolean isFinished() {
    return Robot.hood.onTarget();
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
