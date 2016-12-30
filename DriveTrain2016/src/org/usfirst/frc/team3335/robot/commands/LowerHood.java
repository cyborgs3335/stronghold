package org.usfirst.frc.team3335.robot.commands;

import org.usfirst.frc.team3335.robot.Robot;
import org.usfirst.frc.team3335.robot.subsystems.Hood;

import edu.wpi.first.wpilibj.command.Command;

public class LowerHood extends Command {

  private final Hood hood;

  public LowerHood(Hood hood) {
    // TODO Auto-generated constructor stub
    this.hood = hood;
    requires(hood);
  }

  @Override
  protected void initialize() {
    // TODO Auto-generated method stub

  }

  @Override
  protected void execute() {
    // TODO Auto-generated method stub
    double scalar = Robot.robotPreferences.getHoodDriveScalar();
    hood.rotate(-scalar);
  }

  @Override
  protected boolean isFinished() {
    // TODO Auto-generated method stub
    return hood.isSwitchSet();
  }

  @Override
  protected void end() {
    // TODO Auto-generated method stub
    hood.rotate(0);
  }

  @Override
  protected void interrupted() {
    // TODO Auto-generated method stub
    end();
  }

}
