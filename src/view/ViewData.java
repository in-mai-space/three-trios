package view;

import java.awt.*;

import model.enums.GamePlayer;

public class ViewData {
  public static final int CELL_WIDTH = 200;
  public static final int  CELL_HEIGHT = 300;

  public static Color getCardColor(GamePlayer player) {
    switch (player) {
      case RED:
        return new Color(248, 131, 121);
      case BLUE:
        return new Color(137, 207, 240);
      default:
        throw new IllegalArgumentException("Invalid color");
    }
  }
}
