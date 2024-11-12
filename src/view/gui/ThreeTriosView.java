package view.gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.Feature;
import model.interfaces.ReadOnlyGameModel;

/**
 * Represent the game GUI view in the MVC model for ThreeTriosGame.
 */
public class ThreeTriosView extends JFrame implements GameGUIView {
  private final ThreeTriosMainPanel mainPanel;

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
    setTitle("Current player: " + model.getCurrentPlayer().toString());
    mainPanel = new ThreeTriosMainPanel(model);
    setContentPane(mainPanel);
  }

  /**
   * Make the view visible when the game starts.
   */
  @Override
  public void makeVisible() {
    setPreferredSize(new Dimension(1400, 1200));
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
    revalidate();
  }

  /**
   * Add features to the view.
   *
   * @param features controller that implements features
   * @throws IllegalArgumentException if features is null
   */
  @Override
  public void addFeatures(Feature features) {
    if (features == null) {
      throw new IllegalArgumentException("Features cannot be null");
    }
    mainPanel.addFeatures(features);
  }

  /**
   * Refresh the view when there is new changes to the game.
   */
  @Override
  public void refresh() {
    repaint();
  }
}
