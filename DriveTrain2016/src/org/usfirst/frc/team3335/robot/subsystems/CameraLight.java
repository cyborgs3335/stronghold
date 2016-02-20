package org.usfirst.frc.team3335.robot.subsystems;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;

public class CameraLight extends Subsystem implements LoggableSubsystem {
  private Relay relay;

  public CameraLight() {
    super();
    relay = new Relay(1);
    relay.set(Relay.Value.kOff);
  }

  public void turnOn() {
    relay.set(Relay.Value.kOn);
  }

  public void turnOff() {
    relay.set(Relay.Value.kOff);
  }

  @Override
  public void log() {
    // TODO Auto-generated method stub

  }

  @Override
  protected void initDefaultCommand() {
    // TODO Auto-generated method stub

  }

}
