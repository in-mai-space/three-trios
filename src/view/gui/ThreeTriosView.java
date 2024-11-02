package view.gui;

import java.awt.*;
import javax.swing.*;

import controller.Feature;
import model.interfaces.ReadOnlyGameModel;

public class ThreeTriosView extends JFrame implements GameGUIView {
  private final ThreeTriosMainPanel mainPanel;

  public ThreeTriosView(ReadOnlyGameModel model) {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Current player: " + model.getCurrentPlayer().toString());
    mainPanel = new ThreeTriosMainPanel(model);
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
