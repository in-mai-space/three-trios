package view.gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import controller.ControllerFeature;
import model.enums.GamePlayer;
import model.interfaces.ReadOnlyGameModel;

/**
 * Represent the game GUI view in the MVC model for ThreeTriosGame.
 */
public class ThreeTriosView extends JFrame implements GameGUIView {
  private ThreeTriosMainPanel mainPanel;
  private final ReadOnlyGameModel model;
  private GamePlayer player;

  /**
   * Construct the new instance of the view.
   *
   * @param model read only version of game model
   */
  public ThreeTriosView(ReadOnlyGameModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.model = model;
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
    this.player = player;
  }

  /**
   * Make the view visible when the game starts.
   */
  @Override
  public void makeVisible() {
    setTitle(String.format("Player: %s | Current player: %s", player, model.getCurrentPlayer()));
    mainPanel = new ThreeTriosMainPanel(model, player);
    setContentPane(mainPanel);
    setPreferredSize(new Dimension(1400, 1200));
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
    revalidate();
  }

  /**
   * Show message dialog pane to notify player of their turn, any errors, or end of
   * game status.
   *
   * @param message message to be shown to player
   */
  @Override
  public void showMessageDialogPane(String message) {
    JOptionPane.showMessageDialog(this, message);
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
    mainPanel.addObserver(features);
  }

  /**
   * Refresh the view when there is new changes to the game.
   */
  @Override
  public void refresh() {
    mainPanel.refresh();
    setTitle(String.format("Player: %s | Current player: %s", player, model.getCurrentPlayer()));
    revalidate();
    repaint();
  }
}
