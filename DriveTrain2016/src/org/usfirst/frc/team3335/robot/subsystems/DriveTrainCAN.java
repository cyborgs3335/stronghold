package org.usfirst.frc.team3335.robot.subsystems;

import org.usfirst.frc.team3335.robot.Robot;
import org.usfirst.frc.team3335.robot.RobotMap;
import org.usfirst.frc.team3335.robot.commands.TankDriveWithJoystick;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The DriveTrain subsystem incorporates the sensors and actuators attached to
 * the robots chassis. These include four drive motors, a left and right encoder
 * and a gyro.
 */
public class DriveTrainCAN extends Subsystem implements LoggableSubsystem {
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
  // For the 6 Degrees of Freedom sensor (AM-2314) with both gyro and
  // accelerometer
  // private ADXL345_I2C_SparkFun m_accel;
  private GyroITG3200 m_gyro;

  public DriveTrainCAN() {
    super();
    front_left_motor = new CANTalon(RobotMap.DRIVE_TRAIN_FRONT_LEFT_MOTOR);
    back_left_motor = new CANTalon(RobotMap.DRIVE_TRAIN_BACK_LEFT_MOTOR);
    front_right_motor = new CANTalon(RobotMap.DRIVE_TRAIN_FRONT_RIGHT_MOTOR);
    back_right_motor = new CANTalon(RobotMap.DRIVE_TRAIN_BACK_RIGHT_MOTOR);
    drive = new RobotDrive(front_left_motor, back_left_motor, front_right_motor, back_right_motor);

    // TODO set encoder parameters, such as distance per pulse
    left_encoder = new Encoder(RobotMap.LEFT_DRIVE_ENCODER_A, RobotMap.LEFT_DRIVE_ENCODER_B, false,
        Encoder.EncodingType.k4X);
    // left_encoder.setDistancePerPulse(distancePerPulse);
    // left_encoder.setMinRate(3.14159265);
    //
    right_encoder = new Encoder(RobotMap.RIGHT_DRIVE_ENCODER_A, RobotMap.RIGHT_DRIVE_ENCODER_B, false,
        Encoder.EncodingType.k4X);
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
    // back_right_motor.setPID(1, 0/*0.001*/, 0);

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
    gyro = new AnalogGyro(RobotMap.GYRO_CHANNEL);
    gyro.calibrate();
    gyro.reset();

    // AM-2314
    // m_accel = new ADXL345_I2C_SparkFun(I2C.Port.kOnboard,
    // Accelerometer.Range.k16G);
    m_gyro = null;// new GyroITG3200(Port.kOnboard);
    // m_gyro.initialize();
    // m_gyro.reset();

    // Let's show everything on the LiveWindow
    // LiveWindow.addActuator("Drive Train", "Front_Left Motor",
    // front_left_motor);
    // LiveWindow.addActuator("Drive Train", "Back Left Motor",
    // back_left_motor);
    // LiveWindow.addActuator("Drive Train", "Front Right Motor",
    // front_right_motor);
    // LiveWindow.addActuator("Drive Train", "Back Right Motor",
    // back_right_motor);
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
  @Override
  public void log() {
    if (left_encoder != null) {
      SmartDashboard.putNumber("Left Raw Distance", left_encoder.getDistance());
      SmartDashboard.putNumber("Left Distance", left_encoder.getDistance() / -13.5);
      SmartDashboard.putNumber("Left Speed", left_encoder.getRate());
    }
    if (right_encoder != null) {
      SmartDashboard.putNumber("Right Raw Distance", right_encoder.getDistance());
      SmartDashboard.putNumber("Right Distance", right_encoder.getDistance() / -13.5);
      SmartDashboard.putNumber("Right Speed", right_encoder.getRate());
    }
    if (gyro != null) {
      SmartDashboard.putNumber("Gyro", gyro.getAngle());
    }
    if (m_gyro != null) {
      SmartDashboard.putNumber("ITG3200 Gyro X degrees", getAngleX());
      SmartDashboard.putNumber("ITG3200 Gyro Y degrees", getAngleY());
      SmartDashboard.putNumber("ITG3200 Gyro Z degrees", getAngleZ());
    }
    // SmartDashboard.putNumber("Joystick Axis 1", );
    SmartDashboard.putNumber("Left Drive Value", leftDriveValue);
    SmartDashboard.putNumber("Right Drive Value", rightDriveValue);
    // SmartDashboard.putBoolean("Brake On?", );
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
    // right *= 0.98;
    drive.tankDrive(left, right);
    leftDriveValue = left;
    rightDriveValue = right;
  }

  public void driveScaled(double leftIn, double rightIn) {
    double scalar = Robot.robotPreferences.getJoystickScalar();
    double powScalar = Robot.robotPreferences.getJoystickPowerScalar();
    double leftOut = scalar * Math.signum(leftIn) * Math.pow(leftIn, powScalar);
    double rightOut = scalar * Math.signum(rightIn) * Math.pow(rightIn, powScalar);
    // drive(leftOut, rightOut);
    if (limitAcceleration) {
      driveLimitAcceleration(leftOut, rightOut);
    } else {
      drive(leftOut, rightOut);
    }
  }

  public void setBrake(boolean brake) {
    back_left_motor.enableBrakeMode(brake);
    front_left_motor.enableBrakeMode(brake);
    back_right_motor.enableBrakeMode(brake);
    front_right_motor.enableBrakeMode(brake);
  }

  /**
   * Scale down values if turning; that is, left and right joystick controllers
   * are pushed in opposite directions.
   *
   * @param left
   *          speed for left side of drivetrain
   * @param right
   *          speed for right side of drivetrain
   * @return array of output speeds [left, right]
   */
  public double[] reduceTurnSpeed(double left, double right) {
    double scalar = 0.88;
    if (Math.signum(left) != Math.signum(right)) {
      left *= scalar;
      right *= scalar;
    }
    return new double[] { left, right };
  }

  /**
   * @param joy
   *          The ps3 style joystick to use to drive tank style.
   */
  public void drive(Joystick joy) {
    // double scalar = Robot.robotPreferences.getJoystickScalar();
    // double powScalar = Robot.robotPreferences.getJoystickPowerScalar();
    double leftIn = joy.getRawAxis(1);
    double rightIn = joy.getRawAxis(5);
    double[] newvals = reduceTurnSpeed(leftIn, rightIn);
    leftIn = newvals[0];
    rightIn = newvals[1];
    driveScaled(leftIn, rightIn);
    // double leftOut = scalar * Math.signum(leftIn) * Math.pow(leftIn,
    // powScalar);
    // double rightOut = scalar * Math.signum(rightIn) * Math.pow(rightIn,
    // powScalar);
    // drive(leftOut, rightOut);
    // if (limitAcceleration) {
    // driveLimitAcceleration(leftOut, rightOut);
    // } else {
    // drive(leftOut, rightOut);
    // }

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
    if (Math.abs(rightOut - rightDriveValue) > maxChange) {
      if (rightOut > rightDriveValue) {
        rightOut = rightDriveValue + maxChange;
      } else {
        rightOut = rightDriveValue - maxChange;
      }
    }
    drive(leftOut, rightOut);
  }

  /**
   * @return The robots heading in degrees.
   */
  public double getHeading() {
    if (gyro != null) {
      return gyro.getAngle();
    }
    return 0;
  }

  /**
   * Reset the robots sensors to the zero states.
   */
  public void reset() {
    if (gyro != null) {
      gyro.reset();
    }
    if (m_gyro != null) {
      m_gyro.reset();
    }
    if (left_encoder != null) {
      left_encoder.reset();
    }
    if (right_encoder != null) {
      right_encoder.reset();
    }
  }

  public double getAngleX() {
    return m_gyro.getRotationX();
  }

  public double getAngleY() {
    return m_gyro.getRotationY();
  }

  public double getAngleZ() {
    return m_gyro.getRotationZ();
  }

  /**
   * @return The distance driven (average of left and right encoders).
   */
  public double getDistance() {
    return (left_encoder.getDistance() + right_encoder.getDistance()) / 2;
    // return 0;
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
