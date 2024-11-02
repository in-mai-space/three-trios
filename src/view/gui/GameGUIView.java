package view.gui;

import controller.Feature;

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
  void addFeatures(Feature features);

  /**
   * Refresh the view when there is new changes to the game.
   */
  void refresh();

  /**
   * Make the view visible when the game starts.
   */
  void makeVisible();
}
