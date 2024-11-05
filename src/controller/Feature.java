package controller;

import model.enums.GamePlayer;

/**
 * Represents the feature of the game. This interface should be implemented by the controller.
 */
public interface Feature {
  void printCardClicked(int index, GamePlayer player);
  void printCellClicked(int row, int col);
}
