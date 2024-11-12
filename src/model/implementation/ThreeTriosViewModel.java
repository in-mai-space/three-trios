package model.implementation;

import java.util.List;
import java.util.Optional;

import model.enums.CellType;
import model.enums.GamePlayer;
import model.interfaces.Cell;
import model.interfaces.GameModel;
import model.interfaces.ReadOnlyGameModel;

/**
 * Represents the adapter for GameModel. This utilizes object adapter, which adapts the GameModel
 * into a ReadOnlyGameModel to prevent the view from mutating the game model or casting it to
 * access mutation methods.
 */
public class ThreeTriosViewModel implements ReadOnlyGameModel {
  private final GameModel adaptee;

  /**
   * Construct a new instance of ReadOnlyGameModel.
   *
   * @param adaptee the GameModel to adapt to ReadOnlyGameModel
   * @throws IllegalArgumentException if the model is null
   */
  public ThreeTriosViewModel(GameModel adaptee) {
    if (adaptee == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.adaptee = adaptee;
  }

  /**
   * Returns the current player.
   *
   * @return The current player's identifier
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public GamePlayer getCurrentPlayer() {
    return adaptee.getCurrentPlayer();
  }

  /**
   * Gets a copy of current grid of the game. Modifying this 2d array does not modify
   * the game state.
   *
   * @return the 2d-array representation of cards in the grid
   * @throws IllegalStateException if game is not started
   */
  @Override
  public Cell[][] getGrid() {
    return adaptee.getGrid();
  }

  /**
   * Check if a card can be placed in a position in the model.
   *
   * @param row row index (0-indexed)
   * @param col col index (0-indexed)
   * @throws IllegalArgumentException if row or col is out of bound
   */
  @Override
  public boolean canPlaceCard(int row, int col) {
    return adaptee.canPlaceCard(row, col);
  }

  /**
   * Get a copy of the layout of cell types. Modifying this 2d array does not modify
   * the game state.
   *
   * @return the 2d-array cell type representation of the grid
   * @throws IllegalStateException if game is not started
   */
  @Override
  public CellType[][] getCellTypes() {
    return adaptee.getCellTypes();
  }

  /**
   * Return the list of cards in hand of the current player in the game. Modifying this
   * list does not change the cards in player's hand.
   *
   * @return list of cards in current player's hand
   * @throws IllegalStateException if the game is not started
   */
  @Override
  public List<Cell> getCurrentPlayerHand() {
    return adaptee.getCurrentPlayerHand();
  }

  /**
   * Get the winner of the game. When there is only one winner, it will return the
   * winner. If the game results in a tie, it will return Optional.empty() to avoid
   * returning null.
   *
   * @return the winner of the game
   * @throws IllegalStateException if the game is not started
   */
  @Override
  public Optional<GamePlayer> getWinner() {
    return adaptee.getWinner();
  }

  /**
   * Get the width of grid.
   *
   * @return the width of the grid
   * @throws IllegalStateException if the game is not started
   */
  @Override
  public int getGridWidth() {
    return adaptee.getGridWidth();
  }

  /**
   * Get the height of grid.
   *
   * @return the height of the grid
   * @throws IllegalStateException if the game is not started
   */
  @Override
  public int getGridHeight() {
    return adaptee.getGridHeight();
  }

  /**
   * Get the score of a player.
   *
   * @param player a player in the game (one of Red and Blue)
   * @return the number of cards owned in grid and hand of a player
   * @throws IllegalStateException if the game is not started
   */
  @Override
  public int getScore(GamePlayer player) {
    return adaptee.getScore(player);
  }

  /**
   * Get card at a position in grid.
   *
   * @param row row index (0-indexed)
   * @param col col index (0-indexed)
   * @return the card at a row and position in grid
   * @throws IllegalArgumentException if index is out of bound
   * @throws IllegalStateException    if there is no card at that position
   * @throws IllegalStateException    if game is not started
   */
  @Override
  public Cell getCardAt(int row, int col) {
    return adaptee.getCardAt(row, col);
  }

  /**
   * Get the owner of a card given row index and column index (0-based).
   *
   * @param row row index (0-indexed)
   * @param col column index (0-indexed)
   * @return the player that owns the card at specific location on grid
   * @throws IllegalStateException    if there is no card at the location
   * @throws IllegalArgumentException if index is out of bound
   * @throws IllegalStateException    if game is not started
   */
  @Override
  public GamePlayer getOwnerAt(int row, int col) {
    return adaptee.getOwnerAt(row, col);
  }

  /**
   * Gets the hand of the specified player. Modifying this list does not modify actual
   * cards in a player's hand.
   *
   * @param player player whose hand is to be retrieved
   * @return list of cards in the player's hand
   * @throws IllegalStateException    if the game has not started
   * @throws IllegalArgumentException if player is null
   */
  @Override
  public List<Cell> getHand(GamePlayer player) {
    return adaptee.getHand(player);
  }

  /**
   * Count how many opponents' card would be flipped if a card is played in the grid
   * at a certain position.
   *
   * @param cell card to be placed in grid
   * @param row  row index position on grid (0-indexed)
   * @param col  col index position on grid (0-indexed)
   * @return number of opponents' card flipped if a card is placed in a position
   * @throws IllegalArgumentException if row or column index out of bounds
   * @throws IllegalStateException    if a card cannot be placed that location
   * @throws IllegalStateException    if game is not started or game is over
   */
  @Override
  public int countCardFlip(Cell cell, int row, int col) {
    return adaptee.countCardFlip(cell, row, col);
  }
}
