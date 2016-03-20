package org.usfirst.frc.team3335.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class StartFullOuttake extends CommandGroup {
  public StartFullOuttake() {
    addParallel(new StartShooter(false));
    addParallel(new IntakeBoulder(-1, true));
  }
}
