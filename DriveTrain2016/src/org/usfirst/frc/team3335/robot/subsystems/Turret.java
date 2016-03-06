package org.usfirst.frc.team3335.robot.subsystems;

import org.usfirst.frc.team3335.robot.RobotMap;
import org.usfirst.frc.team3335.robot.commands.StopTurret;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Turret extends Subsystem implements LoggableSubsystem {

  public static enum Direction {
    COUNTER_CLOCKWISE, CLOCKWISE
  }

  private CANTalon turretMotor;
  private Encoder encoder;
  private DigitalInput limitSwitchClock;
  private DigitalInput limitSwitchCounter;
  private Counter counterCW;
  private Counter counterCCW;
  private final float MAX_CCW_POSITION = 0, MAX_CW_POSITION = -180;
  private double motorScalar = 0.5;

  public Turret() {
    turretMotor = new CANTalon(RobotMap.TURRET_MOTOR);
    encoder = new Encoder(RobotMap.TURRET_ENCODER_A, RobotMap.TURRET_ENCODER_B, false, Encoder.EncodingType.k4X);
    limitSwitchClock = new DigitalInput(RobotMap.TURRET_CW_SWITCH);
    limitSwitchCounter = new DigitalInput(RobotMap.TURRET_CCW_SWITCH);
    counterCW = new Counter(limitSwitchClock);
    counterCCW = new Counter(limitSwitchCounter);

    reset();
    // encoder.reset();
    // Let's show everything on the LiveWindow
    LiveWindow.addActuator("Turret", "Turret Motor", turretMotor);
    LiveWindow.addActuator("Turret", "Turret Encoder", encoder);
  }

  @Override
  protected void initDefaultCommand() {
    setDefaultCommand(new StopTurret(false));
  }

  /**
   * Start the turret motor.
   *
   * @param forward
   *          if true, set output to negative; if false, set output to positive
   */
  public void start(boolean forward) {
    turretMotor.set(forward ? -motorScalar : motorScalar);
  }

  /**
   * Stop the turret motor.
   */
  public void stop() {
    turretMotor.set(0);
  }

  /**
   * Reset current count on encoder to zero.
   */
  public void resetEncoder() {
    encoder.reset();
  }

  /**
   * The log method puts interesting information to the SmartDashboard.
   */
  @Override
  public void log() {
    SmartDashboard.putNumber("Turret Input Voltage", turretMotor.getBusVoltage());
    SmartDashboard.putNumber("Turret Output Voltage", turretMotor.getOutputVoltage());
    SmartDashboard.putNumber("Turret Position", getAngularPosition());
    SmartDashboard.putNumber("Turret encoder raw", encoder.getRaw());
    SmartDashboard.putNumber("Turret encoder scaled", encoder.get());
    SmartDashboard.putBoolean("Turret encoder direction", encoder.getDirection());
    SmartDashboard.putBoolean("Turret encoder stopped", encoder.getStopped());
    SmartDashboard.putBoolean("Turret in limits?", this.inLimits());
    SmartDashboard.putBoolean("Turret SwitchCW State?", this.isSwitchCWSet());
    SmartDashboard.putNumber("Turret SwitchCW Counter", this.counterCW.get());
    SmartDashboard.putBoolean("Turret SwitchCCW State?", this.isSwitchCCWSet());
    SmartDashboard.putNumber("Turret SwitchCCW Counter", this.counterCCW.get());
  }

  /**
   * Return the angular position of the turret on the horizontal plane, relative
   * to the CCW limit switch.
   *
   * @return angular position in degrees
   */
  public float getAngularPosition() {
    return (float) (360f * encoder.getDistance() / 4096);
  }

  /**
   * Return whether the clockwise limit switch is set.
   *
   * @return true if the clockwise limit switch is set
   */
  public boolean isSwitchCWSet() {
    return counterCW.get() > 0;
  }

  /**
   * Reset the clockwise counter / switch.
   */
  public void intializeCWCounter() {
    counterCW.reset();
  }

  /**
   * Return whether the counter clockwise limit switch is set.
   *
   * @return true if the counter clockwise limit switch is set
   */
  public boolean isSwitchCCWSet() {
    return counterCCW.get() > 0;
  }

  /**
   * Reset the counter clockwise counter / switch.
   */
  public void intializeCCWCounter() {
    counterCCW.reset();
  }

  /**
   * Return whether the turret is within the maximum allowable angle limits.
   *
   * @return true if the turret is within the angle limits
   */
  public boolean inLimits() {
    float pos = getAngularPosition();
    return pos < MAX_CCW_POSITION && pos > MAX_CW_POSITION;
  }

  /**
   * Return whether the turret is allowed to move in the specified direction.
   *
   * @param direction
   *          desired direction to move
   * @return true if the turret can move in the desired direction
   */
  public boolean canMove(Direction direction) {
    return true;
    // float pos = getAngularPosition();
    // switch (direction) {
    // case COUNTER_CLOCKWISE:
    // return pos < MAX_CCW_POSITION; // forward motor
    // case CLOCKWISE:
    // return pos > MAX_CW_POSITION; // reverse motor
    // default:
    // return inLimits();
    // }
  }

  /**
   * Reset the robots sensors to the zero states.
   */
  public void reset() {
    stop();
    counterCW.reset();
    counterCCW.reset();
  }

}
