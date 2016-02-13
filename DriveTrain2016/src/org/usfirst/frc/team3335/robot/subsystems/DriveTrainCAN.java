package org.usfirst.frc.team3335.robot.subsystems;

import org.usfirst.frc.team3335.robot.Robot;
import org.usfirst.frc.team3335.robot.commands.TankDriveWithJoystick;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The DriveTrain subsystem incorporates the sensors and actuators attached to
 * the robots chassis. These include four drive motors, a left and right encoder
 * and a gyro.
 */
public class DriveTrainCAN extends Subsystem {
  private CANTalon front_left_motor, back_left_motor, front_right_motor, back_right_motor;
  private RobotDrive drive;
  private Encoder left_encoder, right_encoder;
  private AnalogInput rangefinder;
  private AnalogGyro gyro;
  private double leftDriveValue;
  private double rightDriveValue;
  private boolean limitAcceleration = true;
  // TODO calibrate the distance per pulse
  private double distancePerPulse = 5;

  public DriveTrainCAN() {
    super();
    front_left_motor = new CANTalon(1);
    back_left_motor = new CANTalon(4);
    front_right_motor = new CANTalon(2);
    back_right_motor = new CANTalon(3);
    drive = new RobotDrive(front_left_motor, back_left_motor, front_right_motor, back_right_motor);
    // left_encoder = new Encoder(1, 2);
    // right_encoder = new Encoder(3, 4);

    // left_encoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
    // left_encoder.setDistancePerPulse(distancePerPulse);
    // left_encoder.setMinRate(3.14159265);
    //
    // right_encoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
    // right_encoder.setDistancePerPulse(distancePerPulse);
    // right_encoder.setMinRate(3.14159265);

    // percent vbus is default mode
    front_left_motor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    front_left_motor.set(0);
    // percent vbus is default mode
    front_right_motor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    front_right_motor.set(0);
    // percent vbus is default mode
    back_left_motor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    back_left_motor.set(0);
    // percent vbus is default mode
    back_right_motor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    back_right_motor.set(0);

    // Encoders may measure differently in the real world and in
    // simulation. In this example the robot moves 0.042 barleycorns
    // per tick in the real world, but the simulated encoders
    // simulate 360 tick encoders. This if statement allows for the
    // real robot to handle this difference in devices.
    // if (Robot.isReal()) {
    // left_encoder.setDistancePerPulse(0.042);
    // right_encoder.setDistancePerPulse(0.042);
    // } else {
    // // Circumference in ft = 4in/12(in/ft)*PI
    // left_encoder.setDistancePerPulse((4.0/12.0*Math.PI) / 360.0);
    // right_encoder.setDistancePerPulse((4.0/12.0*Math.PI) / 360.0);
    // }

    // rangefinder = new AnalogInput(6);
    // gyro = new AnalogGyro(1);

    // Let's show everything on the LiveWindow
    LiveWindow.addActuator("Drive Train", "Front_Left Motor", front_left_motor);
    LiveWindow.addActuator("Drive Train", "Back Left Motor", back_left_motor);
    LiveWindow.addActuator("Drive Train", "Front Right Motor", front_right_motor);
    LiveWindow.addActuator("Drive Train", "Back Right Motor", back_right_motor);
    // LiveWindow.addSensor("Drive Train", "Left Encoder", left_encoder);
    // LiveWindow.addSensor("Drive Train", "Right Encoder", right_encoder);
    // LiveWindow.addSensor("Drive Train", "Rangefinder", rangefinder);
    // LiveWindow.addSensor("Drive Train", "Gyro", gyro);
  }

  /**
   * When no other command is running let the operator drive around using the
   * PS3 joystick.
   */
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new TankDriveWithJoystick());
  }

  /**
   * The log method puts interesting information to the SmartDashboard.
   */
  public void log() {
    // SmartDashboard.putNumber("Left Distance", left_encoder.getDistance());
    // SmartDashboard.putNumber("Right Distance", right_encoder.getDistance());
    // SmartDashboard.putNumber("Left Speed", left_encoder.getRate());
    // SmartDashboard.putNumber("Right Speed", right_encoder.getRate());
    // SmartDashboard.putNumber("Gyro", gyro.getAngle());
    // SmartDashboard.putNumber("Joystick Axis 1", );
    SmartDashboard.putNumber("Left Drive Value", leftDriveValue);
    SmartDashboard.putNumber("Right Drive Value", rightDriveValue);
  }

  /**
   * Tank style driving for the DriveTrain.
   * 
   * @param left
   *          Speed in range [-1,1]
   * @param right
   *          Speed in range [-1,1]
   */
  public void drive(double left, double right) {
    drive.tankDrive(left, right);
    leftDriveValue = left;
    rightDriveValue = right;
  }

  /**
   * @param joy
   *          The ps3 style joystick to use to drive tank style.
   */
  public void drive(Joystick joy) {
    double scalar = Robot.robotPreferences.getJoystickScalar();
    double powScalar = Robot.robotPreferences.getJoystickPowerScalar();
    double leftIn = joy.getRawAxis(1);
    double rightIn = joy.getRawAxis(5);
    double leftOut = scalar * Math.signum(leftIn) * Math.pow(leftIn, powScalar);
    double rightOut = scalar * Math.signum(rightIn) * Math.pow(rightIn, powScalar);
    drive(leftOut, rightOut);
    if (limitAcceleration) {
      driveLimitAcceleration(leftOut, rightOut);
    } else {
      drive(leftOut, rightOut);
    }

    // drive(scalar*joy.getRawAxis(1),scalar*joy.getRawAxis(5));
    // drive(-joy.getY(), -joy.getAxis(AxisType.kThrottle));
  }

  private void driveLimitAcceleration(double left, double right) {
    double leftOut = left;
    double rightOut = right;
    double maxChange = Robot.robotPreferences.getAccelLimit();
    if (Math.abs(leftOut - leftDriveValue) > maxChange) {
      if (leftOut > leftDriveValue) {
        leftOut = leftDriveValue + maxChange;
      } else {
        leftOut = leftDriveValue - maxChange;
      }
    }
    drive(leftOut, rightOut);
  }

  /**
   * @return The robots heading in degrees.
   */
  public double getHeading() {
    // return gyro.getAngle();
    return 0;
  }

  /**
   * Reset the robots sensors to the zero states.
   */
  public void reset() {
    // gyro.reset();
    // left_encoder.reset();
    // right_encoder.reset();
  }

  /**
   * @return The distance driven (average of left and right encoders).
   */
  public double getDistance() {
    // return (left_encoder.getDistance() + right_encoder.getDistance())/2;
    return 0;
  }

  /**
   * @return The distance to the obstacle detected by the rangefinder.
   */
  public double getDistanceToObstacle() {
    // Really meters in simulation since it's a rangefinder...
    // return rangefinder.getAverageVoltage();
    return 0;
  }
}
