package controller.mocks;

import java.util.Optional;

import controller.GameController;
import model.Utils;
import model.enums.GamePlayer;
import model.interfaces.GameModel;
import player.ThreeTriosPlayer;
import view.gui.GameGUIView;

public class MockController implements GameController {
  private final Appendable log;
  private final GamePlayer color;
  private final ThreeTriosPlayer player;

  public MockController(GameModel model, ThreeTriosPlayer player,
                        GameGUIView view, GamePlayer color, Appendable log) {
    this.log = log;
    this.color = color;
    this.player = player;
    model.addObserver(this);
  }

  /**
   * Retrieves that player that the controller represents for.
   *
   * @return the color of player controlled by this controller
   */
  @Override
  public GamePlayer getPlayer() {
    Utils.transmit(log, "getPlayer() called");
    return this.color;
  }

  /**
   * Handles the selection of a card by the player. The selection is identified by its index
   * in the player's hand or the game context.
   *
   * @param index  the index of the card to be selected in hand (0-indexed)
   * @param player
   */
  @Override
  public void selectCard(int index, GamePlayer player) {
    Utils.transmit(log, "Select card " + index + " for " + player);
  }

  /**
   * Places a card at the specified position on the game grid.
   * This method is called to perform a card placement action, which may depend on game rules.
   *
   * @param row row index in grid (0-indexed)
   * @param col column index in grid (0-indexed)
   */
  @Override
  public void placeCard(int row, int col) {
    Utils.transmit(log, "Place card " + row + ", " + col);
  }

  /**
   * Announces the end of the game, specifying the winner and the final score.
   *
   * @param winner an Optional containing the winning player, or empty if the game ends in a tie
   * @param score  the score of winner, or score of one of players is there is a tie
   */
  @Override
  public void announceGameOver(Optional<GamePlayer> winner, int score) {
    Utils.transmit(log, "announceGameOver() called");
  }

  /**
   * Initiates the start of the game. This method is responsible for performing
   * any setup necessary to begin gameplay.
   */
  @Override
  public void gameStart() {
    Utils.transmit(log, "gameStart() called");
  }

  /**
   * Notifies that it is a particular player's turn. This method is used to update
   * the game's state or inform the player that they can now take an action.
   *
   * @param player the player whose turn it is.
   */
  @Override
  public void notifyPlayerTurn(GamePlayer player) {
    Utils.transmit(log, "notifyPlayerTurn called()");
    this.player.playCard();
  }
}
