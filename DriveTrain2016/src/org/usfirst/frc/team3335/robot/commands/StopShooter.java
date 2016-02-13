package org.usfirst.frc.team3335.robot.commands;

import org.usfirst.frc.team3335.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class StopShooter extends Command {
  private boolean finished;

  public StopShooter(boolean finished) {
    requires(Robot.fly);
    this.finished = finished;
  }

  @Override
  protected void initialize() {
    Robot.fly.intializeCounter();
  }

  @Override
  protected void execute() {
    // TODO Auto-generated method stub
  }

  @Override
  protected boolean isFinished() {
    if (!finished) {
      finished = Robot.fly.isSwitchSet();
    }
    return finished;
  }

  @Override
  protected void end() {
    Robot.fly.stop();
  }

  @Override
  protected void interrupted() {
    end();
  }

}
