package controller.mocks;

import controller.ControllerFeature;
import model.Utils;
import model.enums.GamePlayer;
import model.interfaces.ReadOnlyGameModel;
import player.ThreeTriosPlayer;
import strategy.infallible.InfallibleGameStrategy;

/**
 * Represents a machine-controlled player in the Three Trios game.
 * Uses a game strategy to decide moves based on the game state.
 */
public class MockMachinePlayer implements ThreeTriosPlayer {
  private final Appendable log;

  /**
   * Constructs a MachinePlayer with the specified game model and strategy.
   *
   * @param model    A read-only view of the game model
   * @param strategy The strategy used to decide moves for the machine player
   * @throws IllegalArgumentException if model or strategy is null
   */
  public MockMachinePlayer(ReadOnlyGameModel model, InfallibleGameStrategy strategy,
                           Appendable log) {
    this.log = log;
  }

  /**
   * Executes the action of playing a card. This method will trigger the logic
   * for the player to select and play a card, either using strategy for machine player
   * or does nothing for human player.
   */
  public void playCard() {
    Utils.transmit(log, "playCard in machine is called");
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
    GamePlayer player = observer.getPlayer();
    Utils.transmit(log, "add controller as observer with player " + player);
    return true;
  }
}
