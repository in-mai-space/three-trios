package model.interfaces;

import controller.ControllerFeature;

/**
 * Interface representing features of the game model that can be observed by a controller.
 * Provides methods for managing observers and updating the game state.
 */
public interface ModelFeature {

  /**
   * Registers a controller as an observer to this model, allowing it to receive updates.
   *
   * @param observer The controller to be added as an observer
   * @throws IllegalArgumentException if controller is null
   */
  void addObserver(ControllerFeature observer);
}
