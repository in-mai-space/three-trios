package model.components.manager;

import model.components.card.Card;
import model.components.enums.CellType;
import model.components.enums.GamePlayer;

public interface GridManager {
  /**
   * Checks if a card can be placed at the specified row and column
   * based on the current state of the grid and game rules.
   *
   * @param row the row index of the cell
   * @param col the column index of the cell
   * @return true if the card can be placed, false otherwise
   */
  boolean canPlaceCard(int row, int col);

  /**
   * Places a card in the specified cell and initiates the battle phase.
   * The card is removed from the player's hand and placed on the grid.
   *
   * @param card the card to be placed
   * @param row the row index where the card will be placed
   * @param col the column index where the card will be placed
   */
  void placeCard(Card card, int row, int col);

  /**
   * Executes the battle phase after a card is placed.
   * The placed card battles all adjacent cards belonging to the opposing player.
   *
   * @param row the row index of the newly placed card
   * @param col the column index of the newly placed card
   */
  void executeBattle(int row, int col);

  /**
   * Counts the number of cards owned by a specific player on the grid.
   *
   * @param player the player whose cards are being counted
   * @return the number of cards owned by the player
   */
  int countPlayerCards(GamePlayer player);

  /**
   * Checks whether the game is over, i.e., when all card cells are filled.
   *
   * @return true if the game is over, false otherwise
   */
  boolean isGameOver();

  Card[][] getGrid();

  CellType[][] getCellTypes();
}
