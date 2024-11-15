package controller;

import java.util.Optional;

import model.enums.GamePlayer;
import model.interfaces.GameModel;
import player.ThreeTriosPlayer;
import view.gui.GameGUIView;

/**
 * Controller class for the Three Trios game, managing interactions between the model,
 * view, and player. It handles game actions such as selecting and placing cards,
 * starting the game, and announcing the game outcome.
 */
public class ThreeTriosController implements GameController {
  private final GameModel model;
  private final GameGUIView view;
  private int selectedCardIndex;
  private final GamePlayer color;
  private final ThreeTriosPlayer player;
  private final boolean isMachine;

  /**
   * Constructs a ThreeTriosController with the given game model, player, view, and color.
   *
   * @param model  The core logic model of the game
   * @param player The player interacting with the game
   * @param view   The GUI view for displaying game state
   * @param color  The color/type of the player
   * @throws IllegalArgumentException if model, player, view, color is null
   */
  public ThreeTriosController(GameModel model, ThreeTriosPlayer player,
                              GameGUIView view, GamePlayer color) {
    if (model == null || player == null || view == null || color == null) {
      throw new IllegalArgumentException("Model, player, color or view cannot be null");
    }
    this.model = model;
    this.view = view;
    this.color = color;
    this.selectedCardIndex = -1;
    this.player = player;
    this.isMachine = player.addObserver(this);
    model.addObserver(this);
  }

  /**
   * Returns the player's color/type.
   *
   * @return The color/type of the player
   */
  @Override
  public GamePlayer getPlayer() {
    return color;
  }

  /**
   * Selects a card by index, preparing it to be placed on the game grid.
   *
   * @param index index of card in hand
   */
  @Override
  public void selectCard(int index) {
    selectedCardIndex = index;
  }

  /**
   * Places the selected card on the grid at the specified row and column.
   *
   * @param row row index (0-indexed)
   * @param col col index (0-indexed)
   */
  @Override
  public void placeCard(int row, int col) {
    System.out.println(model.getHand(color).size());
    view.refresh();
    if (selectedCardIndex == -1) {
      view.showMessageDialogPane("Please select a card before placing it on the grid");
    } else {
      try {
        model.placeCard(selectedCardIndex, row, col);
        view.refresh();
        selectedCardIndex = -1;
      } catch (IllegalStateException | IllegalArgumentException exception) {
        view.showMessageDialogPane(exception.getMessage());
      }
    }
  }

  /**
   * Announces the end of the game, displaying the winner and final score.
   *
   * @param winner winning player, empty is game results in tie
   * @param score  score of the winner
   */
  @Override
  public void announceGameOver(Optional<GamePlayer> winner, int score) {
    view.refresh();
    if (winner.isEmpty()) {
      view.showMessageDialogPane("Game results in a tie with score " + score);
    } else {
      view.showMessageDialogPane("Winner is " + winner.get() + ", the score is " + score);
    }
  }

  /**
   * Starts the game, setting the player's color/type in the view and making it visible.
   * Registers the view observer only if the player is human.
   */
  @Override
  public void gameStart() {
    view.setPlayer(getPlayer());
    view.makeVisible();
    // does not need to listen to view if it's a machine player!
    if (!isMachine) {
      view.addObserver(this);
    }
  }

  /**
   * Notifies the player when it's their turn and prompts them to select a card if human.
   *
   * @param nextPlayer The player whose turn is next
   */
  @Override
  public void notifyPlayerTurn(GamePlayer nextPlayer) {
    view.refresh();
    if (color == nextPlayer) {
      view.showMessageDialogPane("Player " + nextPlayer + ": Please select a card");
    }
    player.playCard();
  }
}
