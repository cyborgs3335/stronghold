package org.usfirst.frc.team3335.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class StopFullOuttake extends CommandGroup {

  public StopFullOuttake() {
    addParallel(new StopShooter(true));
    addParallel(new StopIntake(true));
  }
}
