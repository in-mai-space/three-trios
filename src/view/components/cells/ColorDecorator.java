package view.components.cells;

import java.awt.Color;

import javax.swing.*;

public class ColorDecorator extends CellDecorator {
  private final Color color;

  public ColorDecorator(CellComponent component, Color color) {
    super(component);
    this.color = color;
    customize();
  }

  @Override
  public void customize() {
    button.setBackground(color);
    button.setOpaque(true);
    button.setBorder(BorderFactory.createEmptyBorder());
    button.setForeground(Color.BLACK);
  }
}