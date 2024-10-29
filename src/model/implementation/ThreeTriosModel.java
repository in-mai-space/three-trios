package model.implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import model.interfaces.Card;
import model.enums.CellType;
import model.interfaces.GameModel;
import model.enums.GamePlayer;
import model.interfaces.GridManager;
import model.interfaces.Hand;

/**
 * Represents ThreeTriosModel for the ThreeTriosGame.
 */
public class ThreeTriosModel implements GameModel {
  private final GamePlayer[] players; // array because list of players is fixed throughout game
  // INVARIANT: there are always 2 players
  // - logical statement
  // - it is final field, which means you can reinitialize
  // - it is instantaneous at any state
  // - it is enforced by the constructor
  // - none of the methods are modifying thi

  private Map<GamePlayer, Hand> playerHands;
  private final GridManager ruleKeeper;
  private int currentPlayerIndex;
  private boolean gameStarted;
  private final List<Card> allCards;
  private final int numCells;

  /**
   * Construct a new ThreeTriosModel.
   * This constructor is private and is called by the static factory methods.
   *
   * @param cellTypes configuration grid of cellTypes
   * @param allCards list of cards to be played in the game
   */
  private ThreeTriosModel(CellType[][] cellTypes, List<Card> allCards) {
    validateModelArgs(cellTypes, allCards);
    GridManager manager = new ThreeTriosGridManager(cellTypes);
    if (allCards.size() < manager.numberOfCells() + 1) {
      throw new IllegalArgumentException("There must be at least " + (manager.numberOfCells() + 1)
              + " cards available.");
    }
    this.numCells = manager.numberOfCells();
    this.ruleKeeper = manager;
    this.players = new GamePlayer[]{GamePlayer.RED, GamePlayer.BLUE};
    this.currentPlayerIndex = 0;
    this.allCards = allCards;
  }

  /**
   * Creates a new instance of {@code ThreeTriosModel} using the specified file paths
   * to load the grid layout and card data.
   *
   * @param cellTypesFilePath the path to the configuration file that defines the grid layout
   * @param cardsFilePath the path to the card database file
   * @return a new instance of {@code ThreeTriosModel} initialized with data from the files
   *
   * @throws IllegalArgumentException if the file paths cannot be found or config data is invalid
   * @throws IllegalArgumentException if cellTypes or allCards is null
   * @throws IllegalArgumentException if cellTypes is empty or has a length of 0
   * @throws IllegalArgumentException if the number of non-hole cells is even
   * @throws IllegalArgumentException if cards in the allCards list are not unique
   * @throws IllegalArgumentException if a row in the grid is null or contains a null cell type
   * @throws IllegalArgumentException if the number of cards is not at least the number of non-hole
   *                                  cells + 1
   */
  public static ThreeTriosModel fromFiles(String cellTypesFilePath, String cardsFilePath) {
    CellType[][] cellTypes = GameConfigParser.getCellTypes(cellTypesFilePath);
    List<Card> allCards = GameConfigParser.getCards(cardsFilePath);
    return new ThreeTriosModel(cellTypes, allCards);
  }

  /**
   * Creates a new instance of {@code ThreeTriosModel} using the provided grid layout
   * and list of cards. This constructor is used for testing purposes.
   *
   * @param cellTypes a 2D array representing the grid layout of the game board
   * @param allCards a list of {@code Card} objects representing the card database
   * @return a new instance of {@code ThreeTriosModel} initialized with the given data
   *
   * @throws IllegalArgumentException if cellTypes or allCards is null
   * @throws IllegalArgumentException if cellTypes is empty or has a length of 0
   * @throws IllegalArgumentException if the number of non-hole cells is even
   * @throws IllegalArgumentException if cards in the allCards list are not unique
   * @throws IllegalArgumentException if a row in the grid is null or contains a null cell type
   * @throws IllegalArgumentException if the number of cards is not at least the number of non-hole
   *                                  cells + 1
   */
  public static ThreeTriosModel fromData(CellType[][] cellTypes, List<Card> allCards) {
    return new ThreeTriosModel(cellTypes, allCards);
  }

