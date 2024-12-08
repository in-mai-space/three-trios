package view.gui;

import java.awt.BorderLayout;
import javax.swing.JPanel;

import controller.ControllerFeature;
import model.enums.GamePlayer;
import model.interfaces.ReadOnlyGameModel;

/**
 * Represents the main panel, which is made of of 3 smaller panels: red hand panel on the left,
 * grid panel in the center, and blue hand panel on the right.
 */
public class ThreeTriosMainPanel extends JPanel implements GamePanel {
  private final ThreeTriosGridPanel gridPanel;
  private final ThreeTriosHandPanel blueHand;
  private final ThreeTriosHandPanel redHand;

  /**
   * Construct the main panel for the game, taking in a read only game model.
   *
   * @param model read only version of the model that contains only observational methods
   * @throws IllegalArgumentException if model is null
   */
  public ThreeTriosMainPanel(ReadOnlyGameModel model, GamePlayer player) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    setLayout(new BorderLayout(10, 0));
    gridPanel = new ThreeTriosGridPanel(model);
    blueHand = new ThreeTriosHandPanel(model, GamePlayer.BLUE, player);
    redHand = new ThreeTriosHandPanel(model, GamePlayer.RED, player);
    setUpSubPanels();
  }

  private void setUpSubPanels() {
    add(redHand, BorderLayout.WEST);
    add(gridPanel, BorderLayout.CENTER);
    add(blueHand, BorderLayout.EAST);
  }

  /**
   * Add features to the panel.
   *
   * @param features controller that implements features
   * @throws IllegalArgumentException if features is null
   */
  @Override
  public void addObserver(ControllerFeature features) {
    if (features == null) {
      throw new IllegalArgumentException("Observer cannot be null");
    }
    gridPanel.addObserver(features);
    blueHand.addObserver(features);
    redHand.addObserver(features);
  }

  /**
   * Refresh the view when there is new changes to the game.
   */
  @Override
  public void refresh() {
    blueHand.refresh();
    redHand.refresh();
    gridPanel.refresh();
    revalidate();
    repaint();
  }
}
