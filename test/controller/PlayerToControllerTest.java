package controller;

import org.junit.Test;

import controller.mocks.MockController;
import controller.mocks.MockGUIView;
import controller.mocks.MockHumanPlayer;
import model.Utils;
import model.enums.GamePlayer;
import model.implementation.ThreeTriosModel;
import model.interfaces.GameModel;
import player.HumanPlayer;
import player.MachinePlayer;
import player.ThreeTriosPlayer;
import strategy.infallible.CornerInfallibleStrategy;
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
    model.startGame(false);
    assertFalse(player.addObserver(controller)); // for HumanPlayer, addObserver is always false
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
    model.startGame(false);
    assertTrue(player.addObserver(controller));
    assertEquals(out.toString(), "getPlayer() called\n");
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
    model.startGame(false);
    assertEquals(out.toString(), ""); // nothing is called
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
    model.startGame(false);
    assertEquals(model.getCurrentPlayer(), GamePlayer.RED);
    player.addObserver(controller);
    player.playCard(); // calls observer.selectCard and observer.placeCard
    assertEquals(out.toString(), "getPlayer() called\n"
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
    model.startGame(false);
    assertEquals(model.getCurrentPlayer(), GamePlayer.RED);
    bluePlayer.addObserver(blueController);
    // doesn't call observer.selectCard and observer.placeCard because currentPlayer is not BLUE
    bluePlayer.playCard();
    assertEquals(out.toString(), "getPlayer() called\n");
  }

  @Test
  public void testMachinePlayerPlayCardGameOver() {
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
    model.startGame(false);
    redPlayer.addObserver(redController);
    bluePlayer.addObserver(blueController);
    redPlayer.playCard();
    bluePlayer.playCard();
    //assertEquals(out.toString(), "");
  }
}


