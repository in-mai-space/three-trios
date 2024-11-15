package controller;

import java.util.Optional;

import model.enums.GamePlayer;
import model.interfaces.GameModel;
import player.ThreeTriosPlayer;
import view.gui.GameGUIView;

/**
 * Represents the ThreeTriosController.
 */
public class ThreeTriosController implements GameController {
  private final GameModel model;
  private final GameGUIView view;
  private int selectedCardIndex;
  private final GamePlayer color;
  private final ThreeTriosPlayer player;
  private final boolean isMachine;

  /**
   * Construct a new controller given a game model.
   *
   * @param model core logic model of the game
   * @throws IllegalArgumentException if the model or appendable is null
   */
  public ThreeTriosController(GameModel model, ThreeTriosPlayer player,
                              GameGUIView view, GamePlayer color) {
    if (model == null || player == null || view == null) {
      throw new IllegalArgumentException("Model, player, or view cannot be null");
    }
    this.model = model;
    this.view = view;
    this.color = color;
    this.selectedCardIndex = -1;
    this.player = player;
    this.isMachine = player.addObserver(this);
    model.addObserver(this);
  }

  @Override
  public GamePlayer getPlayer() {
    return color;
  }

  @Override
  public void selectCard(int index) {
    selectedCardIndex = index;
  }

  @Override
  public void placeCard(int row, int col) {
    view.refresh();
    if (selectedCardIndex == -1) {
      view.showMessageDialogPane("Please select a card before placing it to grid");
    }
    else {
      try {
        model.placeCard(selectedCardIndex, row, col);
        view.refresh();
        selectedCardIndex = -1;
      } catch (IllegalStateException | IllegalArgumentException exception) {
        view.showMessageDialogPane(exception.getMessage());
      }
    }
  }

  @Override
  public void announceGameOver(Optional<GamePlayer> winner, int score) {
    view.refresh();
    if (winner.isEmpty()) {
      view.showMessageDialogPane("Game results in a tie with score " + score);
    }
    else {
      view.showMessageDialogPane("Winner is " + winner.get() + ", the score is " + score);
    }
  }

  @Override
  public void gameStart() {
    view.setPlayer(getPlayer());
    view.makeVisible();
    if (!isMachine) {
      view.addObserver(this);
    }
  }

  @Override
  public void notifyPlayerTurn(GamePlayer nextPlayer) {
    view.refresh();
    if (color == nextPlayer) {
      view.showMessageDialogPane("Player " + nextPlayer + ": Please select a card");
    }
    player.playCard();
  }
}
