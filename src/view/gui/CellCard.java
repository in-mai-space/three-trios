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

  /**
   * Construct the CellCard object.
   *
   * @param color color of the card
   * @param attackValues array of attack values of the card
   */
  private CellCard(Color color, AttackValue[] attackValues) {
    this.color = color;
    this.attackValues = attackValues;

    moveTo(0, 0);
    lineTo(GameViewConfig.DEFAULT_WIDTH, 0);
    lineTo(GameViewConfig.DEFAULT_WIDTH, GameViewConfig.DEFAULT_HEIGHT);
    lineTo(0, GameViewConfig.DEFAULT_HEIGHT);
    closePath();
  }

  /**
   * Draw the graphics.
   *
   * @param g2d graphic to be drawn
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

  /**
   * Draw all attack values on the card.
   *
   * @param g2d graphics to be drawn on
   */
  private void drawAttackValues(Graphics2D g2d) {
    float fontSize = (float)
            (Math.min(GameViewConfig.DEFAULT_WIDTH, GameViewConfig.DEFAULT_HEIGHT) * 0.2);
    Font font = new Font("Arial", Font.BOLD, (int) fontSize);
    g2d.setFont(font);
    g2d.setColor(Color.BLACK);

    drawAttackValue(g2d, attackValues[0], (float) GameViewConfig.DEFAULT_WIDTH / 2,
            (float) (GameViewConfig.DEFAULT_HEIGHT * 0.2));
    drawAttackValue(g2d, attackValues[1], (float) GameViewConfig.DEFAULT_WIDTH / 2,
            (float) (GameViewConfig.DEFAULT_HEIGHT * 0.8));
    drawAttackValue(g2d, attackValues[2], (float) (GameViewConfig.DEFAULT_WIDTH * 0.8),
            (float) (GameViewConfig.DEFAULT_HEIGHT * 0.5));
    drawAttackValue(g2d, attackValues[3], (float) (GameViewConfig.DEFAULT_WIDTH * 0.2),
            (float) (GameViewConfig.DEFAULT_HEIGHT * 0.5));
  }

  /**
   * Draw an attack value on the card.
   *
   * @param g2d graphics to be drawn on
   * @param value an attack value
   * @param xPos horizontal position on the card
   * @param yPos vertical position on the card
   */
  private void drawAttackValue(Graphics2D g2d, AttackValue value, float xPos, float yPos) {
    String attackValue = value.toString();
    g2d.drawString(attackValue,
            xPos - (float) g2d.getFontMetrics().stringWidth(attackValue) / 2, yPos);
  }
}
