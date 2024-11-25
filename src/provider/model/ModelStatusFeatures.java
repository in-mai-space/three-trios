package provider.model;

/**
 * An interface to subscribe the controller to the model
 * for updates on the game.
 */
public interface ModelStatusFeatures {

  /**
   * Switches the next player by communicating to the model.
   */
  void switchToNextPlayer();

  /**
   * Asks the model for the winner of the game.
   * @return the winner of the game.
   */
  Player getWinner();
}
