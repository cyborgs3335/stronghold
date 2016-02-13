package org.usfirst.frc.team3335.robot;

import org.usfirst.frc.team3335.robot.commands.IntakeBoulder;
import org.usfirst.frc.team3335.robot.commands.MoveTurret;
import org.usfirst.frc.team3335.robot.commands.OuttakeBoulder;
import org.usfirst.frc.team3335.robot.commands.StartShooter;
import org.usfirst.frc.team3335.robot.commands.StopIntake;
import org.usfirst.frc.team3335.robot.commands.StopShooter;
import org.usfirst.frc.team3335.robot.commands.StopTurret;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

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
    //
    // SmartDashboard.putData("Wrist Horizontal", new SetWristSetpoint(0));
    // SmartDashboard.putData("Raise Wrist", new SetWristSetpoint(-45));
    //
    // SmartDashboard.putData("Open Claw", new OpenClaw());
    // SmartDashboard.putData("Close Claw", new CloseClaw());
    //
    // SmartDashboard.putData("Deliver Soda", new Autonomous());

    // Create some buttons
    JoystickButton intakeStart = new JoystickButton(joyDriver, 1); // A button
    JoystickButton intakeStop = new JoystickButton(joyDriver, 2); // B button
    JoystickButton intakeReverse = new JoystickButton(joyDriver, 4); // Y button
    JoystickButton turretLeft = new JoystickButton(joyShooter, 1); // A button
    JoystickButton turretRight = new JoystickButton(joyShooter, 4); // Y button
    JoystickButton turretStop = new JoystickButton(joyShooter, 2); // B button
    JoystickButton armUp = new JoystickButton(joyDriver, 5);
    JoystickButton armDown = new JoystickButton(joyDriver, 6);
    JoystickButton shooterStart = new JoystickButton(joyShooter, 7);
    JoystickButton shooterStop = new JoystickButton(joyShooter, 8);

    // Connect the buttons to commands
    intakeStart.whenPressed(new IntakeBoulder());
    intakeStop.whenPressed(new StopIntake(true));
    intakeReverse.whenPressed(new OuttakeBoulder());
    // armUp.whenPressed(new SetArmPosition(90));
    // armDown.whenPressed(new SetArmPosition(0));
    shooterStart.whenPressed(new StartShooter());
    shooterStop.whenPressed(new StopShooter(true));
    // d_up.whenPressed(new SetElevatorSetpoint(0.2));
    // d_down.whenPressed(new SetElevatorSetpoint(-0.2));
    // d_right.whenPressed(new CloseClaw());
    // d_left.whenPressed(new OpenClaw());
    turretLeft.whenPressed(new MoveTurret(MoveTurret.Direction.LEFT));
    turretRight.whenPressed(new MoveTurret(MoveTurret.Direction.RIGHT));
    turretStop.whenPressed(new StopTurret(true));

    // r1.whenPressed(new PrepareToPickup());
    // r2.whenPressed(new Pickup());
    // l1.whenPressed(new Place());
    // l2.whenPressed(new Autonomous());
  }

  public Joystick getJoystick() {
    return joyDriver;
  }
}
