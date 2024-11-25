package adapter;

import model.interfaces.GridManager;
import provider.model.GridCell;
import provider.model.GridInt;

public class GridAdapter implements GridInt {
  private final GridManager grid;

  public GridAdapter(GridManager grid) {
    if (grid == null) {
      throw new IllegalArgumentException("Grid cannot be null");
    }
    this.grid = grid;
  }

  /**
   * Sets a cell to playable or not playable given a cell and a truth value.
   *
   * @param row        the row the cell is located
   * @param col        the column the cell is located
   * @param isPlayable a truth value
   */
  @Override
  public void initializeCell(int row, int col, boolean isPlayable) {

  }

  /**
   * Determines whether the cell a player is trying to play to is a legal move or not.
   *
   * @return true if a player can play to that cell
   */
  @Override
  public boolean isValid() {
    return false;
  }

  /**
   * Returns a cell given its coordinates.
   *
   * @param row the row the cell is located
   * @param col the column the cell is located
   * @return the cell
   */
  @Override
  public GridCell getCell(int row, int col) {
    return null;
  }

  /**
   * Returns the number of cells in the graph that are not holes.
   *
   * @return a number of playable cells
   */
  @Override
  public int getNumberOfNonHoleCardCells() {
    return 0;
  }

  /**
   * Returns the number of rows of the graph.
   *
   * @return the number of rows
   */
  @Override
  public int getRows() {
    return 0;
  }

  /**
   * Returns the number of columns of the graph.
   *
   * @return the number of columns
   */
  @Override
  public int getCols() {
    return 0;
  }
}