  /**
   * Distribute (N + 1)/2 cards to each player's hand, where N is number of non-hole cells.
   */
  private void distributeCards() {
    playerHands = new HashMap<>();
    for (GamePlayer player : players) {
      playerHands.put(player, new ThreeTriosHand(new ArrayList<>()));
    }
    int cardsPerPlayer = (numCells + 1) / players.length;
    int playerIndex = 0;
    for (int i = 0; i < cardsPerPlayer * 2; i++) {
      if (playerHands.get(players[playerIndex]).handSize() < cardsPerPlayer) {
        Card cardToDistribute = allCards.get(i);
        playerHands.get(players[playerIndex]).addCard(cardToDistribute);
        cardToDistribute.setOwner(players[playerIndex]);
        playerIndex = (playerIndex + 1) % players.length;
      }
    }
  }

  /**
   * Initializes the game by distributing cards and shuffling cards.
   *
   * @param shuffle true if want to shuffle this list of cards, false otherwise
   * @throws IllegalStateException if game is already in progress
   */
  public void startGame(boolean shuffle) {
    validateGameInProgress();
    if (shuffle) {
      Collections.shuffle(allCards);
    }
    distributeCards();
    this.gameStarted = true;
  }

  /**
   * Get the winner of the game. When there is only one winner, it will return the
   * winner. If the game results in a tie, it will return Optional.empty() to avoid
   * returning null.
   *
   * @return the winner of the game
   * @throws IllegalStateException if the game is not started
   */
  public Optional<GamePlayer> getWinner() {
    validateGameNotStarted();
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
    return winners.size() == 1 ? Optional.of(winners.get(0)) : Optional.empty();
  }

  /**
   * Switch the currentPlayerIndex to the next one.
   */
  private void nextPlayer() {
    currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
  }

  /**
   * Return the list of cards in hand of the current player in the game. Modifying this
   * list does not change the cards in player's hand.
   *
   * @return list of cards in current player's hand
   * @throws IllegalStateException if the game is not started or is over
   */
  public GamePlayer getCurrentPlayer() {
    validateGameNotStarted();
    return players[currentPlayerIndex];
  }

  /**
   * Gets the hand of the specified player. Modifying this list does not modify actual
   * cards in a player's hand.
   *
   * @param player player whose hand is to be retrieved
   *
   * @return list of cards in the player's hand
   * @throws IllegalStateException if the game has not started or is over
   */
  private List<Card> getHand(GamePlayer player) {
    validateGameNotStarted();
    return playerHands.get(player).getCards();
  }

  /**
   * Places a card at the specified position on the grid.
   *
   * @param index The index of the card in the player's hand (0-indexed)
   * @param row   The row to place the card (0-indexed)
   * @param col   The column to place the card (0-indexed)
   *
   * @throws IllegalArgumentException if index, row, or col out of bound
   * @throws IllegalStateException if cannot place a card because cell is a hole or is non-empty
   * @throws IllegalStateException if the game is not started or is over
   */
  public void placeCard(int index, int row, int col) {
    validateGameNotStartOrOver();
    Hand currentPlayerHand = playerHands.get(players[currentPlayerIndex]);
    Card card = currentPlayerHand.removeCard(index);
    ruleKeeper.placeCard(card, row, col);
    ruleKeeper.executeBattle(row, col);
    nextPlayer();
  }

  /**
   * Checks if the game is over.
   *
   * @return true if the game is over, otherwise false
   * @throws IllegalStateException if game is not started
   */
  public boolean gameOver() {
    validateGameNotStarted();
    return ruleKeeper.isGameOver();
  }

