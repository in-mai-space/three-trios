package model.components.grid;

import java.util.ArrayList;
import java.util.List;

import model.components.card.Card;
import model.components.enums.CellType;
import model.components.enums.GamePlayer;

public class ThreeTriosGrid implements Grid {
  private final Card[][] cells;
  private final CellType[][] cellTypes;
  private final int rows;
  private final int cols;

  public ThreeTriosGrid(Card[][] cells, CellType[][] cellTypes) {
    validateGridDimensions(cells, cellTypes);
    this.cells = cells;
    this.cellTypes = cellTypes;
    this.rows = cells.length;
    this.cols = cells[0].length;
  }

  private static void validateGridDimensions(Card[][] cells, CellType[][] cellTypes) {
    if (cells == null || cellTypes == null) {
      throw new IllegalArgumentException("Cells and cellTypes cannot be null.");
    }
    if (cells.length == 0 || cellTypes.length == 0) {
      throw new IllegalArgumentException("Cells and cellTypes cannot be empty.");
    }
    if (cells.length != cellTypes.length) {
      throw new IllegalArgumentException("Cells and cellTypes must have same number of rows.");
    }
    if (cells[0].length != cellTypes[0].length) {
      throw new IllegalArgumentException("Cells and cellTypes must have same number of columns.");
    }
  }

  private void validateIndex(int row, int col) {
    if (row < 0 || row >= this.rows) {
      throw new IllegalArgumentException("Row index is out of bounds.");
    }
    if (col < 0 || col >= this.cols) {
      throw new IllegalArgumentException("Column index is out of bounds.");
    }
  }

  public boolean isCellEmpty(int row, int col) {
    validateIndex(row, col);
    return cells[row][col] == null;
  }

  public CellType getCellType(int row, int col) {
    validateIndex(row, col);
    return cellTypes[row][col];
  }

  public Card[][] getGrid() {
    Card[][] gridCopy = new Card[this.rows][this.cols];
    for (int row = 0; row < this.rows; row++) {
      for (int col = 0; col < this.cols; col++) {
        gridCopy[row][col] = this.cells[row][col];
      }
    }
    return gridCopy;
  }

  public void placeCard(Card card, int row, int col) {
    validateIndex(row, col);
    if (canPlaceCard(row, col)) {
      cells[row][col] = card;
    } else {
      throw new IllegalArgumentException(String.format(
              "Cannot place card into row %d and column %d", row, col));
    }
  }

  private boolean canPlaceCard(int row, int col) {
    validateIndex(row, col);
    return isCellEmpty(row, col) && getCellType(row, col) == CellType.CELL;
  }

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

  private List<Card> getAdjacentCards(int row, int col) {
    validateIndex(row, col);
    List<Card> neighborCards = new ArrayList<>();
    neighborCards.addAll(getHorizontalNeighbors(row, col));
    neighborCards.addAll(getVerticalNeighbors(row, col));
    return neighborCards;
  }

  private List<Card> getHorizontalNeighbors(int row, int col) {
    validateIndex(row, col);
    List<Card> neighborCards = new ArrayList<>();
    if (col > 0) {
      Card leftNeighbor = cells[row][col - 1];
      if (leftNeighbor != null) {
        neighborCards.add(leftNeighbor);
      }
    }
    if (col < cols - 1) {
      Card rightNeighbor = cells[row][col + 1];
      if (rightNeighbor != null) {
        neighborCards.add(rightNeighbor);
      }
    }
    return neighborCards;
  }


  private List<Card> getVerticalNeighbors(int row, int col) {
    validateIndex(row, col);
    List<Card> neighborCards = new ArrayList<>();
    if (row > 0) {
      Card upperNeighbor = cells[row - 1][col];
      if (upperNeighbor != null) {
        neighborCards.add(upperNeighbor);
      }
    }
    if (row < rows - 1) {
      Card lowerNeighbor = cells[row + 1][col];
      if (lowerNeighbor != null) {
        neighborCards.add(lowerNeighbor);
      }
    }
    return neighborCards;
  }


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

  private void battlePropagation(int row, int col) {
    /**
     * TODO: implement BFS loop
     * - search for adjacent cards and battle against each of them
     * - propagate and search for neighbor of adjacent cards
     * - termination condition: all neighbor cards are already visited
     */
  }

  private void switchOwner(int row, int col, GamePlayer player) {
    validateIndex(row, col);
    Card card = cells[row][col];
    card.setOwner(player);
  }
}
