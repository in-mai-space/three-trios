package model.interfaces;

import java.util.Optional;

import controller.ControllerFeature;
import model.enums.GamePlayer;

public interface ModelFeature {
  void addObserver(ControllerFeature controller);
  void onTurnChange(GamePlayer player);
  void onGameOver(Optional<GamePlayer> winner, int score);
}
