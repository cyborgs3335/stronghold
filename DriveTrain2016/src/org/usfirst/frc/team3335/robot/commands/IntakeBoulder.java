package org.usfirst.frc.team3335.robot.commands;

import org.usfirst.frc.team3335.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeBoulder extends Command {

  public IntakeBoulder() {
    requires(Robot.intake);
  }

  @Override
  protected void initialize() {
    Robot.intake.intializeCounter();
  }

  @Override
  protected void execute() {
    Robot.intake.start(true); // forward motor
  }

  @Override
  protected boolean isFinished() {
    return Robot.intake.isSwitchSet();
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
