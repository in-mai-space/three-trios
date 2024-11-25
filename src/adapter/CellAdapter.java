package adapter;

import model.enums.CellType;
import model.interfaces.GridManager;
import provider.model.GridCell;
import provider.model.ThreeTriosCard;

public class CellAdapter implements GridCell {
  private final GridManager grid;
  private final int row;
  private final int col;

  public CellAdapter(GridManager grid, int row, int col) {
    if (grid == null) {
      throw new IllegalArgumentException("Grid cannot be null");
    }
    if (grid.getHeight() <= row || grid.getWidth() <= col) {
      throw new IllegalArgumentException("Invalid row or column");
    }
    this.grid = grid;
    this.row = row;
    this.col = col;
  }

  /**
   * Determines whether the cell is a hole or not.
   *
   * @return true if the cell is a hole
   */
  @Override
  public boolean isHole() {
    return grid.getCellTypes()[row][col] == CellType.HOLE;
  }

  /**
   * Gives the current card in the cell.
   *
   * @return a card
   */
  @Override
  public ThreeTriosCard getCard() {
    return new CardAdapter(grid.getCardAt(row, col));
  }

  /**
   * Fills the cell with the given card if the cell is playable.
   *
   * @param card the card used to change the cell
   */
  @Override
  public void setCellCard(ThreeTriosCard card) {
    if (card == null) {
      throw new IllegalArgumentException("Card cannot be null");
    }
    grid.placeCard(null, row, col);
  }
}
