package org.usfirst.frc.team3335.robot.commands;

import org.usfirst.frc.team3335.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeBoulder extends Command {

  private final double motorSpeed;

  /**
   * Constructor specifying motor speed to use, limited to range of [-1:1].
   *
   * @param motorSpeed
   *          speed of motor for intake
   */
  public IntakeBoulder(double motorSpeed) {
    requires(Robot.intake);
    this.motorSpeed = motorSpeed;
  }

  @Override
  protected void initialize() {
    Robot.intake.intializeCounter();
  }

  @Override
  protected void execute() {
    Robot.intake.start(motorSpeed);
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
