package org.macy.test;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.macy.parameters.FloatParameter;
import org.macy.test.TowerTrackerNew.TargetInfo;
import org.macy.ui.FloatTextField;

public class TowerTrackerApp {

  private final JFrame frame;
  private final ImagePanel imagePanel;
  private final TowerTrackerNew towerTracker;
  private final ArrayList<FloatParameter> parameterList;
  private final FloatParameter distanceParameter;
  private final FloatParameter azimuthParameter;

  public TowerTrackerApp() {
    frame = new JFrame();
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    imagePanel = new ImagePanel(frame);
    frame.setLayout(new BorderLayout());
    frame.add(imagePanel, BorderLayout.CENTER);

    // Create parameters
    parameterList = new ArrayList<FloatParameter>();
    distanceParameter = new FloatParameter("distanceParameter", "Distance", "Distance to target", 10, 0,
        -Float.MAX_VALUE, Float.MAX_VALUE);
    azimuthParameter = new FloatParameter("azimuthParameter", "Azimuth", "Azimuth from center of target", 10, 0,
        -Float.MAX_VALUE, Float.MAX_VALUE);
    parameterList.add(distanceParameter);
    parameterList.add(azimuthParameter);

    frame.add(createTrackerInfoPanel(), BorderLayout.SOUTH);
    frame.pack();
    frame.setVisible(true);
    towerTracker = new TowerTrackerNew(0, imagePanel);
  }

  public void run() {
    towerTracker.setVerbose(false);
    new Thread(towerTracker).start();
    // int count = 0;
    while (true) {
      TargetInfo info = towerTracker.getTargetInfo();
      // System.out.println("info: " + count++ + " distance " +
      // info.getDistance() + " azimuth " + info.getAzimuth());
      distanceParameter.setValue((float) info.getDistance());
      azimuthParameter.setValue((float) info.getAzimuth());
    }
  }

  public static void main(String[] args) {
    new TowerTrackerApp().run();
  }

  private JPanel createTrackerInfoPanel() {
    JPanel panel = new JPanel();
    Box vbox = Box.createVerticalBox();
    for (FloatParameter p : parameterList) {
      vbox.add(new FloatTextField(p));
    }
    panel.add(vbox);
    return panel;
  }
}
