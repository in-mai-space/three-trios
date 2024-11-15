package player;

import controller.ControllerFeature;
import model.interfaces.ReadOnlyGameModel;

public class HumanPlayer implements ThreeTriosPlayer {
  public HumanPlayer(ReadOnlyGameModel model) {
    // does nothing
  }

  @Override
  public boolean addObserver(ControllerFeature observer) {
    return false;
  }

  @Override
  public void playCard() {
    // does nothing
  }
}
