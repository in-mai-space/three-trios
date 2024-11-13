package model.implementation;

import java.util.AbstractMap;
import java.util.Map;

import model.interfaces.Cell;
import model.enums.CellType;
import model.enums.Direction;
import model.enums.GamePlayer;
import model.interfaces.Grid;
import model.interfaces.GridManager;

/**
 * Manages the grid in the ThreeTriosGame.
 */
public class ThreeTriosGridManager implements GridManager {
  private final Grid grid;

  /**
   * Constructs a new ThreeTriosGridManager with the specified grid.
   *
   * @param cellTypes the grid to be managed
   * @throws IllegalArgumentException if the grid is null
   */
  public ThreeTriosGridManager(CellType[][] cellTypes) {
    if (cellTypes == null) {
      throw new IllegalArgumentException("Grid cannot be null");
    }
    this.grid = new ThreeTriosGrid(cellTypes);
  }

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
  public boolean canPlaceCard(int row, int col) {
    return grid.canPlaceCard(row, col);
  }

  /**
   * Places a card in the specified cell and initiates the battle phase.
   * The card is removed from the player's hand and placed on the grid.
   *
   * @param cell the card to be placed
   * @param row the row index where the card will be placed
   * @param col the column index where the card will be placed
   *
   * @throws IllegalArgumentException if the card is null
   * @throws IllegalArgumentException if the row or column index is out of bounds
   */
  public void placeCard(Cell cell, int row, int col) {
    grid.placeCard(cell, row, col);
  }

  /**
   * Get the direction of the adjacent card relative to the placed card.
   *
   * @param placedRow the row index of the placed card
   * @param placedCol the column index of the placed card
   * @param adjacentRow the row index of the adjacent card
   * @param adjacentCol the column index of the adjacent card
   *
   * @return the direction of the adjacent card
   */
  private Direction getDirection(int placedRow, int placedCol, int adjacentRow, int adjacentCol) {
    // Check if the adjacent card is directly above (North)
    if (adjacentRow == placedRow - 1 && adjacentCol == placedCol) {
      return Direction.NORTH;
    }
    // Check if the adjacent card is directly below (South)
    else if (adjacentRow == placedRow + 1 && adjacentCol == placedCol) {
      return Direction.SOUTH;
    }
    // Check if the adjacent card is directly to the left (West)
    else if (adjacentRow == placedRow && adjacentCol == placedCol - 1) {
      return Direction.WEST;
    }
    // Check if the adjacent card is directly to the right (East)
    else if (adjacentRow == placedRow && adjacentCol == placedCol + 1) {
      return Direction.EAST;
    }
    throw new IllegalStateException("Two cards are not neighbors");
  }

  /**
   * Counts the number of cards owned by a specific player on the grid.
   *
   * @param player the player whose cards are being counted
   * @return the number of cards owned by the player
   */
  public int countPlayerCards(GamePlayer player) {
    return grid.countPlayerCards(player);
  }

  /**
   * Checks whether the game is over, i.e., when all card cells are filled.
   *
   * @return true if the game is over, false otherwise
   */
  public boolean isGameOver() {
    return grid.isFilled();
  }

  /**
   * Retrieves the current state of the grid as a 2D array of Cards.
   * Modifying this array does not change the state of the grid.
   *
   * @return a 2D array representing the grid of cards
   */
  public Cell[][] getGrid() {
    return grid.getGrid();
  }

  /**
   * Retrieves the current cell types of the grid as a 2D array.
   * Modifying this array does not change the state of the grid.
   *
   * @return a 2D array representing the types of cells in the grid
   */
  public CellType[][] getCellTypes() {
    return grid.getCellTypesGrid();
  }

  /**
   * Gets the total number of cells in the grid.
   *
   * @return the number of cells in the grid.
   */
  public int numberOfCells() {
    return grid.getNumberOfCells();
  }

