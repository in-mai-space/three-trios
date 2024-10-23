package model.components.grid;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import model.components.card.Card;
import model.components.enums.CellType;
import model.components.enums.GamePlayer;

/**
 * Represents a grid in the game ThreeTrios.
 */
public class ThreeTriosGrid implements Grid {
  private final Card[][] cells;
  private final CellType[][] cellTypes;
  private final int rows;
  private final int cols;

  /**
   * Constructs a new ThreeTriosGrid with the specified cell types.
   *
   * @param cellTypes 2D array of cell types
   * @throws IllegalArgumentException if cellTypes is null or empty or has an even number of cells
   */
  public ThreeTriosGrid(CellType[][] cellTypes) {
    validateGrid(cellTypes);
    this.cellTypes = cellTypes;
    this.rows = cellTypes.length;
    this.cols = cellTypes[0].length;
    this.cells = new Card[rows][cols];
  }

  /**
   * Gets the total number of cells in the grid.
   *
   * @return the number of cells in the grid.
   */
  public int getNumberOfCells() {
    int count = 0;
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        if (cellTypes[row][col] != CellType.HOLE) {
          count++;
        }
      }
    }
    return count;
  }

  /**
   * Checks if a specific cell is empty.
   *
   * @param row the row index of the cell (0-indexed)
   * @param col the column index of the cell (0-indexed)
   *
   * @return true if the cell is empty, false otherwise
   * @throws IllegalArgumentException if the row or column index is out of bounds
   */
  public boolean isCellEmpty(int row, int col) {
    validateIndex(row, col);
    return cells[row][col] == null;
  }

  /**
   * Gets the type of a specific cell.
   *
   * @param row the row index of the cell (0-indexed)
   * @param col the column index of the cell (0-indexed)
   *
   * @return the type of the cell as a CellType enum
   * @throws IllegalArgumentException if the row or column index is out of bounds
   */
  public CellType getCellType(int row, int col) {
    validateIndex(row, col);
    return cellTypes[row][col];
  }

  /**
   * Retrieves the current state of the grid as a 2D array of Cards. Modifying this array
   * does not change the state of the grid.
   *
   * @return a 2D array representing the grid of cards.
   */
  public Card[][] getGrid() {
    Card[][] gridCopy = new Card[this.rows][this.cols];
    for (int row = 0; row < this.rows; row++) {
      for (int col = 0; col < this.cols; col++) {
        gridCopy[row][col] = this.cells[row][col];
      }
    }
    return gridCopy;
  }

  /**
   * Retrieves the current cell types of the grid as a 2D array. Modifying this array
   * does not change the state of the grid.
   *
   * @return a 2D array representing the types of cells in the grid.
   */
  public CellType[][] getCellTypesGrid() {
    CellType[][] copy = new CellType[this.rows][this.cols];
    for (int row = 0; row < this.rows; row++) {
      for (int col = 0; col < this.cols; col++) {
        copy[row][col] = this.cellTypes[row][col];
      }
    }
    return copy;
  }

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
  public void placeCard(Card card, int row, int col) {
    if (card == null) {
      throw new IllegalArgumentException("Card cannot be null");
    }
    validateIndex(row, col);
    if (canPlaceCard(row, col)) {
      cells[row][col] = card;
    } else {
      throw new IllegalStateException(String.format(
              "Cannot place card into row %d and column %d", row, col));
    }
  }

  /**
   * Checks if a card can be placed in the specified cell.
   *
   * @param row the row index where the card will be placed (0-indexed)
   * @param col the column index where the card will be placed (0-indexed)
   *
   * @return true if the card can be placed, false otherwise
   * @throws IllegalArgumentException if the row or column index is out of bounds
   */
  public boolean canPlaceCard(int row, int col) {
    validateIndex(row, col);
    return isCellEmpty(row, col) && getCellType(row, col) == CellType.CELL;
  }

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
  public Card getCardAt(int row, int col) {
    validateIndex(row, col);
    if (cells[row][col] == null || getCellType(row, col) == CellType.HOLE) {
      throw new IllegalStateException("Cannot get card at this position");
    }
    return cells[row][col];
  }

  /**
   * Checks if the grid is completely filled with cards.
   *
   * @return true if all cells are filled, false otherwise
   */
  public boolean isFilled() {
    boolean isFilled = true;
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        if (canPlaceCard(row, col)) {
          isFilled = false;
          break;
        }
      }
    }
    return isFilled;
  }

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
  public Map<Card, AbstractMap.SimpleEntry<Integer, Integer>> getAdjacentCards(int row, int col) {
    validateIndex(row, col);
    Map<Card, AbstractMap.SimpleEntry<Integer, Integer>> neighborCards = new HashMap<>();
    Map<Card, AbstractMap.SimpleEntry<Integer, Integer>> horizontalNeighbors = getHorizontalNeighbors(row, col);
    neighborCards.putAll(horizontalNeighbors);
    Map<Card, AbstractMap.SimpleEntry<Integer, Integer>> verticalNeighbors = getVerticalNeighbors(row, col);
    neighborCards.putAll(verticalNeighbors);
    return neighborCards;
  }

  /**
   * Get the map of horizontal neighbors (left and right of a card).
   *
   * @param row the row index of the card (0-indexed)
   * @param col the column index of the card (0-indexed)
   *
   * @return map of left and right neighbors of a card
   */
  private Map<Card, AbstractMap.SimpleEntry<Integer, Integer>> getHorizontalNeighbors(int row, int col) {
    validateIndex(row, col);
    Map<Card, AbstractMap.SimpleEntry<Integer, Integer>> neighborCards = new HashMap<>();
    if (col > 0) {
      Card leftNeighbor = cells[row][col - 1];
      if (leftNeighbor != null) {
        neighborCards.put(leftNeighbor, new AbstractMap.SimpleEntry<>(row, col - 1));
      }
    }
    if (col < cols - 1) {
      Card rightNeighbor = cells[row][col + 1];
      if (rightNeighbor != null) {
        neighborCards.put(rightNeighbor, new AbstractMap.SimpleEntry<>(row, col + 1));
      }
    }

    return neighborCards;
  }

  /**
   * Get the map of vertical neighbors (top and bottom of a card).
   *
   * @param row the row index of the card (0-indexed)
   * @param col the column index of the card (0-indexed)
   *
   * @return map of top and left neighbors of a card
   */
  private Map<Card, AbstractMap.SimpleEntry<Integer, Integer>> getVerticalNeighbors(int row, int col) {
    validateIndex(row, col);
    Map<Card, AbstractMap.SimpleEntry<Integer, Integer>> neighborCards = new HashMap<>();
    if (row > 0) {
      Card upperNeighbor = cells[row - 1][col];
      if (upperNeighbor != null) {
        neighborCards.put(upperNeighbor, new AbstractMap.SimpleEntry<>(row - 1, col));
      }
    }
    if (row < rows - 1) {
      Card lowerNeighbor = cells[row + 1][col];
      if (lowerNeighbor != null) {
        neighborCards.put(lowerNeighbor, new AbstractMap.SimpleEntry<>(row + 1, col));
      }
    }
    return neighborCards;
  }

  /**
   * Counts the number of cards belonging to a specific player in the grid.
   *
   * @param player the player whose cards are to be counted
   * @return the number of cards belonging to the specified player
   */
  public int countPlayerCards(GamePlayer player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null");
    }
    int count = 0;
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        if (cells[row][col] != null && cellTypes[row][col] == CellType.CELL
                && cells[row][col].getOwner() == player) {
          count += 1;
        }
      }
    }
    return count;
  }

  /**
   * Validates the row and column indices.
   *
   * @param row row index of grid (0-indexed)
   * @param col column index of grid (0-indexed)
   *
   * @throws IllegalArgumentException if the row or column index is out of bounds
   */
  private void validateIndex(int row, int col) {
    if (row < 0 || row >= this.rows) {
      throw new IllegalArgumentException("Row index is out of bounds.");
    }
    if (col < 0 || col >= this.cols) {
      throw new IllegalArgumentException("Column index is out of bounds.");
    }
  }

  /**
   * Validates the cellTypes argument if it is not null or not empty or has an odd number of cells.
   *
   * @param cellTypes 2D array of cell types
   * @throws IllegalArgumentException if cellTypes is null or empty or has an even number of cells
   */
  private void validateGrid(CellType[][] cellTypes) {
    if (cellTypes == null) {
      throw new IllegalArgumentException("Cells and cellTypes cannot be null.");
    }
    if (cellTypes.length == 0) {
      throw new IllegalArgumentException("Cells and cellTypes cannot be empty.");
    }
    validateOddNumberOfCells(cellTypes);
  }

  /**
   * Validates that the number of non-hole cells is odd.
   *
   * @param cellTypes 2D array of cell types
   * @throws IllegalArgumentException if the number of non-hole cells is even
   */
  private void validateOddNumberOfCells(CellType[][] cellTypes) {
    int cellCount = 0;
    for (int i = 0; i < cellTypes.length; i++) {
      if (cellTypes[i] == null) {
        throw new IllegalArgumentException("Row " + i + " cannot be null.");
      }
      for (int j = 0; j < cellTypes[i].length; j++) {
        if (cellTypes[i][j] == null) {
          throw new IllegalArgumentException("Cell type at (" + i + ", " + j + ") cannot be null.");
        }
        if (cellTypes[i][j] == CellType.CELL) {
          cellCount++;
        }
      }
    }
    if (cellCount % 2 == 0) {
      throw new IllegalArgumentException("The number of non-hole cells must be odd.");
    }
  }
}
