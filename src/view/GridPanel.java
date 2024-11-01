package view;

import java.awt.*;
import javax.swing.*;
import model.Utils;
import model.enums.CellType;
import model.interfaces.Card;

class GridPanel extends JPanel {
  private final CellType[][] cellTypes;
  private final Card[][] cards;

  public GridPanel(CellType[][] cellTypes, Card[][] cards) {
    this.cellTypes = cellTypes;
    this.cards = cards;
    setOpaque(false);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();

    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    int rows = cellTypes.length;
    int cols = cellTypes[0].length;

    // Calculate integer cell width and height to avoid gaps
    int cellWidth = getWidth() / cols;
    int cellHeight = getHeight() / rows;

    // Offset for potential remaining space if the division is not perfect
    int extraWidth = getWidth() % cols;
    int extraHeight = getHeight() % rows;

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        Graphics2D cellG2d = (Graphics2D) g2d.create();

        // Calculate x and y positions, adding any extra space offset for last row/column
        int x = col * cellWidth + Math.min(col, extraWidth);
        int y = row * cellHeight + Math.min(row, extraHeight);
        cellG2d.translate(x, y);

        // Adjust cell dimensions for last row/column if there's extra space
        int currentCellWidth = cellWidth + (col < extraWidth ? 1 : 0);
        int currentCellHeight = cellHeight + (row < extraHeight ? 1 : 0);

        // Build the cell frame based on the cell type or card
        CellCardFrame cellFrame;
        if (cellTypes[row][col] == CellType.HOLE) {
          cellFrame = new CellCardFrame.CardBuilder()
                  .setColor(new Color(189, 165, 93))
                  .build();
        } else if (cards[row][col] == null) {
          cellFrame = new CellCardFrame.CardBuilder()
                  .setColor(new Color(249, 224, 118))
                  .build();
        } else {
          Card card = cards[row][col];
          cellFrame = new CellCardFrame.CardBuilder()
                  .setColor(Utils.getCardColor(card.getOwner()))
                  .setAttackValues(card.getAllAttackValues())
                  .build();
        }

        // Scale and draw the cell frame in the specified space
        cellG2d.scale((double) currentCellWidth / 200,
                (double) currentCellHeight / 300);
        cellFrame.draw(cellG2d);
        cellG2d.dispose();
      }
    }

    g2d.dispose();
  }
}
