package org.usfirst.frc.team3335.robot.subsystems;

import org.usfirst.frc.team3335.robot.commands.ArmDriveWithJoystick;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Arm extends Subsystem implements LoggableSubsystem {

  public static enum Direction {
    UP, DOWN
  }

  private CANTalon motor;
  private Encoder encoder;
  private double distancePerPulse;

  /** Minimum position, where hood is fully down. */
  private final float MIN_POSITION = 0;
  /** Maximum position, where hood is fully up. */
  private final float MAX_POSITION = 90;

  public Arm() {
    super();
    motor = new CANTalon(2); // TODO verify this is correct motor device
    encoder = new Encoder(9, 7, false, Encoder.EncodingType.k4X); // TODO change
                                                                  // channels to
                                                                  // match robot
    encoder.setDistancePerPulse(distancePerPulse);

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
    motor.set(forward ? 1 : -1);
  }

  public void stop() {
    motor.set(0);
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
   * @return the angular position of the arm on the vertical plane
   */
  @Override
  public void log() {
    SmartDashboard.putNumber("Arm Position", getAngularPosition());
    SmartDashboard.putNumber("Arm encoder raw", encoder.getRaw());
    SmartDashboard.putNumber("Arm encoder scaled", encoder.get());
    SmartDashboard.putBoolean("Arm encoder direction", encoder.getDirection());
    SmartDashboard.putBoolean("Arm encoder stopped", encoder.getStopped());
  }

  /**
   * Return the angular position in degrees.
   *
   * @return angular position in degrees
   */
  public float getAngularPosition() {
    return (float) (360f * encoder.getDistance() / 4096);
  }

  public boolean inLimits() {
    float pos = getAngularPosition();
    return pos < MAX_POSITION && pos > MIN_POSITION;
  }

  public boolean canMove(Direction direction) {
    float pos = getAngularPosition();
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
   *          value provided to motor; positive=up, negative=down
   * @return true if the arm can be moved
   */
  private boolean canMove(double value) {
    if (value < 0) {
      return canMove(Direction.DOWN);
    }
    return canMove(Direction.UP);
  }

}