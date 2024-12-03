package provider.view;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.FontMetrics;

import provider.controller.PlayerActionFeatures;
import provider.model.CardColor;
import provider.model.ThreeTriosCard;
import provider.model.ThreeTriosGameModel;

/**
 * Creates the game panel, illustrates the grid, cards,
 * text on the cards, and hands and uses the3
 * GridClickListener to highlight, select, and deselect
 * cards.
 */
public class ThreeTriosGamePanel extends JPanel implements GamePanel {
  private final ThreeTriosGameModel<ThreeTriosCard> model;
  private int cellSize;
  private int selectedRedCardIndex = -1;
  private int selectedBlueCardIndex = -1;
  private static final Color PASTEL_PINK = new Color(246, 194, 202);
  private static final Color PASTEL_BLUE = new Color(186, 228, 241);
  private static final Color PASTEL_GREEN = new Color(188, 234, 188);
  private static final Color PASTEL_YELLOW = new Color(253, 253, 150);
  private PlayerActionFeatures playerActionFeatures;

  /**
   * Creates an instance of the ThreeTriosGamePanel.
   */
  public ThreeTriosGamePanel(ThreeTriosGameModel<ThreeTriosCard> model) {
    this.model = model;
  }

  /**
   * Used to initialize the PlayerActionFeatures.
   * @param playerActionFeatures a controller that subscribes to the view
   */
  public void setFeatures(PlayerActionFeatures playerActionFeatures) {
    this.playerActionFeatures = playerActionFeatures;
    GridClickListener gridClickListener = new GridClickListener(this);
    this.addMouseListener(gridClickListener);
  }

  public int getCellSize() {
    return cellSize;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    int panelWidth = getWidth();
    int panelHeight = getHeight();
    int centerGridCols = model.getGrid()[0].length;
    int centerGridRows = model.getGrid().length;

    cellSize = Math.min(panelWidth / (centerGridCols + 2), panelHeight / centerGridRows);
    int cardWidth = cellSize;

    int centerGridHeight = centerGridRows * cellSize;

    int redHandSize = model.getRedHand().size();
    int blueHandSize = model.getBlueHand().size();

    int redCardHeight = redHandSize > 0 ? centerGridHeight / redHandSize : cellSize;
    int blueCardHeight = blueHandSize > 0 ? centerGridHeight / blueHandSize : cellSize;

    int redHandWidth = cellSize - cardWidth;
    int redHandEndX = redHandWidth + cardWidth;

    drawCards(g2, redHandWidth, 0, cardWidth,
            redCardHeight, redHandSize, true);

    drawGrid(g2, centerGridCols, centerGridRows,
            redHandEndX, centerGridHeight);

    drawCards(g2, (centerGridCols + 1) * cellSize, 0,
            cardWidth, blueCardHeight, blueHandSize, false);
  }

  private void drawGrid(Graphics2D g2, int centerGridCols,
                        int centerGridRows, int redHandEndX, int gridHeight) {
    g2.setStroke(new BasicStroke(1));

    for (int row = 0; row < centerGridRows; row++) {
      for (int col = 0; col < centerGridCols; col++) {
        int x = (col + 1) * cellSize;
        int y = row * cellSize;

        if (model.isHole(row, col)) {
          g2.setColor(Color.GRAY);
          g2.fillRect(x, y, cellSize, cellSize);
        } else {
          g2.setColor(PASTEL_YELLOW);
          g2.fillRect(x, y, cellSize, cellSize);

          ThreeTriosCard card = model.getCardAt(row, col);
          if (card != null) {
            if (card.getColor() == CardColor.BLUE) {
              g2.setColor(PASTEL_BLUE);
            }
            else {
              g2.setColor(PASTEL_PINK);
            }

            g2.fillRect(x, y, cellSize, cellSize);

            String[] cardText = {
                    card.getNorthValString(),
                    card.getEastValString(),
                    card.getSouthValString(),
                    card.getWestValString()
            };
            drawTextInCell(g2, x, y, cellSize, cellSize, cardText);
          }

        }
        g2.setColor(Color.BLACK);
        g2.drawRect(x, y, cellSize, cellSize);
      }
    }

    g2.setStroke(new BasicStroke(3));
    g2.setColor(Color.BLACK);
    int blueHandEndX = redHandEndX + (cellSize * model.getGrid()[0].length) - 1;
    g2.drawLine(redHandEndX, 0, redHandEndX, gridHeight);
    g2.drawLine(blueHandEndX, 0, blueHandEndX, gridHeight);
  }


