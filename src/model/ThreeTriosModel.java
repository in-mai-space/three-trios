package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import model.components.card.Card;
import model.components.enums.GamePlayer;
import model.components.enums.GameState;
import model.components.grid.Grid;
import model.components.grid.ThreeTriosGrid;
import model.components.hand.Hand;
import model.components.hand.ThreeTriosHand;
import model.components.manager.GridManager;
import model.components.enums.CellType;
import model.components.manager.ThreeTriosGridManager;

public class ThreeTriosModel implements GameModel {
  private final List<GamePlayer> players;
  private Map<GamePlayer, Hand> playerHands;
  private final GridManager ruleKeeper;
  private int currentPlayerIndex;
  private final boolean shuffle;
  private GameState gameState;
  private final List<Card> allCards;
  private final int numCells;

  public ThreeTriosModel(CellType[][] cellTypes, List<Card> allCards, boolean shuffle) {
    validateModelArgs(cellTypes, allCards);
    Grid grid = new ThreeTriosGrid(cellTypes);
    if (allCards.size() < grid.getNumberOfCells() + 1) {
      throw new IllegalArgumentException("There must be at least " + (grid.getNumberOfCells()
              + 1) + " cards available.");
    }
    this.numCells = grid.getNumberOfCells();
    this.ruleKeeper = new ThreeTriosGridManager(grid);
    this.players = new ArrayList<>(List.of(GamePlayer.RED, GamePlayer.BLUE));
    this.currentPlayerIndex = 0;
    this.shuffle = shuffle;
    this.allCards = allCards;
  }

  private void distributeCards() {
    playerHands = new HashMap<>();
    for (GamePlayer player : players) {
      playerHands.put(player, new ThreeTriosHand(new ArrayList<>()));
    }
    int cardsPerPlayer = (numCells + 1) / players.size();
    int index = 0;
    for (int i = 0; i < cardsPerPlayer; i++) {
      if (playerHands.get(players.get(index)).handSize() < cardsPerPlayer) {
        playerHands.get(players.get(index)).addCard(allCards.get(i));
        index = (index + 1) % players.size();
      }
    }
  }

  /**
   * Initializes the game, setting up initial conditions and shuffling cards.
   */
  public void startGame() {
    validateGameInProgress();
    if (shuffle) {
      Collections.shuffle(allCards);
    }
    distributeCards();
    this.gameState = GameState.GAME_STARTED;
  }

  public Optional<List<GamePlayer>> getWinner() {
    Map<GamePlayer, Integer> scores = new HashMap<>();
    for (GamePlayer player : players) {
      int numCardsOwned = ruleKeeper.countPlayerCards(player);
      scores.put(player, numCardsOwned + getHandSize(player));
    }
    int maxScore = Collections.max(scores.values());
    List<GamePlayer> winners = new ArrayList<>();
    for (Map.Entry<GamePlayer, Integer> entry : scores.entrySet()) {
      if (entry.getValue() == maxScore) {
        winners.add(entry.getKey());
      }
    }
    return winners.size() == 1 ? Optional.of(winners) : Optional.empty();
  }

  private void nextPlayer() {
    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
  }

  /**
   * Returns the current player.
   *
   * @return The current player's identifier.
   */
  public GamePlayer getCurrentPlayer() {
    validateGameNotStartOrOver();
    return players.get(currentPlayerIndex);
  }

  /**
   * Gets the hand of the specified player.
   *
   * @param player The player whose hand is to be retrieved.
   * @return A list of cards in the player's hand.
   */
  public List<Card> getHand(GamePlayer player) {
    validateGameNotStartOrOver();
    return playerHands.get(player).getCards();
  }



  /**
   * Places a card at the specified position on the grid.
   *
   * @param index The index of the card in the player's hand.
   * @param row   The row to place the card.
   * @param col   The column to place the card.
   * @throws IllegalArgumentException if the move is invalid.
   */
  public void placeCard(int index, int row, int col) {
    validateGameNotStartOrOver();
    Hand currentPlayerHand = playerHands.get(players.get(currentPlayerIndex));
    Card card = currentPlayerHand.removeCard(index);
    ruleKeeper.placeCard(card, row, col);
    ruleKeeper.executeBattle(row, col);
    nextPlayer();
  }

  /**
   * Checks if the game is over.
   *
   * @return True if the game is over, otherwise false.
   */
  public boolean gameOver() {
    return ruleKeeper.isGameOver();
  }

  /**
   * Gets the size of the specified player's hand.
   *
   * @param player The player whose hand size is to be retrieved.
   * @return The size of the player's hand.
   */
  public int getHandSize(GamePlayer player) {
    validateGameNotStartOrOver();
    return playerHands.get(player).handSize();
  }

  private void validateModelArgs(CellType[][] cellTypes, List<Card> allCards) {
    if (cellTypes == null || allCards == null || players == null) {
      throw new IllegalArgumentException("Cell types, cards, and players cannot be null");
    }
    validateCellTypes(cellTypes);
    Set<Card> uniqueCards = new HashSet<>(allCards);
    if (uniqueCards.size() != allCards.size()) {
      throw new IllegalArgumentException("Cards must be unique");
    }
  }

  private void validateCellTypes(CellType[][] cellTypes) {
    for (int row = 0; row < cellTypes.length; row++) {
      if (cellTypes[row] == null) {
        throw new IllegalArgumentException("Cell types cannot contain null rows");
      }
      for (int col = 0; col < cellTypes[row].length; col++) {
        if (cellTypes[row][col] == null) {
          throw new IllegalArgumentException("Cell type at (" + row + ", " + col + ") can't be null");
        }
      }
    }
    if (cellTypes.length == 0 || cellTypes[0].length == 0) {
      throw new IllegalArgumentException("Cell types must be at least 1x1");
    }
  }

  private void validateGameNotStartOrOver() {
    if (gameState == null || ruleKeeper.isGameOver()) {
      throw new IllegalStateException("Game has not started or is not over");
    }
  }

  private void validateGameInProgress() {
    if (gameState != null && !ruleKeeper.isGameOver()) {
      throw new IllegalStateException("Game is already in progress");
    }
  }

  public Card[][] getGrid() {
    return ruleKeeper.getGrid();
  }

  public CellType[][] getCellTypes() {
    return ruleKeeper.getCellTypes();
  }

  public List<Card> getCurrentPlayerHand() {
    return getHand(players.get(currentPlayerIndex));
  }
}
