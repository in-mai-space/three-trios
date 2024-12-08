package provider.view;

import provider.controller.PlayerActionFeatures;

/**
 * Methods needed to set up a game panel.
 */
public interface GamePanel {
  /**
   * Used to initialize the PlayerActionFeatures.
   * @param playerActionFeatures a controller that subscribes to the view
   */
  void setFeatures(PlayerActionFeatures playerActionFeatures);
}
