package controller;

import org.junit.Test;

import controller.mocks.MockGUIView;
import controller.mocks.MockHumanPlayer;
import controller.mocks.MockMachinePlayer;
import model.Utils;
import model.enums.GamePlayer;
import model.implementation.ThreeTriosModel;
import model.interfaces.GameModel;
import player.HumanPlayer;
import player.ThreeTriosPlayer;
import strategy.infallible.CornerInfallibleStrategy;
import view.gui.GameGUIView;
import view.gui.ThreeTriosView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ControllerToPlayerTest {
  @Test
  public void controllerConstructorHumanPlayerAddObserverDoesNothing() {
    String gridPath = Utils.getFilePath("complex_grid.txt", "grid");
    String cardPath = Utils.getFilePath("big_cards.txt", "cards");

    GameModel model = new ThreeTriosModel(GameConfigParser.getCellTypes(gridPath),
            GameConfigParser.getCells(cardPath));
    Appendable out = new StringBuilder();
    GameGUIView view = new MockGUIView(model, new StringBuilder());
    ThreeTriosPlayer player = new MockHumanPlayer(model, out);
    GameController controller = new ThreeTriosController(model, player, view, GamePlayer.RED);
    model.startGame(true);
    assertEquals(out.toString(), "addObserver method is called but does nothing\n" +
            "playCard is called, does nothing\n");
    assertFalse(player.addObserver(controller));
  }

  @Test
  public void controllerConstructorMachinePlayerAddObserver() {
    String gridPath = Utils.getFilePath("complex_grid.txt", "grid");
    String cardPath = Utils.getFilePath("big_cards.txt", "cards");

    GameModel model = new ThreeTriosModel(GameConfigParser.getCellTypes(gridPath),
            GameConfigParser.getCells(cardPath));
    Appendable out = new StringBuilder();
    GameGUIView view = new MockGUIView(model, new StringBuilder());
    ThreeTriosPlayer player = new MockMachinePlayer(model, new CornerInfallibleStrategy(), out);
    GameController controller = new ThreeTriosController(model, player, view, GamePlayer.RED);
    model.startGame(true);
    assertEquals(out.toString(), "add controller as observer with player RED\n" +
            "playCard in machine is called\n");
    assertTrue(player.addObserver(controller));
  }

  @Test
  public void controllerNotifyPlayerTurnMachinePlayerPlayCard() {
    String gridPath = Utils.getFilePath("complex_grid.txt", "grid");
    String cardPath = Utils.getFilePath("big_cards.txt", "cards");

    GameModel model = new ThreeTriosModel(GameConfigParser.getCellTypes(gridPath),
            GameConfigParser.getCells(cardPath));
    Appendable out = new StringBuilder();
    GameGUIView view = new MockGUIView(model, new StringBuilder());
    ThreeTriosPlayer player = new MockMachinePlayer(model, new CornerInfallibleStrategy(), out);
    new ThreeTriosController(model, player, view, GamePlayer.RED);
    model.startGame(true);
    assertEquals(out.toString(), "add controller as observer with player RED\n" +
            "playCard in machine is called\n");
  }

  @Test
  public void controllerNotifyPlayerTurnHumanPlayerPlayCard() {
    String gridPath = Utils.getFilePath("complex_grid.txt", "grid");
    String cardPath = Utils.getFilePath("big_cards.txt", "cards");

    GameModel model = new ThreeTriosModel(GameConfigParser.getCellTypes(gridPath),
            GameConfigParser.getCells(cardPath));
    Appendable out = new StringBuilder();
    GameGUIView view = new MockGUIView(model, new StringBuilder());
    ThreeTriosPlayer player = new MockHumanPlayer(model, out);
    GameController controller = new ThreeTriosController(model, player, view, GamePlayer.RED);
    model.startGame(true);
    assertEquals(out.toString(), "addObserver method is called but does nothing\n" +
            "playCard is called, does nothing\n");
  }
}
