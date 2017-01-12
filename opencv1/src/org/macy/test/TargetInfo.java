package org.macy.test;

public class TargetInfo {
  private final double distance;
  private final double azimuth;
  private final double cx;
  private final double cy;
  private final double area;
  private final double contourArea;
  private final double areaRatio;
  private final double momentOfInertia;
  private final double aspectRatio;

  public TargetInfo(double distance, double azimuth, double cx, double cy, double area, double contourArea,
      double areaRatio, double momentOfInertia, double aspectRatio) {
    this.distance = distance;
    this.azimuth = azimuth;
    this.cx = cx;
    this.cy = cy;
    this.area = area;
    this.contourArea = contourArea;
    this.areaRatio = areaRatio;
    this.momentOfInertia = momentOfInertia;
    this.aspectRatio = aspectRatio;
  }

  public double getDistance() {
    return distance;
  }

  public double getAzimuth() {
    return azimuth;
  }

  public double getCx() {
    return cx;
  }

  public double getCy() {
    return cy;
  }

  public double getArea() {
    return area;
  }

  public double getContourArea() {
    return contourArea;
  }

  public double getAreaRatio() {
    return areaRatio;
  }

  public double getMomentOfInertia() {
    return momentOfInertia;
  }

  public double getAspectRatio() {
    return aspectRatio;
  }
}
