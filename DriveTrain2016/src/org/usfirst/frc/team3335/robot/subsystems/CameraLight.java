package org.usfirst.frc.team3335.robot.subsystems;

import org.usfirst.frc.team3335.robot.RobotMap;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.command.Subsystem;

public class CameraLight extends Subsystem implements LoggableSubsystem {
  private Relay relay;

  public CameraLight() {
    super();
    relay = new Relay(RobotMap.CAMERA_LIGHT_SWITCH, Direction.kForward);
    relay.set(Relay.Value.kOff);
  }

  public void turnOn() {
    // relay.set(Relay.Value.kOn);
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
