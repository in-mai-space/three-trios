package player;

import controller.ControllerFeature;

/**
 * Interface representing a player in the Three Trios game. Provides methods for
 * adding an observer to the player and for performing player actions during the game.
 */
public interface ThreeTriosPlayer {

  /**
   * Registers a controller as an observer for this player. Observers can be notified
   * of player actions or state changes.
   *
   * @param observer The controller to be added as an observer
   * @return true if the observer is added for machine player, false otherwise
   * @throws IllegalArgumentException if observer is null
   */
  boolean addObserver(ControllerFeature observer);

  /**
   * Executes the action of playing a card. This method will trigger the logic
   * for the player to select and play a card, either using strategy for machine player
   * or does nothing for human player.
   */
  void playCard();
}
