package org.usfirst.frc.team3335.robot.commands;

import org.usfirst.frc.team3335.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeBoulder extends Command {

  private final double motorSpeed;

  private final boolean overrideSwitch;

  private final long delayTime;

  private long startTime;

  /**
   * Constructor specifying motor speed to use, limited to range of [-1:1].
   *
   * @param motorSpeed
   *          speed of motor for intake
   */
  public IntakeBoulder(double motorSpeed) {
    this(motorSpeed, false);
  }

  public IntakeBoulder(double motorSpeed, boolean overrideSwitch) {
    requires(Robot.intake);
    this.motorSpeed = motorSpeed;
    this.overrideSwitch = overrideSwitch;
    this.delayTime = 0;
  }

  public IntakeBoulder(double motorSpeed, boolean overrideSwitch, long delayTime) {
    requires(Robot.intake);
    this.motorSpeed = motorSpeed;
    this.overrideSwitch = overrideSwitch;
    this.delayTime = delayTime;
  }

  @Override
  protected void initialize() {
    this.startTime = System.currentTimeMillis();
    Robot.intake.intializeCounter();
  }

  @Override
  protected void execute() {
    if (System.currentTimeMillis() - this.startTime < this.delayTime) {
      return;
    }
    Robot.intake.start(motorSpeed);
  }

  @Override
  protected boolean isFinished() {
    if (overrideSwitch) {
      return false;
    }
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
