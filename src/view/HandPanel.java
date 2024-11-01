package view;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import model.Utils;
import model.interfaces.Card;
import view.components.cells.BaseCell;
import view.components.cells.CardDecorator;
import view.components.cells.CellDecorator;
import view.components.cells.ColorDecorator;

public class HandPanel extends JPanel implements GamePanel {
  private final List<Card> hand;
  private static final int CELL_WIDTH = 150;
  private static final int CELL_HEIGHT = 200;

  public HandPanel(List<Card> hand) {
    super();
    this.hand = hand;
    setLayout(new GridLayout(0, 1, 5, 5));
    renderHand();
  }

  private void renderHand() {
    for (Card card : hand) {
      CellDecorator button = new CardDecorator(
              new ColorDecorator(new BaseCell(), Utils.getCardColor(card.getOwner())),
              card.getAllAttackValues()
      );
      JButton cardButton = button.getButton();
      cardButton.setPreferredSize(new Dimension(CELL_WIDTH, CELL_HEIGHT));
      this.add(cardButton);
    }
  }

  @Override
  public Dimension getPreferredSize() {
    int height = Math.max(hand.size() * CELL_HEIGHT, 200);
    return new Dimension(CELL_WIDTH, height);
  }
}
