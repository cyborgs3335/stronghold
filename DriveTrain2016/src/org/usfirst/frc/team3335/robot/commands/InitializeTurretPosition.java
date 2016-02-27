package org.usfirst.frc.team3335.robot.commands;

import org.usfirst.frc.team3335.robot.Robot;
import org.usfirst.frc.team3335.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Initialize the turret position by rotating the turret until the counter
 * clockwise limit switch is tripped. The turret encoder position is set to zero
 * at the counter clockwise limit switch position.
 */
public class InitializeTurretPosition extends Command {

  private Turret.Direction direction;

  public InitializeTurretPosition() {
    direction = Turret.Direction.COUNTER_CLOCKWISE;
    requires(Robot.turret);
  }

  @Override
  protected void initialize() {
    Robot.turret.intializeCWCounter();
    Robot.turret.intializeCCWCounter();
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
    if (Robot.turret.isSwitchCCWSet()) {
      // Assumes encoder position 0 is at CCW limit switch
      Robot.turret.resetEncoder();
      // TODO set position at CCW
      return true;
    } else if (Robot.turret.isSwitchCWSet()) {
      // TODO set position at CW
      return true;
    }
    return false;
    // return Robot.turret.isSwitchCWSet() || Robot.turret.isSwitchCCWSet() ||
    // !Robot.turret.canMove(direction);
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
