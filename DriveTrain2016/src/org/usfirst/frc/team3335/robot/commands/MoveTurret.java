package org.usfirst.frc.team3335.robot.commands;

import org.usfirst.frc.team3335.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class MoveTurret extends Command {

  public static enum Direction {
    LEFT, RIGHT
  }

  private final Direction direction;

  public MoveTurret(Direction direction) {
    requires(Robot.turret);
    this.direction = direction;
  }

  @Override
  protected void initialize() {
    // TODO Auto-generated method stub
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
    return !Robot.turret.inLimits();
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
