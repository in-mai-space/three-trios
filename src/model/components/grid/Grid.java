package model.components.grid;

import model.components.card.Card;
import model.components.enums.CellType;
import model.components.enums.GamePlayer;

public interface Grid {
  boolean isCellEmpty(int row, int col);
  CellType getCellType(int row, int col);
  Card[][] getGrid();
  void placeCard(Card card, int row, int col);
  boolean isFilled();
  int countPlayerCards(GamePlayer player);
}
