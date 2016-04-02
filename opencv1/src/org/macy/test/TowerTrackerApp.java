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
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.WindowConstants;

import org.macy.parameters.FloatParameter;
import org.macy.test.TowerTrackerNew.TargetInfo;
import org.macy.ui.FloatTextField;

/**
 * Tower tracker application, with graphical user interface.
 *
 * @author macybk@gmail.com
 *
 */
public class TowerTrackerApp {

  private final JFrame frame;
  private final ImagePanel imagePanel;
  private final ArrayList<FloatParameter> parameterList;
  private final FloatParameter distanceParameter;
  private final FloatParameter azimuthParameter;
  private final FloatParameter fpsParameter;
  private int cameraDeviceId = 0;
  private TowerTrackerMonitor trackerMonitor = null;

  /**
   * Create and start application.
   */
  public TowerTrackerApp() {
    frame = new JFrame();
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    imagePanel = new ImagePanel(frame);
    frame.setLayout(new BorderLayout());
    // frame.add(imagePanel, BorderLayout.CENTER);
    JScrollPane imageScrollPane = new JScrollPane(imagePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    imageScrollPane.setMinimumSize(imagePanel.getMinimumSize());
    imageScrollPane.setSize(imagePanel.getPreferredSize());
    // frame.add(imageScrollPane, BorderLayout.CENTER);

    // Create parameters
    parameterList = new ArrayList<FloatParameter>();
    distanceParameter = new FloatParameter("distanceParameter", "Distance", "Distance to target", 10, 0,
        -Float.MAX_VALUE, Float.MAX_VALUE);
    distanceParameter.setEditable(false);
    azimuthParameter = new FloatParameter("azimuthParameter", "Azimuth", "Azimuth from center of target", 10, 0,
        -Float.MAX_VALUE, Float.MAX_VALUE);
    azimuthParameter.setEditable(false);
    fpsParameter = new FloatParameter("fpsParameter", "Frames/sec", "Frames processed per second", 0, 0,
        -Float.MAX_VALUE, Float.MAX_VALUE);
    fpsParameter.setEditable(false);
    parameterList.add(distanceParameter);
    parameterList.add(azimuthParameter);
    parameterList.add(fpsParameter);
    JPanel trackerInfoPanel = createTrackerInfoPanel();
    JScrollPane parameterScrollPane = new JScrollPane(trackerInfoPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    parameterScrollPane.setMinimumSize(trackerInfoPanel.getPreferredSize());
    // frame.add(createTrackerInfoPanel(), BorderLayout.SOUTH);

    // Create a split pane with two scroll panes.
    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, imageScrollPane, parameterScrollPane);
    splitPane.setOneTouchExpandable(true);
    // splitPane.setDividerLocation(150);

    // Provide minimum sizes for the two components in the split pane
    // Dimension minimumSize = new Dimension(100, 50);
    // imageScrollPane.setMinimumSize(minimumSize);
    // parameterScrollPane.setMinimumSize(minimumSize);

    frame.add(splitPane);
    frame.pack();
    frame.setVisible(true);
  }

  public void runTracker() {
    trackerMonitor = new TowerTrackerMonitor(cameraDeviceId, imagePanel);
    new Thread(trackerMonitor).start();
  }

  public static void main(String[] args) {
    new TowerTrackerApp();
  }

  private JPanel createTrackerInfoPanel() {
    JPanel panel = new JPanel();
    Box vbox = Box.createVerticalBox();
    Box hbox = Box.createHorizontalBox();
    JComboBox<String> videoComboBox = getVideoDevicesComboBox();
    JButton startButton = new JButton("Start");
    JButton stopButton = new JButton("Stop");
    stopButton.setEnabled(false);
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

  private JComboBox<String> getVideoDevicesComboBox() {
    File devDir = new File("/dev");
    String[] videoDevices = devDir.list(new VideoFilenameFilter());
    Arrays.sort(videoDevices);
    JComboBox<String> cb = new JComboBox<String>(videoDevices);
    cb.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JComboBox<?>) {
          @SuppressWarnings("unchecked")
          JComboBox<String> c = (JComboBox<String>) e.getSource();
          String selectedItem = c.getItemAt(c.getSelectedIndex());
          // System.out.println("selected item: " + selectedItem);
          if (selectedItem.startsWith("/dev/video")) {
            cameraDeviceId = Integer.parseInt(selectedItem.substring("/dev/video".length()));
            // System.out.println("camera device id: " + cameraDeviceId);
            return;
          } else if (selectedItem.startsWith("video")) {
            cameraDeviceId = Integer.parseInt(selectedItem.substring("video".length()));
            // System.out.println("camera device id: " + cameraDeviceId);
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

    public TowerTrackerMonitor(int cameraDeviceId, ImagePanel imagePanel) {
      towerTracker = new TowerTrackerNew(cameraDeviceId, imagePanel);
      towerTracker.setVerbose(false);
    }

    @Override
    public void run() {
      new Thread(towerTracker).start();
      while (!stopMonitor) {
        TargetInfo info = towerTracker.getTargetInfo();
        distanceParameter.setValue((float) info.getDistance());
        azimuthParameter.setValue((float) info.getAzimuth());
        fpsParameter.setValue((float) towerTracker.getFramesPerSec());
      }
      towerTracker.stopTracker();
    }

    public void stopMonitor() {
      stopMonitor = true;
    }
  }
}
