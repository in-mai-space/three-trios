package view.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.BasicStroke;
import java.awt.geom.Path2D;

import model.enums.AttackValue;

/**
 * Represent a cell card on the grid. This class supports for rendering a card with its
 * color and attack values.
 */
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

  /**
   * Draw the graphics.
   *
   * @param g2d
   */
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

  /**
   * Represents the builder class for the class.
   */
  public static class CardBuilder {
    private Color color;
    private AttackValue[] attackValues;

    /**
     * Construct a new instance of CardBuilder.
     */
    public CardBuilder() {
      color = Color.WHITE; // default to white
      attackValues = new AttackValue[]{}; // default to empty
    }

    /**
     * Set the color of the card.
     *
     * @param color color of card
     * @return the CardBuilder
     */
    public CardBuilder setColor(Color color) {
      if (color == null) {
        throw new IllegalArgumentException("Color cannot be null");
      }
      this.color = color;
      return this;
    }

    /**
     * Set the attack values to draw them on the card.
     *
     * @param attackValues attack values of card
     * @return the CardBuilder
     */
    public CardBuilder setAttackValues(AttackValue[] attackValues) {
      this.attackValues = attackValues;
      return this;
    }

    /**
     * Create the instance of card after customization. Return an empty white card cell if
     * no customization is provided.
     *
     * @return a new instance of CellCard
     */
    CellCard build() {
      return new CellCard(color, attackValues);
    }
  }
}
