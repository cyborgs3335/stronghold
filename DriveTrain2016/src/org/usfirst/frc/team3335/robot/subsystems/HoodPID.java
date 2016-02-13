package org.usfirst.frc.team3335.robot.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class HoodPID extends PIDSubsystem {

  private static final double kP = 4;
  private static final double kI = 0.07;
  private static final double kD = 0;

  private SpeedController motor;
  private Potentiometer pot;

  public HoodPID() {
    this(kP, kI, kD);
  }

  public HoodPID(double p, double i, double d) {
    super(p, i, d);
    this.setAbsoluteTolerance(0.01);
    this.motor = new CANTalon(9);
    reset();
    // pot = new AnalogPotentiometer(2, 90);
  }

  public void reset() {
    motor.set(0);
  }

  public void log() {
    SmartDashboard.putNumber("Hood Potentiometer", pot.get());
  }

  @Override
  protected double returnPIDInput() {
    return 90;
    // return pot.get();
  }

  @Override
  protected void usePIDOutput(double output) {
    motor.set(output);
  }

  @Override
  protected void initDefaultCommand() {
  }

}
