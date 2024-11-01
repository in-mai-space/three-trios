package view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.List;
import javax.swing.*;

import model.enums.GamePlayer;
import model.interfaces.Card;

class HandPanel extends JPanel {
  private final List<Card> hand;
  private static final int PREFERRED_WIDTH = 180;
  private int selectedCardIndex = -1;
  private final GamePlayer currentPlayer;

  public HandPanel(List<Card> hand, GamePlayer currentPlayer) {
    this.hand = hand;
    this.currentPlayer = currentPlayer;
    setOpaque(false);
    setPreferredSize(new Dimension(PREFERRED_WIDTH, 0));

    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        handleCardClick(e);
      }
    });
  }

  private int getCardHeight() {
    return getHeight() / Math.max(1, hand.size());
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (hand.isEmpty()) return;

    Graphics2D g2d = (Graphics2D) g.create();
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    int panelWidth = getWidth();
    int panelHeight = getHeight();

    // Calculate card dimensions
    int cardHeight = getCardHeight(); // Use the shared method
    int cardWidth = Math.min(panelWidth - 10, 200);
    int startX = (panelWidth - cardWidth) / 2;

    // Create an AffineTransform object
    AffineTransform affineTransform = new AffineTransform();

    for (int i = 0; i < hand.size(); i++) {
      Card card = hand.get(i);
      Graphics2D cardG2d = (Graphics2D) g2d.create();

      int yPos = i * cardHeight;
      affineTransform.setToIdentity();
      affineTransform.translate(startX, yPos);
      double scaleX = (double) cardWidth / ViewData.CELL_WIDTH;
      double scaleY = (double) cardHeight / ViewData.CELL_HEIGHT;
      affineTransform.scale(scaleX, scaleY);

      cardG2d.transform(affineTransform);

      if (card.getOwner() == currentPlayer) {
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
      } else {
        cardG2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        CellCardFrame cardFrame = new CellCardFrame.CardBuilder()
                .setColor(ViewData.getCardColor(card.getOwner()))
                .setAttackValues(card.getAllAttackValues())
                .build();
        cardFrame.draw(cardG2d);
        cardG2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
      }

      cardG2d.dispose();
    }

    g2d.dispose();
  }

  private void handleCardClick(MouseEvent e) {
    if (hand.isEmpty()) return;

    int cardHeight = getCardHeight();
    int cardIndex = e.getY() / cardHeight;

    if (cardIndex >= 0 && cardIndex < hand.size()) {
      Card clickedCard = hand.get(cardIndex);
      if (clickedCard.getOwner() == currentPlayer) {
        selectedCardIndex = cardIndex;
        System.out.println("Card clicked: Index " + selectedCardIndex + ", " +
                "Owner: " + clickedCard.getOwner());
        repaint();
      } else {
        System.out.println("Card clicked: Index " + cardIndex + " is owned by another player.");
      }
    }
  }

  public void updateHand(List<Card> newHand) {
    this.hand.clear();
    this.hand.addAll(newHand);
    selectedCardIndex = -1;
    repaint();
  }
}