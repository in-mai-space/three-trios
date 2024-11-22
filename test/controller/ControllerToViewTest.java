package controller;

import org.junit.Test;

import java.util.Optional;

import controller.mocks.MockGUIView;
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

/**
 * Tests that the controller calls the appropriate view method using mock view.
 */
public class ControllerToViewTest {
  @Test
  public void controllerPlaceCardWhenCardNotSelected() {
    String gridPath = Utils.getFilePath("complex_grid.txt", "grid");
    String cardPath = Utils.getFilePath("big_cards.txt", "cards");

    GameModel model = new ThreeTriosModel(GameConfigParser.getCellTypes(gridPath),
            GameConfigParser.getCells(cardPath));
    Appendable out = new StringBuilder();
    GameGUIView view = new MockGUIView(model, out);
    ThreeTriosPlayer player = new MachinePlayer(model, new CornerInfallibleStrategy());
    GameController controller = new ThreeTriosController(model, player, view, GamePlayer.RED);
    model.startGame(true);
    controller.placeCard(0, 0);
    assertEquals(out.toString(), "View is set player with color RED\n" +
            "Make the view visible\n" +
            "Refresh the view\n" +
            "Refresh the view\n" +
            "Refresh the view\n" +
            "Refresh the view\n" +
            "Refresh the view\n" +
            "showMessageDialogPane is called with message: " +
            "Please select a card before placing it on the grid\n");
  }

  @Test
  public void controllerPlaceCardAfterCardSelected() {
    String gridPath = Utils.getFilePath("complex_grid.txt", "grid");
    String cardPath = Utils.getFilePath("big_cards.txt", "cards");

    GameModel model = new ThreeTriosModel(GameConfigParser.getCellTypes(gridPath),
            GameConfigParser.getCells(cardPath));
    Appendable out = new StringBuilder();
    GameGUIView view = new MockGUIView(model, out);
    ThreeTriosPlayer player = new HumanPlayer(model);
    GameController controller = new ThreeTriosController(model, player, view, GamePlayer.RED);
    model.startGame(true);
    controller.selectCard(0, GamePlayer.RED);
    controller.placeCard(0, 0);
    assertEquals(out.toString(), "View is set player with color RED\n" +
            "Make the view visible\n" +
            "Add controller as an observer\n" +
            "Refresh the view\n" +
            "showMessageDialogPane is called with message: Player RED: Please select a card\n" +
            "Refresh the view\n" +
            "Refresh the view\n" +
            "Refresh the view\n");
  }

  @Test
  public void controllerPlaceCardWhenGameIsOver() {
    String gridPath = Utils.getFilePath("no_holes.txt", "grid");
    String cardPath = Utils.getFilePath("big_cards.txt", "cards");

    GameModel model = new ThreeTriosModel(GameConfigParser.getCellTypes(gridPath),
            GameConfigParser.getCells(cardPath));
    Appendable out = new StringBuilder();
    GameGUIView view = new MockGUIView(model, out);
    ThreeTriosPlayer player = new HumanPlayer(model);
    GameController controller = new ThreeTriosController(model, player, view, GamePlayer.RED);
    model.startGame(true);
    model.placeCard(0, 0, 0);
    model.placeCard(0, 0, 1);
    model.placeCard(0, 0, 2);
    model.placeCard(0, 1, 0);
    model.placeCard(0, 1, 1);
    model.placeCard(0, 1, 2);
    model.placeCard(0, 2, 0);
    model.placeCard(0, 2, 1);
    model.placeCard(0, 2, 2);
    controller.selectCard(0, GamePlayer.RED);
    controller.placeCard(0, 0);
    assertTrue(out.toString().contains("showMessageDialogPane is called with message: " +
            "Game is already over\n"));
  }

  @Test
  public void testControllerRelaysMessageToViewWhenModelThrowsException() {
    String gridPath = Utils.getFilePath("no_holes.txt", "grid");
    String cardPath = Utils.getFilePath("big_cards.txt", "cards");

    GameModel model = new ThreeTriosModel(GameConfigParser.getCellTypes(gridPath),
            GameConfigParser.getCells(cardPath));
    Appendable out = new StringBuilder();
    GameGUIView redView = new MockGUIView(model, out);
    GameGUIView blueView = new MockGUIView(model, out);
    ThreeTriosPlayer player = new MachinePlayer(model, new CornerInfallibleStrategy());
    GameController redController = new ThreeTriosController(model, player, redView, GamePlayer.RED);
    GameController blueController = new ThreeTriosController(model,
            new HumanPlayer(model), blueView, GamePlayer.BLUE);
    model.startGame(true);
    // controller cannot place card in (0, 0) because the machine plays in (0, 0) already
    blueController.selectCard(0, GamePlayer.BLUE);
    blueController.placeCard(0, 0);
    assertTrue(out.toString().contains("showMessageDialogPane is called with message: " +
            "Card cannot be placed in this position\n"));
  }

