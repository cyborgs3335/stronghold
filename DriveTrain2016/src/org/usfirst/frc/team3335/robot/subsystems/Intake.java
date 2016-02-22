package org.usfirst.frc.team3335.robot.subsystems;

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
  private final double maxForwardMotorValue = 1; // TODO need different values
                                                 // for intake and for feed to
                                                 // flywheel
  private final double maxReverseMotorValue = 1;

  public Intake() {
    super();
    intakeMotor = new CANTalon(5);
    limitSwitch = new DigitalInput(6);
    counter = new Counter(limitSwitch);
    intakeMotor.set(0);

    // LiveWindow.addActuator("Intake", "Motor", intakeMotor);
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
    intakeMotor.set(forward ? maxForwardMotorValue : -maxReverseMotorValue);
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
    SmartDashboard.putNumber("Intake Counter Value", counter.get());
    SmartDashboard.putBoolean("Intake Switch State", isSwitchSet());
    SmartDashboard.putBoolean("Intake Limit Switch", limitSwitch.get());
  }

  /**
   * Reset the robots sensors to the zero states.
   */
  public void reset() {
    stop();
    counter.reset();
  }

}
