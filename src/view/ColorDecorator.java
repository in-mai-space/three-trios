package view;

import java.awt.*;

class ColorDecorator extends CardDecorator {
  private final Color color;

  public ColorDecorator(CardPath cardPath, Color color) {
    super(cardPath);
    this.color = color;
  }

  @Override
  public void draw(Graphics2D g2d) {
    g2d.setColor(color);
    g2d.fill(cardPath);
    g2d.setColor(Color.BLACK);
    g2d.setStroke(new BasicStroke(2f));
    g2d.draw(cardPath);
  }
}