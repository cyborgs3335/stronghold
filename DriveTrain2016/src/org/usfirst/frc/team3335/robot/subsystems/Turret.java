package org.usfirst.frc.team3335.robot.subsystems;

import org.usfirst.frc.team3335.robot.commands.StopTurret;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Turret extends Subsystem {

  private CANTalon turretMotor;
  private Encoder turretEncoder;
  private final float MAX_POSITION = 100, MIN_POSITION = -100;

  public Turret() {
    turretMotor = new CANTalon(7);
    turretEncoder = new Encoder(2, 3, false, Encoder.EncodingType.k4X);

    turretMotor.set(0);
    // Let's show everything on the LiveWindow
    LiveWindow.addActuator("Turret", "Turret_Encoder", turretEncoder);
  }

  @Override
  protected void initDefaultCommand() {
    setDefaultCommand(new StopTurret(false));
  }

  public void start(boolean forward) {
    turretMotor.set(forward ? 1 : -1);
  }

  public void stop() {
    turretMotor.set(0);
  }

  /**
   * The log method puts interesting information to the SmartDashboard.
   */
  public void log() {
    SmartDashboard.putNumber("Turret Position", getPosition());
  }

  /**
   * Return the angular position in degrees.
   *
   * @return angular position in degrees
   */
  public float getPosition() {
    turretEncoder.getDistance();
    return (float) (360f * turretEncoder.getDistance() / 4096);
  }

  public boolean inLimits() {
    float pos = getPosition();
    return pos < MAX_POSITION && pos > MIN_POSITION;
  }
}
