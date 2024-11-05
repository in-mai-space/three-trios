package controller;

import model.enums.GamePlayer;
import model.interfaces.GameModel;
import view.gui.GameGUIView;

public class ThreeTriosController implements GameController {
  private final GameModel model;
  private GameGUIView view;

  public ThreeTriosController(GameModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
  }

  public void setView(GameGUIView view) {
    this.view = view;
    view.addFeatures(this);
  }

  @Override
  public void printCardClicked(int index, GamePlayer player) {
    System.out.printf("Card clicked: Index %d, Owner: %s%n",
            index, player.toString());
  }

  @Override
  public void printCellClicked(int row, int col) {
    System.out.println("Cell clicked at: Row " + row + ", Column " + col);
  }
}
