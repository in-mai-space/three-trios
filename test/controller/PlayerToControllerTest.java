package controller;

import org.junit.Test;

import controller.mocks.MockController;
import controller.mocks.MockGUIView;
import model.Utils;
import model.enums.GamePlayer;
import model.implementation.ThreeTriosModel;
import model.interfaces.GameModel;
import player.HumanPlayer;
import player.MachinePlayer;
import player.ThreeTriosPlayer;
import strategy.infallible.CornerInfallibleStrategy;
import strategy.infallible.UpperLeftInfallibleStrategy;
import view.gui.GameGUIView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PlayerToControllerTest {
  @Test
  public void testHumanPlayerAddObserver() {
    String gridPath = Utils.getFilePath("complex_grid.txt", "grid");
    String cardPath = Utils.getFilePath("big_cards.txt", "cards");

    GameModel model = new ThreeTriosModel(GameConfigParser.getCellTypes(gridPath),
            GameConfigParser.getCells(cardPath));
    Appendable out = new StringBuilder();
    GameGUIView view = new MockGUIView(model, new StringBuilder());
    ThreeTriosPlayer player = new HumanPlayer(model);
    GameController controller = new MockController(model, player, view, GamePlayer.RED, out);
    model.startGame(false); // calls controller.gameStart() and controller.notifyPlayerTurn()
    assertFalse(player.addObserver(controller)); // for HumanPlayer, addObserver is always false
    assertEquals(out.toString(), "gameStart() called\n" + "notifyPlayerTurn called()\n");
  }

  @Test
  public void testMachinePlayerAddObserver() {
    String gridPath = Utils.getFilePath("complex_grid.txt", "grid");
    String cardPath = Utils.getFilePath("big_cards.txt", "cards");

    GameModel model = new ThreeTriosModel(GameConfigParser.getCellTypes(gridPath),
            GameConfigParser.getCells(cardPath));
    Appendable out = new StringBuilder();
    GameGUIView view = new MockGUIView(model, new StringBuilder());
    ThreeTriosPlayer player = new MachinePlayer(model, new CornerInfallibleStrategy());
    GameController controller = new MockController(model, player, view, GamePlayer.RED, out);
    model.startGame(false); // calls controller.gameStart() and controller.notifyPlayerTurn(
    assertTrue(player.addObserver(controller)); // calls controller.getPlayer
    assertEquals(out.toString(), "gameStart() called\n"
            + "notifyPlayerTurn called()\n"
            + "getPlayer() called\n");
    assertEquals(controller.getPlayer(), GamePlayer.RED);
  }

  @Test
  public void testHumanPlayerPlayCard() {
    String gridPath = Utils.getFilePath("complex_grid.txt", "grid");
    String cardPath = Utils.getFilePath("big_cards.txt", "cards");

    GameModel model = new ThreeTriosModel(GameConfigParser.getCellTypes(gridPath),
            GameConfigParser.getCells(cardPath));
    Appendable out = new StringBuilder();
    GameGUIView view = new MockGUIView(model, new StringBuilder());
    ThreeTriosPlayer player = new HumanPlayer(model);
    GameController controller = new MockController(model, player, view, GamePlayer.RED, out);
    model.startGame(false); // calls controller.gameStart() and controller.notifyPlayerTurn()
    player.addObserver(controller);
    player.playCard(); // calls nothing
    assertEquals(out.toString(), "gameStart() called\n" + "notifyPlayerTurn called()\n");
  }

  @Test
  public void testMachinePlayerPlayCardGameNotOverAndCurrentPlayerSame() {
    String gridPath = Utils.getFilePath("complex_grid.txt", "grid");
    String cardPath = Utils.getFilePath("big_cards.txt", "cards");

    GameModel model = new ThreeTriosModel(GameConfigParser.getCellTypes(gridPath),
            GameConfigParser.getCells(cardPath));
    Appendable out = new StringBuilder();
    GameGUIView view = new MockGUIView(model, new StringBuilder());
    ThreeTriosPlayer player = new MachinePlayer(model, new CornerInfallibleStrategy());
    GameController controller = new MockController(model, player, view, GamePlayer.RED, out);
    model.startGame(false); // calls controller.gameStart() and controller.notifyPlayerTurn()
    assertEquals(model.getCurrentPlayer(), GamePlayer.RED);
    player.addObserver(controller); // calls observer.getPlayer
    player.playCard(); // calls observer.selectCard and observer.placeCard
    assertEquals(out.toString(), "gameStart() called\n" + "notifyPlayerTurn called()\n"
            + "getPlayer() called\n"
            + "Select card 3 for RED\n"
            + "Place card 0, 0\n");
  }

  @Test
  public void testMachinePlayerPlayCardGameNotOverAndCurrentPlayerDifferent() {
    String gridPath = Utils.getFilePath("complex_grid.txt", "grid");
    String cardPath = Utils.getFilePath("big_cards.txt", "cards");

    GameModel model = new ThreeTriosModel(GameConfigParser.getCellTypes(gridPath),
            GameConfigParser.getCells(cardPath));
    Appendable out = new StringBuilder();
    GameGUIView redView = new MockGUIView(model, new StringBuilder());
    GameGUIView blueView = new MockGUIView(model, new StringBuilder());
    ThreeTriosPlayer redPlayer = new MachinePlayer(model, new CornerInfallibleStrategy());
    ThreeTriosPlayer bluePlayer = new MachinePlayer(model, new CornerInfallibleStrategy());
    GameController redController = new MockController(model, redPlayer, redView, GamePlayer.RED, out);
    GameController blueController = new MockController(model, bluePlayer, blueView, GamePlayer.BLUE, out);
    model.startGame(false); // calls controller.gameStart() and controller.notifyPlayerTurn()
    assertEquals(model.getCurrentPlayer(), GamePlayer.RED);
    bluePlayer.addObserver(blueController); // calls observer.getPlayer
    // doesn't call observer.selectCard and observer.placeCard because currentPlayer is not BLUE
    bluePlayer.playCard();
    assertEquals(out.toString(), "gameStart() called\n" + "notifyPlayerTurn called()\n"
            + "getPlayer() called\n");
  }

  @Test
  public void testMachinePlayerPlayCardGameOver() {
    String gridPath = Utils.getFilePath("no_holes.txt", "grid");
    String cardPath = Utils.getFilePath("big_cards.txt", "cards");

    GameModel model = new ThreeTriosModel(GameConfigParser.getCellTypes(gridPath),
            GameConfigParser.getCells(cardPath));
    Appendable out = new StringBuilder();
    GameGUIView redView = new MockGUIView(model, new StringBuilder());
    GameGUIView blueView = new MockGUIView(model, new StringBuilder());
    ThreeTriosPlayer redPlayer = new MachinePlayer(model, new UpperLeftInfallibleStrategy());
    ThreeTriosPlayer bluePlayer = new MachinePlayer(model, new UpperLeftInfallibleStrategy());
    GameController redController = new MockController(model, redPlayer, redView, GamePlayer.RED, out);
    GameController blueController = new MockController(model, bluePlayer, blueView, GamePlayer.BLUE, out);
    model.startGame(false); // calls controller.gameStart() and controller.notifyPlayerTurn()
    redPlayer.addObserver(redController); // calls observer.getPlayer
    bluePlayer.addObserver(blueController); // calls observer.getPlayer
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        model.placeCard(0, row, col);
      }
    }
    // controller.announceGameOver() is called
    assertEquals(out.toString(), "gameStart() called\n" +
            "notifyPlayerTurn called()\n" +
            "gameStart() called\n" +
            "notifyPlayerTurn called()\n" +
            "getPlayer() called\n" +
            "getPlayer() called\n" +
            "notifyPlayerTurn called()\n" +
            "Select card 0 for BLUE\n" +
            "Place card 0, 1\n" +
            "notifyPlayerTurn called()\n" +
            "notifyPlayerTurn called()\n" +
            "notifyPlayerTurn called()\n" +
            "Select card 0 for RED\n" +
            "Place card 0, 2\n" +
            "notifyPlayerTurn called()\n" +
            "Select card 0 for BLUE\n" +
            "Place card 1, 0\n" +
            "notifyPlayerTurn called()\n" +
            "notifyPlayerTurn called()\n" +
            "notifyPlayerTurn called()\n" +
            "Select card 0 for RED\n" +
            "Place card 1, 1\n" +
            "notifyPlayerTurn called()\n" +
            "Select card 0 for BLUE\n" +
            "Place card 1, 2\n" +
            "notifyPlayerTurn called()\n" +
            "notifyPlayerTurn called()\n" +
            "notifyPlayerTurn called()\n" +
            "Select card 0 for RED\n" +
            "Place card 2, 0\n" +
            "notifyPlayerTurn called()\n" +
            "Select card 0 for BLUE\n" +
            "Place card 2, 1\n" +
            "notifyPlayerTurn called()\n" +
            "notifyPlayerTurn called()\n" +
            "notifyPlayerTurn called()\n" +
            "Select card 0 for RED\n" +
            "Place card 2, 2\n" +
            "announceGameOver() called\n" +
            "announceGameOver() called\n");
  }
}


