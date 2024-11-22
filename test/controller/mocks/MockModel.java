package controller.mocks;

import java.util.List;
import java.util.Optional;

import controller.ControllerFeature;
import model.Utils;
import model.enums.CellType;
import model.enums.GamePlayer;
import model.interfaces.Cell;
import model.interfaces.GameModel;

/**
 * Represents MockModel for controller unit test.
 */
public class MockModel implements GameModel {
  private final Appendable log;

  /**
   * Construct a new mock model with an appendable.
   * @param log appendable to print out messages
   */
  public MockModel(Appendable log) {
    this.log = log;
  }

  /**
   * Initializes the game by distributing cards and shuffling cards.
   *
   * @param shuffle true if want to shuffle this list of cards, false otherwise
   * @throws IllegalStateException if game is already in progress
   */
  @Override
  public void startGame(boolean shuffle) {
    Utils.transmit(log, "Model starts game");
  }

  /**
   * Places a card at the specified position on the grid.
   *
   * @param index The index of the card in the player's hand (0-indexed)
   * @param row   The row to place the card (0-indexed)
   * @param col   The column to place the card (0-indexed)
   * @throws IllegalArgumentException if index, row, or col out of bound
   * @throws IllegalStateException    if cannot place a card because cell is a hole or is non-empty
   * @throws IllegalStateException    if the game is not started or is over
   */
  @Override
  public void placeCard(int index, int row, int col) {
    Utils.transmit(log, "Place card with index " + index + " into row " + row +
            " and col " + col);
  }

  /**
   * Gets the size of the specified player's hand.
   *
   * @param player The player whose hand size is to be retrieved
   * @return The size of the player's hand
   * @throws IllegalStateException if game is not started or is over
   */
  @Override
  public int getHandSize(GamePlayer player) {
    return 0;
  }

  /**
   * Registers a controller as an observer to this model, allowing it to receive updates.
   *
   * @param observer The controller to be added as an observer
   * @throws IllegalArgumentException if controller is null
   */
  @Override
  public void addObserver(ControllerFeature observer) {
    Utils.transmit(log, "Model adds controller as observer");
  }

  /**
   * Returns the current player.
   *
   * @return The current player's identifier
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public GamePlayer getCurrentPlayer() {
    return GamePlayer.RED;
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
    return new Cell[0][];
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
    return false;
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
    return new CellType[0][];
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
    return null;
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
    return Optional.empty();
  }

  /**
   * Get the width of grid.
   *
   * @return the width of the grid
   * @throws IllegalStateException if the game is not started
   */
  @Override
  public int getGridWidth() {
    return 0;
  }

  /**
   * Get the height of grid.
   *
   * @return the height of the grid
   * @throws IllegalStateException if the game is not started
   */
  @Override
  public int getGridHeight() {
    return 0;
  }

  /**
   * Get the score of a player.
   *
   * @param player player Red or Blue
   * @return the number of cards owned in grid and hand of a player
   * @throws IllegalStateException if the game is not started
   */
  @Override
  public int getScore(GamePlayer player) {
    return 0;
  }

  /**
   * Get card at a position in grid.
   *
   * @param row row index
   * @param col col index
   * @return the card at a row and position in grid
   * @throws IllegalArgumentException if index is out of bound
   * @throws IllegalStateException    if there is no card at that position
   * @throws IllegalStateException    if game is not started
   */
  @Override
  public Cell getCardAt(int row, int col) {
    return null;
  }

  /**
   * Get the owner of a card given row index and column index (0-based).
   *
   * @param row row index
   * @param col column index
   * @return the player that owns the card at specific location on grid
   * @throws IllegalStateException    if there is no card at the location
   * @throws IllegalArgumentException if index is out of bound
   * @throws IllegalStateException    if game is not started
   */
  @Override
  public GamePlayer getOwnerAt(int row, int col) {
    return null;
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
    return null;
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
    return 0;
  }

  /**
   * Checks if the game is over.
   *
   * @return True if the game is over, otherwise false
   * @throws IllegalStateException if game is not started
   */
  @Override
  public boolean gameOver() {
    Utils.transmit(log, "Check if game is over");
    return false;
  }
}
