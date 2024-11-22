package controller.mocks;

import controller.ControllerFeature;
import model.Utils;
import model.interfaces.ReadOnlyGameModel;
import player.ThreeTriosPlayer;

/**
 * Represents a mock human player for controller unit testing.
 */
public class MockHumanPlayer implements ThreeTriosPlayer {
  private final Appendable log;

  /**
   * Construct a new mock human player.
   *
   * @param model game model
   * @param log appendable to collect messages
   */
  public MockHumanPlayer(ReadOnlyGameModel model, Appendable log) {
    // does nothing
    this.log = log;
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
    Utils.transmit(log, "addObserver method is called but does nothing");
    return false;
  }

  /**
   * Executes the action of playing a card. This method will trigger the logic
   * for the player to select and play a card, either using strategy for machine player
   * or does nothing for human player.
   */
  @Override
  public void playCard() {
    Utils.transmit(log, "playCard is called, does nothing");
  }
}
