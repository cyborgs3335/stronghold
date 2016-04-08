package org.usfirst.frc.team3335.robot.commands;

import org.usfirst.frc.team3335.robot.subsystems.Arm;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ArmPreset extends CommandGroup {
  // Lower arm to reset position, then fully raise
  public ArmPreset() {
    addSequential(new MoveArm(Arm.Direction.DOWN, 0));
    // addSequential(new MoveArm(Arm.Direction.UP, Arm.MAX_POSITION, 1));
    addSequential(new MoveArm(Arm.Direction.UP, 60, 1));
  }
}
