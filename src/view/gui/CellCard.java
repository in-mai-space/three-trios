package view.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.BasicStroke;
import java.awt.geom.Path2D;

import model.enums.AttackValue;

class CellCard extends Path2D.Double {
  private final Color color;
  private final AttackValue[] attackValues;

  private CellCard(Color color, AttackValue[] attackValues) {
    this.color = color;
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

    if (attackValues.length > 0) {
      drawAttackValues(g2d);
    }
  }

  private void drawAttackValues(Graphics2D g2d) {
    float fontSize = (float) (Math.min(ViewData.CELL_WIDTH, ViewData.CELL_HEIGHT) * 0.2);
    Font font = new Font("Arial", Font.BOLD, (int) fontSize);
    g2d.setFont(font);
    g2d.setColor(Color.BLACK);

    drawAttackValue(g2d, attackValues[0], (float) ViewData.CELL_WIDTH / 2, (float) (ViewData.CELL_HEIGHT * 0.2));
    drawAttackValue(g2d, attackValues[1], (float) ViewData.CELL_WIDTH / 2, (float) (ViewData.CELL_HEIGHT * 0.8));
    drawAttackValue(g2d, attackValues[2], (float) (ViewData.CELL_WIDTH * 0.8), (float) (ViewData.CELL_HEIGHT * 0.5));
    drawAttackValue(g2d, attackValues[3], (float) (ViewData.CELL_WIDTH * 0.2), (float) (ViewData.CELL_HEIGHT * 0.5));
  }

  private void drawAttackValue(Graphics2D g2d, AttackValue value, float x, float y) {
    String attackValue = value.toString();
    g2d.drawString(attackValue, x - (float) g2d.getFontMetrics().stringWidth(attackValue) / 2, y);
  }

  public static class CardBuilder {
    private Color color;
    private AttackValue[] attackValues;
    public CardBuilder() {
      color = Color.WHITE; // default to white
      attackValues = new AttackValue[]{}; // default to empty
    }

    public CardBuilder setColor(Color color) {
      this.color = color;
      return this;
    }

    public CardBuilder setAttackValues(AttackValue[] attackValues) {
      this.attackValues = attackValues;
      return this;
    }

    CellCard build() {
      return new CellCard(color, attackValues);
    }
  }
}
