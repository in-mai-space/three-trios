package strategy;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import controller.GameConfigParser;
import model.Utils;
import model.enums.CellType;
import model.enums.GamePlayer;
import model.interfaces.Cell;
import model.interfaces.GameModel;
import strategy.infallible.CornerInfallibleStrategy;
import strategy.infallible.InfallibleGameStrategy;
import strategy.infallible.UpperLeftInfallibleStrategy;
import strategy.mocks.GoToCornerMockModel;
import strategy.mocks.NoMoveFoundMockModel;

import static org.junit.Assert.assertEquals;

public class CornerInfallibleStrategyTest {
  @Test
  public void cornerStrategyFallBack() {
    GameModel model = Utils.loadModel("no_holes.txt", "big_cards.txt");
    InfallibleGameStrategy cornerStrategy = new CornerInfallibleStrategy();

    // all corners are occupied
    model.placeCard(0, 0, 0);
    model.placeCard(0, 0,2);
    model.placeCard(0, 2,0);
    model.placeCard(0, 2,2);

    // fallback to most upper and leftest with card index 0 in hand
    Pair<Move, Integer> move = cornerStrategy.decideMove(model, GamePlayer.RED);
    assertEquals(move, new Pair<>(new Move(model.getHand(GamePlayer.RED).get(0), 0,1), 1));
  }

  @Test
  public void cornerStrategyWithNoHolesRealModel() {
    GameModel model = Utils.loadModel("no_holes.txt", "big_cards.txt");
    InfallibleGameStrategy cornerStrategy = new CornerInfallibleStrategy();

    // strategy picks all the four corners to fill in first with the card that is least likely
    // to be flipped
    Pair<Move, Integer> redFirstMove = cornerStrategy.decideMove(model, GamePlayer.RED);
    assertEquals(redFirstMove, new Pair<>(new Move(model.getHand(GamePlayer.RED).get(3), 0,0), 0));

    model.placeCard(model.getHand(GamePlayer.RED).indexOf(redFirstMove.getKey().getCard()),
            redFirstMove.getKey().getRow(), redFirstMove.getKey().getCol());

    Pair<Move, Integer> blueFirstMove = cornerStrategy.decideMove(model, GamePlayer.BLUE);
    assertEquals(blueFirstMove, new Pair<>(new Move(model.getHand(GamePlayer.BLUE).get(4), 0,2), 0));

    model.placeCard(model.getHand(GamePlayer.BLUE).indexOf(blueFirstMove.getKey().getCard()),
            blueFirstMove.getKey().getRow(), blueFirstMove.getKey().getCol());

    Pair<Move, Integer> redSecondMove = cornerStrategy.decideMove(model, GamePlayer.RED);
    assertEquals(redSecondMove, new Pair<>(new Move(model.getHand(GamePlayer.RED).get(3), 2,0), 0));

    model.placeCard(model.getHand(GamePlayer.RED).indexOf(redSecondMove.getKey().getCard()),
            redSecondMove.getKey().getRow(), redSecondMove.getKey().getCol());

    Pair<Move, Integer> blueSecondMove = cornerStrategy.decideMove(model, GamePlayer.BLUE);
    assertEquals(blueSecondMove, new Pair<>(new Move(model.getHand(GamePlayer.BLUE).get(0), 2,2), 0));

    model.placeCard(model.getHand(GamePlayer.BLUE).indexOf(blueSecondMove.getKey().getCard()),
            blueSecondMove.getKey().getRow(), blueSecondMove.getKey().getCol());

    // when all the four corners are occupied, fallback to upper left strategy
    Pair<Move, Integer> redThirdMove = cornerStrategy.decideMove(model, GamePlayer.RED);
    assertEquals(redThirdMove, new Pair<>(new Move(model.getHand(GamePlayer.RED).get(0), 0,1), 1));

    model.placeCard(model.getHand(GamePlayer.RED).indexOf(redThirdMove.getKey().getCard()),
            redThirdMove.getKey().getRow(), redThirdMove.getKey().getCol());

    Pair<Move, Integer> blueThirdMove = cornerStrategy.decideMove(model, GamePlayer.BLUE);
    assertEquals(blueThirdMove, new Pair<>(new Move(model.getHand(GamePlayer.BLUE).get(0), 1,0), 0));
  }

