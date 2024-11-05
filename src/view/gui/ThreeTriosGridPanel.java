package view.gui;

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

import controller.Feature;
import model.enums.CellType;
import model.interfaces.Cell;

/**
 * Represent the Grid view of ThreeTrios game.
 */
class ThreeTriosGridPanel extends JPanel implements GamePanel {
  private final CellType[][] cellTypes;
  private final Cell[][] cells;
  private static final Color DEFAULT_COLOR = new Color(249, 224, 118);
  private Feature feature;

  /**
   * Construct a new ThreeTriosGridPanel.
   *
   * @param cellTypes cell types of the grid
   * @param cells current grid of the game
   * @throws IllegalArgumentException if cellTypes or cards is null
   */
  public ThreeTriosGridPanel(CellType[][] cellTypes, Cell[][] cells) {
    if (cellTypes == null || cells == null) {
      throw new IllegalArgumentException("Cell types and cards grid cannot be null");
    }
    this.cellTypes = cellTypes;
    this.cells = cells;
    setOpaque(false);

    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        handleMouseClick(e.getX(), e.getY());
        repaint();
      }
    });
  }

  private void handleMouseClick(int x, int y) {
    int rows = cellTypes.length;
    int cols = cellTypes[0].length;
    AffineTransform transform = physicalToModel();
    try {
      AffineTransform inverseTransform = transform.createInverse();
      Point2D pixelPoint = new Point2D.Double(x, y);
      Point2D gridPoint = inverseTransform.transform(pixelPoint, null);

      int col = (int) gridPoint.getX();
      int row = (int) gridPoint.getY();

      if (row >= 0 && row < rows && col >= 0 && col < cols) {
        feature.printCellClicked(row, col);
      }
    } catch (NoninvertibleTransformException e) {
      System.err.println("Cannot invert");
    }
  }

  private AffineTransform physicalToModel() {
    int rows = cellTypes.length;
    int cols = cellTypes[0].length;

    int cellWidth = getWidth() / cols;
    int cellHeight = getHeight() / rows;

    AffineTransform transform = new AffineTransform();
    transform.translate(0, 0);
    transform.scale(cellWidth, cellHeight);
    return transform;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();

    int rows = cellTypes.length;
    int cols = cellTypes[0].length;

    int cellWidth = getWidth() / cols;
    int cellHeight = getHeight() / rows;
    int extraWidth = getWidth() % cols;
    int extraHeight = getHeight() % rows;

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        drawCell(g2d, row, col, cellWidth, cellHeight, extraWidth, extraHeight);
      }
    }
    g2d.dispose();
  }

  private void drawCell(Graphics2D g2d, int row, int col, int cellWidth, int cellHeight,
                        int extraWidth, int extraHeight) {
    Graphics2D cellG2d = (Graphics2D) g2d.create();

    int x = col * cellWidth + Math.min(col, extraWidth);
    int y = row * cellHeight + Math.min(row, extraHeight);
    cellG2d.translate(x, y);

    int currentCellWidth = cellWidth + (col < extraWidth ? 1 : 0);
    int currentCellHeight = cellHeight + (row < extraHeight ? 1 : 0);

    CellCard cellFrame = createCellFrame(row, col);
    cellG2d.scale((double) currentCellWidth / ViewData.CELL_WIDTH,
            (double) currentCellHeight / ViewData.CELL_HEIGHT);
    cellFrame.draw(cellG2d);
    cellG2d.dispose();
  }

  private CellCard createCellFrame(int row, int col) {
    if (cellTypes[row][col] == CellType.HOLE) {
      return new CellCard.CardBuilder()
              .setColor(new Color(189, 165, 93))
              .build();
    }
    else if (cells[row][col] == null) {
      return new CellCard.CardBuilder().setColor(DEFAULT_COLOR).build();
    }
    else {
      Cell cell = cells[row][col];
      return new CellCard.CardBuilder()
              .setColor(ViewData.getCardColor(cell.getOwner()))
              .setAttackValues(cell.getAllAttackValues())
              .build();
    }
  }

  @Override
  public void addFeatures(Feature features) {
    if (features == null) {
      throw new IllegalArgumentException("Features cannot be null");
    }
    this.feature = features;
  }

  @Override
  public void refresh() {
    repaint();
  }
}
