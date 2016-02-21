package org.usfirst.frc.team3335.robot.commands;

import org.usfirst.frc.team3335.robot.Robot;
import org.usfirst.frc.team3335.robot.subsystems.Hood;

import edu.wpi.first.wpilibj.command.Command;

public class MoveHood extends Command {

  private final Hood.Direction direction;

  public MoveHood(Hood.Direction direction) {
    requires(Robot.hood);
    this.direction = direction;
  }

  @Override
  protected void initialize() {
    // Robot.hood.intializeUpCounter();
    // Robot.hood.intializeDownCounter();
  }

  @Override
  protected void execute() {
    switch (direction) {
      case UP:
        Robot.hood.start(true); // forward motor
        break;
      case DOWN:
        Robot.hood.start(false); // reverse motor
        break;
    }
  }

  @Override
  protected boolean isFinished() {
    // return Robot.hood.isSwitchUpSet() || Robot.hood.isSwitchDownSet() ||
    // !Robot.hood.canMove(direction);
    return !Robot.hood.canMove(direction);
  }

  @Override
  protected void end() {
    Robot.hood.stop();
  }

  @Override
  protected void interrupted() {
    end();
  }

}
