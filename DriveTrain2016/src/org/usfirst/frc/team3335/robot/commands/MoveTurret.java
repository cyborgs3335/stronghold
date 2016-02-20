package org.usfirst.frc.team3335.robot.commands;

import org.usfirst.frc.team3335.robot.Robot;
import org.usfirst.frc.team3335.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.command.Command;

public class MoveTurret extends Command {

  private final Turret.Direction direction;

  public MoveTurret(Turret.Direction direction) {
    requires(Robot.turret);
    this.direction = direction;
  }

  @Override
  protected void initialize() {
    Robot.turret.intializeCWCounter();
    Robot.turret.intializeCCWCounter();
  }

  @Override
  protected void execute() {
    switch (direction) {
      case LEFT:
        Robot.turret.start(true); // forward motor
        break;
      case RIGHT:
        Robot.turret.start(false); // reverse motor
        break;
    }
  }

  @Override
  protected boolean isFinished() {
    return Robot.turret.isSwitchCWSet() || Robot.turret.isSwitchCCWSet() || !Robot.turret.canMove(direction);
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
