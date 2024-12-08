package adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Utils;
import model.enums.CellType;
import model.enums.GamePlayer;
import model.interfaces.GameModel;
import provider.model.Card;
import provider.model.CardColor;
import provider.model.GridCell;
import provider.model.GridInt;
import provider.model.ModelStatusFeatures;
import provider.model.Phases;
import provider.model.Player;
import provider.model.ThreeTriosCard;
import provider.model.ThreeTriosGameModel;

/**
 * Represents the GameModelAdapter to adapt original model to provider's model.
 *
 * @param <C> any interface that extends Card interface
 */
public class GameModelAdapter<C extends Card> implements ThreeTriosGameModel<C> {
  private final GameModel baseModel;
  private final Player redPlayer;
  private final Player bluePlayer;

  /**
   * Construct a new model adapter.
   *
   * @param model model to be adapted
   */
  public GameModelAdapter(GameModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.baseModel = model;
    this.redPlayer = new PlayerAdapter(CardColor.RED);
    this.bluePlayer = new PlayerAdapter(CardColor.BLUE);
  }

  /**
   * Returns if the game is over.
   *
   * @return true if the game has ended and false otherwise
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public boolean gameOver() {
    return baseModel.gameOver();
  }

  /**
   * Returns the winning player.
   *
   * @return the player with the most cords, on the grid and in their hand, or null if it's a tie
   * @throws IllegalStateException if the game is not over
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public Player determineWinner() {
    Optional<GamePlayer> winner = baseModel.getWinner();
    if (winner.isPresent()) {
      return getPlayer(Utils.convertColor(winner.get()));
    }
    return getPlayer(CardColor.UNASSIGNED);
  }

  /**
   * Provides the current game state.
   *
   * @return the game phase from the constructor
   */
  @Override
  public Phases getGamePhase() {
    throw new UnsupportedOperationException("Method not supported");
  }

  /**
   * Provides the copy of the player associated with the appropriate color.
   *
   * @param color the color given to find a player
   * @return a player from the constructor or a new player if the color was unassigned
   */
  @Override
  public Player getPlayer(CardColor color) {
    if (color == CardColor.RED) {
      return redPlayer;
    }
    else if (color == CardColor.BLUE) {
      return bluePlayer;
    }
    else {
      return new PlayerAdapter(CardColor.UNASSIGNED);
    }
  }

  /**
   * Provides the current grid.
   *
   * @return the grid from the constructor
   */
  @Override
  public GridCell[][] getGrid() {
    return new GridCell[baseModel.getGridHeight()][baseModel.getGridWidth()];
  }

  /**
   * Returns whether the given cell is a hole or not.
   *
   * @param row row index (0-indexed)
   * @param col col index (0-indexed)
   */
  @Override
  public boolean isHole(int row, int col) {
    return baseModel.getCellTypes()[row][col] == CellType.HOLE;
  }

  /**
   * Returns the blue player's hand.
   */
  @Override
  public ArrayList<ThreeTriosCard> getBlueHand() {
    return getPlayerHand(GamePlayer.BLUE);
  }

  /**
   * Returns the blue player's hand.
   */
  @Override
  public ArrayList<ThreeTriosCard> getRedHand() {
    return getPlayerHand(GamePlayer.RED);
  }

  /**
   * Return list of ThreeTriosCard given a GamePlayer color enum.
   *
   * @param color color
   * @return list of player's hand given a color
   */
  private ArrayList<ThreeTriosCard> getPlayerHand(GamePlayer color) {
    List<model.interfaces.Cell> hand = baseModel.getHand(color);
    ArrayList<ThreeTriosCard> handCards = new ArrayList<>();
    for (model.interfaces.Cell cell : hand) {
      handCards.add(new CardAdapter(cell));
    }
    return handCards;
  }

  /**
   * Returns the card at the given row and col of the grid.
   *
   * @param row the row from which to get the card
   * @param col the column from which to get the card
   * @return the card at the given spot, if it's a hole or does not contain one, return null
   */
  @Override
  public ThreeTriosCard getCardAt(int row, int col) {
    try {
      return new CardAdapter(baseModel.getCardAt(row, col));
    }
    catch (IllegalStateException e) {
      return null;
    }
  }

  /**
   * Returns the color of the current player in String format.
   *
   * @return a string representation of the player whose turn it is.
   */
  @Override
  public String getCurrentPlayerName() {
    try {
      return baseModel.getCurrentPlayer().toString();
    }
    catch (IllegalStateException e) {
      return GamePlayer.RED.toString();
    }
  }

  /**
   * Prints to the console of row and col. This method is not supported because we are not printing
   * out anything to the console.
   *
   * @param row row index (0-indexed)
   * @param col col index (0-indexed)
   */
  @Override
  public void logInspection(int row, int col) {
    throw new UnsupportedOperationException("Method not supported");
  }

  /**
   * Starts the game with the given options. The deck given is used
   * to set up the player's hands. Modifying the deck given to this method
   * will not modify the game state in any way.
   *
   * @param grid     the grid used to set up and play the game
   * @param deck     the cards used to set up and play the game
   * @param handSize the maximum number of cards allowed in the player's hands
   * @param red      player 1
   * @param blue     player 2
   */
  @Override
  public void startGame(GridInt grid, ArrayList<C> deck, int handSize, Player red, Player blue) {
    baseModel.startGame(true);
  }

  /**
   * Play the given card from the given player's hand to the grid.
   * The method can only be called once per turn. This method is unsupported because our model
   * does not involve a player.
   *
   * @param player    the player playing the card
   * @param cardIndex a 0-index number representing the card to play from the hand
   * @param row       the row of the grid the player wants to play to
   * @param col       the column of the grid the player wants to play to
   * @throws UnsupportedOperationException if is called
   */
  @Override
  public void playCard(Player player, int cardIndex, int row, int col) {
    throw new UnsupportedOperationException("Method not supported");
  }

  /**
   * Play the given card from the given player's hand to the grid.
   * The method can only be called once per turn. This method is not support because our model
   * does all the operations of draw card and switch turn when a card is placed on a grid.
   *
   * @param player the player drawing cards
   * @throws UnsupportedOperationException if is called
   */
  @Override
  public void drawCard(Player player) {
    throw new UnsupportedOperationException("Method not supported");
  }

  /**
   * Add controller as an observer of model events. This method is currently not supported
   * since we are still using our original model and controller.
   *
   * @param status the controller
   * @throws UnsupportedOperationException if is called
   */
  @Override
  public void setModelActionFeatures(ModelStatusFeatures status) {
    throw new UnsupportedOperationException("Method not supported");
  }
}
