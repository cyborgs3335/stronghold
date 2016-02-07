package org.usfirst.frc.team3335.robot.commands;

import org.usfirst.frc.team3335.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class StopIntake extends Command {
  private boolean finished;

  public StopIntake(boolean finished) {
    // TODO Auto-generated constructor stub
    requires(Robot.intake);
    this.finished = finished;
  }

  @Override
  protected void initialize() {
    Robot.intake.intializeCounter();
  }

  @Override
  protected void execute() {
    // TODO Auto-generated method stub
  }

  @Override
  protected boolean isFinished() {
    if (!finished) {
      finished = Robot.intake.isSwitchSet();
    }
    return finished;
  }

  @Override
  protected void end() {
    Robot.intake.stop();
  }

  @Override
  protected void interrupted() {
    end();
  }

}
