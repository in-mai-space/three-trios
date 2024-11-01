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

    double cellWidth = getWidth() / (double) cols;
    double cellHeight = getHeight() / (double) rows;

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        Graphics2D cellG2d = (Graphics2D) g2d.create();
        cellG2d.translate(col * cellWidth, row * cellHeight);

        CardPath cellPath = new CardPath(cellWidth, cellHeight);

        if (cellTypes[row][col] == CellType.HOLE) {
          new ColorDecorator(cellPath, new Color(189,165,93)).draw(cellG2d);
        } else if (cards[row][col] == null) {
          new ColorDecorator(cellPath, new Color(249,224,118)).draw(cellG2d);
        } else {
          Card card = cards[row][col];
          new ColorDecorator(cellPath, Utils.getCardColor(card.getOwner())).draw(cellG2d);
          new AttackValuesDecorator(cellPath, card.getAllAttackValues()).draw(cellG2d);
        }

        cellG2d.dispose();
      }
    }

    g2d.dispose();
  }
}
