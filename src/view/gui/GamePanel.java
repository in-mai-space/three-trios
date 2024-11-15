package view.gui;

import controller.ControllerFeature;
import model.enums.GamePlayer;

/**
 * Represents interface for the game panel.
 */
public interface GamePanel {

  /**
   * Add features to the panel.
   *
   * @param features controller that implements features
   * @throws IllegalArgumentException if features is null
   */
  void addObserver(ControllerFeature features);

  /**
   * Refresh the view when there is new changes to the game.
   */
  void refresh();
}