  @Test
  public void cornerStrategyWithSimpleGridRealModel() {
    GameModel model = Utils.loadModel("simple_grid.txt", "big_cards.txt");
    InfallibleGameStrategy cornerStrategy = new CornerInfallibleStrategy();

    Pair<Move, Integer> redFirstMove = cornerStrategy.decideMove(model, GamePlayer.RED);
    assertEquals(redFirstMove, new Pair<>(new Move(model.getHand(GamePlayer.RED).get(3), 0,0), 0));

    model.placeCard(model.getHand(GamePlayer.RED).indexOf(redFirstMove.getKey().getCard()),
            redFirstMove.getKey().getRow(), redFirstMove.getKey().getCol());

    Pair<Move, Integer> blueFirstMove = cornerStrategy.decideMove(model, GamePlayer.BLUE);
    assertEquals(blueFirstMove, new Pair<>(new Move(model.getHand(GamePlayer.BLUE).get(0), 2,0), 0));

    model.placeCard(model.getHand(GamePlayer.BLUE).indexOf(blueFirstMove.getKey().getCard()),
            blueFirstMove.getKey().getRow(), blueFirstMove.getKey().getCol());

    Pair<Move, Integer> redSecondMove = cornerStrategy.decideMove(model, GamePlayer.RED);
    assertEquals(redSecondMove, new Pair<>(new Move(model.getHand(GamePlayer.RED).get(1), 2,3), 0));

    model.placeCard(model.getHand(GamePlayer.RED).indexOf(redSecondMove.getKey().getCard()),
            redSecondMove.getKey().getRow(), redSecondMove.getKey().getCol());

    Pair<Move, Integer> blueSecondMove = cornerStrategy.decideMove(model, GamePlayer.BLUE);
    assertEquals(blueSecondMove, new Pair<>(new Move(model.getHand(GamePlayer.BLUE).get(0), 1,0), 0));

    model.placeCard(model.getHand(GamePlayer.BLUE).indexOf(blueSecondMove.getKey().getCard()),
            blueSecondMove.getKey().getRow(), blueSecondMove.getKey().getCol());

    Pair<Move, Integer> redThirdMove = cornerStrategy.decideMove(model, GamePlayer.RED);
    assertEquals(redThirdMove, new Pair<>(new Move(model.getHand(GamePlayer.RED).get(0), 1,3), 0));
  }

  @Test
  public void cornerStrategyComplexGridRealModel() {
    GameModel model = Utils.loadModel("complex_grid.txt", "big_cards.txt");
    InfallibleGameStrategy cornerStrategy = new CornerInfallibleStrategy();

    Pair<Move, Integer> redFirstMove = cornerStrategy.decideMove(model, GamePlayer.RED);
    assertEquals(redFirstMove, new Pair<>(new Move(model.getHand(GamePlayer.RED).get(3), 0,0), 0));

    model.placeCard(model.getHand(GamePlayer.RED).indexOf(redFirstMove.getKey().getCard()),
            redFirstMove.getKey().getRow(), redFirstMove.getKey().getCol());

    Pair<Move, Integer> blueFirstMove = cornerStrategy.decideMove(model, GamePlayer.BLUE);
    assertEquals(blueFirstMove, new Pair<>(new Move(model.getHand(GamePlayer.BLUE).get(4), 0,4), 0));

    model.placeCard(model.getHand(GamePlayer.BLUE).indexOf(blueFirstMove.getKey().getCard()),
            blueFirstMove.getKey().getRow(), blueFirstMove.getKey().getCol());

    // pick a different card compared to two grids above since the grid above it is a hole
    Pair<Move, Integer> redSecondMove = cornerStrategy.decideMove(model, GamePlayer.RED);
    assertEquals(redSecondMove, new Pair<>(new Move(model.getHand(GamePlayer.RED).get(0), 3,0), 0));

    model.placeCard(model.getHand(GamePlayer.RED).indexOf(redSecondMove.getKey().getCard()),
            redSecondMove.getKey().getRow(), redSecondMove.getKey().getCol());

    Pair<Move, Integer> blueSecondMove = cornerStrategy.decideMove(model, GamePlayer.BLUE);
    assertEquals(blueSecondMove, new Pair<>(new Move(model.getHand(GamePlayer.BLUE).get(0), 0,2), 0));
  }

