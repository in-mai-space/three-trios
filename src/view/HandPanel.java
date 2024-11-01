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

    int cardWidth = Math.min(panelWidth - 10, 200);
    int cardHeight = Math.min(panelHeight / hand.size(), 300);

    int startX = (panelWidth - cardWidth) / 2;

    for (int i = 0; i < hand.size(); i++) {
      Card card = hand.get(i);
      Graphics2D cardG2d = (Graphics2D) g2d.create();

      int yPos = i * cardHeight;
      cardG2d.translate(startX, yPos);

      CellCardFrame cardFrame = new CellCardFrame.CardBuilder()
              .setColor(Utils.getCardColor(card.getOwner()))
              .setAttackValues(card.getAllAttackValues())
              .build();

      double scaleX = (double) cardWidth / 200;
      double scaleY = (double) cardHeight / 300;
      cardG2d.scale(scaleX, scaleY);

      cardFrame.draw(cardG2d);
      cardG2d.dispose();
    }

    g2d.dispose();
  }
}
