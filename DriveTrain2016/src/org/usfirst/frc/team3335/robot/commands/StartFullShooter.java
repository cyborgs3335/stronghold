package org.usfirst.frc.team3335.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class StartFullShooter extends CommandGroup {

  public StartFullShooter() {
    addParallel(new StartShooter());
    addParallel(new IntakeBoulder(1));
  }
}
