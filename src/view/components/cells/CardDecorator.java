package view.components.cells;

import java.awt.*;
import javax.swing.*;
import model.enums.AttackValue;

public class CardDecorator extends CellDecorator {
  private static final int REQUIRED_VALUES = 4;
  private final AttackValue[] attackValues;

  public CardDecorator(CellComponent component, AttackValue[] attackValues) {
    super(component);
    validateAttackValues(attackValues);
    this.attackValues = attackValues.clone(); // Defensive copy
    customize(); // Call customize after super constructor
  }

  private void validateAttackValues(AttackValue[] values) {
    if (values == null || values.length != REQUIRED_VALUES) {
      throw new IllegalArgumentException("Exactly " + REQUIRED_VALUES + " attack values are required.");
    }
  }

  @Override
  public void customize() {
    button.setLayout(new GridBagLayout());
    addDirectionalLabels();
  }

  private void addDirectionalLabels() {
    addLabel(attackValues[0].toString(), 1, 0, new Insets(5, 0, 5, 0)); // North
    addLabel(attackValues[1].toString(), 1, 3, new Insets(5, 0, 5, 0)); // South
    addLabel(attackValues[2].toString(), 2, 1, new Insets(0, 5, 0, 5)); // East
    addLabel(attackValues[3].toString(), 0, 1, new Insets(0, 5, 0, 5)); // West
  }

  private void addLabel(String text, int x, int y, Insets padding) {
    JLabel label = new JLabel(text, SwingConstants.CENTER);

    // Set the font size
    Font font = new Font("Arial", Font.PLAIN, 23); // Change the font size as needed
    label.setFont(font);

    GridBagConstraints gbc = createConstraints(x, y, padding);
    button.add(label, gbc);
  }

  private GridBagConstraints createConstraints(int x, int y, Insets padding) {
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.BOTH;
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.gridx = x;
    gbc.gridy = y;
    gbc.insets = padding;
    return gbc;
  }
}
