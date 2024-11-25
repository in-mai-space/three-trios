package adapter;

import controller.ControllerFeature;
import model.enums.GamePlayer;
import provider.view.ThreeTriosView;
import view.gui.GameGUIView;

public class ViewAdapter implements GameGUIView {
  private final ThreeTriosView view;

  public ViewAdapter(ThreeTriosView view) {
    if (view == null) {
      throw new IllegalArgumentException("View cannot be null");
    }
    this.view = view;
  }

  /**
   * Add features to the view.
   *
   * @param features controller that implements features
   * @throws IllegalArgumentException if features is null
   */
  @Override
  public void addObserver(ControllerFeature features) {
    if (features == null) {
      throw new IllegalArgumentException("Features cannot be null");
    }
    view.setPlayerActionFeatures(new ControllerAdapter(features));
  }

  /**
   * Refresh the view when there is new changes to the game.
   */
  @Override
  public void refresh() {
    view.refresh();
  }

  /**
   * Make the view visible when the game starts.
   */
  @Override
  public void makeVisible() {
    view.setVisible(true);
  }

  /**
   * Show message dialog pane to notify player of their turn, any errors, or end of
   * game status.
   *
   * @param message message to be shown to player
   */
  @Override
  public void showMessageDialogPane(String message) {
    view.showMessage(message);
  }

  /**
   * Sets the player for the game window, updating the interface to reflect
   * the current player’s details or state.
   *
   * @param player The player to be set for the window, typically used to
   *               display player-specific information or status
   */
  @Override
  public void setPlayer(GamePlayer player) {
    // not implemented
  }
}
