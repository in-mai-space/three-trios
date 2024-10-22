package model.components.grid;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

import model.components.card.Card;
import model.components.enums.CellType;
import model.components.enums.Direction;
import model.components.enums.GamePlayer;

public interface Grid {
  boolean isCellEmpty(int row, int col);
  CellType getCellType(int row, int col);
  Card[][] getGrid();
  CellType[][] getCellTypesGrid();
  void placeCard(Card card, int row, int col);
  boolean isFilled();
  int countPlayerCards(GamePlayer player);
  Map<Card, AbstractMap.SimpleEntry<Integer, Integer>> getAdjacentCards(int row, int col);
  boolean canPlaceCard(int row, int col);
  Card getCardAt(int row, int col);
  int getNumberOfCells();
}