  /**
   * Gets the size of the specified player's hand.
   *
   * @param player The player whose hand size is to be retrieved
   * @return The size of the player's hand
   *
   * @throws IllegalStateException if game is not started or is over
   */
  public int getHandSize(GamePlayer player) {
    validateGameNotStarted();
    return playerHands.get(player).handSize();
  }

  /**
   * Gets a copy of current grid of the game. Modifying this 2d array does not modify
   * the game state.
   *
   * @return the 2d-array representation of cards in the grid
   * @throws IllegalStateException if game is not started
   */
  public Card[][] getGrid() {
    validateGameNotStarted();
    return ruleKeeper.getGrid();
  }

  /**
   * Get a copy of the layout of cell types. Modifying this 2d array does not modify
   * the game state.
   *
   * @return the 2d-array cell type representation of the grid
   * @throws IllegalStateException if game is not started
   */
  public CellType[][] getCellTypes() {
    validateGameNotStarted();
    return ruleKeeper.getCellTypes();
  }

  /**
   * Return the list of cards in hand of the current player in the game. Modifying this
   * list does not change the cards in player's hand.
   *
   * @return list of cards in current player's hand
   * @throws IllegalStateException if the game is not started
   */
  public List<Card> getCurrentPlayerHand() {
    validateGameNotStarted();
    return getHand(players[currentPlayerIndex]);
  }

  /**
   * Validates if game is not started.
   *
   * @throws IllegalStateException if game is not started
   */
  private void validateGameNotStarted() {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started");
    }
  }

  /**
   * Validates if game is not started and game is over.
   *
   * @throws IllegalStateException if game is not started
   * @throws IllegalStateException if game is over
   */
  private void validateGameNotStartOrOver() {
    validateGameNotStarted();
    if (ruleKeeper.isGameOver()) {
      throw new IllegalStateException("Game is over");
    }
  }

  /**
   * Validates if game is already in progress.
   *
   * @throws IllegalStateException if game is in progress (game is started but not over)
   */
  private void validateGameInProgress() {
    if (gameStarted && !ruleKeeper.isGameOver()) {
      throw new IllegalStateException("Game is already in progress");
    }
  }

  /**
   * Validate that cellTypes and allCards are not null, cellTypes is not empty, and cell
   * inside it is not empty, and cards are unique.
   *
   * @param cellTypes cellTypes configuration of the game
   * @param allCards list of cards to be played in the game
   *
   * @throws IllegalArgumentException if cellTypes and allCards are null
   * @throws IllegalArgumentException if row is null or a cellType at specific col and row is null
   * @throws IllegalArgumentException if cellTypes is empty
   * @throws IllegalArgumentException if cards are not unique
   */
  private void validateModelArgs(CellType[][] cellTypes, List<Card> allCards) {
    if (cellTypes == null || allCards == null) {
      throw new IllegalArgumentException("Cell types and cards cannot be null");
    }
    validateCellTypes(cellTypes);
    Set<Card> uniqueCards = new HashSet<>(allCards);
    if (uniqueCards.size() != allCards.size()) {
      throw new IllegalArgumentException("Cards must be unique");
    }
  }

  /**
   * Validate that cellTypes do not have null row or null cell and is not empty.
   *
   * @param cellTypes cellTypes configuration of the game
   *
   * @throws IllegalArgumentException if row is null or a cellType at specific col and row is null
   * @throws IllegalArgumentException if cellTypes is empty
   */
  private void validateCellTypes(CellType[][] cellTypes) {
    for (int row = 0; row < cellTypes.length; row++) {
      if (cellTypes[row] == null) {
        throw new IllegalArgumentException("Cell types cannot contain null rows");
      }
      for (int col = 0; col < cellTypes[row].length; col++) {
        if (cellTypes[row][col] == null) {
          throw new IllegalArgumentException(String.format("Cell type at (%d, %d) can't be null",
                  row, col));
        }
      }
    }
    if (cellTypes.length == 0 || cellTypes[0].length == 0) {
      throw new IllegalArgumentException("Cell types must be at least 1x1");
    }
  }
}
