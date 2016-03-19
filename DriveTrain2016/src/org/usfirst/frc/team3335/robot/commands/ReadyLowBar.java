package org.usfirst.frc.team3335.robot.commands;

import org.usfirst.frc.team3335.robot.subsystems.Hood;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ReadyLowBar extends CommandGroup {
  public ReadyLowBar() {
    addSequential(new ResetTurretPosition());
    addSequential(new MoveHood(Hood.Direction.DOWN, 0));
  }
}
