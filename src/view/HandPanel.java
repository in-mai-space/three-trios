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

    // Add mouse listener to handle card clicks
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

    // Calculate card height based on the number of cards
    int cardHeight = panelHeight / hand.size(); // Each card fills available space equally
    int cardWidth = Math.min(panelWidth - 10, 200);
    int startX = (panelWidth - cardWidth) / 2;

    // Create an AffineTransform object
    AffineTransform affineTransform = new AffineTransform();

    for (int i = 0; i < hand.size(); i++) {
      Card card = hand.get(i);
      Graphics2D cardG2d = (Graphics2D) g2d.create();

      // Calculate position and apply transformations
      int yPos = i * cardHeight; // Position based on the index
      affineTransform.setToIdentity(); // Reset the transform
      affineTransform.translate(startX, yPos); // Translate to the correct position
      double scaleX = (double) cardWidth / ViewData.CELL_WIDTH;
      double scaleY = (double) cardHeight / ViewData.CELL_HEIGHT;
      affineTransform.scale(scaleX, scaleY); // Scale the card

      // Apply the affine transform to the graphics context
      cardG2d.transform(affineTransform);

      // Check if the card owner matches the current player
      if (card.getOwner() == currentPlayer) {
        CellCardFrame cardFrame = new CellCardFrame.CardBuilder()
                .setColor(ViewData.getCardColor(card.getOwner()))
                .setAttackValues(card.getAllAttackValues())
                .build();
        cardFrame.draw(cardG2d);

        // Highlight selected card
        if (i == selectedCardIndex) {
          CellCardFrame selectedCard = new CellCardFrame.CardBuilder()
                  .setColor(ViewData.getSelectedCardColor(card.getOwner()))
                  .setAttackValues(card.getAllAttackValues())
                  .build();
          selectedCard.draw(cardG2d);
        }
      } else {
        // Dim the card if the owner does not match the player
        cardG2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); // Dim the card
        CellCardFrame cardFrame = new CellCardFrame.CardBuilder()
                .setColor(ViewData.getCardColor(card.getOwner()))
                .setAttackValues(card.getAllAttackValues())
                .build();
        cardFrame.draw(cardG2d);
        cardG2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); // Reset to opaque
      }

      cardG2d.dispose();
    }

    g2d.dispose();
  }

  private void handleCardClick(MouseEvent e) {
    int cardHeight = Math.min(getHeight() / hand.size(), 300); // Adjust height to current hand size
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

  // Call this method when a card is removed to update the panel
  public void updateHand(List<Card> newHand) {
    // Replace current hand with the new hand
    this.hand.clear();
    this.hand.addAll(newHand);
    selectedCardIndex = -1; // Reset selection
    repaint(); // Repaint to show changes
  }
}
