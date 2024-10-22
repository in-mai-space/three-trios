package model;

import java.util.List;
import java.util.Optional;

import model.components.card.Card;
import model.components.enums.CellType;
import model.components.enums.GamePlayer;

public interface GameModel {
  /**
   * Initializes the game, setting up initial conditions and shuffling cards.
   */
  void startGame();

  /**
   * Returns the current player.
   *
   * @return The current player's identifier.
   */
  GamePlayer getCurrentPlayer();

  /**
   * Gets the hand of the specified player.
   *
   * @param player The player whose hand is to be retrieved.
   * @return A list of cards in the player's hand.
   */
  List<Card> getHand(GamePlayer player);
  Card[][] getGrid();
  CellType[][] getCellTypes();

  List<Card> getCurrentPlayerHand();

  /**
   * Places a card at the specified position on the grid.
   *
   * @param index The index of the card in the player's hand.
   * @param row   The row to place the card.
   * @param col   The column to place the card.
   * @throws IllegalArgumentException if the move is invalid.
   */
  void placeCard(int index, int row, int col);

  /**
   * Checks if the game is over.
   *
   * @return True if the game is over, otherwise false.
   */
  boolean gameOver();

  /**
   * Gets the size of the specified player's hand.
   *
   * @param player The player whose hand size is to be retrieved.
   * @return The size of the player's hand.
   */
  int getHandSize(GamePlayer player);
  Optional<List<GamePlayer>> getWinner();
}
