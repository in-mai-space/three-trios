package controller;

import view.gui.GameGUIView;

/**
 * Represent a simple controller interface for the game.
 */
public interface GameController extends Feature {

  /**
   * Set the view of the game.
   *
   * @param view GUI view
   * @throws IllegalArgumentException if view is null
   */
  void setView(GameGUIView view);
}
