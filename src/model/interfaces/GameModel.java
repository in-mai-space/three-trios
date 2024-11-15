package model.interfaces;

import model.enums.GamePlayer;

/**
 * Represents a model for the game.
 */
public interface GameModel extends ReadOnlyGameModel, ModelFeature {

  /**
   * Initializes the game by distributing cards and shuffling cards.
   *
   * @param shuffle true if want to shuffle this list of cards, false otherwise
   * @throws IllegalStateException if game is already in progress
   */
  void startGame(boolean shuffle);

  /**
   * Places a card at the specified position on the grid.
   *
   * @param index The index of the card in the player's hand (0-indexed)
   * @param row   The row to place the card (0-indexed)
   * @param col   The column to place the card (0-indexed)
   *
   * @throws IllegalArgumentException if index, row, or col out of bound
   * @throws IllegalStateException if cannot place a card because cell is a hole or is non-empty
   * @throws IllegalStateException if the game is not started or is over
   */
  void placeCard(int index, int row, int col);

  /**
   * Checks if the game is over.
   *
   * @return True if the game is over, otherwise false
   * @throws IllegalStateException if game is not started or is over
   */
  boolean gameOver();

  /**
   * Gets the size of the specified player's hand.
   *
   * @param player The player whose hand size is to be retrieved
   * @return The size of the player's hand
   *
   * @throws IllegalStateException if game is not started or is over
   */
  int getHandSize(GamePlayer player);
}
