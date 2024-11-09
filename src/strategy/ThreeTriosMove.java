package strategy;

import model.interfaces.Cell;

import java.util.Objects;

/**
 * Represent a move for a strategy.
 */
public class ThreeTriosMove implements Move {
  private final Cell cell;
  private final int row;
  private final int col;

  /**
   * Construct a new move.
   *
   * @param cell card in user's hand that can be placed on grid
   * @param row row index on grid (0-indexed)
   * @param col col index on grid (0-indexed)
   * @throws IllegalArgumentException if cell is null
   */
  public ThreeTriosMove(Cell cell, int row, int col) {
    if (cell == null) {
      throw new IllegalArgumentException("Card should not be null");
    }
    this.cell = cell;
    this.row = row;
    this.col = col;
  }

  /**
   * Get the cell associated with this move.
   *
   * @return the cell (card) associated with this move
   */
  public Cell getCard() {
    return cell;
  }

  /**
   * Get the row index of this move.
   *
   * @return the row index (0-indexed)
   */
  public int getRow() {
    return row;
  }

  /**
   * Get the column index of this move.
   *
   * @return the column index (0-indexed)
   */
  public int getCol() {
    return col;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof ThreeTriosMove)) {
      return false;
    }
    ThreeTriosMove other = (ThreeTriosMove) obj;
    return Objects.equals(cell, other.cell) &&
            row == other.row &&
            col == other.col;
  }

  @Override
  public int hashCode() {
    return Objects.hash(cell, row, col);
  }
}
