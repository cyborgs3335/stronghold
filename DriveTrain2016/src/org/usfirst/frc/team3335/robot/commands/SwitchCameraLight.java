package org.usfirst.frc.team3335.robot.commands;

import org.usfirst.frc.team3335.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SwitchCameraLight extends Command {
  private boolean lightOn;

  public SwitchCameraLight(boolean lightOn) {
    this.lightOn = lightOn;
    requires(Robot.cameraLight);
  }

  @Override
  protected void initialize() {
    if (lightOn) {
      Robot.cameraLight.turnOn();
    } else {
      Robot.cameraLight.turnOff();
    }

  }

  @Override
  protected void execute() {
    // TODO Auto-generated method stub

  }

  @Override
  protected boolean isFinished() {
    return true;
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
