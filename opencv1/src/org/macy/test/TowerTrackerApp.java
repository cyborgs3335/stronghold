package org.macy.test;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class TowerTrackerApp {

  private final JFrame frame;
  private final ImagePanel imagePanel;
  private final TowerTrackerNew towerTracker;

  public TowerTrackerApp() {
    frame = new JFrame();
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    imagePanel = new ImagePanel(frame);
    frame.add(imagePanel);
    frame.pack();
    frame.setVisible(true);
    towerTracker = new TowerTrackerNew(0, imagePanel);
  }

  public void run() {
    towerTracker.run();
  }

  public static void main(String[] args) {
    new TowerTrackerApp().run();
  }
}
