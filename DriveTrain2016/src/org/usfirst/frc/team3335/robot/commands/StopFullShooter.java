package org.usfirst.frc.team3335.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class StopFullShooter extends CommandGroup {

  public StopFullShooter() {
    addParallel(new StopShooter(true));
    addParallel(new StopIntake(true));
  }
}
