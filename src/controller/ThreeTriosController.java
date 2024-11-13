package controller;

import java.io.IOException;

import model.enums.GamePlayer;
import model.interfaces.GameModel;
import view.gui.GameGUIView;

/**
 * Represents the ThreeTriosController.
 */
public class ThreeTriosController implements GameController {
  private final GameModel model;
  private GameGUIView view;
  private final Appendable log;

  /**
   * Construct a new controller given a game model.
   *
   * @param model core logic model of the game
   * @param log appendable object (this will change when specification of controller changes)
   * @throws IllegalArgumentException if the model or appendable is null
   */
  public ThreeTriosController(GameModel model, Appendable log) {
    if (model == null || log == null) {
      throw new IllegalArgumentException("Model or appendable cannot be null");
    }
    this.model = model;
    this.log = log;
  }

  /**
   * Transmit message (to console in this case).
   *
   * @param message message to be transmit
   */
  private void transmit(String message) {
    try {
      log.append(message).append("\n");
    }
    catch (IOException ignored) { }
  }

  /**
   * Set the view of the game.
   *
   * @param view GUI view
   * @throws IllegalArgumentException if view is null
   */
  public void setView(GameGUIView view) {
    if (view == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.view = view;
    view.addFeatures(this);
  }

  /**
   * Print in the console what index and which player clicks a card.
   *
   * @param index index of card in a hand (0-indexed)
   * @param player player who owns the card
   */
  @Override
  public void printCardClicked(int index, GamePlayer player) {
    transmit(String.format("Card clicked: Index %d, Owner: %s",
            index, player.toString()));
  }

  /**
   * Print in the console which row and col player clicks on grid.
   *
   * @param row row index of the cell (0-indexed)
   * @param col col index of the cell (0-indexed)
   */
  @Override
  public void printCellClicked(int row, int col) {
    transmit(String.format("Cell clicked at: Row " + row + ", Column " + col));
  }
}
