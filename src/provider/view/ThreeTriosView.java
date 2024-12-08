package provider.view;

import java.io.IOException;

import provider.controller.PlayerActionFeatures;

/**
 * Behaviors needed for a view of the Three Trios implementation
 * that transmits information to the user.
 */
public interface ThreeTriosView {
  /**
   * Renders a model in some manner (e.g. as text, or as graphics, etc.).
   *
   * @throws IOException if the rendering fails
   */
  void render() throws IOException;

  void setVisible(boolean b);

  void initialize();

  void refresh();

  void showMessage(String message);

  void setPlayerActionFeatures(PlayerActionFeatures playerActionFeatures);
}
