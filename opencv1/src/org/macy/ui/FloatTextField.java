package org.macy.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.macy.parameters.FloatParameter;

public class FloatTextField extends Box {

  private static final long serialVersionUID = 1L;

  private final FloatParameter parameter;
  private final JLabel label;
  private final JTextField textField;

  public FloatTextField(FloatParameter floatParameter) {
    super(BoxLayout.X_AXIS);
    parameter = floatParameter;
    label = new JLabel(parameter.getName());
    label.setToolTipText(parameter.getDescription());
    textField = new JTextField("" + parameter.getValue(), parameter.getWidth());
    textField.setMinimumSize(textField.getPreferredSize()); // not sure this has
                                                            // any effect

    this.add(label);
    this.add(textField);

    textField.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        parameter.setValue(Float.parseFloat(textField.getText()));
      }
    });
    parameter.addPropertyChangeListener(new PropertyChangeListener() {

      @Override
      public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        if ("value".equals(propertyName)) {
          textField.setText("" + evt.getNewValue());
        }
      }
    });
  }

  // TODO add support for enabling, disabling, delegate methods for getting
  // value, etc.
}
