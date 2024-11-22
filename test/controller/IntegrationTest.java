package controller;

import org.junit.Before;
import org.junit.Test;

import controller.mocks.MockGUIView;
import model.Utils;
import model.enums.AttackValue;
import model.enums.GamePlayer;
import model.implementation.ThreeTriosCell;
import model.interfaces.Cell;
import model.interfaces.GameModel;
import player.HumanPlayer;
import player.MachinePlayer;
import player.ThreeTriosPlayer;
import strategy.infallible.CornerInfallibleStrategy;
import strategy.infallible.FlipCardsInfallibleStrategy;
import view.gui.GameGUIView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class IntegrationTest {
  private Cell angryDragon97A2;
  private Cell heroKnight4231;
  private Cell skyWhale4594;
  private Cell firePhoenix28A3;
  private Cell corruptKing6293;
  private Cell windBird7253;
  private Cell worldDragon7253;
  private Cell waterSeal3A74;
  private Cell earthLizard9166;

  @Before
  public void setUp() {
    angryDragon97A2 = new ThreeTriosCell(new AttackValue[]{ AttackValue.NINE, AttackValue.SEVEN,
            AttackValue.A, AttackValue.TWO}, "AngryDragon", GamePlayer.BLUE);
    heroKnight4231 = new ThreeTriosCell(new AttackValue[]{ AttackValue.FOUR, AttackValue.TWO,
            AttackValue.THREE, AttackValue.ONE}, "HeroKnight", GamePlayer.BLUE);
    skyWhale4594 = new ThreeTriosCell(new AttackValue[]{ AttackValue.FOUR, AttackValue.FIVE,
            AttackValue.NINE, AttackValue.FOUR}, "SkyWhale", GamePlayer.BLUE);
    firePhoenix28A3 = new ThreeTriosCell(new AttackValue[]{ AttackValue.TWO, AttackValue.EIGHT,
            AttackValue.A, AttackValue.THREE}, "FirePhoenix", GamePlayer.BLUE);

    corruptKing6293 = new ThreeTriosCell(new AttackValue[]{ AttackValue.SIX, AttackValue.TWO,
            AttackValue.NINE, AttackValue.THREE}, "CorruptKing", GamePlayer.RED);
    windBird7253 = new ThreeTriosCell(new AttackValue[]{ AttackValue.SEVEN, AttackValue.TWO,
            AttackValue.FIVE, AttackValue.THREE}, "WindBird", GamePlayer.RED);
    worldDragon7253 = new ThreeTriosCell(new AttackValue[]{ AttackValue.SEVEN, AttackValue.TWO,
            AttackValue.FIVE, AttackValue.THREE}, "WorldDragon", GamePlayer.RED);
    waterSeal3A74 = new ThreeTriosCell(new AttackValue[]{ AttackValue.THREE, AttackValue.A,
            AttackValue.SEVEN, AttackValue.FOUR}, "WaterSeal", GamePlayer.RED);
    earthLizard9166 = new ThreeTriosCell(new AttackValue[]{ AttackValue.NINE, AttackValue.ONE,
            AttackValue.SIX, AttackValue.SIX}, "EarthLizard", GamePlayer.RED);
  }

  @Test
  public void twoHumanPlayersPlaceCardInNonAvailableCell() {
    GameModel model = Utils.loadModel("no_holes.txt", "big_cards.txt");
    GameGUIView redView = new MockGUIView(model, new StringBuilder());
    GameGUIView blueView = new MockGUIView(model, new StringBuilder());
    ThreeTriosPlayer redPlayer = new HumanPlayer(model);
    ThreeTriosPlayer bluePlayer = new HumanPlayer(model);
    GameController redController = new ThreeTriosController(model, redPlayer, redView, GamePlayer.RED);
    GameController blueController = new ThreeTriosController(model, bluePlayer, blueView, GamePlayer.BLUE);
    // player Red places card in 0, 0
    redController.selectCard(0, GamePlayer.RED);
    redController.placeCard(0, 0);

    // red tries to play again when it's not their turn, nothing happens
    redController.selectCard(0, GamePlayer.RED);
    redController.placeCard(0, 1);
    assertNull(model.getGrid()[0][1]);

    // player Blue tries to play card in 0, 0 again, not error is thrown since it's handled by view
    blueController.selectCard(0, GamePlayer.BLUE);
    blueController.placeCard(0, 0);

    // card is still owned by player Red in 0, 0
    assertEquals(model.getCardAt(0, 0).getOwner(), GamePlayer.RED);
    assertEquals(model.getCardAt(0, 0), corruptKing6293);

    // now blue selects the correct location so they can place the card there
    blueController.selectCard(0, GamePlayer.BLUE);
    blueController.placeCard(0, 1);

    assertEquals(model.getCardAt(0, 1).getOwner(), GamePlayer.BLUE);
    assertEquals(model.getCardAt(0, 1), angryDragon97A2);
  }

  @Test
  public void humanPlayerPlaceCardButNoCardSelected() {
    GameModel model = Utils.loadModel("no_holes.txt", "big_cards.txt");
    GameGUIView redView = new MockGUIView(model, new StringBuilder());
    ThreeTriosPlayer redPlayer = new HumanPlayer(model);
    GameController redController = new ThreeTriosController(model, redPlayer, redView, GamePlayer.RED);
    // player Red attempts to place card in 0, 0 but has not selected card
    redController.placeCard(0, 0);

    // no card was placed there and no error was thrown
    assertNull(model.getGrid()[0][0]);
  }

  @Test
  public void humanPlayerChangesSelectedCardThenPlaceCard() {
    GameModel model = Utils.loadModel("no_holes.txt", "big_cards.txt");
    GameGUIView redView = new MockGUIView(model, new StringBuilder());
    ThreeTriosPlayer redPlayer = new HumanPlayer(model);
    GameController redController = new ThreeTriosController(model, redPlayer, redView, GamePlayer.RED);
    redController.selectCard(1, GamePlayer.RED);
    redController.selectCard(0, GamePlayer.RED);
    redController.placeCard(0, 0);

    assertEquals(model.getCardAt(0, 0), corruptKing6293);
  }

  @Test
  public void testTwoHumanPlayersFullGame() {
    GameModel model = Utils.loadModel("no_holes.txt", "big_cards.txt");
    GameGUIView redView = new MockGUIView(model, new StringBuilder());
    GameGUIView blueView = new MockGUIView(model, new StringBuilder());
    ThreeTriosPlayer redPlayer = new HumanPlayer(model);
    ThreeTriosPlayer bluePlayer = new HumanPlayer(model);
    GameController redController = new ThreeTriosController(model, redPlayer, redView, GamePlayer.RED);
    GameController blueController = new ThreeTriosController(model, bluePlayer, blueView, GamePlayer.BLUE);

    assertFalse(model.gameOver());

    redController.selectCard(0, GamePlayer.RED);
    redController.placeCard(0, 0);
    assertEquals(model.getCardAt(0, 0), corruptKing6293);

    blueController.selectCard(0, GamePlayer.BLUE);
    blueController.placeCard(0, 1);
    assertEquals(model.getCardAt(0, 1), angryDragon97A2);

    redController.selectCard(0, GamePlayer.RED);
    redController.placeCard(0, 2);
    assertEquals(model.getCardAt(0, 2), windBird7253);

    blueController.selectCard(0, GamePlayer.BLUE);
    blueController.placeCard(1, 0);
    assertEquals(model.getCardAt(1, 0), heroKnight4231);

    redController.selectCard(0, GamePlayer.RED);
    redController.placeCard(1, 1);
    assertEquals(model.getCardAt(1, 1), worldDragon7253);

    blueController.selectCard(0, GamePlayer.BLUE);
    blueController.placeCard(1, 2);
    assertEquals(model.getCardAt(1, 2), skyWhale4594);

    redController.selectCard(0, GamePlayer.RED);
    redController.placeCard(2, 0);
    assertEquals(model.getCardAt(2, 0), waterSeal3A74);

    blueController.selectCard(0, GamePlayer.BLUE);
    blueController.placeCard(2, 1);
    assertEquals(model.getCardAt(2, 1), firePhoenix28A3);

    redController.selectCard(0, GamePlayer.RED);
    redController.placeCard(2, 2);
    assertEquals(model.getCardAt(2, 2), earthLizard9166);

    assertEquals(model.getWinner().get(), GamePlayer.RED);
    assertEquals(model.getScore(GamePlayer.RED), 8);
    assertTrue(model.gameOver());
  }

  @Test
  public void humanPlayerAttemptToPlayCardWhenGameOver() {
    GameModel model = Utils.loadModel("no_holes.txt", "big_cards.txt");
    GameGUIView redView = new MockGUIView(model, new StringBuilder());
    GameGUIView blueView = new MockGUIView(model, new StringBuilder());
    ThreeTriosPlayer redPlayer = new HumanPlayer(model);
    ThreeTriosPlayer bluePlayer = new HumanPlayer(model);
    GameController redController = new ThreeTriosController(model, redPlayer, redView, GamePlayer.RED);
    GameController blueController = new ThreeTriosController(model, bluePlayer, blueView, GamePlayer.BLUE);

    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        model.placeCard(0, row, col);
      }
    }

    assertTrue(model.gameOver());

    // no error was thrown since it's handled by view notification to player
    blueController.selectCard(0, GamePlayer.BLUE);
    blueController.placeCard(0, 0);

    // the card did not get change since game is already over
    assertEquals(model.getCardAt(0, 0), corruptKing6293);
  }

  @Test
  public void twoMachinePlayersFullGame() {
    GameModel model = Utils.loadModelNotStarted("no_holes.txt", "big_cards.txt");
    GameGUIView redView = new MockGUIView(model, new StringBuilder());
    GameGUIView blueView = new MockGUIView(model, new StringBuilder());
    Appendable out = new StringBuilder();
    ThreeTriosPlayer redPlayer = new MachinePlayer(model, new FlipCardsInfallibleStrategy(), out);
    ThreeTriosPlayer bluePlayer = new MachinePlayer(model, new CornerInfallibleStrategy(), out);
    GameController redController = new ThreeTriosController(model, redPlayer, redView, GamePlayer.RED);
    GameController blueController = new ThreeTriosController(model, bluePlayer, blueView, GamePlayer.BLUE);
    model.startGame(false);

    // assert that when the model starts, it does notify controller and controller passes
    // to player and back to model back and forth
    assertEquals(out.toString(), "Player RED plays card with index 0 to row 0 and col 0\n" +
            "Player BLUE plays card with index 4 to row 0 and col 2\n" +
            "Player RED plays card with index 2 to row 0 and col 1\n" +
            "Player BLUE plays card with index 0 to row 2 and col 0\n" +
            "Player RED plays card with index 0 to row 1 and col 0\n" +
            "Player BLUE plays card with index 1 to row 2 and col 2\n" +
            "Player RED plays card with index 0 to row 2 and col 1\n" +
            "Player BLUE plays card with index 0 to row 1 and col 1\n" +
            "Player RED plays card with index 0 to row 1 and col 2\n");

    // game over because both machine players play all cards immediately
    assertTrue(model.gameOver());
    assertEquals(model.getWinner().get(), GamePlayer.RED);
    assertEquals(model.getScore(GamePlayer.RED), 8);
  }

  @Test
  public void testHumanVsMachineFullGame() {
    GameModel model = Utils.loadModelNotStarted("no_holes.txt", "big_cards.txt");
    GameGUIView redView = new MockGUIView(model, new StringBuilder());
    GameGUIView blueView = new MockGUIView(model, new StringBuilder());
    Appendable out = new StringBuilder();
    ThreeTriosPlayer redPlayer = new HumanPlayer(model);
    ThreeTriosPlayer bluePlayer = new MachinePlayer(model, new FlipCardsInfallibleStrategy(), out);
    GameController redController = new ThreeTriosController(model, redPlayer, redView, GamePlayer.RED);
    GameController blueController = new ThreeTriosController(model, bluePlayer, blueView, GamePlayer.BLUE);
    model.startGame(false);

    // machine did not play since it's the human player's turn first
    assertEquals(out.toString(), "");

    redController.selectCard(0, GamePlayer.RED);
    redController.placeCard(0,0);
    assertEquals(model.getCardAt(0, 0), corruptKing6293);
    // machine player plays a card
    assertEquals(out.toString(), "Player BLUE plays card with index 0 to row 1 and col 0\n");
    assertEquals(model.getScore(GamePlayer.RED), 4);
    assertEquals(model.getScore(GamePlayer.BLUE), 6);

    redController.selectCard(0, GamePlayer.RED);
    redController.placeCard(0,1);
    assertEquals(model.getCardAt(0, 1), windBird7253);
    assertEquals(out.toString(), "Player BLUE plays card with index 0 to row 1 and col 0\n" +
            "Player BLUE plays card with index 0 to row 1 and col 1\n");
    assertEquals(model.getScore(GamePlayer.RED), 3);
    assertEquals(model.getScore(GamePlayer.BLUE), 7);

    redController.selectCard(0, GamePlayer.RED);
    redController.placeCard(0,2);
    assertEquals(model.getCardAt(0, 2), worldDragon7253);
    assertEquals(out.toString(), "Player BLUE plays card with index 0 to row 1 and col 0\n" +
            "Player BLUE plays card with index 0 to row 1 and col 1\n" +
            "Player BLUE plays card with index 0 to row 1 and col 2\n");
    assertEquals(model.getScore(GamePlayer.RED), 2);
    assertEquals(model.getScore(GamePlayer.BLUE), 8);

    redController.selectCard(0, GamePlayer.RED);
    redController.placeCard(2,0);
    assertEquals(model.getCardAt(2, 0), waterSeal3A74);
    assertEquals(out.toString(), "Player BLUE plays card with index 0 to row 1 and col 0\n" +
            "Player BLUE plays card with index 0 to row 1 and col 1\n" +
            "Player BLUE plays card with index 0 to row 1 and col 2\n" +
            "Player BLUE plays card with index 0 to row 2 and col 1\n");
    assertEquals(model.getScore(GamePlayer.RED), 2);
    assertEquals(model.getScore(GamePlayer.BLUE), 8);

    redController.selectCard(0, GamePlayer.RED);
    redController.placeCard(2,2);
    assertTrue(model.gameOver());

    assertEquals(model.getWinner().get(), GamePlayer.RED);
    assertEquals(model.getScore(GamePlayer.RED), 6);
    assertEquals(model.getScore(GamePlayer.BLUE), 4);
  }

  @Test
  public void humanPlayerPlaysInHoleCellDoesNotThrowException() {
    GameModel model = Utils.loadModelNotStarted("complex_grid.txt", "big_cards.txt");
    GameGUIView redView = new MockGUIView(model, new StringBuilder());
    ThreeTriosPlayer redPlayer = new HumanPlayer(model);
    GameController redController = new ThreeTriosController(model, redPlayer, redView, GamePlayer.RED);

    model.startGame(false);

    redController.selectCard(0, GamePlayer.RED);
    redController.placeCard(0, 1); // hole cell

    // card was not removed from hand since it's a hole
    assertEquals(model.getHand(GamePlayer.RED).size(), 5);
    assertNull(model.getGrid()[0][1]);

    // place a card in empty non-hole cell
    redController.selectCard(0, GamePlayer.RED);
    redController.placeCard(0, 0);

    // card is now removed from hand and placed in grid
    assertEquals(model.getGrid()[0][0], corruptKing6293);
    assertEquals(model.getHand(GamePlayer.RED).size(), 4);
  }
}

