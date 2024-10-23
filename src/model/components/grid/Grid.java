package model.components.grid;

import java.util.AbstractMap;
import java.util.Map;

import model.components.card.Card;
import model.components.enums.CellType;
import model.components.enums.GamePlayer;

/**
 * Represents a grid in the game.
 * This interface defines the necessary operations for manipulating
 * and accessing the grid, including cell state, card placement, and player card counting.
 */
public interface Grid {

  /**
   * Checks if a specific cell is empty.
   *
   * @param row the row index of the cell (0-indexed)
   * @param col the column index of the cell (0-indexed)
   *
   * @return true if the cell is empty, false otherwise
   * @throws IllegalArgumentException if the row or column index is out of bounds
   */
  boolean isCellEmpty(int row, int col);

  /**
   * Gets the type of a specific cell.
   *
   * @param row the row index of the cell (0-indexed)
   * @param col the column index of the cell (0-indexed)
   *
   * @return the type of the cell as a CellType enum
   * @throws IllegalArgumentException if the row or column index is out of bounds
   */
  CellType getCellType(int row, int col);

  /**
   * Retrieves the current state of the grid as a 2D array of Cards. Modifying this array
   * does not change the state of the grid.
   *
   * @return a 2D array representing the grid of cards.
   */
  Card[][] getGrid();

  /**
   * Retrieves the current cell types of the grid as a 2D array. Modifying this array
   * does not change the state of the grid.
   *
   * @return a 2D array representing the types of cells in the grid.
   */
  CellType[][] getCellTypesGrid();

  /**
   * Places a card in the specified cell of the grid if it is empty and the cell is not a hole.
   *
   * @param card the card to be placed in the grid.
   * @param row the row index where the card will be placed (0-indexed)
   * @param col the column index where the card will be placed (0-indexed)
   *
   * @throws IllegalArgumentException if the card is null
   * @throws IllegalArgumentException if the row or column index is out of bounds
   * @throws IllegalStateException if the cell is not empty or is a hole
   */
  void placeCard(Card card, int row, int col);

  /**
   * Checks if the grid is completely filled with cards.
   *
   * @return true if all cells are filled, false otherwise
   */
  boolean isFilled();

  /**
   * Counts the number of cards belonging to a specific player in the grid.
   *
   * @param player the player whose cards are to be counted
   * @return the number of cards belonging to the specified player
   */
  int countPlayerCards(GamePlayer player);

  /**
   * Retrieves a map of adjacent cards to the specified cell. Modifying the map does not
   * change the state of the grid.
   *
   * @param row the row index of the cell (0-indexed)
   * @param col the column index of the cell (0-indexed)
   *
   * @return a map where the key is a Card and the value is a SimpleEntry
   *         containing the row and column indices of the adjacent cards
   * @throws IllegalArgumentException if the row or column index is out of bounds
   */
  Map<Card, AbstractMap.SimpleEntry<Integer, Integer>> getAdjacentCards(int row, int col);

  /**
   * Checks if a card can be placed in the specified cell.
   *
   * @param row the row index where the card will be placed (0-indexed)
   * @param col the column index where the card will be placed (0-indexed)
   *
   * @return true if the card can be placed, false otherwise
   * @throws IllegalArgumentException if the row or column index is out of bounds
   */
  boolean canPlaceCard(int row, int col);

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
   * Gets the total number of cells in the grid.
   *
   * @return the number of cells in the grid.
   */
  int getNumberOfCells();
}
