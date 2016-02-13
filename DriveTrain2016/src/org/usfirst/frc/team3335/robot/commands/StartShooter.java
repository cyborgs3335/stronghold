package org.usfirst.frc.team3335.robot.commands;

import org.usfirst.frc.team3335.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class StartShooter extends Command {

  public StartShooter() {
    requires(Robot.fly);
  }

  @Override
  protected void initialize() {
    // TODO Auto-generated method stub
  }

  @Override
  protected void execute() {
    Robot.fly.start();
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
