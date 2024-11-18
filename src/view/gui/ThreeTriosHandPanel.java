package view.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

import controller.ControllerFeature;
import model.enums.GamePlayer;
import model.interfaces.Cell;
import model.interfaces.ReadOnlyGameModel;

/**
 * Represent player's hand with cards in the game.
 */
class ThreeTriosHandPanel extends JPanel implements GamePanel {
  private static final int PREFERRED_WIDTH = 180;
  private int selectedCardIndex = -1;
  private ControllerFeature observer;
  private final GamePlayer handOwner;
  private final ReadOnlyGameModel model;
  private final GamePlayer playerInWindow;

  /**
   * Construct a new instance of player's hand.
   */
  public ThreeTriosHandPanel(ReadOnlyGameModel model, GamePlayer playerHand, GamePlayer playerInWindow) {
    if (model == null) {
      throw new IllegalArgumentException("Hand or current player cannot be null");
    }
    this.model = model;
    this.handOwner = playerHand;
    this.playerInWindow = playerInWindow;
    setOpaque(false);
    setPreferredSize(new Dimension(PREFERRED_WIDTH, 0));
  }

  /**
   * Add features to the panel.
   *
   * @param features controller that implements features
   * @throws IllegalArgumentException if features is null
   */
  @Override
  public void addObserver(ControllerFeature features) {
    if (features == null) {
      throw new IllegalArgumentException("Features cannot be null");
    }
    this.observer = features;
    // only add mouse listener when there is an observer
    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        handleCardClick(e);
      }
    });
  }

  /**
   * Refresh the view when there is new changes to the game.
   */
  @Override
  public void refresh() {
    selectedCardIndex = -1;
    revalidate();
    repaint();
  }

  /**
   * Calculate dynamic card height based on how many cards are in hand.
   * @return the card height
   */
  private int getCardHeight() {
    return getHeight() / Math.max(1, model.getHand(handOwner).size());
  }

  /**
   * Render all cards in hand.
   * @param g the <code>Graphics</code> object to protect
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (model.getHand(handOwner).isEmpty()) {
      return;
    }

    Graphics2D g2d = (Graphics2D) g.create();

    int panelWidth = getWidth();
    int cardHeight = getCardHeight();
    int cardWidth = Math.min(panelWidth - 10, 200);
    int startX = (panelWidth - cardWidth) / 2;

    for (int i = 0; i < model.getHand(handOwner).size(); i++) {
      drawCard(g2d, model.getHand(handOwner).get(i), i, startX, cardHeight, cardWidth);
    }
    g2d.dispose();
  }

  /**
   * Handle actions when the card is clicked in hand.
   *
   * @param e mouse clicked event
   */
  private void handleCardClick(MouseEvent e) {
    if (model.getHand(handOwner).isEmpty()) {
      return;
    }

    int cardHeight = getCardHeight();
    int cardIndex = e.getY() / cardHeight;

    if (cardIndex >= 0 && cardIndex < model.getHand(handOwner).size()) {
      selectedCardIndex = cardIndex;
      observer.selectCard(cardIndex, handOwner);
      repaint();
    }
  }

  /**
   * Draw current player's card.
   * @param cardG2d graphics to drawn on
   * @param cell a player's card
   * @param index current selected card index
   */
  private void drawOwnedCard(Graphics2D cardG2d, Cell cell, int index) {
    CellCard cardFrame = new CellCard.CardBuilder()
            .setColor(GameViewConfig.getCardColor(cell.getOwner()))
            .setAttackValues(cell.getAllAttackValues())
            .build();
    cardFrame.draw(cardG2d);

    if (index == selectedCardIndex) {
      CellCard selectedCard = new CellCard.CardBuilder()
              .setColor(GameViewConfig.getSelectedCardColor(cell.getOwner()))
              .setAttackValues(cell.getAllAttackValues())
              .build();
      selectedCard.draw(cardG2d);
    }
  }

  /**
   * Draw current player's card.
   * @param cardG2d graphics to drawn on
   * @param cell a player's card
   * @param index current selected card index
   */
  private void drawOwnedCardGrayOut(Graphics2D cardG2d, Cell cell, int index) {
    cardG2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
    CellCard cardFrame = new CellCard.CardBuilder()
            .setColor(GameViewConfig.getCardColor(cell.getOwner()))
            .setAttackValues(cell.getAllAttackValues())
            .build();
    cardFrame.draw(cardG2d);
  }

  /**
   * Draw the opponent's card.
   * @param cardG2d graphics to be drawn on
   * @param cell a opponent's card
   */
  private void drawOpponentCard(Graphics2D cardG2d, Cell cell) {
    cardG2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
    CellCard cardFrame = new CellCard.CardBuilder()
            .setColor(GameViewConfig.getCardColor(cell.getOwner()))
            .setAttackValues(cell.getAllAttackValues())
            .build();
    cardFrame.draw(cardG2d);
  }

  /**
   * Transformation from physical to model coordinate.
   * @param startX starting x coordinate
   * @param yPos y coordinate
   * @param targetWidth screen width to convert to model coordinate
   * @param targetHeight screen height to convert to model coordinate
   * @return the transformation
   */
  private AffineTransform physicalToModel(int startX, int yPos, int targetWidth, int targetHeight) {
    AffineTransform transform = new AffineTransform();
    transform.translate(startX, yPos);
    double scaleX = (double) targetWidth / GameViewConfig.DEFAULT_WIDTH;
    double scaleY = (double) targetHeight / GameViewConfig.DEFAULT_HEIGHT;
    transform.scale(scaleX, scaleY);
    return transform;
  }

  /**
   * Draw a card in the hand.
   *
   * @param g2d graphics to be drawn on
   * @param cell a card in hand
   * @param index current selected card index
   * @param startX starting position of the card
   * @param cardHeight card height
   * @param cardWidth card width
   */
  private void drawCard(Graphics2D g2d, Cell cell, int index, int startX, int cardHeight,
                        int cardWidth) {
    Graphics2D cardG2d = (Graphics2D) g2d.create();
    int yPos = index * cardHeight;
    AffineTransform transform = physicalToModel(startX, yPos, cardWidth, cardHeight);
    cardG2d.transform(transform);

    if (cell.getOwner() == playerInWindow && model.getCurrentPlayer() != handOwner) {
      drawOwnedCardGrayOut(cardG2d, cell, index);
    }
    else if (cell.getOwner() == playerInWindow) {
      drawOwnedCard(cardG2d, cell, index);
    }
    else if (cell.getOwner() != playerInWindow) {
      drawOpponentCard(cardG2d, cell);
    }

    cardG2d.dispose();
  }
}