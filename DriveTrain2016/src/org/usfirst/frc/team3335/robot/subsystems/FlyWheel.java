package org.usfirst.frc.team3335.robot.subsystems;

import org.usfirst.frc.team3335.robot.commands.StopShooter;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class FlyWheel extends Subsystem {
  private CANTalon flywheelMotor;
  private DigitalInput limitSwitch;
  private Counter counter;

  public FlyWheel() {
    super();
    flywheelMotor = new CANTalon(4/* Set this equal to new value */);
    limitSwitch = new DigitalInput(1);
    counter = new Counter(limitSwitch);
    flywheelMotor.set(0);

    // LiveWindow.addActuator("Drive Train", "Front_Left Motor", (CANTalon)
    // front_left_motor);
    // LiveWindow.addActuator("Drive Train", "Back Left Motor", (CANTalon)
    // back_left_motor);
    // LiveWindow.addActuator("Drive Train", "Front Right Motor", (CANTalon)
    // front_right_motor);
    // LiveWindow.addActuator("Drive Train", "Back Right Motor", (CANTalon)
    // back_right_motor);

  }

  public FlyWheel(String name) {
    super(name);
    // TODO Auto-generated constructor stub
  }

  @Override
  protected void initDefaultCommand() {
    // TODO Auto-generated method stub
    setDefaultCommand(new StopShooter(false));
  }

  /**
   * start the intake
   *
   * @param forward
   *          - motor either goes forward or backward
   */
  public void start() {
    flywheelMotor.set(1);
  }

  /**
   * stop the intake
   */
  public void stop() {
    flywheelMotor.set(0);
  }

  public boolean isSwitchSet() {
    return counter.get() > 0;
  }

  public void intializeCounter() {
    counter.reset();
  }

  /**
   * The log method puts interesting information to the SmartDashboard.
   */
  public void log() {
    // SmartDashboard.putNumber("Left Drive Value", leftDriveValue);
    // SmartDashboard.putNumber("Right Drive Value", rightDriveValue);
  }

  /**
   * Reset the robots sensors to the zero states.
   */
  public void reset() {
    stop();
    counter.reset();
    // gyro.reset();
    // left_encoder.reset();
    // right_encoder.reset();
  }

}
