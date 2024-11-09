package view.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JPanel;

import controller.Feature;
import model.enums.GamePlayer;
import model.interfaces.Cell;

class ThreeTriosHandPanel extends JPanel implements GamePanel, CardSelection {
  private final List<Cell> hand;
  private static final int PREFERRED_WIDTH = 180;
  private int selectedCardIndex = -1;
  private final GamePlayer currentPlayer;
  private GamePanel opponentPanel;
  private Feature feature;

  public ThreeTriosHandPanel(List<Cell> hand, GamePlayer currentPlayer) {
    this.hand = hand;
    this.currentPlayer = currentPlayer;
    setOpaque(false);

    // Set initial minimum size to ensure panel doesn't collapse
    setMinimumSize(new Dimension(100, 0));

    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        handleCardClick(e);
      }
    });

    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        updateSize();
      }

      @Override
      public void componentShown(ComponentEvent e) {
        updateSize();
      }
    });
  }

  private void updateSize() {
    if (getParent() != null) {
      int parentWidth = getParent().getWidth();
      int newWidth = Math.min(parentWidth / 3, PREFERRED_WIDTH);
      setPreferredSize(new Dimension(newWidth, getHeight()));
      revalidate();
      repaint();
    }
  }

  @Override
  public Dimension getPreferredSize() {
    if (getParent() != null) {
      int parentWidth = getParent().getWidth();
      int width = Math.max(100, Math.min(parentWidth / 3, PREFERRED_WIDTH));
      return new Dimension(width, super.getPreferredSize().height);
    }
    return super.getPreferredSize();
  }

  private int getCardHeight() {
    return getHeight() / Math.max(1, hand.size());
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (hand.isEmpty()) {
      return;
    }

    Graphics2D g2d = (Graphics2D) g.create();

    int panelWidth = getWidth();
    int cardHeight = getCardHeight();
    // Adjust card width to be proportional to panel width
    int cardWidth = Math.max(80, Math.min(panelWidth - 10, 200));
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

    drawCard(cardG2d, cell, index);
    cardG2d.dispose();
  }

  private void drawCard(Graphics2D cardG2d, Cell cell, int index) {
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

  private void handleCardClick(MouseEvent e) {
    if (hand.isEmpty()) return;

    int panelWidth = getWidth();
    int cardHeight = getCardHeight();
    int cardWidth = Math.min(panelWidth - 10, 200);
    int startX = (panelWidth - cardWidth) / 2;

    int relativeX = e.getX() - startX;
    int cardIndex = e.getY() / cardHeight;

    int horizontalBuffer = (int)(cardWidth * 0.02);
    int verticalBuffer = (int)(cardHeight * 0.02);

    boolean isWithinHorizontalBounds = relativeX >= horizontalBuffer &&
            relativeX <= (cardWidth - horizontalBuffer);
    boolean isWithinVerticalBounds = (e.getY() % cardHeight) >= verticalBuffer &&
            (e.getY() % cardHeight) <= (cardHeight - verticalBuffer);

    if (cardIndex >= 0 && cardIndex < hand.size() &&
            isWithinHorizontalBounds && isWithinVerticalBounds) {
      Cell clickedCell = hand.get(cardIndex);
      selectedCardIndex = cardIndex;
      feature.printCardClicked(selectedCardIndex, clickedCell.getOwner());
      repaint();
      opponentPanel.refresh();
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


  @Override
  public void addOtherPanel(GamePanel panel) {
    this.opponentPanel = panel;
  }
}