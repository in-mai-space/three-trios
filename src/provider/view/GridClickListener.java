package provider.view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Translates the clicks made to the board to highlight
 * anything that's clicked, specifically cards and cells.
 */
public class GridClickListener implements MouseListener {
  private final ThreeTriosGamePanel gamePanel;

  public GridClickListener(ThreeTriosGamePanel gamePanel) {
    this.gamePanel = gamePanel;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    int mouseX = e.getX();
    int mouseY = e.getY();

    int cellSize = gamePanel.getCellSize();
    int gridCols = gamePanel.getModel().getGrid()[0].length;
    int gridRows = gamePanel.getModel().getGrid().length;

    int gridWidth = gridCols * cellSize;
    int gridHeight = gridRows * cellSize;

    if (mouseX >= cellSize && mouseX < gridWidth + cellSize && mouseY >= 0 && mouseY < gridHeight) {
      int col = (mouseX - cellSize) / cellSize;
      int row = mouseY / cellSize;

      if (mouseX >= gridWidth + cellSize - 1) {
        col = gridCols - 1;
      }
      if (mouseY >= gridHeight - cellSize) {
        row = gridRows - 1;
      }
      gamePanel.onCardClicked(row, col);
    }
    cardClicked(cellSize, gridWidth, gridHeight, mouseX, mouseY);
  }

  private void cardClicked(int cellSize, int gridWidth, int gridHeight, int mouseX, int mouseY) {
    int blueHandStartX = cellSize + gridWidth;
    int blueHandEndX = blueHandStartX + cellSize;

    int redHandSize = gamePanel.getModel().getRedHand().size();
    int blueHandSize = gamePanel.getModel().getBlueHand().size();

    int redCardHeight = redHandSize > 0 ? gridHeight / redHandSize : cellSize;
    int blueCardHeight = blueHandSize > 0 ? gridHeight / blueHandSize : cellSize;

    if (mouseX >= 0 && mouseX <= cellSize) {
      for (int row = 0; row < redHandSize; row++) {
        int yOffset = row * redCardHeight;
        if (mouseY >= yOffset && mouseY < (yOffset + redCardHeight)) {
          gamePanel.setSelectedRedCardIndex(row);
          gamePanel.setSelectedBlueCardIndex(-1);
          System.out.println("Clicked on red card: " + gamePanel.getModel().getRedHand().get(row));
          return;
        }
      }
    }

    if (mouseX >= blueHandStartX && mouseX <= blueHandEndX) {
      for (int row = 0; row < blueHandSize; row++) {
        int yOffset = row * blueCardHeight;
        if (mouseY >= yOffset && mouseY < (yOffset + blueCardHeight)) {
          gamePanel.setSelectedBlueCardIndex(row);
          gamePanel.setSelectedRedCardIndex(-1);
          System.out.println("Clicked on blue card: "
                  + gamePanel.getModel().getBlueHand().get(row));
          return;
        }
      }
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    // No implementation needed for now
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    // No implementation needed for now
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    // No implementation needed for now
  }

  @Override
  public void mouseExited(MouseEvent e) {
    // No implementation needed for now
  }
}

