package model.components.manager;

import java.util.AbstractMap;
import java.util.Map;

import model.components.card.Card;
import model.components.enums.CellType;
import model.components.enums.Direction;
import model.components.enums.GamePlayer;
import model.components.grid.Grid;

/**
 * Manages the grid in the ThreeTriosGame.
 */
public class ThreeTriosGridManager implements GridManager {
  private final Grid grid;

  /**
   * Constructs a new ThreeTriosGridManager with the specified grid.
   *
   * @param grid the grid to be managed
   * @throws IllegalArgumentException if the grid is null
   */
  public ThreeTriosGridManager(Grid grid) {
    if (grid == null) {
      throw new IllegalArgumentException("Grid cannot be null");
    }
    this.grid = grid;
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
   * @param card the card to be placed
   * @param row the row index where the card will be placed
   * @param col the column index where the card will be placed
   *
   * @throws IllegalArgumentException if the card is null
   * @throws IllegalArgumentException if the row or column index is out of bounds
   */
  public void placeCard(Card card, int row, int col) {
    grid.placeCard(card, row, col);
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
    Card placedCard = grid.getCardAt(row, col);
    GamePlayer currentPlayer = placedCard.getOwner();
    // get adjacent cards of this specific card
    Map<Card, AbstractMap.SimpleEntry<Integer, Integer>> adjacentCards =
            grid.getAdjacentCards(row, col);

    // battle this card with every adjacent cards
    for (Map.Entry<Card, AbstractMap.SimpleEntry<Integer, Integer>> entry : adjacentCards.entrySet()) {
      // get the adjacent card
      Card adjacentCard = entry.getKey();
      // get its row and col
      AbstractMap.SimpleEntry<Integer, Integer> position = entry.getValue();

      GamePlayer adjacentOwner = adjacentCard.getOwner();

      if (!adjacentOwner.equals(currentPlayer)) {
        int adjacentRow = position.getKey();
        int adjacentCol = position.getValue();

        Direction direction = getDirection(row, col, adjacentRow, adjacentCol);
        if (placedCard.beats(adjacentCard, direction)) {
          adjacentCard.setOwner(currentPlayer);
          executeBattle(adjacentRow, adjacentCol);
        }
      }
    }
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
  public Card[][] getGrid() {
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
}
