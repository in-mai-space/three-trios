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

public class GameModelAdapter<C extends Card> implements ThreeTriosGameModel<C> {
  private final GameModel baseModel;
  private final Player redPlayer;
  private final Player bluePlayer;

  public GameModelAdapter(GameModel model) {
    this.baseModel = model;
    this.redPlayer = new PlayerAdapter(GamePlayer.RED);
    this.bluePlayer = new PlayerAdapter(GamePlayer.BLUE);
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
    return null;
  }

  /**
   * Provides the current game state.
   *
   * @return the game phase from the constructor
   */
  @Override
  public Phases getGamePhase() {
    return null;
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
    else {
      return bluePlayer;
    }
  }

  /**
   * Provides the current grid.
   *
   * @return the grid from the constructor
   */
  @Override
  public GridCell[][] getGrid() {
    return new GridCell[0][];
  }

  /**
   * Returns whether the given cell is a hole or not.
   *
   * @param row
   * @param col
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
   * @throws IllegalStateException if the row or column is out of bounds
   */
  @Override
  public ThreeTriosCard getCardAt(int row, int col) {
    return new CardAdapter(baseModel.getCardAt(row, col));
  }

  /**
   * Returns the color of the current player in String format.
   *
   * @return a string representation of the player whose turn it is.
   */
  @Override
  public String getCurrentPlayerName() {
    return baseModel.getCurrentPlayer().toString();
  }

  @Override
  public void logInspection(int row, int col) {
    // unused method
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
   * The method can only be called once per turn.
   *
   * @param player    the player playing the card
   * @param cardIndex a 0-index number representing the card to play from the hand
   * @param row       the row of the grid the player wants to play to
   * @param col       the column of the grid the player wants to play to
   * @throws IllegalStateException    if the game has not started
   * @throws IllegalStateException    the game is over
   * @throws IllegalStateException    if a player tries to play twice in a row
   * @throws IllegalArgumentException if the row or column is out of bounds
   * @throws IllegalArgumentException if the cell being played to is a hole
   * @throws IllegalArgumentException if cardIndex < 0
   *                                  or greater/equal to the number of cards in hand
   */
  @Override
  public void playCard(Player player, int cardIndex, int row, int col) {
    // unused method
  }

  /**
   * Play the given card from the given player's hand to the grid.
   * The method can only be called once per turn.
   *
   * @param player the player drawing cards
   * @throws IllegalStateException if the deck is empty
   * @throws IllegalStateException if the player already has a full hand
   */
  @Override
  public void drawCard(Player player) throws Exception {
    // unused method
  }

  @Override
  public void setModelActionFeatures(ModelStatusFeatures status) {
    // unused method
  }
}
