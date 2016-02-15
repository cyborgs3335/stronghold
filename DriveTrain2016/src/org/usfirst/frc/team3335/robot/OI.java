package org.usfirst.frc.team3335.robot;

import org.usfirst.frc.team3335.robot.commands.IntakeBoulder;
import org.usfirst.frc.team3335.robot.commands.MoveTurret;
import org.usfirst.frc.team3335.robot.commands.OuttakeBoulder;
import org.usfirst.frc.team3335.robot.commands.StartShooter;
import org.usfirst.frc.team3335.robot.commands.StopIntake;
import org.usfirst.frc.team3335.robot.commands.StopShooter;
import org.usfirst.frc.team3335.robot.commands.StopTurret;
import org.usfirst.frc.team3335.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  private Joystick joyDriver = new Joystick(0);
  private Joystick joyShooter = new Joystick(2);

  public OI() {
    // Put Some buttons on the SmartDashboard
    // SmartDashboard.putData("Elevator Bottom", new SetElevatorSetpoint(0));
    // SmartDashboard.putData("Elevator Platform", new
    // SetElevatorSetpoint(0.2));
    // SmartDashboard.putData("Elevator Top", new SetElevatorSetpoint(0.3));
    // SmartDashboard.putData("Wrist Horizontal", new SetWristSetpoint(0));
    // SmartDashboard.putData("Raise Wrist", new SetWristSetpoint(-45));
    // SmartDashboard.putData("Open Claw", new OpenClaw());
    // SmartDashboard.putData("Close Claw", new CloseClaw());
    // SmartDashboard.putData("Deliver Soda", new Autonomous());

    // ===== Driver Joystick Buttons =====
    // A Button
    JoystickButton intakeStart = addButton(joyDriver, 1, "Intake Start");
    // B button
    JoystickButton intakeStop = addButton(joyDriver, 2, "Intake Stop");
    // Y button
    JoystickButton intakeReverse = addButton(joyDriver, 4, "Intake Reverse");
    // JoystickButton armUp = addButton(joyDriver, 5, "Arm Up");
    // JoystickButton armDown = addButton(joyDriver, 6, "Arm Down");
    // JoystickButton lowerHood = addButton(joyDriver, 3, "Lower Hood");
    // Reserved axes and buttons
    // axis 1 and 5 -- TankDriveWithJoystick

    // ===== Shooter Joystick Buttons =====
    // A button
    JoystickButton turretLeft = addButton(joyShooter, 1, "Turret Left");
    // B button
    JoystickButton turretRight = addButton(joyShooter, 4, "Turret Right");
    // Y button
    JoystickButton turretStop = addButton(joyShooter, 2, "Turret Stop");
    JoystickButton shooterStart = addButton(joyShooter, 7, "Shooter Start");
    JoystickButton shooterStop = addButton(joyShooter, 8, "Shooter Stop");
    // Reserved axes and buttons
    // axis 1 -- HoodDriveWithJoystick

    // Connect the buttons to commands
    intakeStart.whenPressed(new IntakeBoulder());
    intakeStart.whenReleased(new StopIntake(true));
    intakeStop.whenPressed(new StopIntake(true));
    intakeReverse.whenPressed(new OuttakeBoulder());
    intakeReverse.whenReleased(new StopIntake(true));
    // lowerHood.whenPressed(new SetHoodPosition(90));
    // armUp.whenPressed(new SetArmPosition(90));
    // armDown.whenPressed(new SetArmPosition(0));
    shooterStart.whenPressed(new StartShooter());
    shooterStart.whenReleased(new StopShooter(true));
    shooterStop.whenPressed(new StopShooter(true));
    turretLeft.whenPressed(new MoveTurret(Turret.Direction.LEFT)); // Counter-clockwise
    turretLeft.whenReleased(new StopTurret(true));
    turretRight.whenPressed(new MoveTurret(Turret.Direction.RIGHT)); // Clockwise
    turretRight.whenReleased(new StopTurret(true));
    turretStop.whenPressed(new StopTurret(true));
  }

  private JoystickButton addButton(GenericHID joystick, int buttonNumber, String dashboardKey) {
    JoystickButton button = new JoystickButton(joystick, buttonNumber);
    // TODO validate that the joystick button data on the dashboard is useful
    SmartDashboard.putData(dashboardKey, button);
    return button;
  }

  public Joystick getJoystick() {
    return joyDriver;
  }

  public Joystick getJoystickShooter() {
    return joyShooter;
  }
}
