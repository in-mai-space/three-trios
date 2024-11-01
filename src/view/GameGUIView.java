package view;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import model.enums.CellType;
import model.enums.GamePlayer;
import model.interfaces.ReadOnlyGameModel;

public class GameGUIView extends JFrame {
  public GameGUIView(ReadOnlyGameModel model) {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    GridPanel gridPanel = new GridPanel(model.getCellTypes(), model.getGrid());
    HandPanel blueHand = new HandPanel(model.getHand(GamePlayer.BLUE));
    HandPanel redHand = new HandPanel(model.getHand(GamePlayer.RED));

    setTitle("Current player: " + model.getCurrentPlayer().toString());
    int gapSize = 10;
    redHand.setBorder(new EmptyBorder(0, 0, 0, gapSize));
    blueHand.setBorder(new EmptyBorder(0, gapSize, 0, 0));

    add(redHand, BorderLayout.WEST);
    add(gridPanel, BorderLayout.CENTER);
    add(blueHand, BorderLayout.EAST);
    setSize(1400, 1200);
    setVisible(true);
    setLocationRelativeTo(null);
  }
}