  @Test
  public void controllerAnnounceGameOverForHumanPlayerHasWinner() {
    String gridPath = Utils.getFilePath("no_holes.txt", "grid");
    String cardPath = Utils.getFilePath("big_cards.txt", "cards");

    GameModel model = new ThreeTriosModel(GameConfigParser.getCellTypes(gridPath),
            GameConfigParser.getCells(cardPath));
    Appendable out = new StringBuilder();
    GameGUIView view = new MockGUIView(model, out);
    ThreeTriosPlayer player = new HumanPlayer(model);
    GameController controller = new ThreeTriosController(model, player, view, GamePlayer.RED);
    model.startGame(true);
    // controller cannot place card in (0, 0) because the machine plays in (0, 0) already
    controller.announceGameOver(Optional.of(GamePlayer.RED), 5);
    assertTrue(out.toString().contains("showMessageDialogPane is called with message: " +
            "Winner is RED, the score is 5\n"));
  }

  @Test
  public void controllerAnnounceGameOverForHumanPlayerTie() {
    String gridPath = Utils.getFilePath("no_holes.txt", "grid");
    String cardPath = Utils.getFilePath("big_cards.txt", "cards");

    GameModel model = new ThreeTriosModel(GameConfigParser.getCellTypes(gridPath),
            GameConfigParser.getCells(cardPath));
    Appendable out = new StringBuilder();
    GameGUIView view = new MockGUIView(model, out);
    ThreeTriosPlayer player = new HumanPlayer(model);
    GameController controller = new ThreeTriosController(model, player, view, GamePlayer.RED);
    model.startGame(true);
    // controller cannot place card in (0, 0) because the machine plays in (0, 0) already
    controller.announceGameOver(Optional.empty(), 5);
    assertTrue(out.toString().contains("showMessageDialogPane is called with message: " +
            "Game results in a tie with score 5\n"));
  }

  @Test
  public void controllerNotAnnounceGameOverForMachinePlayer() {
    String gridPath = Utils.getFilePath("no_holes.txt", "grid");
    String cardPath = Utils.getFilePath("big_cards.txt", "cards");

    GameModel model = new ThreeTriosModel(GameConfigParser.getCellTypes(gridPath),
            GameConfigParser.getCells(cardPath));
    Appendable out = new StringBuilder();
    GameGUIView view = new MockGUIView(model, out);
    ThreeTriosPlayer player = new MachinePlayer(model, new CornerInfallibleStrategy());
    GameController controller = new ThreeTriosController(model, player, view, GamePlayer.RED);
    model.startGame(true);
    // controller cannot place card in (0, 0) because the machine plays in (0, 0) already
    controller.announceGameOver(Optional.of(GamePlayer.RED), 5);
    assertFalse(out.toString().contains("showMessageDialogPane is called with message: " +
            "Winner is RED, the score is 5\n"));
  }

  @Test
  public void controllerGameStartHuman() {
    String gridPath = Utils.getFilePath("no_holes.txt", "grid");
    String cardPath = Utils.getFilePath("big_cards.txt", "cards");

    GameModel model = new ThreeTriosModel(GameConfigParser.getCellTypes(gridPath),
            GameConfigParser.getCells(cardPath));
    Appendable out = new StringBuilder();
    GameGUIView view = new MockGUIView(model, out);
    ThreeTriosPlayer player = new MachinePlayer(model, new CornerInfallibleStrategy());
    GameController controller = new ThreeTriosController(model, player, view, GamePlayer.RED);
    controller.gameStart();
    assertEquals(out.toString(), "View is set player with color RED\n" +
            "Make the view visible\n");
    // view does not add controller as observer since it's machine player
  }

  @Test
  public void controllerGameStartMachine() {
    String gridPath = Utils.getFilePath("no_holes.txt", "grid");
    String cardPath = Utils.getFilePath("big_cards.txt", "cards");

    GameModel model = new ThreeTriosModel(GameConfigParser.getCellTypes(gridPath),
            GameConfigParser.getCells(cardPath));
    Appendable out = new StringBuilder();
    GameGUIView view = new MockGUIView(model, out);
    ThreeTriosPlayer player = new HumanPlayer(model);
    GameController controller = new ThreeTriosController(model, player, view, GamePlayer.RED);
    controller.gameStart();
    assertEquals(out.toString(), "View is set player with color RED\n" +
            "Make the view visible\n" +
            "Add controller as an observer\n");
    // view is set to a player
    // view is made visible
    // view add observer as listener for human player
  }

  @Test
  public void controllerNotifyPlayerTurnHumanSamePlayer() {
    String gridPath = Utils.getFilePath("no_holes.txt", "grid");
    String cardPath = Utils.getFilePath("big_cards.txt", "cards");

    GameModel model = new ThreeTriosModel(GameConfigParser.getCellTypes(gridPath),
            GameConfigParser.getCells(cardPath));
    Appendable out = new StringBuilder();
    GameGUIView view = new MockGUIView(model, out);
    ThreeTriosPlayer player = new HumanPlayer(model);
    GameController controller = new ThreeTriosController(model, player, view, GamePlayer.RED);
    controller.notifyPlayerTurn(GamePlayer.RED);
    // the player is RED, and it's RED turn so it's going to show this message
    assertTrue(out.toString().contains("showMessageDialogPane is called with message: " +
            "Player RED: Please select a card\n"));
  }

