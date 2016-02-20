/**
 *
 */
package org.usfirst.frc.team3335.robot.subsystems;

import java.util.Arrays;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author jacob
 *
 */
public class LEDStrip extends Subsystem {

  /*
   * (non-Javadoc)
   *
   * @see edu.wpi.first.wpilibj.command.Subsystem#initDefaultCommand()
   */
  SPI spi;
  int bitrate = 4000000;
  int striplength = 120;

  LEDStrip() {
    spi = new SPI(Port.kOnboardCS0);
    spi.setClockRate(bitrate);
    spi.setMSBFirst();
    spi.setSampleDataOnFalling();
    spi.setClockActiveLow();
    spi.setChipSelectActiveLow();
  }

  public boolean startFrame() {
    byte[] header = new byte[4];
    Arrays.fill(header, (byte) 0x00);
    return true;
  }

  public boolean emptyFrame() {
    byte[] cmd = new byte[4];
    Arrays.fill(cmd, (byte) 0x00);
    cmd[0] = (byte) 0xE0;
    return true;
  }

  @Override
  protected void initDefaultCommand() {
    // TODO Auto-generated method stub

  }

}
