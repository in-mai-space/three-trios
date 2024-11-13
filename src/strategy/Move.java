package strategy;

import model.interfaces.Cell;

/**
 * Interface representing a move in a game.
 */
public interface Move {

  /**
   * Get the cell associated with this move.
   *
   * @return the cell (card) associated with this move
   */
  Cell getCard();

  /**
   * Get the row index of this move.
   *
   * @return the row index (0-indexed)
   */
  int getRow();

  /**
   * Get the column index of this move.
   *
   * @return the column index (0-indexed)
   */
  int getCol();
}