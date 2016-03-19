package org.usfirst.frc.team3335.robot.commands;

import org.usfirst.frc.team3335.robot.Robot;
import org.usfirst.frc.team3335.robot.subsystems.Turret;
import org.usfirst.frc.team3335.robot.subsystems.Turret.Direction;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Reset the turret position by rotating the turret until the center limit
 * switch is tripped. The turret encoder position is set to zero at the center
 * limit switch position.
 */
public class ResetTurretPosition extends Command {

  private Turret.Direction direction;

  public ResetTurretPosition() {
    requires(Robot.turret);
    direction = Robot.turret.getAngularPosition() > 0 ? Direction.COUNTER_CLOCKWISE : Direction.CLOCKWISE;
  }

  @Override
  protected void initialize() {
    // Robot.turret.intializeCWCounter();
    // Robot.turret.intializeCCWCounter();
  }

  @Override
  protected void execute() {
    switch (direction) {
      case COUNTER_CLOCKWISE:
        Robot.turret.start(true); // forward motor
        break;
      case CLOCKWISE:
        Robot.turret.start(false); // reverse motor
        break;
    }
  }

  @Override
  protected boolean isFinished() {
    if (Robot.turret.isCenterSwitchSet()) {
      return true;
    }
    return false;
  }

  @Override
  protected void end() {
    Robot.turret.stop();
  }

  @Override
  protected void interrupted() {
    end();
  }

}
