package org.macy.test;

public interface IObjectTracker extends Runnable {

  /**
   * Set whether logging is verbose.
   * 
   * @param verbose
   *          true to make logging verbose
   */
  public void setVerbose(boolean verbose);

  public TargetInfo getTargetInfo();

  public double getFramesPerSec();

  public void stopTracker();
}
