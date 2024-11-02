package view.gui;

import controller.Feature;

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
  void addFeatures(Feature features);

  /**
   * Refresh the view when there is new changes to the game.
   */
  void refresh();
}
