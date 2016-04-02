package org.usfirst.frc.team3335.robot.commands;

import org.usfirst.frc.team3335.robot.Robot;
import org.usfirst.frc.team3335.robot.subsystems.Arm;

import edu.wpi.first.wpilibj.command.Command;

public class MoveArm extends Command {

  private final Arm.Direction direction;
  private final Arm arm;
  private final double targetPosition;

  /**
   * Move arm to desired position. If direction is down, ignore position. Useful
   * to reset to position 0.
   * 
   * @param direction
   *          up or down
   * @param targetPosition
   *          if direction is down, ignore position
   */
  public MoveArm(Arm.Direction direction, double targetPosition) {
    requires(Robot.arm);
    arm = Robot.arm;
    this.direction = direction;
    this.targetPosition = targetPosition;
  }

  @Override
  protected void initialize() {
    // TODO Auto-generated method stub
  }

  @Override
  protected void execute() {
    switch (direction) {
      case UP:
        moveArm(1);
        break;
      case DOWN:
        moveArm(-1);
        break;
    }
  }

  /**
   * Move arm with specified speed.
   *
   * @param speed
   *          speed to move arm, in range [-1:1]
   */
  private void moveArm(double speed) {
    // double scalar = Robot.robotPreferences.getArmDriveScalar();
    // System.out.println(this.getClass().getName() + ": execute");
    // arm.rotate(scalar * speed);
    switch (direction) {
      case DOWN:
        arm.rotate(0.3);
        break;
      case UP:
        arm.rotate(-0.4);
        break;
    }
  }

  @Override
  protected boolean isFinished() {
    if (direction.equals(Arm.Direction.UP)) {
      return !Robot.arm.canMove(direction) || Robot.arm.getAngularPosition() >= targetPosition;
    }
    return !Robot.arm.canMove(direction);
  }

  @Override
  protected void end() {
    arm.rotate(0);
  }

  @Override
  protected void interrupted() {
    end();
  }

}
