package view;

import java.util.List;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import model.enums.GamePlayer;
import model.interfaces.Card;
import model.interfaces.ReadOnlyGameModel;

public class GameGUIView extends JFrame {
  public GameGUIView(ReadOnlyGameModel model) {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Current player: " + model.getCurrentPlayer().toString());

    JPanel contentPane = new JPanel(new BorderLayout(10, 0));
    setContentPane(contentPane);

    Card[][] grid = model.getGrid();
    grid[0][0] = model.getCurrentPlayerHand().get(0);

    GridPanel gridPanel = new GridPanel(model.getCellTypes(), grid);
    HandPanel blueHand = new HandPanel(model.getHand(GamePlayer.BLUE));
    HandPanel redHand = new HandPanel(model.getHand(GamePlayer.RED));

    Dimension handSize = new Dimension(120, 0);
    blueHand.setMinimumSize(handSize);
    redHand.setMinimumSize(handSize);

    contentPane.add(redHand, BorderLayout.WEST);
    contentPane.add(gridPanel, BorderLayout.CENTER);
    contentPane.add(blueHand, BorderLayout.EAST);

    pack();
    setSize(1400, 1200);
    setLocationRelativeTo(null);
    setVisible(true);
    revalidate();
  }
}