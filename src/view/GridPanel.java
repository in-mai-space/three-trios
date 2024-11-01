package view;

import java.awt.*;

import javax.swing.*;

import model.Utils;
import model.enums.CellType;
import model.enums.GamePlayer;
import model.interfaces.Card;
import view.components.cells.BaseCell;
import view.components.cells.CardDecorator;
import view.components.cells.CellComponent;
import view.components.cells.ColorDecorator;

public class GridPanel extends JPanel implements GamePanel {
  private final CellType[][] cellTypes;
  private final Card[][] cards;
  private static final int CELL_SIZE = 200;
  private static final int GAP = 5;
  private final static Color EMPTY_CELL = new Color(249, 224, 118);
  private final static Color HOLE = new Color(189, 166, 109);

  public GridPanel(CellType[][] cellTypes, Card[][] cards) {
    this.cellTypes = cellTypes;
    this.cards = cards;

    setLayout(new GridLayout(cellTypes.length, cellTypes[0].length, GAP, GAP));
    int width = cellTypes[0].length * (CELL_SIZE + GAP) + GAP;
    int height = cellTypes.length * (CELL_SIZE + GAP) + GAP;
    setPreferredSize(new Dimension(width, height));
    setBackground(Color.DARK_GRAY);

    renderGrid();
  }

  private void renderGrid() {
    for (int row = 0; row < cellTypes.length; row++) {
      for (int col = 0; col < cellTypes[0].length; col++) {
        CellComponent cell = createCell(row, col);
        JButton button = cell.getButton();
        button.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
        this.add(button);
      }
    }
  }

  private CellComponent createCell(int row, int col) {
    if (cellTypes[row][col] == CellType.HOLE) {
      return new ColorDecorator(new BaseCell(), HOLE);
    }
    else if (cellTypes[row][col] == CellType.CELL && cards[row][col] == null) {
      return new ColorDecorator(new BaseCell(), EMPTY_CELL);
    }
    else {
      return new CardDecorator(new ColorDecorator(new BaseCell(), Utils.getCardColor(cards[row][col].getOwner())),
              cards[row][col].getAllAttackValues());
    }
  }
}