  /**
   * Retrieves the card located at the specified cell.
   *
   * @param row the row index of the cell (0-indexed)
   * @param col the column index of the cell (0-indexed)
   * @return the card located at the specified cell, or null if the cell is empty
   * @throws IllegalArgumentException if the row or column index is out of bounds
   * @throws IllegalStateException    if there is no card at cell
   */
  public Cell getCardAt(int row, int col) {
    return grid.getCardAt(row, col);
  }

  /**
   * Get the width of grid.
   *
   * @return the width of the grid.
   */
  public int getWidth() {
    return grid.getWidth();
  }

  /**
   * Get the height of grid.
   *
   * @return the height of the grid.
   */
  public int getHeight() {
    return grid.getHeight();
  }

  /**
   * Get the owner of a card given row index and column index (0-based).
   *
   * @param row row index
   * @param col column index
   *
   * @return the player that owns the card at specific location on grid
   * @throws IllegalArgumentException if the row or column index is out of bounds
   * @throws IllegalStateException if there is no card at the location
   */
  @Override
  public GamePlayer getOwnerAt(int row, int col) {
    return getCardAt(row, col).getOwner();
  }

  /**
   * Count how many opponents' card would be flipped if a card is played in the grid
   * at a certain position. This will use a copy of the grid to simulate the propagation
   * rather than actually mutating the grid.
   *
   * @param cell card to be placed in grid
   * @param row  row index position on grid (0-indexed)
   * @param col  col index position on grid (0-indexed)
   * @return number of opponents' card flipped if a card is placed in a position
   * @throws IllegalArgumentException if row or column index out of bounds
   * @throws IllegalStateException    if a card cannot be placed that location
   */
  public int countCardFlip(Cell cell, int row, int col) {
    // use copy to avoid mutation of actual card in the original grid
    Grid copyGrid = grid.getCopy();
    copyGrid.placeCard(cell.getCopy(), row, col);
    return countAndExecuteBattle(row, col, copyGrid);
  }

  /**
   * Executes the battle phase after a card is placed.
   * The placed card battles all adjacent cards belonging to the opposing player.
   *
   * @param row the row index of the newly placed card
   * @param col the column index of the newly placed card
   *
   * @throws IllegalArgumentException if the row or column index is out of bounds
   */
  public void executeBattle(int row, int col) {
    countAndExecuteBattle(row, col, grid);
  }

  /**
   * Execute battle and count how many cards have been flipped.
   *
   * @param row row index (0-indexed)
   * @param col col index (0-indexed)
   * @param targetGrid grid to be mutated
   * @return number of cards that is flipped during the battle
   */
  private int countAndExecuteBattle(int row, int col, Grid targetGrid) {
    int flippedCount = 0;
    Cell placedCell = targetGrid.getCardAt(row, col);
    GamePlayer currentPlayer = placedCell.getOwner();
    // get adjacent cards of this specific card
    Map<Cell, AbstractMap.SimpleEntry<Integer, Integer>> adjacentCards =
            targetGrid.getAdjacentCards(row, col);

    // battle this card with every adjacent cards
    for (Map.Entry<Cell, AbstractMap.SimpleEntry<Integer, Integer>> entry :
            adjacentCards.entrySet()) {
      // get the adjacent card
      Cell adjacentCell = entry.getKey();
      // get its row and col
      AbstractMap.SimpleEntry<Integer, Integer> position = entry.getValue();

      GamePlayer adjacentOwner = adjacentCell.getOwner();

      if (!adjacentOwner.equals(currentPlayer)) {
        int adjacentRow = position.getKey();
        int adjacentCol = position.getValue();

        Direction direction = getDirection(row, col, adjacentRow, adjacentCol);
        if (placedCell.beats(adjacentCell, direction)) {
          adjacentCell.setOwner(currentPlayer);
          flippedCount += 1;
          flippedCount += countAndExecuteBattle(adjacentRow, adjacentCol, targetGrid);
        }
      }
    }
    return flippedCount;
  }
}
