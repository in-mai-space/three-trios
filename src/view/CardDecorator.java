package view;

import java.awt.*;

abstract class CardDecorator {
  protected final CardPath cardPath;

  public CardDecorator(CardPath cardPath) {
    this.cardPath = cardPath;
  }

  public abstract void draw(Graphics2D g2d);
}