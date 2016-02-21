package org.usfirst.frc.team3335.robot.commands;

import org.usfirst.frc.team3335.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class StopHood extends Command {
  private boolean finished;

  public StopHood(boolean finished) {
    requires(Robot.hood);
    this.finished = finished;
  }

  @Override
  protected void initialize() {
    // Robot.hood.intializeUpCounter();
    // Robot.hood.intializeDownCounter();
  }

  @Override
  protected void execute() {
    // TODO Auto-generated method stub
  }

  @Override
  protected boolean isFinished() {
    return finished;
  }

  @Override
  protected void end() {
    Robot.hood.stop();
  }

  @Override
  protected void interrupted() {
    end();
  }

}