  @Test
  public void onlyExposeSouthAttackValue() {
    GameModel model = Utils.loadModel("no_holes.txt", "big_cards.txt");
    InfallibleGameStrategy cornerStrategy = new CornerInfallibleStrategy();

    // card right in middle of first row, the corner can only expose the south attack value
    model.placeCard(0, 0, 1);

    // should search for card with highest attack value for south
    Pair<Move, Integer> redFirstMove = cornerStrategy.decideMove(model, GamePlayer.RED);
    assertEquals(redFirstMove, new Pair<>(new Move(model.getHand(GamePlayer.RED).get(2), 0,0), 0));

    Pair<Move, Integer> blueFirstMove = cornerStrategy.decideMove(model, GamePlayer.BLUE);
    assertEquals(blueFirstMove, new Pair<>(new Move(model.getHand(GamePlayer.BLUE).get(4), 0,0), 1));
  }

  @Test
  public void onlyExposeNorthAttackValue() {
    GameModel model = Utils.loadModel("no_holes.txt", "big_cards.txt");
    InfallibleGameStrategy cornerStrategy = new CornerInfallibleStrategy();

    // block first two corners and place card in middle of last two corners
    model.placeCard(4, 0, 0);
    model.placeCard(0, 0, 2);
    model.placeCard(3, 2, 1);

    // should search for card with highest attack value for north
    Pair<Move, Integer> redFirstMove = cornerStrategy.decideMove(model, GamePlayer.RED);
    // two cards have the same attack value of north, get the lower index one
    assertEquals(redFirstMove, new Pair<>(new Move(model.getHand(GamePlayer.RED).get(1), 2,0), 0));

    Pair<Move, Integer> blueFirstMove = cornerStrategy.decideMove(model, GamePlayer.BLUE);
    // two cards have the same attack value of north, get the lower index one
    assertEquals(blueFirstMove, new Pair<>(new Move(model.getHand(GamePlayer.BLUE).get(0), 2,0), 0));
  }

  @Test
  public void onlyExposeEastAttackValue() {
    GameModel model = Utils.loadModel("no_holes.txt", "big_cards.txt");
    InfallibleGameStrategy cornerStrategy = new CornerInfallibleStrategy();

    // block first two corners and place card in first cell of second row (block north value
    // of next corner)
    model.placeCard(4, 0, 0);
    model.placeCard(1, 0, 2);
    model.placeCard(3, 2, 1);
    // place two cards to leave the two highest cards tie in highest north attack value
    model.placeCard(1, 1, 2);
    model.placeCard(0, 1, 1);

    // should search for card with highest attack value for east
    Pair<Move, Integer> redFirstMove = cornerStrategy.decideMove(model, GamePlayer.RED);
    // two cards have the same highest attack value for east, should get lower index in hand
    assertEquals(redFirstMove, new Pair<>(new Move(model.getHand(GamePlayer.RED).get(0), 2,0), 0));

    Pair<Move, Integer> blueFirstMove = cornerStrategy.decideMove(model, GamePlayer.BLUE);
    // two cards have the same highest attack value for east, should get lower index in hand
    assertEquals(blueFirstMove, new Pair<>(new Move(model.getHand(GamePlayer.BLUE).get(0), 2,0), 4));
  }

