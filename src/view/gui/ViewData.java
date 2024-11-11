package view.gui;


import java.awt.Color;

import model.enums.GamePlayer;

/**
 * Class that contains constant data such as colors and cell width height for the view.
 */
class ViewData {
  static final int CELL_WIDTH = 200;
  static final int  CELL_HEIGHT = 300;

  static Color getCardColor(GamePlayer player) {
    switch (player) {
      case RED:
        return new Color(248, 131, 121);
      case BLUE:
        return new Color(137, 207, 240);
      default:
        throw new IllegalArgumentException("Invalid color");
    }
  }

  static Color getSelectedCardColor(GamePlayer player) {
    switch (player) {
      case RED:
        return new Color(235, 108, 101);
      case BLUE:
        return new Color(91, 183, 217);
      default:
        throw new IllegalArgumentException("Invalid color");
    }
  }
}
