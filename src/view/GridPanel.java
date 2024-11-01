package view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import javax.swing.*;

import model.enums.CellType;
import model.interfaces.Card;

class GridPanel extends JPanel {
  private final CellType[][] cellTypes;
  private final Card[][] cards;
  private final Color[][] cellColors;
  private static final Color DEFAULT_COLOR = new Color(249, 224, 118);

  public GridPanel(CellType[][] cellTypes, Card[][] cards) {
    this.cellTypes = cellTypes;
    this.cards = cards;
    this.cellColors = new Color[cellTypes.length][cellTypes[0].length];
    setOpaque(false);
    initializeCellColors();

    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        handleMouseClick(e.getX(), e.getY());
        repaint();
      }
    });
  }

  private void initializeCellColors() {
    for (int row = 0; row < cellTypes.length; row++) {
      for (int col = 0; col < cellTypes[row].length; col++) {
        cellColors[row][col] = DEFAULT_COLOR; // Set default color
      }
    }
  }

  private void handleMouseClick(int x, int y) {
    int rows = cellTypes.length;
    int cols = cellTypes[0].length;

    int cellWidth = getWidth() / cols;
    int cellHeight = getHeight() / rows;

    AffineTransform transform = new AffineTransform();
    transform.translate(0, 0);
    transform.scale(cellWidth, cellHeight);

    try {
      AffineTransform inverseTransform = transform.createInverse();
      Point2D pixelPoint = new Point2D.Double(x, y);
      Point2D gridPoint = inverseTransform.transform(pixelPoint, null);

      int col = (int) gridPoint.getX();
      int row = (int) gridPoint.getY();

      if (row >= 0 && row < rows && col >= 0 && col < cols) {
        System.out.println("Cell clicked at: Row " + row + ", Column " + col);
      }
    } catch (NoninvertibleTransformException e) {
      System.err.println("Cannot invert");
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();

    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    int rows = cellTypes.length;
    int cols = cellTypes[0].length;

    int cellWidth = getWidth() / cols;
    int cellHeight = getHeight() / rows;

    int extraWidth = getWidth() % cols;
    int extraHeight = getHeight() % rows;

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        Graphics2D cellG2d = (Graphics2D) g2d.create();

        int x = col * cellWidth + Math.min(col, extraWidth);
        int y = row * cellHeight + Math.min(row, extraHeight);
        cellG2d.translate(x, y);

        int currentCellWidth = cellWidth + (col < extraWidth ? 1 : 0);
        int currentCellHeight = cellHeight + (row < extraHeight ? 1 : 0);

        cellG2d.setColor(cellColors[row][col]);

        CellCardFrame cellFrame;
        if (cellTypes[row][col] == CellType.HOLE) {
          cellFrame = new CellCardFrame.CardBuilder()
                  .setColor(new Color(189, 165, 93))
                  .build();
        } else if (cards[row][col] == null) {
          cellFrame = new CellCardFrame.CardBuilder()
                  .setColor(DEFAULT_COLOR) // Use default color if no card
                  .build();
        } else {
          Card card = cards[row][col];
          cellFrame = new CellCardFrame.CardBuilder()
                  .setColor(ViewData.getCardColor(card.getOwner()))
                  .setAttackValues(card.getAllAttackValues())
                  .build();
        }

        cellG2d.scale((double) currentCellWidth / 200,
                (double) currentCellHeight / 300);
        cellFrame.draw(cellG2d);
        cellG2d.dispose();
      }
    }

    g2d.dispose();
  }
}
