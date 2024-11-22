package controller;

import org.junit.Test;

import controller.mocks.MockController;
import controller.mocks.MockGUIView;
import model.Utils;
import model.enums.GamePlayer;
import model.interfaces.GameModel;
import player.HumanPlayer;
import player.ThreeTriosPlayer;
import view.gui.GameGUIView;

import static org.junit.Assert.assertEquals;

/**
 * Tests that the model calls the appropriate controller method using mock controller.
 */
public class ModelToControllerTest {

  @Test
  public void modelStartGameCalledControllerGameStartAndNotifyPlayerTurn() {
    GameModel model = Utils.loadModelNotStarted("no_holes.txt", "big_cards.txt");
    GameGUIView view = new MockGUIView(model, new StringBuilder());
    Appendable out = new StringBuilder();
    ThreeTriosPlayer player = new HumanPlayer(model);
    GameController controller = new MockController(model, player, view, GamePlayer.RED, out);
    model.startGame(false);
    // game starts and notify first player's turn
    assertEquals(out.toString(), "gameStart() called\n" +
            "notifyPlayerTurn called()\n");
  }

  @Test
  public void modelPlaceCardSuccessControllerNotifyPlayerTurn() {
    GameModel model = Utils.loadModelNotStarted("no_holes.txt", "big_cards.txt");
    GameGUIView view = new MockGUIView(model, new StringBuilder());
    Appendable out = new StringBuilder();
    ThreeTriosPlayer player = new HumanPlayer(model);
    GameController controller = new MockController(model, player, view, GamePlayer.RED, out);
    model.startGame(false);
    model.placeCard(0, 0, 0);
    assertEquals(out.toString(), "gameStart() called\n" +
            "notifyPlayerTurn called()\n" +
            "notifyPlayerTurn called()\n"); // notify next player's turn
  }

  @Test
  public void modelGameOverControllerAnnouncesGameOver() {
    GameModel model = Utils.loadModelNotStarted("no_holes.txt", "big_cards.txt");
    GameGUIView view = new MockGUIView(model, new StringBuilder());
    Appendable out = new StringBuilder();
    ThreeTriosPlayer player = new HumanPlayer(model);
    GameController controller = new MockController(model, player, view, GamePlayer.RED, out);
    model.startGame(false);
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        model.placeCard(0, row, col);
      }
    }
    assertEquals(out.toString(), "gameStart() called\n" +
            "notifyPlayerTurn called()\n" +
            "notifyPlayerTurn called()\n" +
            "notifyPlayerTurn called()\n" +
            "notifyPlayerTurn called()\n" +
            "notifyPlayerTurn called()\n" +
            "notifyPlayerTurn called()\n" +
            "notifyPlayerTurn called()\n" +
            "notifyPlayerTurn called()\n" +
            "notifyPlayerTurn called()\n" +
            "announceGameOver() called\n");
  }
}
