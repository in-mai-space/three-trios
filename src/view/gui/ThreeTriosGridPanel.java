package view.gui;

import java.awt.Graphics2D;
import java.awt.Graphics;
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

  /**
   * Add features to the panel.
   *
   * @param features controller that implements features
   * @throws IllegalArgumentException if features is null
   */
  @Override
  public void addFeatures(Feature features) {
    if (features == null) {
      throw new IllegalArgumentException("Features cannot be null");
    }
    this.feature = features;
  }

  /**
   * Refresh the view when there is new changes to the game.
   */
  @Override
  public void refresh() {
    repaint();
  }

  /**
   * Manage actions when the mouse is clicked on grid.
   *
   * @param xPos x position on screen
   * @param yPos y position on screen
   */
  private void handleMouseClick(int xPos, int yPos) {
    int rows = cellTypes.length;
    int cols = cellTypes[0].length;
    AffineTransform transform = physicalToModel(); // map screen size to model

    try {
      // map model back to screen
      AffineTransform inverseTransform = transform.createInverse();
      Point2D pixelPoint = new Point2D.Double(xPos, yPos); // a point clicked on screen
      Point2D gridPoint = inverseTransform.transform(pixelPoint, null); // grid point clicked

      double exactCol = gridPoint.getX();
      double exactRow = gridPoint.getY();

      double colFraction = exactCol - Math.floor(exactCol);
      double rowFraction = exactRow - Math.floor(exactRow);

      // ignore clicks near cell boundaries
      double buffer = 0.02;

      // check if click is within the "interior" of the cell (not near edges)
      if (colFraction > buffer && colFraction < (1 - buffer)
              && rowFraction > buffer && rowFraction < (1 - buffer)) {

        int col = (int) exactCol;
        int row = (int) exactRow;

        if (row >= 0 && row < rows && col >= 0 && col < cols) {
          feature.printCellClicked(row, col);
        }
      }
    } catch (NoninvertibleTransformException e) {
      System.err.println("Cannot invert");
    }
  }

  /**
   * Transformation from physical the model coordinate.
   * @return the transformation
   */
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

  /**
   * Render all cells on the grid.
   * @param g the <code>Graphics</code> object to protect
   */
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

  /**
   * Build a specific cell frame based on its row and col position. This will retrieve what
   * type of card is in the grid and use the builder from cell card class to customize the card.
   *
   * @param row row index in grid (0-indexed)
   * @param col col index in grid (0-indexed)
   * @return the customized version of cell card
   */
  private CellCard createCellFrame(int row, int col) {
    if (cellTypes[row][col] == CellType.HOLE) {
      return new CellCard.CardBuilder()
              .setColor(GameViewConfig.HOLE_COLOR)
              .build();
    }
    else if (cells[row][col] == null) {
      return new CellCard.CardBuilder().setColor(GameViewConfig.CELL_COLOR).build();
    }
    else {
      Cell cell = cells[row][col];
      return new CellCard.CardBuilder()
              .setColor(GameViewConfig.getCardColor(cell.getOwner()))
              .setAttackValues(cell.getAllAttackValues())
              .build();
    }
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
    cellG2d.scale((double) currentCellWidth / GameViewConfig.DEFAULT_WIDTH,
            (double) currentCellHeight / GameViewConfig.DEFAULT_HEIGHT);
    cellFrame.draw(cellG2d);
    cellG2d.dispose();
  }
}
