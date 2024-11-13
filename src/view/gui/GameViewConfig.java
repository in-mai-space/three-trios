package view.gui;


import java.awt.Color;

import model.enums.GamePlayer;

/**
 * Class that contains constant data such as colors and default cell width height for the view.
 */
class GameViewConfig {
  static final int DEFAULT_WIDTH = 200;
  static final int  DEFAULT_HEIGHT = 300;
  static final Color CELL_COLOR = new Color(249, 224, 118);
  static final Color HOLE_COLOR = new Color(189, 165, 93);

  /**
   * Get the color of the card owned by a player. The card is non-selected card.
   *
   * @param player player in the game (Red or Blue)
   * @return the color of the card
   */
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

  /**
   * Get the color of the card owned by a player. The card is a selected card.
   *
   * @param player player in the game (Red or Blue)
   * @return the color of the card
   */
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
