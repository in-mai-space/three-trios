package provider.model;

/**
 * Interface for a grid used to play a game of Three Trios.
 */
public interface ProviderGrid {

  /**
   * Sets a cell to playable or not playable given a cell and a truth value.
   * @param row the row the cell is located
   * @param col the column the cell is located
   * @param isPlayable a truth value
   */
  void initializeCell(int row, int col, boolean isPlayable);

  /**
   * Determines whether the cell a player is trying to play to is a legal move or not.
   * @return true if a player can play to that cell
   */
  boolean isValid();

  /**
   * Returns a cell given its coordinates.
   * @param row the row the cell is located
   * @param col the column the cell is located
   * @return the cell
   */
  ProviderCell getCell(int row, int col);

  /**
   * Returns the number of cells in the graph that are not holes.
   * @return a number of playable cells
   */
  int getNumberOfNonHoleCardCells();

  /**
   * Returns the number of rows of the graph.
   * @return the number of rows
   */
  int getRows();

  /**
   * Returns the number of columns of the graph.
   * @return the number of columns
   */
  int getCols();
}
