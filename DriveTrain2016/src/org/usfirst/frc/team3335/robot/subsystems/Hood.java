package org.usfirst.frc.team3335.robot.subsystems;

import org.usfirst.frc.team3335.robot.RobotMap;
import org.usfirst.frc.team3335.robot.commands.HoodDriveWithJoystick;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Hood extends Subsystem implements LoggableSubsystem {

  public static enum Direction {
    UP, DOWN
  }

  private CANTalon motor;
  private DigitalInput limitSwitch;
  private Encoder encoder;
  /** Minimum position, where hood is fully down. */
  private final float MIN_POSITION = 0;
  /** Maximum position, where hood is fully up. */
  private final float MAX_POSITION = 60;// 90;
  /** Actual minimum position, where hood is fully down */
  private double downPositionDistance = 0;

  public Hood() {
    motor = new CANTalon(RobotMap.HOOD_MOTOR);
    limitSwitch = new DigitalInput(RobotMap.HOOD_SWITCH);
    encoder = new Encoder(RobotMap.HOOD_ENCODER_A, RobotMap.HOOD_ENCODER_B, false, Encoder.EncodingType.k4X);

    motor.set(0);
    // encoder.reset();
    // Let's show everything on the LiveWindow
    // LiveWindow.addActuator("Hood", "Hood Motor", motor);
    // LiveWindow.addActuator("Hood", "Hood Encoder", encoder);
  }

  @Override
  protected void initDefaultCommand() {
    setDefaultCommand(new HoodDriveWithJoystick(this));
  }

  public void start(boolean forward) {
    double value = 0.1;
    motor.set(forward ? value : -value);
  }

  public void stop() {
    motor.set(0);
  }

  public boolean isSwitchSet() {
    if (limitSwitch.get()) {
      downPositionDistance = encoder.getDistance();
    }
    return limitSwitch.get();
  }

  public void rotate(double value) {
    double motorValue = Math.abs(value) < 0.1 ? 0 : value;
    if (canMove(motorValue)) {
      motor.set(motorValue);
    } else {
      motor.set(0);
    }
  }

  /**
   * The log method puts interesting information to the SmartDashboard.
   *
   * @return the angular position of the hood on the vertical plane
   */
  @Override
  public void log() {
    SmartDashboard.putNumber("Hood Position", getAngularPosition());
    SmartDashboard.putBoolean("Hood switch state", isSwitchSet());
    SmartDashboard.putNumber("Hood encoder raw", encoder.getRaw());
    SmartDashboard.putNumber("Hood encoder scaled", encoder.get());
    SmartDashboard.putBoolean("Hood encoder direction", encoder.getDirection());
    SmartDashboard.putBoolean("Hood encoder stopped", encoder.getStopped());
  }

  /**
   * Return the angular position in degrees.
   *
   * @return angular position in degrees
   */
  public float getAngularPosition() {
    return (float) (360f * (encoder.getDistance() - downPositionDistance) / 4096 / 2);
  }

  public boolean inLimits() {
    float pos = getAngularPosition();
    return pos < MAX_POSITION && pos > MIN_POSITION;
  }

  public boolean canMove(Direction direction) {
    switch (direction) {
      case DOWN:
        if (isSwitchSet()) {
          return false;
        }
        return true;
      case UP:
        return getAngularPosition() < MAX_POSITION; // forward motor
      // return true;
    }
    // float pos = getAngularPosition();
    // switch (direction) {
    // case UP:
    // return pos < MAX_POSITION; // forward motor
    // case DOWN:
    // return pos > MIN_POSITION; // reverse motor
    // default:
    // return inLimits();
    // }
    return true;
  }

  /**
   * Query whether the hood can be moved.
   *
   * @param value
   *          value provided to motor; positive=up, negative=down
   * @return true if the hood can be moved
   */
  private boolean canMove(double value) {
    if (value < 0) {
      return canMove(Direction.DOWN);
    }
    return canMove(Direction.UP);
  }
}
