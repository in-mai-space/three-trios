package player;

import controller.ControllerFeature;
import model.Utils;
import model.enums.GamePlayer;
import model.interfaces.ReadOnlyGameModel;
import strategy.Move;
import strategy.Pair;
import strategy.infallible.InfallibleGameStrategy;

/**
 * Represents a machine-controlled player in the Three Trios game.
 * Uses a game strategy to decide moves based on the game state.
 */
public class MachinePlayer implements ThreeTriosPlayer {
  private final InfallibleGameStrategy strategy;
  private final ReadOnlyGameModel model;
  private ControllerFeature observer;
  private GamePlayer player;
  private final Appendable log;

  /**
   * Main constructor to create a MachinePlayer with the specified game model and strategy.
   *
   * @param model    A read-only view of the game model
   * @param strategy The strategy used to decide moves for the machine player
   * @throws IllegalArgumentException if model or strategy is null
   */
  public MachinePlayer(ReadOnlyGameModel model, InfallibleGameStrategy strategy) {
    if (model == null || strategy == null) {
      throw new IllegalArgumentException("Model and strategy cannot be null");
    }
    this.strategy = strategy;
    this.model = model;
    this.log = new StringBuilder();
  }

  /**
   * Constructs a MachinePlayer with the specified game model and strategy. This is a
   * convenient constructor for testing.
   *
   * @param model    A read-only view of the game model
   * @param strategy The strategy used to decide moves for the machine player
   * @throws IllegalArgumentException if model or strategy is null
   */
  public MachinePlayer(ReadOnlyGameModel model, InfallibleGameStrategy strategy, Appendable log) {
    if (model == null || strategy == null) {
      throw new IllegalArgumentException("Model and strategy cannot be null");
    }
    this.strategy = strategy;
    this.model = model;
    this.log = log;
  }

  /**
   * Executes the action of playing a card. This method will trigger the logic
   * for the player to select and play a card, either using strategy for machine player
   * or does nothing for human player.
   */
  public void playCard() {
    if (!model.gameOver() && player == model.getCurrentPlayer()) {
      Pair<Move, Integer> nextMove = strategy.decideMove(model, player);
      int cardIndex = model.getHand(player).indexOf(nextMove.getKey().getCard());
      int row = nextMove.getKey().getRow();
      int col = nextMove.getKey().getCol();
      Utils.transmit(log, "Player " + player + " plays card with index " + cardIndex
              + " to row " + row + " and col " + col);
      observer.selectCard(cardIndex, player);
      observer.placeCard(row, col);
    }
  }

  /**
   * Registers a controller as an observer for this player. Observers can be notified
   * of player actions or state changes.
   *
   * @param observer The controller to be added as an observer
   * @return true if the observer is added for machine player, false otherwise
   * @throws IllegalArgumentException if observer is null
   */
  @Override
  public boolean addObserver(ControllerFeature observer) {
    this.observer = observer;
    this.player = observer.getPlayer();
    return true;
  }
}
