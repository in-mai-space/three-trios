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
import strategy.Move;
import strategy.Pair;
import strategy.infallible.FlipCardsInfallibleStrategy;
import strategy.infallible.InfallibleGameStrategy;
import strategy.infallible.UpperLeftInfallibleStrategy;
import strategy.mocks.FlipManyCardsMockModel;
import strategy.mocks.NoMoveFoundMockModel;

import static org.junit.Assert.assertEquals;

public class FlipCardsInfallibleStrategyTest {
  @Test
  public void testNoHolesGridWithRealModel() {
    GameModel model = Utils.loadModel("no_holes.txt", "big_cards.txt");
    InfallibleGameStrategy flipManyCards = new FlipCardsInfallibleStrategy();

    // at initial state of the game, no cards yet, so get the most upper left and first card index
    Pair<Move, Integer> firstMove = flipManyCards.decideMove(model, GamePlayer.RED);
    assertEquals(firstMove, new Pair<>(new Move(model.getHand(GamePlayer.RED).get(0), 0,0), 0));

    model.placeCard(1, 1, 1); // red places 7253 into 1, 1
    assertEquals(model.getScore(GamePlayer.RED), 5);
    assertEquals(model.getScore(GamePlayer.BLUE), 5);

    Pair<Move, Integer> redFirstMove = flipManyCards.decideMove(model, GamePlayer.RED);
    assertEquals(redFirstMove, new Pair<>(new Move(model.getHand(GamePlayer.RED).get(0), 0,0), 0));
    // since the current card in grid is red, so no card can be flipped, default to top left and
    // first card index for Red player

    Pair<Move, Integer> blueFirstMove = flipManyCards.decideMove(model, GamePlayer.BLUE);
    assertEquals(blueFirstMove, new Pair<>(new Move(model.getHand(GamePlayer.BLUE).get(3), 0,1), 1));
    // since the card in grid is red at (1, 1), which has the value 7235, there are two cards that
    // can beat this card which is 28A3 and 1A45 from blue hand, since 28A3 is smaller index in
    // hand, place it in most upper and leftest position that can flip red card, which is (0, 1)

    model.placeCard(model.getHand(GamePlayer.BLUE).indexOf(blueFirstMove.getKey().getCard()),
            blueFirstMove.getKey().getRow(), blueFirstMove.getKey().getCol()); // blue places 28A3 into (0, 1)
    assertEquals(model.getScore(GamePlayer.RED), 4);
    assertEquals(model.getScore(GamePlayer.BLUE), 6);

    Pair<Move, Integer> redSecondMove = flipManyCards.decideMove(model, GamePlayer.RED);
    assertEquals(redSecondMove, new Pair<>(new Move(model.getHand(GamePlayer.RED).get(0), 0,0), 2));

    model.placeCard(model.getHand(GamePlayer.RED).indexOf(redSecondMove.getKey().getCard()),
            redSecondMove.getKey().getRow(), redSecondMove.getKey().getCol()); // red places 6293 into (0, 0)

    assertEquals(model.getScore(GamePlayer.RED), 6);
    assertEquals(model.getScore(GamePlayer.BLUE), 4);

    Pair<Move, Integer> blueSecondMove = flipManyCards.decideMove(model, GamePlayer.BLUE);
    assertEquals(blueSecondMove, new Pair<>(new Move(model.getHand(GamePlayer.BLUE).get(0), 1,0), 3));

    model.placeCard(model.getHand(GamePlayer.BLUE).indexOf(blueSecondMove.getKey().getCard()),
            blueSecondMove.getKey().getRow(), blueSecondMove.getKey().getCol()); // blue places 97A2 into (1, 0)

    assertEquals(model.getScore(GamePlayer.RED), 3);
    assertEquals(model.getScore(GamePlayer.BLUE), 7);

    Pair<Move, Integer> redThirdMove = flipManyCards.decideMove(model, GamePlayer.RED);
    assertEquals(redThirdMove, new Pair<>(new Move(model.getHand(GamePlayer.RED).get(2), 2,0), 4));
  }

