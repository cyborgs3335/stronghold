package org.usfirst.frc.team3335.robot.subsystems;

import org.usfirst.frc.team3335.robot.RobotMap;
import org.usfirst.frc.team3335.robot.commands.StopShooter;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FlyWheel extends Subsystem implements LoggableSubsystem {
  private CANTalon motor;
  private Encoder encoder;
  private final double targetMotorValue;
  // private final boolean useVoltageMode = true;
  private final boolean useVoltageMode = false;
  // private DigitalInput limitSwitch;
  // private Counter counter;

  public FlyWheel() {
    super();
    motor = new CANTalon(RobotMap.FLYWHEEL_MOTOR);
    if (useVoltageMode) {
      motor.changeControlMode(TalonControlMode.Voltage);
      targetMotorValue = 10; // 10 volts
    } else {
      motor.changeControlMode(TalonControlMode.PercentVbus);
      targetMotorValue = 1; // 100 %
    }
    encoder = new Encoder(RobotMap.FLYWHEEL_ENCODER_A, RobotMap.FLYWHEEL_ENCODER_B, false, Encoder.EncodingType.k4X);
    // limitSwitch = new DigitalInput(6);
    // counter = new Counter(limitSwitch);
    motor.set(0);
    // encoder.reset();

    // Let's show everything on the LiveWindow
    // LiveWindow.addActuator("Fly Wheel", "Fly Wheel Motor", motor);
    // LiveWindow.addActuator("Fly Wheel", "Fly Wheel Encoder", encoder);
  }

  @Override
  protected void initDefaultCommand() {
    // TODO Auto-generated method stub
    setDefaultCommand(new StopShooter(false));
  }

  /**
   * Start the flywheel motor
   */
  public void start(boolean direction) {
    // motor.set(Math.min(targetMotorValue,
    // DriverStation.getInstance().getBatteryVoltage()));
    motor.set(direction ? targetMotorValue : -targetMotorValue);
  }

  /**
   * stop the flywheel motor
   */
  public void stop() {
    motor.set(0);
  }

  public boolean isSwitchSet() {
    return false; // counter.get() > 0;
  }

  public void intializeCounter() {
    // counter.reset();
  }

  /**
   * The log method puts interesting information to the SmartDashboard.
   */
  @Override
  public void log() {
    SmartDashboard.putNumber("Flywheel encoder distance", encoder.getDistance());
    SmartDashboard.putNumber("Flywheel encoder raw", encoder.getRaw());
    SmartDashboard.putNumber("Flywheel encoder scaled", encoder.get());
    SmartDashboard.putBoolean("Flywheel encoder direction", encoder.getDirection());
    SmartDashboard.putBoolean("Flywheel encoder stopped", encoder.getStopped());
  }

  /**
   * Reset the robots sensors to the zero states.
   */
  public void reset() {
    stop();
    encoder.reset();
    // counter.reset();
    // gyro.reset();
    // left_encoder.reset();
    // right_encoder.reset();
  }

}
