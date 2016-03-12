package org.usfirst.frc.team3335.robot.commands;

import org.usfirst.frc.team3335.robot.Robot;
import org.usfirst.frc.team3335.robot.subsystems.Hood;

import edu.wpi.first.wpilibj.command.Command;

public class MoveHood extends Command {

  private final Hood.Direction direction;
  private final Hood hood;
  private final double targetPosition;

  public MoveHood(Hood.Direction direction, double targetPosition) {
    requires(Robot.hood);
    hood = Robot.hood;
    this.direction = direction;
    this.targetPosition = targetPosition;
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
        // Robot.hood.start(true); // forward motor
        moveHood(1);
        break;
      case DOWN:
        // Robot.hood.start(false); // reverse motor
        moveHood(-1);
        break;
    }
  }

  /**
   * Move hood with specified speed.
   *
   * @param speed
   *          speed to move hood, in range [-1:1]
   */
  private void moveHood(double speed) {
    double scalar = Robot.robotPreferences.getHoodDriveScalar();
    // System.out.println(this.getClass().getName() + ": execute");
    hood.rotate(scalar * speed);
  }

  @Override
  protected boolean isFinished() {
    // return Robot.hood.isSwitchUpSet() || Robot.hood.isSwitchDownSet() ||
    // !Robot.hood.canMove(direction);
    if (direction.equals(Hood.Direction.UP)) {
      return !Robot.hood.canMove(direction) || Robot.hood.getAngularPosition() >= targetPosition;
    }
    return !Robot.hood.canMove(direction);
  }

  @Override
  protected void end() {
    // Robot.hood.stop();
    hood.rotate(0);
  }

  @Override
  protected void interrupted() {
    end();
  }

}
