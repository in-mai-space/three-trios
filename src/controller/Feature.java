package controller;

import model.enums.GamePlayer;

/**
 * Represents the feature of the game. This interface should be implemented by the controller.
 */
public interface Feature {

  /**
   * Print in the console what index and which player clicks a card.
   *
   * @param index index of card in a hand (0-indexed)
   * @param player player who owns the card
   */
  void printCardClicked(int index, GamePlayer player);

  /**
   * Print in the console which row and col player clicks on grid.
   *
   * @param row row index of the cell (0-indexed)
   * @param col col index of the cell (0-indexed)
   */
  void printCellClicked(int row, int col);
}