  @Test
  public void onlyExposeWestAttackValue() {
    GameModel model = Utils.loadModel("no_holes.txt", "big_cards.txt");
    InfallibleGameStrategy cornerStrategy = new CornerInfallibleStrategy();

    // block the first corner and the last cell in second row, so next corner only exposes west
    // attack value
    model.placeCard(4, 0, 0);
    model.placeCard(0, 1, 2);
    model.placeCard(3, 1, 1);

    // should search for card with highest attack value for west
    Pair<Move, Integer> redFirstMove = cornerStrategy.decideMove(model, GamePlayer.RED);
    // three cards have the same highest attack value for west, should get lower index in hand
    assertEquals(redFirstMove, new Pair<>(new Move(model.getHand(GamePlayer.RED).get(0), 0,2), 0));

    Pair<Move, Integer> blueFirstMove = cornerStrategy.decideMove(model, GamePlayer.BLUE);
    assertEquals(blueFirstMove, new Pair<>(new Move(model.getHand(GamePlayer.BLUE).get(3), 0,2), 1));
  }

  @Test(expected = IllegalStateException.class)
  public void cornerStrategyOccupiedWithMockModel() throws IOException {
    InfallibleGameStrategy cornerStrategy = new CornerInfallibleStrategy();
    String gridFile = Utils.getFilePath("no_holes.txt", "grid");
    String cardFile = Utils.getFilePath("big_cards.txt", "cards");
    CellType[][] cellTypes = GameConfigParser.getCellTypes(gridFile);
    List<Cell> cells = GameConfigParser.getCells(cardFile);
    GameModel model = new NoMoveFoundMockModel(cellTypes, cells);

    try {
      model.startGame(false);
      cornerStrategy.decideMove(model, GamePlayer.RED);
    } finally {
      List<String> lines = Files.readAllLines(Paths.get("strategy-transcript.txt"));
      String content = String.join("\n", lines);
      assertEquals(content,
              "Check can place card at row 0 and col 0\n" +
              "Check can place card at row 0 and col 2\n" +
              "Check can place card at row 2 and col 0\n" +
              "Check can place card at row 2 and col 2\n" +
              "Check can place card at row 0 and col 0\n" +
              "Check can place card at row 0 and col 1\n" +
              "Check can place card at row 0 and col 2\n" +
              "Check can place card at row 1 and col 0\n" +
              "Check can place card at row 1 and col 1\n" +
              "Check can place card at row 1 and col 2\n" +
              "Check can place card at row 2 and col 0\n" +
              "Check can place card at row 2 and col 1\n" +
              "Check can place card at row 2 and col 2");
    }
  }

  @Test
  public void goToCornerRow2Col2MockModel() throws IOException {
    InfallibleGameStrategy cornerStrategy = new CornerInfallibleStrategy();
    String gridFile = Utils.getFilePath("no_holes.txt", "grid");
    String cardFile = Utils.getFilePath("big_cards.txt", "cards");
    CellType[][] cellTypes = GameConfigParser.getCellTypes(gridFile);
    List<Cell> cells = GameConfigParser.getCells(cardFile);
    GameModel model = new GoToCornerMockModel(cellTypes, cells);
    model.startGame(false);
    Pair<Move, Integer> move = cornerStrategy.decideMove(model, GamePlayer.RED);
    // make the strategy thinks that 2, 2 is opened, while the other corners always return false
    // when canPlaceCard method is called
    assertEquals(move.getKey().getRow(), 2);
    assertEquals(move.getKey().getCol(), 2);

    List<String> lines = Files.readAllLines(Paths.get("strategy-transcript.txt"));
    String content = String.join("\n", lines);
    assertEquals("Check can place card at row 0 and col 0\n" +
            "Check can place card at row 0 and col 2\n" +
            "Check can place card at row 2 and col 0\n" +
            "Check can place card at row 2 and col 2\n" +
            // (2, 2) is opened so now count card flip in it
            "count card flip with card EarthLizard in row 2 and col 2", content);
  }
}