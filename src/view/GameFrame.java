package view;

import java.awt.*;
import javax.swing.*;
import model.enums.GamePlayer;
import model.interfaces.Card;
import model.interfaces.ReadOnlyGameModel;

public class GameFrame extends JFrame {
  public GameFrame(ReadOnlyGameModel model) {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Current player: " + model.getCurrentPlayer().toString());

    JPanel contentPane = new JPanel(new BorderLayout(10, 0));
    setContentPane(contentPane);

    GridPanel gridPanel = new GridPanel(model.getCellTypes(), model.getGrid());
    HandPanel blueHand = new HandPanel(model.getHand(GamePlayer.BLUE), model.getCurrentPlayer());
    HandPanel redHand = new HandPanel(model.getHand(GamePlayer.RED), model.getCurrentPlayer());

    Dimension handSize = new Dimension(120, 0);
    blueHand.setMinimumSize(handSize);
    redHand.setMinimumSize(handSize);

    contentPane.add(redHand, BorderLayout.WEST);
    contentPane.add(gridPanel, BorderLayout.CENTER);
    contentPane.add(blueHand, BorderLayout.EAST);

    setPreferredSize(new Dimension(1400, 1200));
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
    revalidate();
  }
}