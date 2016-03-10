package org.usfirst.frc.team3335.robot.commands;

import org.usfirst.frc.team3335.robot.subsystems.ImageNIVisionStream;

import edu.wpi.first.wpilibj.command.Command;

public class ShowCamera extends Command {

  private final ImageNIVisionStream visionSystem;

  public ShowCamera(ImageNIVisionStream visionSystem) {
    this.visionSystem = visionSystem;
    requires(visionSystem);
  }

  @Override
  protected void initialize() {
    visionSystem.init();
  }

  @Override
  protected void execute() {
    visionSystem.processImage();
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    visionSystem.stop();
  }

  @Override
  protected void interrupted() {
    end();
  }

}
