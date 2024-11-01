package view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;

import model.interfaces.Card;

class HandPanel extends JPanel {
  private final List<Card> hand;
  private static final int PREFERRED_WIDTH = 180;
  private int selectedCardIndex = -1;

  public HandPanel(List<Card> hand) {
    this.hand = hand;
    setOpaque(false);
    setPreferredSize(new Dimension(PREFERRED_WIDTH, 0));
    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        handleCardClick(e);
      }
    });
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

      double scaleX = (double) cardWidth / ViewData.CELL_WIDTH;
      double scaleY = (double) cardHeight / ViewData.CELL_HEIGHT;
      cardG2d.scale(scaleX, scaleY);

      CellCardFrame cardFrame = new CellCardFrame.CardBuilder()
              .setColor(ViewData.getCardColor(card.getOwner()))
              .setAttackValues(card.getAllAttackValues())
              .build();
      cardFrame.draw(cardG2d);

      if (i == selectedCardIndex) {
        CellCardFrame selectedCard = new CellCardFrame.CardBuilder()
                .setColor(ViewData.getSelectedCardColor(card.getOwner()))
                .setAttackValues(card.getAllAttackValues())
                .build();
        selectedCard.draw(cardG2d);
      }

      cardG2d.dispose();
    }

    g2d.dispose();
  }

  private void handleCardClick(MouseEvent e) {
    int cardHeight = Math.min(getHeight() / hand.size(), 300);
    int cardIndex = e.getY() / cardHeight;

    if (cardIndex >= 0 && cardIndex < hand.size()) {
      selectedCardIndex = cardIndex;
      System.out.println("Card clicked: Index " + selectedCardIndex + ", " +
              "Owner: " + hand.get(cardIndex).getOwner());
      repaint();
    }
  }
}
