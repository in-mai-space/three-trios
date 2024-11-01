package view;

import java.awt.*;

import model.enums.AttackValue;

class AttackValuesDecorator extends CardDecorator {
  private final AttackValue[] values;

  public AttackValuesDecorator(CardPath cardPath, AttackValue[] values) {
    super(cardPath);
    this.values = values;
  }

  @Override
  public void draw(Graphics2D g2d) {
    double width = cardPath.getWidth();
    double height = cardPath.getHeight();

    // Scale font based on card size
    float fontSize = (float)(Math.min(width, height) * 0.2);
    Font font = new Font("Arial", Font.BOLD, (int)fontSize);
    g2d.setFont(font);

    FontMetrics metrics = g2d.getFontMetrics();
    g2d.setColor(Color.BLACK);

    String topValue = String.valueOf(values[0]);
    g2d.drawString(topValue,
            (float)(width/2 - metrics.stringWidth(topValue)/2),
            (float)(height * 0.2));
    String rightValue = String.valueOf(values[1]);
    g2d.drawString(rightValue,
            (float)(width * 0.8 - metrics.stringWidth(rightValue)/2),
            (float)(height * 0.5));
    String bottomValue = String.valueOf(values[2]);
    g2d.drawString(bottomValue,
            (float)(width/2 - metrics.stringWidth(bottomValue)/2),
            (float)(height * 0.8));
    String leftValue = String.valueOf(values[3]);
    g2d.drawString(leftValue,
            (float)(width * 0.2 - metrics.stringWidth(leftValue)/2),
            (float)(height * 0.5));
  }
}