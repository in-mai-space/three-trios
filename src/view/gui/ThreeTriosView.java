package view.gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.Feature;
import model.implementation.ThreeTriosViewModel;
import model.interfaces.GameModel;
import model.interfaces.ReadOnlyGameModel;

public class ThreeTriosView extends JFrame implements GameGUIView {
  private final ThreeTriosMainPanel mainPanel;

  public ThreeTriosView(GameModel model) {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    ReadOnlyGameModel readOnlyModel = new ThreeTriosViewModel(model);
    setTitle("Current player: " + model.getCurrentPlayer().toString());
    mainPanel = new ThreeTriosMainPanel(readOnlyModel);
    setUpContent(mainPanel);
  }

  private void setUpContent(JPanel mainPanel) {
    setContentPane(mainPanel);
  }

  public void makeVisible() {
    setPreferredSize(new Dimension(1400, 1200));
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
    revalidate();
  }

  @Override
  public void addFeatures(Feature features) {
    if (features == null) {
      throw new IllegalArgumentException("Features cannot be null");
    }
    mainPanel.addFeatures(features);
  }

  @Override
  public void refresh() {
    repaint();
  }
}
