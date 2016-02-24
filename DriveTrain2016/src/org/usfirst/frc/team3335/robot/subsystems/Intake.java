package org.usfirst.frc.team3335.robot.subsystems;

import org.usfirst.frc.team3335.robot.RobotMap;
import org.usfirst.frc.team3335.robot.commands.StopIntake;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake extends Subsystem implements LoggableSubsystem {
  private CANTalon intakeMotor;
  private DigitalInput limitSwitch;
  private Counter counter;

  /**
   * Default constructor.
   */
  public Intake() {
    super();
    intakeMotor = new CANTalon(RobotMap.INTAKE_MOTOR/* 5 */);
    limitSwitch = new DigitalInput(6);
    counter = new Counter(limitSwitch);
    intakeMotor.set(0);

    // LiveWindow.addActuator("Intake", "Motor", intakeMotor);
  }

  @Override
  protected void initDefaultCommand() {
    setDefaultCommand(new StopIntake(false));
  }

  /**
   * start the intake at full speed
   *
   * @param forward
   *          - if true - goes forward, if false - goes backward
   */
  public void start(boolean forward) {
    intakeMotor.set(forward ? 1 : -1);
  }

  /**
   * Start motor with specified speed.
   *
   * @param speed
   *          speed to set motor, limited to range of [-1:1]
   */
  public void start(double speed) {
    double motorValue = Math.max(Math.min(speed, 1), -1);
    intakeMotor.set(motorValue);
  }

  /**
   * stop the intake
   */
  public void stop() {
    intakeMotor.set(0);
  }

  public boolean isSwitchSet() {
    // return counter.get() > 0;
    return limitSwitch.get();
  }

  public void intializeCounter() {
    counter.reset();
  }

  /**
   * The log method puts interesting information to the SmartDashboard.
   */
  @Override
  public void log() {
    // SmartDashboard.putNumber("Intake Counter Value", counter.get());
    SmartDashboard.putBoolean("Intake Switch State", isSwitchSet());
    // SmartDashboard.putBoolean("Intake Limit Switch", limitSwitch.get());
  }

  /**
   * Reset the robots sensors to the zero states.
   */
  public void reset() {
    stop();
    counter.reset();
  }

}
