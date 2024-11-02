package view.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JPanel;

import controller.Feature;
import model.enums.GamePlayer;
import model.interfaces.Card;

class ThreeTriosHandPanel extends JPanel implements GamePanel {
  private final List<Card> hand;
  private static final int PREFERRED_WIDTH = 180;
  private int selectedCardIndex = -1;
  private final GamePlayer currentPlayer;
  private Feature feature;

  public ThreeTriosHandPanel(List<Card> hand, GamePlayer currentPlayer) {
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

    int panelWidth = getWidth();
    int cardHeight = getCardHeight();
    int cardWidth = Math.min(panelWidth - 10, 200);
    int startX = (panelWidth - cardWidth) / 2;

    for (int i = 0; i < hand.size(); i++) {
      drawCard(g2d, hand.get(i), i, startX, cardHeight, cardWidth);
    }
    g2d.dispose();
  }

  private void drawCard(Graphics2D g2d, Card card, int index, int startX, int cardHeight,
                        int cardWidth) {
    Graphics2D cardG2d = (Graphics2D) g2d.create();
    int yPos = index * cardHeight;
    AffineTransform transform = physicalToModel(startX, yPos, cardWidth, cardHeight);
    cardG2d.transform(transform);

    if (card.getOwner() == currentPlayer) {
      drawOwnedCard(cardG2d, card, index);
    } else {
      drawOpponentCard(cardG2d, card);
    }
    cardG2d.dispose();
  }

  private void drawOwnedCard(Graphics2D cardG2d, Card card, int index) {
    CellCard cardFrame = new CellCard.CardBuilder()
            .setColor(ViewData.getCardColor(card.getOwner()))
            .setAttackValues(card.getAllAttackValues())
            .build();
    cardFrame.draw(cardG2d);

    if (index == selectedCardIndex) {
      CellCard selectedCard = new CellCard.CardBuilder()
              .setColor(ViewData.getSelectedCardColor(card.getOwner()))
              .setAttackValues(card.getAllAttackValues())
              .build();
      selectedCard.draw(cardG2d);
    }
  }

  private void drawOpponentCard(Graphics2D cardG2d, Card card) {
    cardG2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
    CellCard cardFrame = new CellCard.CardBuilder()
            .setColor(ViewData.getCardColor(card.getOwner()))
            .setAttackValues(card.getAllAttackValues())
            .build();
    cardFrame.draw(cardG2d);
  }


  private void handleCardClick(MouseEvent e) {
    if (hand.isEmpty()) return;

    int cardHeight = getCardHeight();
    int cardIndex = e.getY() / cardHeight;

    if (cardIndex >= 0 && cardIndex < hand.size()) {
      Card clickedCard = hand.get(cardIndex);
      if (clickedCard.getOwner() == currentPlayer) {
        selectedCardIndex = cardIndex;
        System.out.printf("Card clicked: Index %d, Owner: %s%n",
                selectedCardIndex, clickedCard.getOwner());
        repaint();
      } else {
        System.out.printf("Card clicked: Index %d is owned by another player.%n", cardIndex);
      }
    }
  }

  private AffineTransform physicalToModel(int startX, int yPos, int targetWidth, int targetHeight) {
    AffineTransform transform = new AffineTransform();
    transform.translate(startX, yPos);
    double scaleX = (double) targetWidth / ViewData.CELL_WIDTH;
    double scaleY = (double) targetHeight / ViewData.CELL_HEIGHT;
    transform.scale(scaleX, scaleY);
    return transform;
  }

  @Override
  public void refresh() {
    selectedCardIndex = -1;
    repaint();
  }

  @Override
  public void addFeatures(Feature features) {
    if (features == null) {
      throw new IllegalArgumentException("Features cannot be null");
    }
    this.feature = features;
  }
}