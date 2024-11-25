package provider.view;

import java.io.IOException;

import javax.swing.JOptionPane;

import provider.controller.PlayerActionFeatures;
import provider.model.ProviderCard;
import provider.model.ThreeTriosGameModel;

/**
 * The GUI view of the ThreeTrios game.
 */
public class ThreeTriosGUIView implements ThreeTriosView {
  private ThreeTriosGameFrame gameFrame;
  private PlayerActionFeatures playerActionFeatures;

  /**
   * Constructs a GUIView object.
   * @param readOnlyModel for the view to render
   */
  public ThreeTriosGUIView(ThreeTriosGameModel<ProviderCard> readOnlyModel) {
    gameFrame = new ThreeTriosGameFrame(readOnlyModel);
  }

  public void setPlayerActionFeatures(PlayerActionFeatures playerActionFeatures) {
    gameFrame.getGamePanel().setFeatures(playerActionFeatures);
  }

  @Override
  public void render() throws IOException {
    // uneeded
  }

  @Override
  public void setVisible(boolean b) {
    gameFrame.initialize();
  }

  public void refresh() {
    gameFrame.refresh();
  }

  public void initialize() {
    gameFrame.initialize();
  }

  /**
   * Displays a message to the user in a dialog box.
   *
   * @param message the message to display.
   */
  public void showMessage(String message) {
    JOptionPane.showMessageDialog(null, message, "Game Message", JOptionPane.INFORMATION_MESSAGE);
  }
}
