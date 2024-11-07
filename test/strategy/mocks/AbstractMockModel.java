package strategy.mocks;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import model.enums.CellType;
import model.enums.GamePlayer;
import model.implementation.ThreeTriosGridManager;
import model.implementation.ThreeTriosHand;
import model.interfaces.Cell;
import model.interfaces.GameModel;
import model.interfaces.GridManager;
import model.interfaces.Hand;

abstract class AbstractMockModel implements GameModel {
  protected final GamePlayer[] players;
  protected final GridManager ruleKeeper;
  protected Map<GamePlayer, Hand> playerHands;
  protected final List<Cell> allCells;
  protected final int numCells;
  protected FileWriter logWriter;

  public AbstractMockModel(CellType[][] cellTypes, List<Cell> allCells) {
    GridManager manager = new ThreeTriosGridManager(cellTypes);
    if (allCells.size() < manager.numberOfCells() + 1) {
      throw new IllegalArgumentException("There must be at least " + (manager.numberOfCells() + 1)
              + " cards available.");
    }
    this.numCells = manager.numberOfCells();
    this.ruleKeeper = manager;
    this.players = new GamePlayer[]{ GamePlayer.RED, GamePlayer.BLUE };
    this.allCells = allCells;
    try {
      this.logWriter = new FileWriter("strategy-transcript.txt", false);
    } catch (IOException ignored) { }
  }

  protected void writeMessage(String message) {
    try {
      logWriter.append(message).append("\n");
      logWriter.flush();
    } catch (IOException ignored) { }
  }

  private void setUpCards() {
    playerHands = new HashMap<>();
    for (GamePlayer player : players) {
      playerHands.put(player, new ThreeTriosHand(new ArrayList<>()));
    }
    int cardsPerPlayer = (numCells + 1) / players.length;
    int playerIndex = 0;
    for (int i = 0; i < cardsPerPlayer * 2; i++) {
      if (playerHands.get(players[playerIndex]).handSize() < cardsPerPlayer) {
        Cell cellToDistribute = allCells.get(i);
        playerHands.get(players[playerIndex]).addCard(cellToDistribute);
        cellToDistribute.setOwner(players[playerIndex]);
        playerIndex = (playerIndex + 1) % players.length;
      }
    }
  }

  /**
   * Returns the current player.
   *
   * @return The current player's identifier
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public GamePlayer getCurrentPlayer() {
    return null;
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
    return ruleKeeper.getGrid();
  }

  /**
   * Check if a card can be placed in a position in the model.
   *
   * @param row
   * @param col
   * @throws IllegalArgumentException if row or col is out of bound
   */
  @Override
  public boolean canPlaceCard(int row, int col) {
    writeMessage("Check can place card at row " + row + " and col " + col);
    return ruleKeeper.canPlaceCard(row, col);
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
   * @param player
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
    return playerHands.get(players[0]).getCards();
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
    if (row == 1 && col == 1 && cell.getName().equals("CorruptKing")) {
      return 10;
    }
    writeMessage(String.format("count card flip with card %s in row %s and col %s",
            cell.getName(), row, col));
    return 0;
  }

  /**
   * Initializes the game by distributing cards and shuffling cards.
   *
   * @param shuffle true if want to shuffle this list of cards, false otherwise
   * @throws IllegalStateException if game is already in progress
   */
  @Override
  public void startGame(boolean shuffle) {
    setUpCards();
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
    Hand currentPlayerHand = playerHands.get(players[0]);
    Cell cell = currentPlayerHand.removeCard(index);
    ruleKeeper.placeCard(cell, row, col);
    ruleKeeper.executeBattle(row, col);
  }

  /**
   * Checks if the game is over.
   *
   * @return True if the game is over, otherwise false
   * @throws IllegalStateException if game is not started or is over
   */
  @Override
  public boolean gameOver() {
    return false;
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

  public String getFileContent() {
    return this.logWriter.toString();
  }
}
