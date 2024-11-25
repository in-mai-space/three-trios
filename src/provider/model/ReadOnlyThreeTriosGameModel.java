package provider.model;

import java.util.ArrayList;

/**
 * ReadOnly version  of the game model that exposes
 * information that cannot be modified to be used in the
 * view.
 */
public interface ReadOnlyThreeTriosGameModel {
  /**
   * Returns if the game is over.
   *
   * @return true if the game has ended and false otherwise
   * @throws IllegalStateException if the game has not started
   */
  boolean gameOver();

  /**
   * Returns the winning player.
   *
   * @return the player with the most cords, on the grid and in their hand, or null if it's a tie
   * @throws IllegalStateException if the game is not over
   * @throws IllegalStateException if the game has not started
   */
  Player determineWinner();

  /**
   * Provides the current game state.
   * @return the game phase from the constructor
   */
  Phases getGamePhase();

  /**
   * Provides the copy of the player associated with the appropriate color.
   * @param color the color given to find a player
   * @return a player from the constructor or a new player if the color was unassigned
   */
  Player getPlayer(CardColor color);

  /**
   * Provides the current grid.
   * @return the grid from the constructor
   */
  GridCell[][] getGrid();

  /**
   * Returns whether the given cell is a hole or not.
   */
  boolean isHole(int row, int col);

  /**
   * Returns the blue player's hand.
   */
  ArrayList<ThreeTriosCard> getBlueHand();

  /**
   * Returns the blue player's hand.
   */
  ArrayList<ThreeTriosCard> getRedHand();

  /**
   * Returns the card at the given row and col of the grid.
   * @param row the row from which to get the card
   * @param col the column from which to get the card
   * @return the card at the given spot, if it's a hole or does not contain one, return null
   * @throws IllegalStateException if the row or column is out of bounds
   */
  ThreeTriosCard getCardAt(int row, int col);

  /**
   * Returns the color of the current player in String format.
   * @return a string representation of the player whose turn it is.
   */
  String getCurrentPlayerName();

  public void logInspection(int row, int col);
}
