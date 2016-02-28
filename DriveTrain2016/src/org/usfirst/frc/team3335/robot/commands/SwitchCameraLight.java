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
    if (lightOn) {
      Robot.cameraLight.turnOn();
    } else {
      Robot.cameraLight.turnOff();
    }
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    if (lightOn) {
      Robot.cameraLight.turnOff();
    } else {
      Robot.cameraLight.turnOn();
    }
  }

  @Override
  protected void interrupted() {
    end();
  }

}
