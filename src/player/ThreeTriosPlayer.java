package player;

import controller.ControllerFeature;
import model.enums.GamePlayer;

public interface ThreeTriosPlayer {
  boolean addObserver(ControllerFeature observer);
  void playCard();
}
