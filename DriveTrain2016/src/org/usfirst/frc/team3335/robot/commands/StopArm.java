package org.usfirst.frc.team3335.robot.commands;

import org.usfirst.frc.team3335.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class StopArm extends Command {
  private boolean finished;

  public StopArm(boolean finished) {
    requires(Robot.arm);
    this.finished = finished;
  }

  @Override
  protected void initialize() {
    // TODO Auto-generated method stub
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
    Robot.arm.stop();
  }

  @Override
  protected void interrupted() {
    end();
  }

}
