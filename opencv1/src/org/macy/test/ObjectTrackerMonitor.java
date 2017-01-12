package org.macy.test;

import java.util.Observable;

import jssc.SerialPortException;

public class ObjectTrackerMonitor extends Observable implements Runnable {
  private final IObjectTracker objectTracker;
  private volatile boolean stopMonitor = false;
  private final SerialPortOutput comm;
  private float distance;
  private float azimuth;
  private float framesPerSecond;

  public ObjectTrackerMonitor(IObjectTracker objectTracker, String portName) {
    this.objectTracker = objectTracker;
    objectTracker.setVerbose(false);
    if (portName.equals("NONE")) {
      comm = null;
    } else {
      try {
        comm = new SerialPortOutput(portName, false/* true */);
      } catch (SerialPortException e) {
        throw new RuntimeException("Caught serial port exception. Exiting...", e);
      }
    }
  }

  @Override
  public void run() {
    new Thread(objectTracker).start();
    long lastTime = System.currentTimeMillis();
    while (!stopMonitor) {
      TargetInfo info = objectTracker.getTargetInfo();
      distance = (float) info.getDistance();
      azimuth = (float) info.getAzimuth();
      framesPerSecond = (float) objectTracker.getFramesPerSec();
      setChanged();
      notifyObservers();
      if (comm != null) {
        long curTime = System.currentTimeMillis();
        if (curTime - lastTime < 3000) {
          continue;
        }
        lastTime = curTime;
        float azimuth = (float) info.getAzimuth();
        float sign = Math.signum(azimuth);
        azimuth = sign * Math.min(Math.abs(azimuth), 15);
        azimuth = Math.abs(azimuth) < 0.5 ? 0 : azimuth;
        System.out.println("azimuth = " + azimuth);
        comm.writeString("" + Math.round(azimuth));
      }
    }
    objectTracker.stopTracker();
  }

  public float getDistance() {
    return distance;
  }

  public float getAzimuth() {
    return azimuth;
  }

  public float getFramesPerSecond() {
    return framesPerSecond;
  }

  public void stopMonitor() {
    this.deleteObservers();
    stopMonitor = true;
  }
}