  @Test
  public void controllerNotifyPlayerTurnHumanOtherPlayerTurn() {
    String gridPath = Utils.getFilePath("no_holes.txt", "grid");
    String cardPath = Utils.getFilePath("big_cards.txt", "cards");

    GameModel model = new ThreeTriosModel(GameConfigParser.getCellTypes(gridPath),
            GameConfigParser.getCells(cardPath));
    Appendable outRed = new StringBuilder();
    Appendable outBlue = new StringBuilder();
    GameGUIView viewRed = new MockGUIView(model, outRed);
    GameGUIView viewBlue = new MockGUIView(model, outBlue);
    ThreeTriosPlayer playerRed = new HumanPlayer(model);
    ThreeTriosPlayer playerBlue = new HumanPlayer(model);
    GameController redController = new ThreeTriosController(model, playerRed,
            viewRed, GamePlayer.RED);
    GameController blueController = new ThreeTriosController(model, playerBlue,
            viewBlue, GamePlayer.BLUE);
    redController.notifyPlayerTurn(GamePlayer.BLUE);
    blueController.notifyPlayerTurn(GamePlayer.BLUE);
    // the player is RED, and it's BLUE turn so it's not going to show message
    assertFalse(outRed.toString().contains("showMessageDialogPane is called with message: " +
            "Player BLUE: Please select a card\n"));
    // the player is BLUE, and it's BLUE turn so it's going to show message
    assertTrue(outBlue.toString().contains("showMessageDialogPane is called with message: " +
            "Player BLUE: Please select a card\n"));
  }

  @Test
  public void controllerDoesNotNotifyPlayerIfMachine() {
    String gridPath = Utils.getFilePath("no_holes.txt", "grid");
    String cardPath = Utils.getFilePath("big_cards.txt", "cards");

    GameModel model = new ThreeTriosModel(GameConfigParser.getCellTypes(gridPath),
            GameConfigParser.getCells(cardPath));
    Appendable out = new StringBuilder();
    GameGUIView view = new MockGUIView(model, out);
    ThreeTriosPlayer player = new MachinePlayer(model, new CornerInfallibleStrategy());
    GameController controller = new ThreeTriosController(model, player, view, GamePlayer.RED);
    model.startGame(true);
    controller.notifyPlayerTurn(GamePlayer.RED);
    // it's player RED machine's turn but since it's machine it does not show window
    assertFalse(out.toString().contains("showMessageDialogPane is called with message: " +
            "Player RED: Please select a card\n"));
  }

  @Test
  public void notAllowSelectCardFromOpponentHand() {
    String gridPath = Utils.getFilePath("no_holes.txt", "grid");
    String cardPath = Utils.getFilePath("big_cards.txt", "cards");

    GameModel model = new ThreeTriosModel(GameConfigParser.getCellTypes(gridPath),
            GameConfigParser.getCells(cardPath));
    Appendable out = new StringBuilder();
    GameGUIView view = new MockGUIView(model, out);
    ThreeTriosPlayer player = new MachinePlayer(model, new CornerInfallibleStrategy());
    GameController controller = new ThreeTriosController(model, player, view, GamePlayer.RED);
    model.startGame(true);
    controller.selectCard(0, GamePlayer.BLUE);
    assertTrue(out.toString().contains("showMessageDialogPane is called with message: " +
            "Please only select cards from your hand.\n"));
  }

  @Test
  public void notAllowPlayerToSelectTheirCardWhenNotTurn() {
    String gridPath = Utils.getFilePath("no_holes.txt", "grid");
    String cardPath = Utils.getFilePath("big_cards.txt", "cards");

    GameModel model = new ThreeTriosModel(GameConfigParser.getCellTypes(gridPath),
            GameConfigParser.getCells(cardPath));
    Appendable out = new StringBuilder();
    GameGUIView viewRed = new MockGUIView(model, new StringBuilder());
    GameGUIView viewBlue = new MockGUIView(model, out);
    ThreeTriosPlayer playerRed = new HumanPlayer(model);
    ThreeTriosPlayer playerBlue = new HumanPlayer(model);
    new ThreeTriosController(model, playerRed, viewRed, GamePlayer.RED);
    GameController blueController = new ThreeTriosController(model, playerBlue,
            viewBlue, GamePlayer.BLUE);
    model.startGame(true);
    blueController.selectCard(0, GamePlayer.BLUE);
    assertTrue(out.toString().contains("showMessageDialogPane is called with message: " +
            "Please wait. It's not your turn.\n"));
  }
}
