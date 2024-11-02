package model.interfaces;

import model.enums.CellType;
import model.enums.GamePlayer;

/**
 * Represents a manager for the grid in the game.
 * This interface defines the necessary operations for manipulating
 * and accessing the grid, including card placement and battle execution.
 */
public interface GridManager {
  /**
   * Checks if a card can be placed at the specified row and column
   * based on the current state of the grid and game rules.
   *
   * @param row the row index of the cell (0-indexed)
   * @param col the column index of the cell (0-indexed)
   *
   * @return true if the card can be placed, false otherwise
   * @throws IllegalArgumentException if the row or column index is out of bounds
   */
  boolean canPlaceCard(int row, int col);

  /**
   * Places a card in the specified cell and initiates the battle phase.
   * The card is removed from the player's hand and placed on the grid.
   *
   * @param card the card to be placed
   * @param row the row index where the card will be placed
   * @param col the column index where the card will be placed
   *
   * @throws IllegalArgumentException if the card is null
   * @throws IllegalArgumentException if the row or column index is out of bounds
   */
  void placeCard(Card card, int row, int col);

  /**
   * Executes the battle phase after a card is placed.
   * The placed card battles all adjacent cards belonging to the opposing player.
   *
   * @param row the row index of the newly placed card
   * @param col the column index of the newly placed card
   *
   * @throws IllegalArgumentException if the row or column index is out of bounds
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

  /**
   * Retrieves the current state of the grid as a 2D array of Cards.
   * Modifying this array does not change the state of the grid.
   *
   * @return a 2D array representing the grid of cards
   */
  Card[][] getGrid();

  /**
   * Retrieves the current cell types of the grid as a 2D array.
   * Modifying this array does not change the state of the grid.
   *
   * @return a 2D array representing the types of cells in the grid
   */
  CellType[][] getCellTypes();

  /**
   * Gets the total number of cells in the grid.
   *
   * @return the number of cells in the grid.
   */
  int numberOfCells();

  /**
   * Retrieves the card located at the specified cell.
   *
   * @param row the row index of the cell (0-indexed)
   * @param col the column index of the cell (0-indexed)
   * @return the card located at the specified cell, or null if the cell is empty
   *
   * @throws IllegalArgumentException if the row or column index is out of bounds
   * @throws IllegalStateException if there is no card at cell
   */
  Card getCardAt(int row, int col);

  /**
   * Get the width of grid.
   *
   * @return the width of the grid
   * @throws IllegalArgumentException if game is not started
   */
  int getWidth();

  /**
   * Get the height of grid.
   *
   * @return the height of the grid
   * @throws IllegalArgumentException if game is not started
   */
  int getHeight();

  /**
   * Get the owner of a card given row index and column index (0-based).
   *
   * @param row row index
   * @param col column index
   *
   * @return the player that owns the card at specific location on grid
   * @throws IllegalStateException if there is no card at the location
   */
  GamePlayer getOwnerAt(int row, int col);

  /**
   * Count how many opponents' card would be flipped if a card is played in the grid
   * at a certain position.
   *
   * @param card card to be placed in grid
   * @param row row index position on grid (0-indexed)
   * @param col col index position on grid (0-indexed)
   * @return number of opponents' card flipped if a card is placed in a position
   * @throws IllegalArgumentException if row or column index out of bounds
   * @throws IllegalStateException if a card cannot be placed that location
   */
  int countCardFlip(Card card, int row, int col);
}
