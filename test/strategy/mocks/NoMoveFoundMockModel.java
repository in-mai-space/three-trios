package strategy.mocks;

import java.util.List;

import model.enums.CellType;
import model.interfaces.Cell;

/**
 * Represents mock model when there is no move found for any row and col position and any index
 * in hand.
 */
public class NoMoveFoundMockModel extends AbstractMockModel {

  /**
   * Construct new instance of mock model where there is no move found.
   *
   * @param cellTypes 2d-array representation of cell types
   * @param allCells all cells can be played in the game
   */
  public NoMoveFoundMockModel(CellType[][] cellTypes, List<Cell> allCells) {
    super(cellTypes, allCells);
  }

  /**
   * Override the super mock model to return false on every position in the grid.
   *
   * @param row row index in grid (0-indexed)
   * @param col col index in grid (0-indexed)
   * @return false all the time
   */
  @Override
  public boolean canPlaceCard(int row, int col) {
    super.canPlaceCard(row, col);
    return false;
  }
}
