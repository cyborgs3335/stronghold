package org.usfirst.frc.team3335.robot.subsystems;

import org.usfirst.frc.team3335.robot.commands.SetHoodPosition;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class HoodPID extends PIDSubsystem implements LoggableSubsystem {

  public static enum Direction {
    UP, DOWN
  }

  private static final double MIN_POSITION = 0;
  private static final double MAX_POSITION = 90;

  public static final double FULLY_LOWERED = MIN_POSITION;

  private static final double kP = 1;
  private static final double kI = 0;// 0.01;
  private static final double kD = 0;

  private SpeedController motor;
  private Encoder encoder;
  private Potentiometer pot;

  public HoodPID() {
    this(kP, kI, kD);
  }

  public HoodPID(double p, double i, double d) {
    super(p, i, d);
    this.setAbsoluteTolerance(0.5);
    this.motor = new CANTalon(9);
    reset();
    this.encoder = new Encoder(2, 3, false, Encoder.EncodingType.k4X);
    // pot = new AnalogPotentiometer(2, 90);

    this.setInputRange(MIN_POSITION, MAX_POSITION);
    LiveWindow.addActuator("Hood", "Hood Motor", (CANTalon) motor);
    LiveWindow.addActuator("Hood", "Hood Encoder", encoder);
  }

  public void reset() {
    motor.set(0);
  }

  @Override
  public void log() {
    // SmartDashboard.putNumber("Hood Potentiometer", pot.get());
    SmartDashboard.putNumber("Hood Position", getAngularPosition());
  }

  @Override
  protected double returnPIDInput() {
    return getAngularPosition();
    // return 90;
    // return pot.get();
  }

  @Override
  protected void usePIDOutput(double output) {
    motor.set(output);
  }

  @Override
  protected void initDefaultCommand() {
    setDefaultCommand(new SetHoodPosition(0));
  }

  private double getAngularPosition() {
    return 360f * encoder.getDistance() / 4096;
  }
}
