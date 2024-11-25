package provider.view;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.io.IOException;

import provider.model.ProviderCard;
import provider.model.ThreeTriosGameModel;

/**
 * Creates the game frame for ThreeTrios using panels
 * and allows us to refresh and update the frame as the
 * game moves along.
 */
public class ThreeTriosGameFrame extends JFrame implements ThreeTriosView {

  private final ThreeTriosGamePanel gameFramePanel;
  private final ThreeTriosGameModel<ProviderCard> model;

  /**
   * Constructor for the ThreeTriosGameFrame.
   */
  public ThreeTriosGameFrame(ThreeTriosGameModel<ProviderCard> model) {
    this.model = model;
    this.setTitle("Three Trios Game - Current Player: " + model.getCurrentPlayerName());
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    gameFramePanel = new ThreeTriosGamePanel(model);
    this.add(gameFramePanel, BorderLayout.CENTER);
  }

  @Override
  public void render() throws IOException {
    // Unnecessary for this version
  }

  @Override
  public void initialize() {
    this.pack();
    this.setVisible(true);
  }

  @Override
  public void refresh() {
    updateWindowTitle();
    gameFramePanel.repaint();
  }

  /**
   * Updates the window title based on the turn of the player.
   */
  public void updateWindowTitle() {
    if (!model.gameOver()) {
      this.setTitle("Three Trios Game - Current Player: " + model.getCurrentPlayerName());
    } else {
      this.setTitle("Three Trios Game - Game Over : The winner is "
              + model.determineWinner().getPlayerColor().toString());
    }
  }

  public GamePanel getGamePanel() {
    return gameFramePanel;
  }
}
