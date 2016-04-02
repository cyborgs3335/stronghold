package org.usfirst.frc.team3335.robot.commands;

import org.usfirst.frc.team3335.robot.Robot;
import org.usfirst.frc.team3335.robot.subsystems.Arm;

import edu.wpi.first.wpilibj.command.Command;

public class MoveArmUp extends Command {

  private final Arm.Direction direction;
  private final Arm arm;
  private final double targetPosition;

  public MoveArmUp(double targetPosition) {
    requires(Robot.arm);
    arm = Robot.arm;
    this.direction = Arm.Direction.UP;
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
    arm.rotate(-0.4);
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
