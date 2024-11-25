package adapter;

import controller.ControllerFeature;
import controller.GameController;
import model.enums.GamePlayer;
import provider.controller.PlayerActionFeatures;
import provider.controller.ThreeTriosController;

public class ControllerAdapter implements ThreeTriosController, PlayerActionFeatures {
  private final ControllerFeature controller;

  public ControllerAdapter(ControllerFeature controller) {
    if (controller == null) {
      throw new IllegalArgumentException("Controller cannot be null");
    }
    this.controller = controller;
  }

  /**
   * A method that handles user clicks and turns them into
   * move on the board.
   *
   * @param cardSelected
   * @param cellRow
   * @param cellCol
   * @param cardColor
   */
  @Override
  public void onCardSelected(int cardSelected, int cellRow, int cellCol, int cardColor) {
    GamePlayer color = cardColor == 0 ? GamePlayer.RED : GamePlayer.BLUE;
    controller.selectCard(cardSelected, color);
    controller.placeCard(cellRow, cellCol);
  }

  /**
   * Starts the game with a message and kicks off the first turn.
   */
  @Override
  public void startGame() {
    controller.gameStart();
  }

  /**
   * Checks if the player is a MachinePlayer and plays it's turn and
   * calls another method to swap to the next player and allow for
   * their turn to take place.
   */
  @Override
  public void takeTurn() {
    // not necessary for adapter
  }
}
