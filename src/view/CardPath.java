package view;

import java.awt.geom.Path2D;

class CardPath extends Path2D.Double {
  private final double width;
  private final double height;

  public CardPath(double width, double height) {
    this.width = width;
    this.height = height;

    moveTo(0, 0);
    lineTo(width, 0);
    lineTo(width, height);
    lineTo(0, height);
    closePath();
  }

  public double getWidth() { return width; }
  public double getHeight() { return height; }
}
