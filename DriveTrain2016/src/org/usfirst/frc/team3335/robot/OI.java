package org.usfirst.frc.team3335.robot;

import org.usfirst.frc.team3335.robot.commands.InitializeTurretPosition;
import org.usfirst.frc.team3335.robot.commands.IntakeBoulder;
import org.usfirst.frc.team3335.robot.commands.MoveTurret;
import org.usfirst.frc.team3335.robot.commands.OuttakeBoulder;
import org.usfirst.frc.team3335.robot.commands.StartFullShooter;
import org.usfirst.frc.team3335.robot.commands.StartShooter;
import org.usfirst.frc.team3335.robot.commands.StopFullShooter;
import org.usfirst.frc.team3335.robot.commands.StopIntake;
import org.usfirst.frc.team3335.robot.commands.StopShooter;
import org.usfirst.frc.team3335.robot.commands.StopTurret;
import org.usfirst.frc.team3335.robot.commands.SwitchCameraLight;
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
  // private Joystick joyDriver = new Joystick(0);
  // private Joystick joyShooter = new Joystick(2);
  private Joystick joyDriver = new Joystick(Robot.robotPreferences.getJoystickDriverPort());
  private Joystick joyShooter = new Joystick(Robot.robotPreferences.getJoystickShooterPort());

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
    SmartDashboard.putData("Start Full Shooter", new StartFullShooter());
    SmartDashboard.putData("Stop Full Shooter", new StopFullShooter());
    SmartDashboard.putData("Initialize Turret Position", new InitializeTurretPosition());
    SmartDashboard.putData("Turn Light On", new SwitchCameraLight(true));
    // SmartDashboard.putData("Process Image No Grip", new
    // ProcessImageNoGrip());
    // SmartDashboard.putData("Show Camera cam0", new
    // ShowCamera(Robot.imageNIVision));
    // SmartDashboard.putData("Show Camera cam1", new
    // ShowCamera(Robot.imageNIVision2));

    // ps3
    // axis 0 lx
    // axis 1 ly
    // axis 2 ltrigger
    // axis 3 rtrigger
    // axis 4 rx
    // axis 5 ry
    // button 1 x
    // button 2 o
    // button 3 square
    // button 4 triangle
    // button 5 lbutton
    // button 6 rbutton
    // button 7 select
    // button 8 start
    // button 9 laxis
    // button 10 raxis

    // ===== Driver Joystick Buttons =====
    // A Button
    JoystickButton intakeStart = addButton(joyDriver, 6, "Intake Start");
    // B button
    JoystickButton intakeStop = addButton(joyDriver, 2, "Intake Stop");
    // Y button
    JoystickButton intakeReverse = addButton(joyDriver, 5, "Intake Reverse");
    // JoystickButton armUp = addButton(joyDriver, 5, "Arm Up");
    // JoystickButton armDown = addButton(joyDriver, 6, "Arm Down");
    // JoystickButton lowerHood = addButton(joyDriver, 3, "Lower Hood");
    // Reserved axes and buttons
    // axis 1 and 5 -- TankDriveWithJoystick

    // ===== Shooter Joystick Buttons =====
    // A button
    JoystickButton turretCCW = addButton(joyShooter, 1, "Turret CCW");
    // B button
    JoystickButton turretCW = addButton(joyShooter, 4, "Turret CW");
    // Y button
    JoystickButton turretStop = addButton(joyShooter, 2, "Turret Stop");
    // JoystickButton hoodUp = addButton(joyShooter, 6, "Hood Up");
    // JoystickButton hoodDown = addButton(joyShooter, 5, "Hood Down");
    JoystickButton shooterStart = addButton(joyShooter, 7, "Shooter Start");
    JoystickButton shooterStop = addButton(joyShooter, 8, "Shooter Stop");
    // Reserved axes and buttons
    // axis 1 -- HoodDriveWithJoystick

    // Connect the buttons to commands
    intakeStart.whenPressed(new IntakeBoulder(0.7));
    intakeStart.whenReleased(new StopIntake(true));
    intakeStop.whenPressed(new StopIntake(true));
    intakeReverse.whenPressed(new OuttakeBoulder());
    intakeReverse.whenReleased(new StopIntake(true));
    // lowerHood.whenPressed(new SetHoodPosition(90));
    // armUp.whenPressed(new SetArmPosition(90));
    // armDown.whenPressed(new SetArmPosition(0));
    // hoodUp.whenPressed(new MoveHood(Hood.Direction.UP));
    // hoodUp.whenReleased(new StopHood(true));
    // hoodDown.whenPressed(new MoveHood(Hood.Direction.DOWN));
    // hoodDown.whenReleased(new StopHood(true));
    shooterStart.whenPressed(new StartShooter());
    shooterStart.whenReleased(new StopShooter(true));
    shooterStop.whenPressed(new StopShooter(true));
    turretCCW.whenPressed(new MoveTurret(Turret.Direction.COUNTER_CLOCKWISE)); // Counter-clockwise
    turretCCW.whenReleased(new StopTurret(true));
    turretCW.whenPressed(new MoveTurret(Turret.Direction.CLOCKWISE)); // Clockwise
    turretCW.whenReleased(new StopTurret(true));
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