  private void drawCards(Graphics2D g2, int xOffset, int yOffset,
                         int cardWidth, int cardHeight, int handSize, boolean isRedHand) {
    for (int row = 0; row < handSize; row++) {
      int x = xOffset;
      int y = yOffset + row * cardHeight;

      g2.setColor(isRedHand ? PASTEL_PINK : PASTEL_BLUE);
      if ((isRedHand && row == selectedRedCardIndex) ||
              (!isRedHand && row == selectedBlueCardIndex)) {
        g2.setColor(PASTEL_GREEN);
      }
      g2.fillRect(x, y, cardWidth, cardHeight);

      g2.setStroke(new BasicStroke(1));
      g2.setColor(Color.BLACK);
      g2.drawRect(x, y, cardWidth, cardHeight);

      String[] text = isRedHand ? getLeftPanelCellText(row) : getRightPanelCellText(row);
      drawTextInCards(g2, x, y, cardWidth, cardHeight, text);
    }
  }

  private String[] getLeftPanelCellText(int row) {
    if (row < model.getRedHand().size()) {
      ThreeTriosCard card = model.getRedHand().get(row);
      return new String[]{
              card.getNorthValString(),
              card.getEastValString(),
              card.getSouthValString(),
              card.getWestValString()
      };
    }
    return new String[0];
  }

  private String[] getRightPanelCellText(int row) {
    if (row < model.getBlueHand().size()) {
      ThreeTriosCard card = model.getBlueHand().get(row);
      return new String[]{
              card.getNorthValString(),
              card.getEastValString(),
              card.getSouthValString(),
              card.getWestValString()
      };
    }
    return new String[0];
  }

  private void drawTextInCards(Graphics2D g2, int x, int y, int cardWidth,
                               int cardHeight, String[] text) {
    Font font = new Font("SansSerif", Font.BOLD, 12);
    g2.setFont(font);
    FontMetrics metrics = g2.getFontMetrics(font);

    int xCenter = x + (cardWidth / 2);
    int yCenter = y + (cardHeight / 2);

    if (text.length > 0) {
      g2.drawString(text[0],
              xCenter - metrics.stringWidth(text[0]) / 2, y + 20);
    }
    if (text.length > 1) {
      g2.drawString(text[1],
              x + cardWidth - 30, yCenter + 10);
    }
    if (text.length > 2) {
      g2.drawString(text[2],
              xCenter - metrics.stringWidth(text[2]) / 2, y + cardHeight - 10);
    }
    if (text.length > 3) {
      g2.drawString(text[3],
              x + 10, yCenter + 10);
    }
  }

  private void drawTextInCell(Graphics2D g2, int x, int y, int cellWidth,
                              int cellHeight, String[] text) {
    g2.setColor(Color.BLACK);
    Font font = new Font("SansSerif", Font.BOLD, 12);
    g2.setFont(font);
    FontMetrics metrics = g2.getFontMetrics(font);

    int xCenter = x + (cellWidth / 2);
    int yCenter = y + (cellHeight / 2);

    if (text.length > 0) {
      g2.drawString(text[0],
              xCenter - metrics.stringWidth(text[0]) / 2, y + 20);
    }
    if (text.length > 1) {
      g2.drawString(text[1],
              x + cellWidth - 30, yCenter + 10);
    }
    if (text.length > 2) {
      g2.drawString(text[2],
              xCenter - metrics.stringWidth(text[2]) / 2, y + cellHeight - 10);
    }
    if (text.length > 3) {
      g2.drawString(text[3],
              x + 10, yCenter + 10);
    }
  }

  /**
   * Sets the value of the setSelectedRedCardIndex
   * variable to indicate to the clicker whether to
   * select or deselect.
   * @param index of the card
   */
  public void setSelectedRedCardIndex(int index) {
    if (selectedRedCardIndex == index) {
      selectedRedCardIndex = -1;
    } else {
      selectedRedCardIndex = index;
    }
    repaint();
  }

  /**
   * Sets the value of the setSelectedBlueCardIndex
   * variable to indicate to the clicker whether to
   * select or deselect.
   * @param index of the card
   */
  public void setSelectedBlueCardIndex(int index) {
    if (selectedBlueCardIndex == index) {
      selectedBlueCardIndex = -1;
    } else {
      selectedBlueCardIndex = index;
    }
    repaint();
  }

  /**
   * Returns the index of the highlighted card.
   */
  public int getHighlightedCard() {
    if (selectedRedCardIndex != -1) {
      return selectedRedCardIndex;
    } else if (selectedBlueCardIndex != -1) {
      return selectedBlueCardIndex;
    } else {
      return -1;
    }
  }

  public ThreeTriosGameModel<ThreeTriosCard> getModel() {
    return model;
  }

  /**
   * Plays the card clicked on in the game.
   */
  public void onCardClicked(int row, int col) {
    int color = 0;
    if (selectedRedCardIndex != -1) {
      color = 0;
    } else if (selectedBlueCardIndex != -1) {
      color = 1;
    }
    playerActionFeatures.onCardSelected(getHighlightedCard(), row, col, color);
  }
}

