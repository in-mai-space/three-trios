package provider.controller;

/**
 * A controller interface that defines the necessary methods
 * to create a controller unique to ThreeTrios.
 */
public interface ThreeTriosController {
  /**
   * Starts the game with a message and kicks off the first turn.
   */
  void startGame();

  /**
   * Checks if the player is a MachinePlayer and plays it's turn and
   * calls another method to swap to the next player and allow for
   * their turn to take place.
   */
  void takeTurn();
}
