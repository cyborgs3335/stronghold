package org.usfirst.frc.team3335.robot.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArmPID extends PIDSubsystem {

  private static final double kP = 4;
  private static final double kI = 0.07;
  private static final double kD = 0;

  private SpeedController motor;
  private Potentiometer pot;

  public ArmPID() {
    this(kP, kI, kD);
  }

  public ArmPID(double p, double i, double d) {
    super(p, i, d);
    this.setAbsoluteTolerance(0.01);
    this.motor = new CANTalon(1);
    reset();
    // pot = new AnalogPotentiometer(2, 90);
  }

  public void reset() {
    motor.set(0);
  }

  public void log() {
    SmartDashboard.putNumber("Arm Potentiometer", pot.get());
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
