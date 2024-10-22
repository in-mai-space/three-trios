package model.components;

import java.util.List;

import model.components.card.Card;
import model.components.enums.GamePlayer;
import model.components.grid.Grid;

public interface GameModel {
  // void startGame();
  GamePlayer getCurrentPlayer();
  Grid getGrid();
  List<Card> getHand(GamePlayer player);
  void placeCard(int index, int row, int col);
  boolean gameOver();
  int getWidth();
  int getHeight();
  int handSize();
}
