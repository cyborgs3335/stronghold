package org.usfirst.frc.team3335.robot.subsystems;

import org.usfirst.frc.team3335.robot.RobotMap;
import org.usfirst.frc.team3335.robot.commands.ArmDriveWithJoystick;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Arm extends Subsystem implements LoggableSubsystem {

  public static enum Direction {
    UP, DOWN
  }

  private CANTalon motor;
  private Encoder encoder;
  private DigitalInput limitSwitch;
  private double distancePerPulse;

  /** Minimum position, where arm is fully down. */
  public static final float MIN_POSITION = 0;
  /** Maximum position, where arm is fully up. */
  public static final float MAX_POSITION = 140;
  /** Actual minimum position, where arm is fully down */
  private double downPositionDistance = 0;

  private long timeSinceSwitchSet = Long.MAX_VALUE;
  private long maxExtraTime = 625; // milliseconds

  private final double motorMax = 0.01;

  public Arm() {
    super();
    motor = new CANTalon(RobotMap.ARM_MOTOR/* 2 */); // TODO verify this is
                                                     // correct motor device
    encoder = new Encoder(RobotMap.ARM_ENCODER_A, RobotMap.ARM_ENCODER_B, false, Encoder.EncodingType.k4X);
    // encoder.setDistancePerPulse(distancePerPulse);
    limitSwitch = new DigitalInput(RobotMap.ARM_SWITCH_DOWN);

    motor.set(0);
    // encoder.reset();
    // Let's show everything on the LiveWindow
    // LiveWindow.addActuator("Arm", "Arm Motor", motor);
    // LiveWindow.addActuator("Arm", "Arm Encoder", encoder);
  }

  @Override
  protected void initDefaultCommand() {
    setDefaultCommand(new ArmDriveWithJoystick(this));
  }

  public void start(boolean forward) {
    motor.set(forward ? motorMax : -motorMax);
  }

  public void stop() {
    motor.set(0);
    // motor.enableBrakeMode(brake);
  }

  public boolean isSwitchSet() {
    if (limitSwitch.get()) {
      downPositionDistance = encoder.getDistance();
      if (timeSinceSwitchSet == Long.MAX_VALUE) {
        timeSinceSwitchSet = System.currentTimeMillis();
      }
    } else {
      timeSinceSwitchSet = Long.MAX_VALUE;
    }
    return limitSwitch.get();
  }

  public void rotate(double value) {
    double motorValue = Math.abs(value) < 0.1 ? 0 : value;
    if (canMove(motorValue)) {
      motorValue = adjustMotorValue(motorValue);
      motor.set(motorValue);
    } else {
      motor.set(0);
    }
  }

  private double adjustMotorValue(double inputValue) {
    // TODO massage motorValue to increase voltage if arms moving too slowly
    // up slow rate = 50 at input value of -0.1?
    // up fast rate = 170 at input value of -0.4
    double outputValue = inputValue;
    if (Math.abs(encoder.getRate()) < 100 && Math.abs(inputValue) > 0.3) {
      outputValue = Math.min(1, 2 * inputValue);
    }
    return outputValue;
  }

  /**
   * The log method puts interesting information to the SmartDashboard.
   *
   * @return the angular position of the arm on the vertical plane
   */
  @Override
  public void log() {
    SmartDashboard.putNumber("Arm Position", getAngularPosition());
    SmartDashboard.putNumber("Arm encoder rate", encoder.getRate());
    SmartDashboard.putNumber("Arm encoder raw", encoder.getRaw());
    SmartDashboard.putNumber("Arm encoder scaled", encoder.get());
    SmartDashboard.putBoolean("Arm encoder direction", encoder.getDirection());
    SmartDashboard.putBoolean("Arm encoder stopped", encoder.getStopped());
    SmartDashboard.putBoolean("Arm switch state", isSwitchSet());
  }

  /**
   * Return the angular position in degrees.
   *
   * @return angular position in degrees
   */
  public float getAngularPosition() {
    // return (float) (360f * (encoder.getDistance() - downPositionDistance) /
    // 4096 / 2);
    return (float) (360f * (encoder.getDistance() - downPositionDistance) / 1024);
  }

  public boolean inLimits() {
    float pos = getAngularPosition();
    return pos < MAX_POSITION && pos > MIN_POSITION;
  }

  public boolean canMove(Direction direction) {
    switch (direction) {
      case DOWN:
        if (isSwitchSet()) {
          if (System.currentTimeMillis() - timeSinceSwitchSet < maxExtraTime) {
            return true;
          }
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
   * Query whether the arm can be moved.
   *
   * @param value
   *          value provided to motor; positive=down, negative=up
   * @return true if the arm can be moved
   */
  private boolean canMove(double value) {
    if (value > 0) {
      return canMove(Direction.DOWN);
    }
    return canMove(Direction.UP);
  }

}