package view.components.cells;

import javax.swing.*;

public abstract class CellDecorator implements CellComponent {
  protected final CellComponent wrapped;
  protected final JButton button;

  protected CellDecorator(CellComponent component) {
    this.wrapped = component;
    this.button = component.getButton();
  }

  @Override
  public JButton getButton() {
    return button;
  }
}
