package org.usfirst.frc.team3335.robot.subsystems;

import org.usfirst.frc.team3335.robot.commands.StopIntake;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake extends Subsystem implements LoggableSubsystem {
  private CANTalon intakeMotor;
  private DigitalInput limitSwitch;
  private Counter counter;

  public Intake() {
    super();
    intakeMotor = new CANTalon(5);
    limitSwitch = new DigitalInput(7);
    counter = new Counter(limitSwitch);
    intakeMotor.set(0);

    LiveWindow.addActuator("Intake", "Motor", intakeMotor);
  }

  public Intake(String name) {
    super(name);
  }

  @Override
  protected void initDefaultCommand() {
    setDefaultCommand(new StopIntake(false));
  }

  /**
   * start the intake
   *
   * @param forward
   *          - if true - goes forward, if false - goes backward
   */
  public void start(boolean forward) {
    intakeMotor.set(forward ? 1 : -1);
  }

  /**
   * stop the intake
   */
  public void stop() {
    intakeMotor.set(0);
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
  @Override
  public void log() {
    SmartDashboard.putNumber("Counter Value", counter.get());
    SmartDashboard.putBoolean("Switch State", isSwitchSet());
  }

  /**
   * Reset the robots sensors to the zero states.
   */
  public void reset() {
    stop();
    counter.reset();
  }

}
