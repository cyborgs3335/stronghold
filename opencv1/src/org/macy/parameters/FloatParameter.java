package org.macy.parameters;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class FloatParameter {

  private final String id;
  private String name;
  private String description;
  private int width;
  private float value;
  private final float defaultValue;
  private float minValue;
  private float maxValue;
  private final PropertyChangeSupport pcs;

  public FloatParameter(String id, String name, String description, int width, float defaultValue, float minValue,
      float maxValue) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.value = defaultValue;
    this.defaultValue = defaultValue;
    this.minValue = minValue;
    this.maxValue = maxValue;
    pcs = new PropertyChangeSupport(this);
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    String nameOld = this.name;
    this.name = name;
    pcs.firePropertyChange("name", nameOld, name);
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    String descriptionOld = this.description;
    this.description = description;
    pcs.firePropertyChange("description", descriptionOld, description);
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    float widthOld = this.width;
    this.width = width;
    pcs.firePropertyChange("width", widthOld, width);
  }

  public float getDefaultValue() {
    return defaultValue;
  }

  public float getValue() {
    return value;
  }

  public void setValue(float value) {
    float valueOld = this.value;
    this.value = value;
    pcs.firePropertyChange("value", valueOld, value);
  }

  public float getMinValue() {
    return minValue;
  }

  public void setMinValue(float minValue) {
    float minValueOld = this.minValue;
    this.minValue = minValue;
    pcs.firePropertyChange("minValue", minValueOld, minValue);
  }

  public float getMaxValue() {
    return maxValue;
  }

  public void setMaxValue(float maxValue) {
    float maxValueOld = this.maxValue;
    this.maxValue = maxValue;
    pcs.firePropertyChange("maxValue", maxValueOld, maxValue);
  }

  public void addPropertyChangeListener(PropertyChangeListener listener) {
    pcs.addPropertyChangeListener(listener);
  }

  public void removePropertyChangeListener(PropertyChangeListener listener) {
    pcs.removePropertyChangeListener(listener);
  }
}
