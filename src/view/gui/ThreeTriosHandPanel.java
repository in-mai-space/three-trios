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
import model.interfaces.Cell;

/**
 * Represent player's hand with cards in the game.
 */
class ThreeTriosHandPanel extends JPanel implements GamePanel {
  private final List<Cell> hand;
  private static final int PREFERRED_WIDTH = 180;
  private int selectedCardIndex = -1;
  private final GamePlayer currentPlayer;
  private Feature feature;

  /**
   * Construct a new instance of player's hand.
   *
   * @param hand list of cards owned by this player that can be placed in grid
   * @param currentPlayer current player in the game, which can be this player, or
   *                      the opponent
   */
  public ThreeTriosHandPanel(List<Cell> hand, GamePlayer currentPlayer) {
    if (hand == null || currentPlayer == null) {
      throw new IllegalArgumentException("Hand or current player cannot be null");
    }
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

  private void drawCard(Graphics2D g2d, Cell cell, int index, int startX, int cardHeight,
                        int cardWidth) {
    Graphics2D cardG2d = (Graphics2D) g2d.create();
    int yPos = index * cardHeight;
    AffineTransform transform = physicalToModel(startX, yPos, cardWidth, cardHeight);
    cardG2d.transform(transform);

    if (cell.getOwner() == currentPlayer) {
      drawOwnedCard(cardG2d, cell, index);
    } else {
      drawOpponentCard(cardG2d, cell);
    }
    cardG2d.dispose();
  }

  private void drawOwnedCard(Graphics2D cardG2d, Cell cell, int index) {
    CellCard cardFrame = new CellCard.CardBuilder()
            .setColor(ViewData.getCardColor(cell.getOwner()))
            .setAttackValues(cell.getAllAttackValues())
            .build();
    cardFrame.draw(cardG2d);

    if (index == selectedCardIndex) {
      CellCard selectedCard = new CellCard.CardBuilder()
              .setColor(ViewData.getSelectedCardColor(cell.getOwner()))
              .setAttackValues(cell.getAllAttackValues())
              .build();
      selectedCard.draw(cardG2d);
    }
  }

  private void drawOpponentCard(Graphics2D cardG2d, Cell cell) {
    cardG2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
    CellCard cardFrame = new CellCard.CardBuilder()
            .setColor(ViewData.getCardColor(cell.getOwner()))
            .setAttackValues(cell.getAllAttackValues())
            .build();
    cardFrame.draw(cardG2d);
  }


  private void handleCardClick(MouseEvent e) {
    if (hand.isEmpty()) return;

    int cardHeight = getCardHeight();
    int cardIndex = e.getY() / cardHeight;

    if (cardIndex >= 0 && cardIndex < hand.size()) {
      Cell clickedCell = hand.get(cardIndex);
      selectedCardIndex = cardIndex;
      feature.printCardClicked(selectedCardIndex, clickedCell.getOwner());
      repaint();
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