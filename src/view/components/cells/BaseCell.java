package view.components.cells;

import javax.swing.*;

public class BaseCell implements CellComponent {
  private final JButton button;

  public BaseCell() {
    this.button = new JButton();
    customize();
  }

  @Override
  public JButton getButton() {
    return button;
  }

  @Override
  public void customize() {
    button.setOpaque(true);
  }
}