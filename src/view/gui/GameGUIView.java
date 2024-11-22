package view.gui;

import controller.ControllerFeature;
import model.enums.GamePlayer;

/**
 * Represents the interface of GameGUIView which allows it to add features, refresh view
 * and make view visible.
 */
public interface GameGUIView {

  /**
   * Add features to the view.
   *
   * @param features controller that implements features
   * @throws IllegalArgumentException if features is null
   */
  void addObserver(ControllerFeature features);

  /**
   * Refresh the view when there is new changes to the game.
   */
  void refresh();

  /**
   * Make the view visible when the game starts.
   */
  void makeVisible();

  /**
   * Show message dialog pane to notify player of their turn, any errors, or end of
   * game status.
   *
   * @param message message to be shown to player
   */
  void showMessageDialogPane(String message);

  /**
   * Sets the player for the game window, updating the interface to reflect
   * the current player’s details or state.
   *
   * @param player The player to be set for the window, typically used to
   *               display player-specific information or status
   */
  void setPlayer(GamePlayer player);
}
