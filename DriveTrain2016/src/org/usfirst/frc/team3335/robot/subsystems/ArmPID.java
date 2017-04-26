package org.usfirst.frc.team3335.robot.subsystems;

import org.usfirst.frc.team3335.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class ArmPID extends PIDSubsystem implements LoggableSubsystem {

  private static final double kP = 1;
  private static final double kI = 0;// 0.01;
  private static final double kD = 0;

  private CANTalon motor;
  private Potentiometer pot;

  public ArmPID() {
    this(kP, kI, kD);
  }

  public ArmPID(double p, double i, double d) {
    super(p, i, d);
    this.setAbsoluteTolerance(0.01);
    this.motor = new CANTalon(RobotMap.ARM_MOTOR/* 2 */);
    reset();
    LiveWindow.addActuator("Defensive Arm", "Arm Motor", motor);
    // pot = new AnalogPotentiometer(2, 90);
  }

  public void reset() {
    motor.set(0);
  }

  @Override
  public void log() {
    // SmartDashboard.putNumber("Arm Potentiometer", pot.get());
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
