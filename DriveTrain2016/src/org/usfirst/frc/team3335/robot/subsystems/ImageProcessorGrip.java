package org.usfirst.frc.team3335.robot.subsystems;

import java.io.IOException;

import org.usfirst.frc.team3335.robot.commands.ProcessImage;

import edu.wpi.first.wpilibj.command.Subsystem;

public class ImageProcessorGrip extends Subsystem {

  private final static String[] GRIP_ARGS = new String[] { "/usr/local/frc/JRE/bin/java", "-jar",
      "/home/lvuser/grip.jar", "/home/lvuser/project.grip" };

  private Process gripProcess;

  public ImageProcessorGrip() {
    try {
      gripProcess = Runtime.getRuntime().exec(GRIP_ARGS);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void initDefaultCommand() {
    this.setDefaultCommand(new ProcessImage());
  }

  public void terminate() {
    if (gripProcess != null) {
      gripProcess.destroy();
    }
  }

}
