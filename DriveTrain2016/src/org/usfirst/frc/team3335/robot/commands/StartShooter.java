package org.usfirst.frc.team3335.robot.commands;

import org.usfirst.frc.team3335.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class StartShooter extends Command {
  boolean direction;

  public StartShooter(boolean direction) {
    requires(Robot.fly);
    this.direction = direction;
  }

  @Override
  protected void initialize() {
    // TODO Auto-generated method stub
  }

  @Override
  protected void execute() {
    Robot.fly.start(direction);
  }

  @Override
  protected boolean isFinished() {
    // TODO Auto-generated method stub
    return false;
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
