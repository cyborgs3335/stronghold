package org.macy.test;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.opencv.core.Mat;

public class ImagePanel extends JPanel {
  private Image bi;
  private Dimension panelDimension;
  private final JFrame parent;

  public ImagePanel(JFrame parent) {
    bi = null;
    panelDimension = new Dimension(100, 100);
    this.parent = parent;
  }

  @Override
  public Dimension getPreferredSize() {
    return panelDimension;
  }

  @Override
  public void paint(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    if (bi == null) {
      return;
    }
    g2d.drawImage(bi, 0, 0, null);
  }

  public void drawNewImage(Image image) {
    bi = image;
    if (bi.getWidth(null) != panelDimension.width || bi.getHeight(null) != panelDimension.height) {
      panelDimension.setSize(bi.getWidth(null), bi.getHeight(null));
      System.out.println("Setting size to " + panelDimension);
      this.setSize(panelDimension);
      this.revalidate();
      parent.pack();
    }
    repaint();
  }

  public void drawNewImage(Mat mat) {
    drawNewImage(toBufferedImage(mat));
  }

  public static ImagePanel createDisplayWindow() {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    ImagePanel panel = new ImagePanel(frame);
    frame.add(panel);
    frame.pack();
    frame.setVisible(true);
    return panel;
  }

  public static Image toBufferedImage(Mat m) {
    int type = BufferedImage.TYPE_BYTE_GRAY;
    if (m.channels() > 1) {
      type = BufferedImage.TYPE_3BYTE_BGR;
    }
    int bufferSize = m.channels() * m.cols() * m.rows();
    byte[] b = new byte[bufferSize];
    m.get(0, 0, b);
    BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
    final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
    System.arraycopy(b, 0, targetPixels, 0, b.length);
    return image;
  }

}
