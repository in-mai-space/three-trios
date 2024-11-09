package strategy;

import org.junit.Test;

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
import strategy.infallible.InfallibleGameStrategy;
import strategy.infallible.UpperLeftInfallibleStrategy;
import strategy.mocks.NoMoveFoundMockModel;

import static org.junit.Assert.assertEquals;

/**
 * Represent tests for UpperLeftStrategy.
 */
public class UpperLeftStrategyTest {

  @Test
  public void testNoHolesGridWithRealModel() {
    GameModel model = Utils.loadModel("no_holes.txt", "big_cards.txt");
    InfallibleGameStrategy mostUpperLeftestStrat = new UpperLeftInfallibleStrategy();
    Pair<Move, Integer> firstMove = mostUpperLeftestStrat.decideMove(model, GamePlayer.RED);
    assertEquals(firstMove, new Pair<>(new ThreeTriosMove(model.getHand(GamePlayer.RED).get(0), 0,0), 0));

    model.placeCard(0, 0, 0);

    Pair<Move, Integer> secondMove = mostUpperLeftestStrat.decideMove(model, GamePlayer.RED);
    assertEquals(secondMove, new Pair<>(new ThreeTriosMove(model.getHand(GamePlayer.RED).get(0), 0,1), 0));

    model.placeCard(0, 1, 1);

    assertEquals(secondMove, new Pair<>(new ThreeTriosMove(model.getHand(GamePlayer.RED).get(0), 0,1), 0));

    model.placeCard(0, 0, 1);
    model.placeCard(0, 0, 2);

    Pair<Move, Integer> thirdMove = mostUpperLeftestStrat.decideMove(model, GamePlayer.RED);
    assertEquals(thirdMove, new Pair<>(new ThreeTriosMove(model.getHand(GamePlayer.RED).get(0), 1,0), 1));

    model.placeCard(0, 1, 0);

    Pair<Move, Integer> fourthMove = mostUpperLeftestStrat.decideMove(model, GamePlayer.RED);
    assertEquals(fourthMove, new Pair<>(new ThreeTriosMove(model.getHand(GamePlayer.RED).get(0), 1,2), 1));
  }

  @Test
  public void testSimpleGridWithRealModel() {
    GameModel model = Utils.loadModel("simple_grid.txt", "big_cards.txt");
    InfallibleGameStrategy mostUpperLeftestStrat = new UpperLeftInfallibleStrategy();
    Pair<Move, Integer> firstMove = mostUpperLeftestStrat.decideMove(model, GamePlayer.RED);
    assertEquals(firstMove, new Pair<>(new ThreeTriosMove(model.getHand(GamePlayer.RED).get(0), 0,0), 0));
    // first empty cell

    model.placeCard(0, 0, 0);

    Pair<Move, Integer> secondMove = mostUpperLeftestStrat.decideMove(model, GamePlayer.RED);
    assertEquals(secondMove, new Pair<>(new ThreeTriosMove(model.getHand(GamePlayer.RED).get(0), 1,0), 0));
    // second empty cell that is not a hole

    model.placeCard(0, 1, 0);

    Pair<Move, Integer> thirdMove = mostUpperLeftestStrat.decideMove(model, GamePlayer.RED);
    assertEquals(thirdMove, new Pair<>(new ThreeTriosMove(model.getHand(GamePlayer.RED).get(0), 1,3), 0));
  }

  @Test(expected = IllegalStateException.class)
  public void testExceptionMoveNotFoundMockModel() throws IOException {
    InfallibleGameStrategy mostUpperLeftestStrat = new UpperLeftInfallibleStrategy();
    String gridFile = Utils.getFilePath("no_holes.txt", "grid");
    String cardFile = Utils.getFilePath("big_cards.txt", "cards");
    CellType[][] cellTypes = GameConfigParser.getCellTypes(gridFile);
    List<Cell> cells = GameConfigParser.getCells(cardFile);
    GameModel model = new NoMoveFoundMockModel(cellTypes, cells);
    try {
      model.startGame(false);
      mostUpperLeftestStrat.decideMove(model, GamePlayer.RED);
    } finally {
      List<String> lines = Files.readAllLines(Paths.get("strategy-transcript.txt"));
      int lineIndex = 0;
      // check for every single row and col
      for (int row = 0; row < 3; row++) {
        for (int col = 0; col < 3; col++) {
          assertEquals("Check can place card at row " + row + " and col " + col,
                  lines.get(lineIndex++));
        }
      }
    }
  }
}