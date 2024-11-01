package view;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import model.Utils;
import model.interfaces.Card;

class HandPanel extends JPanel {
  private final List<Card> hand;
  private static final int PREFERRED_WIDTH = 150;

  public HandPanel(List<Card> hand) {
    this.hand = hand;
    setOpaque(false);
    setPreferredSize(new Dimension(PREFERRED_WIDTH, 0));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (hand.isEmpty()) return;

    Graphics2D g2d = (Graphics2D) g.create();
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    int panelWidth = getWidth();
    int panelHeight = getHeight();

    int cardWidth = panelWidth - 10;
    int cardHeight = panelHeight / hand.size();

    int startX = (panelWidth - cardWidth) / 2;

    for (int i = 0; i < hand.size(); i++) {
      Card card = hand.get(i);
      Graphics2D cardG2d = (Graphics2D) g2d.create();

      int yPos = i * cardHeight;

      cardG2d.translate(startX, yPos);

      CardPath cardPath = new CardPath(cardWidth, cardHeight);
      new ColorDecorator(cardPath, Utils.getCardColor(card.getOwner())).draw(cardG2d);
      new AttackValuesDecorator(cardPath, card.getAllAttackValues()).draw(cardG2d);

      cardG2d.dispose();
    }
    g2d.dispose();
  }
}