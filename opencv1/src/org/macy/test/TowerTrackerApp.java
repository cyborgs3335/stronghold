package org.macy.test;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.macy.parameters.FloatParameter;
import org.macy.test.TowerTrackerNew.TargetInfo;
import org.macy.ui.FloatTextField;

public class TowerTrackerApp {

  private final JFrame frame;
  private final ImagePanel imagePanel;
  private final ArrayList<FloatParameter> parameterList;
  private final FloatParameter distanceParameter;
  private final FloatParameter azimuthParameter;
  private int cameraDeviceId = 0;
  private TowerTrackerMonitor trackerMonitor = null;

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
    // towerTracker = new TowerTrackerNew(cameraDeviceId, imagePanel);
  }

  public void runTracker() {
    trackerMonitor = new TowerTrackerMonitor();
    new Thread(trackerMonitor).start();
    // towerTracker = new TowerTrackerNew(cameraDeviceId, imagePanel);
    // towerTracker.setVerbose(false);
    // new Thread(towerTracker).start();
    // // int count = 0;
    // while (true) {
    // TargetInfo info = towerTracker.getTargetInfo();
    // // System.out.println("info: " + count++ + " distance " +
    // // info.getDistance() + " azimuth " + info.getAzimuth());
    // distanceParameter.setValue((float) info.getDistance());
    // azimuthParameter.setValue((float) info.getAzimuth());
    // }
  }

  public static void main(String[] args) {
    new TowerTrackerApp();// .run();
  }

  private JPanel createTrackerInfoPanel() {
    JPanel panel = new JPanel();
    Box vbox = Box.createVerticalBox();
    Box hbox = Box.createHorizontalBox();
    JComboBox<String> videoComboBox = getVideoDevices();
    JButton startButton = new JButton("Start");
    JButton stopButton = new JButton("Stop");
    hbox.add(videoComboBox);
    hbox.add(startButton);
    hbox.add(stopButton);
    vbox.add(hbox);

    // Action listeners
    startButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        runTracker();
        startButton.setEnabled(false);
        videoComboBox.setEnabled(false);
        stopButton.setEnabled(true);
      }
    });
    stopButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (trackerMonitor != null) {
          trackerMonitor.stopMonitor();
        }
        startButton.setEnabled(true);
        videoComboBox.setEnabled(true);
        stopButton.setEnabled(false);
      }
    });

    for (FloatParameter p : parameterList) {
      vbox.add(new FloatTextField(p));
    }
    panel.add(vbox);
    return panel;
  }

  private class VideoFilenameFilter implements FilenameFilter {
    @Override
    public boolean accept(File dir, String name) {
      return name.startsWith("video");
    }
  }

  private JComboBox<String> getVideoDevices() {
    File devDir = new File("/dev");
    String[] videoDevices = devDir.list(new VideoFilenameFilter());
    Arrays.sort(videoDevices);
    JComboBox<String> cb = new JComboBox<String>(videoDevices);
    cb.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JComboBox<?>) {
          JComboBox<String> c = (JComboBox<String>) e.getSource();
          String selectedItem = c.getItemAt(c.getSelectedIndex());
          System.out.println("selected item: " + selectedItem);
          if (selectedItem.startsWith("/dev/video")) {
            cameraDeviceId = Integer.parseInt(selectedItem.substring("/dev/video".length()));
            System.out.println("camera device id: " + cameraDeviceId);
            return;
          } else if (selectedItem.startsWith("video")) {
            cameraDeviceId = Integer.parseInt(selectedItem.substring("video".length()));
            System.out.println("camera device id: " + cameraDeviceId);
            return;
          }
        }
      }
    });
    cb.setSelectedIndex(0);
    return cb;
  }

  private class TowerTrackerMonitor implements Runnable {
    private final TowerTrackerNew towerTracker;
    private volatile boolean stopMonitor = false;

    public TowerTrackerMonitor() {
      towerTracker = new TowerTrackerNew(cameraDeviceId, imagePanel);
      towerTracker.setVerbose(false);
    }

    @Override
    public void run() {
      new Thread(towerTracker).start();
      // int count = 0;
      while (!stopMonitor) {
        TargetInfo info = towerTracker.getTargetInfo();
        // System.out.println("info: " + count++ + " distance " +
        // info.getDistance() + " azimuth " + info.getAzimuth());
        distanceParameter.setValue((float) info.getDistance());
        azimuthParameter.setValue((float) info.getAzimuth());
      }
      towerTracker.stopTracker();
    }

    public void stopMonitor() {
      stopMonitor = true;
    }
  }
}
