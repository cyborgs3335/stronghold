/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.usfirst.frc.team3335.robot;

import org.usfirst.frc.team3335.robot.commands.Autonomous;
import org.usfirst.frc.team3335.robot.subsystems.ArmPID;
import org.usfirst.frc.team3335.robot.subsystems.CameraLight;
import org.usfirst.frc.team3335.robot.subsystems.DriveTrainCAN;
import org.usfirst.frc.team3335.robot.subsystems.FlyWheel;
import org.usfirst.frc.team3335.robot.subsystems.HoodPID;
import org.usfirst.frc.team3335.robot.subsystems.Intake;
import org.usfirst.frc.team3335.robot.subsystems.LoggableSubsystem;
import org.usfirst.frc.team3335.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
  Command autonomousCommand;

  public static DriveTrainCAN drivetrain;
  public static Intake intake;
  public static ArmPID arm;
  public static HoodPID hood;
  public static Turret turret;
  public static FlyWheel fly;
  // public static ImageProcessorGrip imageProcessor;
  public static CameraLight cameraLight;

  public static OI oi;
  public static RobotPreferences robotPreferences;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    // Initialize all subsystems
    drivetrain = new DriveTrainCAN();
    intake = new Intake();
    arm = null;// new ArmPID();
    hood = null;// new HoodPID();
    turret = new Turret();
    fly = new FlyWheel();
    // imageProcessor = new ImageProcessorGrip();
    cameraLight = null; // new CameraLight();

    // Instantiate after all subsystems - or you will die
    oi = new OI();

    // instantiate the command used for the autonomous period
    autonomousCommand = new Autonomous();

    // Get preferences from robot
    robotPreferences = new RobotPreferences();

    // Show what command your subsystem is running on the SmartDashboard
    addSubsystemToDashboard(drivetrain);
    addSubsystemToDashboard(intake);
    addSubsystemToDashboard(arm);
    addSubsystemToDashboard(hood);
    addSubsystemToDashboard(turret);
    addSubsystemToDashboard(fly);
    addSubsystemToDashboard(cameraLight);
  }

  @Override
  public void autonomousInit() {
    autonomousCommand.start(); // schedule the autonomous command (example)
  }

  /**
   * This function is called periodically during autonomous
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
    log();
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    autonomousCommand.cancel();
  }

  /**
   * This function is called periodically during operator control
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    log();
  }

  /**
   * This function is called periodically during test mode
   */
  @Override
  public void testPeriodic() {
    LiveWindow.run();
  }

  /**
   * The log method puts interesting information to the SmartDashboard.
   */
  private void log() {
    logSubsystem(drivetrain);
    logSubsystem(intake);
    logSubsystem(arm);
    logSubsystem(hood);
    logSubsystem(turret);
    logSubsystem(fly);
    logSubsystem(cameraLight);
  }

  private void logSubsystem(LoggableSubsystem subsystem) {
    if (subsystem == null) {
      return;
    }
    subsystem.log();
  }

  private void addSubsystemToDashboard(Subsystem subsystem) {
    if (subsystem == null) {
      return;
    }
    SmartDashboard.putData(subsystem);
  }
}
