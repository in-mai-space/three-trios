package controller.mocks;

import javax.swing.*;

import controller.ControllerFeature;
import model.Utils;
import model.enums.GamePlayer;
import model.interfaces.ReadOnlyGameModel;
import view.gui.GameGUIView;

public class MockGUIView implements GameGUIView {
  private final Appendable log;
  public MockGUIView(ReadOnlyGameModel model, Appendable log) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.log = log;
  }

  /**
   * Add features to the view.
   *
   * @param features controller that implements features
   * @throws IllegalArgumentException if features is null
   */
  @Override
  public void addObserver(ControllerFeature features) {
    Utils.transmit(log, "Add controller as an observer");
  }

  /**
   * Refresh the view when there is new changes to the game.
   */
  @Override
  public void refresh() {
    Utils.transmit(log, "Refresh the view");
  }

  /**
   * Make the view visible when the game starts.
   */
  @Override
  public void makeVisible() {
    Utils.transmit(log, "Make the view visible");
  }

  /**
   * Show message dialog pane to notify player of their turn, any errors, or end of
   * game status.
   *
   * @param message message to be shown to player
   */
  @Override
  public void showMessageDialogPane(String message) {
    Utils.transmit(log, "showMessageDialogPane is called with message: " + message);
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
    Utils.transmit(log, "View is set player with color " + player);
  }
}
