package model.components.grid;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import model.components.card.Card;
import model.components.enums.CellType;
import model.components.enums.GamePlayer;

public class ThreeTriosGrid implements Grid {
  private final Card[][] cells;
  private final CellType[][] cellTypes;
  private final int rows;
  private final int cols;

  public ThreeTriosGrid(CellType[][] cellTypes) {
    validateGrid(cellTypes);
    this.cellTypes = cellTypes;
    this.rows = cellTypes.length;
    this.cols = cellTypes[0].length;
    this.cells = new Card[rows][cols];
  }

  private void validateGrid(CellType[][] cellTypes) {
    if (cellTypes == null) {
      throw new IllegalArgumentException("Cells and cellTypes cannot be null.");
    }
    if (cellTypes.length == 0) {
      throw new IllegalArgumentException("Cells and cellTypes cannot be empty.");
    }
    validateOddNumberOfCells(cellTypes);
  }

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

  public CellType[][] getCellTypesGrid() {
    CellType[][] copy = new CellType[this.rows][this.cols];
    for (int row = 0; row < this.rows; row++) {
      for (int col = 0; col < this.cols; col++) {
        copy[row][col] = this.cellTypes[row][col];
      }
    }
    return copy;
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

  public boolean canPlaceCard(int row, int col) {
    validateIndex(row, col);
    return isCellEmpty(row, col) && getCellType(row, col) == CellType.CELL;
  }

  public Card getCardAt(int row, int col) {
    validateIndex(row, col);
    if (cells[row][col] == null || getCellType(row, col) == CellType.HOLE) {
      throw new IllegalArgumentException("Cannot get card at this position");
    }
    return cells[row][col];
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

  public Map<Card, AbstractMap.SimpleEntry<Integer, Integer>> getAdjacentCards(int row, int col) {
    validateIndex(row, col);
    Map<Card, AbstractMap.SimpleEntry<Integer, Integer>> neighborCards = new HashMap<>();
    Map<Card, AbstractMap.SimpleEntry<Integer, Integer>> horizontalNeighbors = getHorizontalNeighbors(row, col);
    neighborCards.putAll(horizontalNeighbors);
    Map<Card, AbstractMap.SimpleEntry<Integer, Integer>> verticalNeighbors = getVerticalNeighbors(row, col);
    neighborCards.putAll(verticalNeighbors);
    return neighborCards;
  }

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
}
