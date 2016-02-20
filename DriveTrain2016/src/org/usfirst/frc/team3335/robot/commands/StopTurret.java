package org.usfirst.frc.team3335.robot.commands;

import org.usfirst.frc.team3335.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class StopTurret extends Command {
  private boolean finished;

  public StopTurret(boolean finished) {
    requires(Robot.turret);
    this.finished = finished;
  }

  @Override
  protected void initialize() {
    Robot.turret.intializeCWCounter();
    Robot.turret.intializeCCWCounter();
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
    Robot.turret.stop();
  }

  @Override
  protected void interrupted() {
    end();
  }

}
