package player;

import controller.ControllerFeature;
import model.interfaces.ReadOnlyGameModel;

/**
 * Represents a human-controlled player in the Three Trios game.
 * This class is mainly a placeholder, as a human player’s actions are
 * handled directly through the game GUI rather than through code.
 */
public class HumanPlayer implements ThreeTriosPlayer {

  /**
   * Constructs a HumanPlayer with a reference to the game model. For a human
   * player, no specific initialization actions are required.
   *
   * @param model A read-only view of the game model
   */
  public HumanPlayer(ReadOnlyGameModel model) {
    // no initialization necessary for a human player
  }

  /**
   * This method does not add an observer for a human player, as actions are
   * handled directly through the user interface rather than programmatically.
   *
   * @param observer The controller to be added as an observer (not used)
   * @return false, indicating no observer is added for a human player
   */
  @Override
  public boolean addObserver(ControllerFeature observer) {
    return false;
  }

  /**
   * This method does not perform any action for a human player, as the player’s
   * actions are managed directly through the user interface.
   */
  @Override
  public void playCard() {
    // no action taken for a human player since interaction is handled by the GUI
  }
}
