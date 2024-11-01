package view;

import java.awt.*;
import java.awt.geom.Path2D;

import model.enums.AttackValue;

public class CellCardFrame extends Path2D.Double {
  private final Color color;
  private final AttackValue[] attackValues;

  private CellCardFrame(Color color, AttackValue[] attackValues) {
    this.color = color != null ? color : Color.WHITE;
    this.attackValues = attackValues;

    moveTo(0, 0);
    lineTo(ViewData.CELL_WIDTH, 0);
    lineTo(ViewData.CELL_WIDTH, ViewData.CELL_HEIGHT);
    lineTo(0, ViewData.CELL_HEIGHT);
    closePath();
  }

  public void draw(Graphics2D g2d) {
    g2d.setColor(color);
    g2d.fill(this);

    g2d.setColor(Color.BLACK);
    g2d.setStroke(new BasicStroke(2f));
    g2d.draw(this);

    if (attackValues != null) {
      drawAttackValues(g2d);
    }
  }

  private void drawAttackValues(Graphics2D g2d) {
    float fontSize = (float)(Math.min(ViewData.CELL_WIDTH, ViewData.CELL_HEIGHT) * 0.2);
    Font font = new Font("Arial", Font.BOLD, (int)fontSize);
    g2d.setFont(font);
    FontMetrics metrics = g2d.getFontMetrics();
    g2d.setColor(Color.BLACK);

    String topValue = String.valueOf(attackValues[0]);
    g2d.drawString(topValue,
            (float)(ViewData.CELL_WIDTH / 2 - metrics.stringWidth(topValue) / 2),
            (float)(ViewData.CELL_HEIGHT * 0.2));
    String rightValue = String.valueOf(attackValues[1]);
    g2d.drawString(rightValue,
            (float)(ViewData.CELL_WIDTH * 0.8 - metrics.stringWidth(rightValue) / 2),
            (float)(ViewData.CELL_HEIGHT * 0.5));
    String bottomValue = String.valueOf(attackValues[2]);
    g2d.drawString(bottomValue,
            (float)(ViewData.CELL_WIDTH / 2 - metrics.stringWidth(bottomValue) / 2),
            (float)(ViewData.CELL_HEIGHT * 0.8));
    String leftValue = String.valueOf(attackValues[3]);
    g2d.drawString(leftValue,
            (float)(ViewData.CELL_WIDTH * 0.2 - metrics.stringWidth(leftValue) / 2),
            (float)(ViewData.CELL_HEIGHT * 0.5));
  }

  public static class CardBuilder {
    private Color color = Color.WHITE;
    private AttackValue[] attackValues = null;

    public CardBuilder setColor(Color color) {
      this.color = color;
      return this;
    }

    public CardBuilder setAttackValues(AttackValue[] attackValues) {
      this.attackValues = attackValues;
      return this;
    }

    public CellCardFrame build() {
      return new CellCardFrame(color, attackValues);
    }
  }
}
