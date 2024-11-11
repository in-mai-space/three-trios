package view.gui;

import java.awt.BorderLayout;
import javax.swing.JPanel;

import controller.Feature;
import model.enums.GamePlayer;
import model.interfaces.ReadOnlyGameModel;

/**
 * Represents the main panel, which is made of of 3 smaller panels: red hand panel on the left,
 * grid panel in the center, and blue hand panel on the right.
 */
class ThreeTriosMainPanel extends JPanel implements GamePanel {
  private final ThreeTriosGridPanel gridPanel;
  private final ThreeTriosHandPanel blueHand;
  private final ThreeTriosHandPanel redHand;

  /**
   * Construct the main panel for the game, taking in a read only game model.
   *
   * @param model read only version of the model that contains only observational methods
   * @throws IllegalArgumentException if model is null
   */
  public ThreeTriosMainPanel(ReadOnlyGameModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    setLayout(new BorderLayout(10, 0));
    gridPanel = new ThreeTriosGridPanel(model.getCellTypes(), model.getGrid());
    blueHand = new ThreeTriosHandPanel(model.getHand(GamePlayer.BLUE), model.getCurrentPlayer());
    redHand = new ThreeTriosHandPanel(model.getHand(GamePlayer.RED), model.getCurrentPlayer());
    setUpSubPanels();
  }

  private void setUpSubPanels() {
    add(redHand, BorderLayout.WEST);
    add(gridPanel, BorderLayout.CENTER);
    add(blueHand, BorderLayout.EAST);
  }

  @Override
  public void addFeatures(Feature features) {
    if (features == null) {
      throw new IllegalArgumentException("Features cannot be null");
    }
    gridPanel.addFeatures(features);
    blueHand.addFeatures(features);
    redHand.addFeatures(features);
  }

  @Override
  public void refresh() {
    blueHand.refresh();
    redHand.refresh();
    gridPanel.refresh();
    repaint();
  }
}