  @Test
  public void testSimpleGridWithRealModel() {
    GameModel model = Utils.loadModel("simple_grid.txt", "big_cards.txt");
    InfallibleGameStrategy flipManyCards = new FlipCardsInfallibleStrategy();

    // since the grid is empty, the first move default to top left and first card index
    Pair<Move, Integer> redInitialMove = flipManyCards.decideMove(model, GamePlayer.RED);
    assertEquals(redInitialMove, new Pair<>(new Move(model.getHand(GamePlayer.RED).get(0), 0,0), 0));
    Pair<Move, Integer> blueInitialMove = flipManyCards.decideMove(model, GamePlayer.BLUE);
    assertEquals(blueInitialMove, new Pair<>(new Move(model.getHand(GamePlayer.BLUE).get(0), 0,0), 0));

    assertEquals(model.getScore(GamePlayer.RED), 4);
    assertEquals(model.getScore(GamePlayer.BLUE), 4);

    model.placeCard(0, 0, 0); // red places 6293 in (0, 0)

    Pair<Move, Integer> blueFirstMove = flipManyCards.decideMove(model, GamePlayer.BLUE);
    assertEquals(blueFirstMove, new Pair<>(new Move(model.getHand(GamePlayer.BLUE).get(0), 1,0), 1));

    model.placeCard(model.getHand(GamePlayer.BLUE).indexOf(blueFirstMove.getKey().getCard()),
            blueFirstMove.getKey().getRow(), blueFirstMove.getKey().getCol());

    // since there is no card in red that can flip, default to most upper leftest
    // and first card index in hand
    Pair<Move, Integer> redSecondMove = flipManyCards.decideMove(model, GamePlayer.RED);
    assertEquals(redSecondMove, new Pair<>(new Move(model.getHand(GamePlayer.RED).get(0), 1,3), 0));

    model.placeCard(model.getHand(GamePlayer.RED).indexOf(redSecondMove.getKey().getCard()),
            redSecondMove.getKey().getRow(), redSecondMove.getKey().getCol());

    Pair<Move, Integer> blueSecondMove = flipManyCards.decideMove(model, GamePlayer.BLUE);
    assertEquals(blueSecondMove, new Pair<>(new Move(model.getHand(GamePlayer.BLUE).get(0), 2,3), 1));

    model.placeCard(model.getHand(GamePlayer.BLUE).indexOf(blueSecondMove.getKey().getCard()),
            blueSecondMove.getKey().getRow(), blueSecondMove.getKey().getCol());

    Pair<Move, Integer> redThirdMove = flipManyCards.decideMove(model, GamePlayer.RED);
    assertEquals(redThirdMove, new Pair<>(new Move(model.getHand(GamePlayer.RED).get(0), 2,2), 2));

    model.placeCard(model.getHand(GamePlayer.RED).indexOf(redThirdMove.getKey().getCard()),
            redThirdMove.getKey().getRow(), redThirdMove.getKey().getCol());

    Pair<Move, Integer> blueThirdMove = flipManyCards.decideMove(model, GamePlayer.BLUE);
    assertEquals(blueThirdMove, new Pair<>(new Move(model.getHand(GamePlayer.BLUE).get(0), 2,1), 3));

    model.placeCard(model.getHand(GamePlayer.BLUE).indexOf(blueThirdMove.getKey().getCard()),
            blueThirdMove.getKey().getRow(), blueThirdMove.getKey().getCol());

    Pair<Move, Integer> redFourthMove = flipManyCards.decideMove(model, GamePlayer.RED);
    assertEquals(redFourthMove, new Pair<>(new Move(model.getHand(GamePlayer.RED).get(0), 2,0), 4));
  }

  @Test
  public void noHolesWithMockModel() {
    InfallibleGameStrategy flipManyCardsStrategy = new FlipCardsInfallibleStrategy();
    String gridFile = Utils.getFilePath("no_holes.txt", "grid");
    String cardFile = Utils.getFilePath("big_cards.txt", "cards");
    CellType[][] cellTypes = GameConfigParser.getCellTypes(gridFile);
    List<Cell> cells = GameConfigParser.getCells(cardFile);
    GameModel model = new FlipManyCardsMockModel(cellTypes, cells);
    model.startGame(false);
    Pair<Move, Integer> move = flipManyCardsStrategy.decideMove(model, GamePlayer.RED);
    // mock model that returns highest number for count card flip if the name is corrupt king
    // and the col and row is equal to 1
    assertEquals(move.getKey().getCard().getName(), "CorruptKing");
    assertEquals(move.getKey().getCol(), 1);
    assertEquals(move.getKey().getRow(), 1);
  }

  @Test(expected = IllegalStateException.class)
  public void mockModelCannotPlaceCard() throws IOException {
    InfallibleGameStrategy flipManyCardsStrategy = new FlipCardsInfallibleStrategy();
    String gridFile = Utils.getFilePath("no_holes.txt", "grid");
    String cardFile = Utils.getFilePath("big_cards.txt", "cards");
    CellType[][] cellTypes = GameConfigParser.getCellTypes(gridFile);
    List<Cell> cells = GameConfigParser.getCells(cardFile);
    GameModel model = new NoMoveFoundMockModel(cellTypes, cells);
    try {
      model.startGame(false);
      flipManyCardsStrategy.decideMove(model, GamePlayer.RED);
    } finally {
      List<String> lines = Files.readAllLines(Paths.get("strategy-transcript.txt"));
      int lineIndex = 0;
      // check for each of 5 cards in hand for every single row and col
      for (int i = 0; i < 5; i++) {
        for (int row = 0; row < 3; row++) {
          for (int col = 0; col < 3; col++) {
            assertEquals("Check can place card at row " + row + " and col " + col,
                    lines.get(lineIndex++));
          }
        }
      }
    }
  }
}