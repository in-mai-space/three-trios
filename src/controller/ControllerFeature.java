package controller;

import java.util.Optional;

import model.enums.GamePlayer;

/**
 * Represents the feature of the game. This interface should be implemented by the controller.
 * The method of this interface is temporary, and will change with the specification of the
 * controller.
 */
public interface ControllerFeature {
  GamePlayer getPlayer();
  void selectCard(int index);
  void placeCard(int row, int col);
  void announceGameOver(Optional<GamePlayer> winner, int score);
  void gameStart();
  void notifyPlayerTurn(GamePlayer player);
}
