package org.usfirst.frc.team3335.robot.commands;

import org.usfirst.frc.team3335.robot.subsystems.HoodPID;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LowerHood extends CommandGroup {

  public LowerHood() {
    // addSequential(new SetTurretPosition(Turret.CENTER_POSITION));
    addSequential(new SetHoodPosition(HoodPID.FULLY_LOWERED));
  }
}
