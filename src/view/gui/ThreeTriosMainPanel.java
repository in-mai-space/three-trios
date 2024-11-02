package view.gui;

import java.awt.BorderLayout;
import javax.swing.JPanel;

import controller.Feature;
import model.enums.GamePlayer;
import model.interfaces.ReadOnlyGameModel;

class ThreeTriosMainPanel extends JPanel implements GamePanel {
  private final ThreeTriosGridPanel gridPanel;
  private final ThreeTriosHandPanel blueHand;
  private final ThreeTriosHandPanel redHand;

  public ThreeTriosMainPanel(ReadOnlyGameModel model) {
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
