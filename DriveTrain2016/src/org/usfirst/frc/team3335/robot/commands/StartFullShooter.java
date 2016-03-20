package org.usfirst.frc.team3335.robot.commands;

import org.usfirst.frc.team3335.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class StartFullShooter extends CommandGroup {

  public StartFullShooter() {
    addParallel(new StartShooter(true));
    addParallel(new IntakeBoulder(1, true, Robot.robotPreferences.getFullShooterIntakeDelay()));
  }
}
