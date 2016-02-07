/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3335.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.usfirst.frc.team3335.robot.commands.Autonomous;
import org.usfirst.frc.team3335.robot.subsystems.ArmPID;
import org.usfirst.frc.team3335.robot.subsystems.DriveTrainCAN;
import org.usfirst.frc.team3335.robot.subsystems.HoodPID;
import org.usfirst.frc.team3335.robot.subsystems.ImageProcessorGrip;
import org.usfirst.frc.team3335.robot.subsystems.Intake;
import org.usfirst.frc.team3335.robot.subsystems.Turret;

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
    //public static Elevator elevator;
    //public static Wrist wrist;
    //public static Claw claw;
    public static OI oi;
    //public static ImageProcessorGrip imageProcessor;
    public static RobotPreferences robotPreferences;

	public static ArmPID arm;
	public static HoodPID hood;
	public static Turret turret;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // Initialize all subsystems
        drivetrain = new DriveTrainCAN();
        //elevator = new Elevator();
        //wrist = new Wrist();
        //claw = new Claw();
        intake = new Intake();
        arm = new ArmPID();
        hood = new HoodPID();
        turret = new Turret();
        
        // Instantiate after all subsystems - or you will die
        oi = new OI();
        //imageProcessor = new ImageProcessorGrip();
        
        // instantiate the command used for the autonomous period
        autonomousCommand = new Autonomous();

        // Get preferences from robot
        robotPreferences = new RobotPreferences();
        
        // Show what command your subsystem is running on the SmartDashboard
        SmartDashboard.putData(drivetrain);
        //SmartDashboard.putData(elevator);
        //SmartDashboard.putData(wrist);
        //SmartDashboard.putData(claw);
    }

    public void autonomousInit() {
        autonomousCommand.start(); // schedule the autonomous command (example)
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        log();
    }

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
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        log();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }

	/**
	 * The log method puts interesting information to the SmartDashboard.
	 */
    private void log() {
        //wrist.log();
        //elevator.log();
        drivetrain.log();
        //claw.log();
    }
}
