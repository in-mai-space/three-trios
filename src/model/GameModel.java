package model;

import java.util.List;
import java.util.Optional;

import model.components.card.Card;
import model.components.enums.CellType;
import model.components.enums.GamePlayer;

/**
 * Represents a model for the game.
 */
public interface GameModel {

  /**
   * Initializes the game by distributing cards and shuffling cards.
   *
   * @param shuffle true if want to shuffle this list of cards, false otherwise
   * @throws IllegalStateException if game is already in progress
   */
  void startGame(boolean shuffle);

  /**
   * Returns the current player.
   *
   * @return The current player's identifier
   * @throws IllegalStateException if the game has not started or is over
   */
  GamePlayer getCurrentPlayer();

  /**
   * Gets the hand of the specified player. Modifying this list does not modify actual
   * cards in a player's hand.
   *
   * @param player player whose hand is to be retrieved
   *
   * @return list of cards in the player's hand
   * @throws IllegalStateException if the game has not started or is over
   */
  List<Card> getHand(GamePlayer player);

  /**
   * Gets a copy of current grd of the game. Modifying this 2d array does not modify
   * the game state.
   *
   * @return the 2d-array representation of cards in the grid
   */
  Card[][] getGrid();

  /**
   * Get a copy of the layout of cell types. Modifying this 2d array does not modify
   * the game state.
   *
   * @return the 2d-array cell type representation of the grid
   */
  CellType[][] getCellTypes();

  /**
   * Return the list of cards in hand of the current player in the game. Modifying this
   * list does not change the cards in player's hand.
   *
   * @return list of cards in current player's hand
   * @throws IllegalStateException if the game is not started or is over
   */
  List<Card> getCurrentPlayerHand();

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

  /**
   * Get the winner of the game. When there is only one winner, it will return the
   * winner. If the game results in a tie, it will return Optional.empty() to avoid
   * returning null.
   *
   * @return the winner of the game
   * @throws IllegalStateException if the game is not started or is over
   */
  Optional<GamePlayer> getWinner();
}
