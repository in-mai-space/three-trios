package model.interfaces;

import java.util.List;
import java.util.Optional;

import model.enums.CellType;
import model.enums.GamePlayer;

/**
 * Represents the ReadOnlyGameModel, which only exposes observational methods to prevent
 * unwanted mutation.
 */
public interface ReadOnlyGameModel {

  /**
   * Returns the current player.
   *
   * @return The current player's identifier
   * @throws IllegalStateException if the game has not started
   */
  GamePlayer getCurrentPlayer();

  /**
   * Gets a copy of current grid of the game. Modifying this 2d array does not modify
   * the game state.
   *
   * @return the 2d-array representation of cards in the grid
   * @throws IllegalStateException if game is not started
   */
  Card[][] getGrid();

  /**
   * Get a copy of the layout of cell types. Modifying this 2d array does not modify
   * the game state.
   *
   * @return the 2d-array cell type representation of the grid
   * @throws IllegalStateException if game is not started
   */
  CellType[][] getCellTypes();

  /**
   * Return the list of cards in hand of the current player in the game. Modifying this
   * list does not change the cards in player's hand.
   *
   * @return list of cards in current player's hand
   * @throws IllegalStateException if the game is not started
   */
  List<Card> getCurrentPlayerHand();

  /**
   * Get the winner of the game. When there is only one winner, it will return the
   * winner. If the game results in a tie, it will return Optional.empty() to avoid
   * returning null.
   *
   * @return the winner of the game
   * @throws IllegalStateException if the game is not started
   */
  Optional<GamePlayer> getWinner();

  /**
   * Get the width of grid.
   *
   * @return the width of the grid
   * @throws IllegalStateException if the game is not started
   */
  int getGridWidth();

  /**
   * Get the height of grid.
   *
   * @return the height of the grid
   * @throws IllegalStateException if the game is not started
   */
  int getGridHeight();
}
