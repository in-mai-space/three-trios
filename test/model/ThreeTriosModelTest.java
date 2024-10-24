package model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.components.card.Card;
import model.components.card.ThreeTriosCard;
import model.components.enums.AttackValue;
import model.components.enums.CellType;
import model.components.enums.Direction;
import model.components.enums.GamePlayer;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ThreeTriosModelTest {
  private ThreeTriosModel loadModel(String gridFile, String cardsFile) {
    String gridFilePath = GameConfigParserTest.getFilePath(gridFile, "grid");
    String cardsFilePath = GameConfigParserTest.getFilePath(cardsFile, "cards");
    return ThreeTriosModel.fromFiles(gridFilePath, cardsFilePath);
  }

  private void assertThrowsWithMessage(Class<? extends Throwable> expectedException,
                                       String expectedMessage, Runnable executable) {
    Throwable thrown = assertThrows(expectedException, executable::run);
    assertEquals(expectedMessage, thrown.getMessage());
  }

  @Test
  public void nullFilePathTests() {
    String validCardsFilePath = GameConfigParserTest.getFilePath("big_cards.txt", "cards");
    String validGridFilePath = GameConfigParserTest.getFilePath("big_no_hole.txt", "grid");

    assertThrowsWithMessage(IllegalArgumentException.class, "Filepath should not be null", () ->
            ThreeTriosModel.fromFiles(null, validCardsFilePath)
    );

    assertThrowsWithMessage(IllegalArgumentException.class, "Filepath should not be null", () ->
            ThreeTriosModel.fromFiles(validGridFilePath, null)
    );
  }

  @Test
  public void invalidGridOrCardFileTests() {
    assertThrowsWithMessage(IllegalStateException.class, "Cannot find file: nonexistent.txt", () ->
            ThreeTriosModel.fromFiles("nonexistent.txt", "nonexistent.txt")
    );

    assertThrowsWithMessage(IllegalStateException.class, "Config file wrong format", () ->
            loadModel("wrong_format.txt", "big_cards.txt")
    );

    assertThrowsWithMessage(IllegalStateException.class, "Insufficient rows in config file", () ->
            loadModel("not_enough_rows.txt", "big_cards.txt")
    );

    assertThrowsWithMessage(IllegalStateException.class, "Row 0 does not have 4 columns", () ->
            loadModel("not_enough_cols.txt", "big_cards.txt")
    );

    assertThrowsWithMessage(IllegalStateException.class, "Invalid character in grid config: M", () ->
            loadModel("invalid_char.txt", "big_cards.txt")
    );

    assertThrowsWithMessage(IllegalStateException.class, "Card entry must have 5 elements: 7 3 9 A", () ->
            loadModel("big_no_hole.txt", "no_name.txt")
    );

    assertThrowsWithMessage(IllegalArgumentException.class, "Invalid attack value: B", () ->
            loadModel("big_no_hole.txt", "invalid_letter.txt")
    );

    assertThrowsWithMessage(IllegalArgumentException.class, "Invalid attack value: 10", () ->
            loadModel("big_no_hole.txt", "invalid_number.txt")
    );

    assertThrowsWithMessage(IllegalStateException.class, "Card entry must have 5 elements: HeroKnight 4 2 3", () ->
            loadModel("big_no_hole.txt", "not_enough_values.txt")
    );

    assertThrowsWithMessage(IllegalArgumentException.class, "There must be at least 26 cards available.", () ->
            loadModel("big_no_hole.txt", "small_cards.txt")
    );
  }

  @Test
  public void invalidGameConfigurationTests() {
    List<Card> cards = GameConfigParser.getCards(GameConfigParserTest.getFilePath("big_cards.txt", "cards"));

    assertThrowsWithMessage(IllegalArgumentException.class, "Cell types must be at least 1x1", () ->
            ThreeTriosModel.fromData(new CellType[][]{}, cards)
    );

    assertThrowsWithMessage(IllegalArgumentException.class, "The number of non-hole cells must be odd.", () ->
            ThreeTriosModel.fromData(new CellType[][]{
                    {CellType.CELL, CellType.CELL},
                    {CellType.CELL, CellType.CELL}
            }, cards)
    );
  }

  @Test
  public void nonUniqueCards() {
    Card card = new ThreeTriosCard(new AttackValue[]{AttackValue.A, AttackValue.FIVE, AttackValue.NINE, AttackValue.SEVEN}, "card");
    List<Card> cards = new ArrayList<>(Collections.nCopies(10, card));

    assertThrowsWithMessage(IllegalArgumentException.class, "Cards must be unique", () ->
            ThreeTriosModel.fromData(new CellType[][]{
                    {CellType.CELL, CellType.CELL, CellType.CELL},
                    {CellType.CELL, CellType.CELL, CellType.CELL},
                    {CellType.CELL, CellType.CELL, CellType.CELL}
            }, cards)
    );
  }

  @Test
  public void startGameTest() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    model.startGame(false);

    // cards are distributed to hand equally
    assertEquals(5, model.getHand(GamePlayer.RED).size());
    assertEquals(5, model.getHand(GamePlayer.BLUE).size());
  }

  @Test(expected = IllegalStateException.class)
  public void startGameAlreadyInProgress() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    model.startGame(false);
    model.startGame(true);
  }

  @Test
  public void startGameShufflesCard() {
    ThreeTriosModel modelOne = loadModel("no_holes.txt", "big_cards.txt");
    ThreeTriosModel modelTwo = loadModel("no_holes.txt", "big_cards.txt");
    modelOne.startGame(false);
    modelTwo.startGame(true);
    assertNotEquals(modelOne.getHand(GamePlayer.RED), modelTwo.getHand(GamePlayer.RED));
  }

  @Test
  public void getCurrentPlayer() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    model.startGame(false);
    assertEquals(GamePlayer.RED, model.getCurrentPlayer());
    model.placeCard(0, 0, 0);
    assertEquals(GamePlayer.BLUE, model.getCurrentPlayer());
    model.placeCard(0, 1, 0);
    assertEquals(GamePlayer.RED, model.getCurrentPlayer());
  }

  @Test(expected = IllegalStateException.class)
  public void getCurrentPlayerGameNotStarted() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    model.getCurrentPlayer();
  }

  @Test
  public void getCurrentPlayerGameOver() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    model.startGame(false);
    placeCardsInGrid(model, new int[][]{
            {0, 0}, {0, 1}, {0, 2},
            {1, 0}, {1, 1}, {1, 2},
            {2, 0}, {2, 1}, {2, 2}
    });
    IllegalStateException thrown = assertThrows(IllegalStateException.class, model::getCurrentPlayer);
    assertEquals("Game is over", thrown.getMessage());
  }

  private void placeCardsInGrid(ThreeTriosModel model, int[][] positions) {
    for (int[] pos : positions) {
      model.placeCard(0, pos[0], pos[1]);
    }
  }

  @Test
  public void placeCard() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    model.startGame(false);
    model.placeCard(0, 0, 0);
    assertEquals(6, model.getGrid()[0][0].getAttackValue(Direction.NORTH));
    assertEquals(2, model.getGrid()[0][0].getAttackValue(Direction.SOUTH));
    assertEquals(9, model.getGrid()[0][0].getAttackValue(Direction.EAST));
    assertEquals(3, model.getGrid()[0][0].getAttackValue(Direction.WEST));
    assertEquals("CorruptKing", model.getGrid()[0][0].getName());
  }

  @Test
  public void placeCardInvalidIndexInHand() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    model.startGame(false);
    assertThrowsWithMessage(IllegalArgumentException.class, "Index out of bound for card",
            () -> model.placeCard(5, 5, 0)
    );
  }

  @Test
  public void placeCardGridPositionRowOutOfBound() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    model.startGame(false);
    assertThrowsWithMessage(IllegalArgumentException.class, "Row index is out of bounds.",
            () -> model.placeCard(0, 3, 0)
    );
  }

  @Test
  public void placeCardGridPositionColOutOfBound() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    model.startGame(false);
    assertThrowsWithMessage(IllegalArgumentException.class, "Column index is out of bounds.",
            () -> model.placeCard(0, 0, 3)
    );
  }

  @Test(expected = IllegalStateException.class)
  public void placeCardInHoleCell() {
    ThreeTriosModel model = loadModel("simple_grid.txt", "big_cards.txt");
    model.startGame(false);
    model.placeCard(0, 0, 3);
  }

  @Test(expected = IllegalStateException.class)
  public void placeCardIntoNonEmptyCell() {
    ThreeTriosModel model = loadModel("simple_grid.txt", "big_cards.txt");
    model.startGame(false);
    model.placeCard(0, 0, 0);
    model.placeCard(0, 0, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void placeCardGameNotStarted() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    model.placeCard(0, 0, 0);
  }

  @Test
  public void placeCardGameAlreadyOver() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    model.startGame(false);
    placeCardsInGrid(model, new int[][]{
            {0, 0}, {0, 1}, {0, 2},
            {1, 0}, {1, 1}, {1, 2},
            {2, 0}, {2, 1}, {2, 2}
    });
    assertThrowsWithMessage(IllegalStateException.class, "Game is over",
            () -> model.placeCard(0, 0, 0)
    );
  }

  @Test
  public void gameOver() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    model.startGame(false);
    assertFalse(model.gameOver());
    placeCardsInGrid(model, new int[][]{
            {0, 0}, {0, 1}, {0, 2},
            {1, 0}, {1, 1}, {1, 2},
            {2, 0}, {2, 1}, {2, 2}
    });
    assertTrue(model.gameOver());
  }

  @Test(expected = IllegalStateException.class)
  public void gameOverGameNotStarted() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    assertFalse(model.gameOver());
  }

  @Test
  public void getHandSize() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    model.startGame(false);
    assertEquals(5, model.getHandSize(GamePlayer.RED));
    assertEquals(5, model.getHandSize(GamePlayer.BLUE));
  }

  @Test
  public void getHandSizeGameNotStarted() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    assertThrowsWithMessage(IllegalStateException.class, "Game has not started",
            () -> model.getHandSize(GamePlayer.RED)
    );
  }

  @Test
  public void getHandSizeGameOver() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    model.startGame(false);
    placeCardsInGrid(model, new int[][]{
            {0, 0}, {0, 1}, {0, 2},
            {1, 0}, {1, 1}, {1, 2},
            {2, 0}, {2, 1}, {2, 2}
    });
    assertThrowsWithMessage(IllegalStateException.class, "Game is over",
            () -> model.getHandSize(GamePlayer.RED)
    );
  }

  @Test
  public void getCellTypesNoHoles() {
    ThreeTriosModel noHoles = loadModel("no_holes.txt", "big_cards.txt");
    noHoles.startGame(false);
    CellType[][] noHolesCellTypes = new CellType[][] {
            {CellType.CELL, CellType.CELL, CellType.CELL},
            {CellType.CELL, CellType.CELL, CellType.CELL},
            {CellType.CELL, CellType.CELL, CellType.CELL},
    };
    // modification attempt should not affect the model
    assertArrayEquals(noHolesCellTypes, noHoles.getCellTypes());
    noHolesCellTypes[0][0] = CellType.HOLE;
    assertEquals(CellType.CELL, noHoles.getCellTypes()[0][0]);

    ThreeTriosModel complexModel = loadModel("complex_grid.txt", "big_cards.txt");
    complexModel.startGame(false);
    CellType[][] complexCellTypes = new CellType[][]{
            {CellType.CELL, CellType.HOLE, CellType.CELL, CellType.CELL, CellType.CELL},
            {CellType.HOLE, CellType.HOLE, CellType.HOLE, CellType.CELL, CellType.HOLE},
            {CellType.HOLE, CellType.CELL, CellType.HOLE, CellType.HOLE, CellType.HOLE},
            {CellType.CELL, CellType.CELL, CellType.CELL, CellType.HOLE, CellType.HOLE}
    };
    // modification attempt should not affect the model
    assertArrayEquals(complexCellTypes, complexModel.getCellTypes());
    complexCellTypes[0][0] = CellType.HOLE;
    assertEquals(CellType.CELL, complexModel.getCellTypes()[0][0]);
  }

  @Test
  public void getCellTypesGameNotStarted() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    assertThrowsWithMessage(IllegalStateException.class, "Game has not started",
            model::getCellTypes
    );
  }

  @Test
  public void getCellTypesGameOver() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    model.startGame(false);
    placeCardsInGrid(model, new int[][]{
            {0, 0}, {0, 1}, {0, 2},
            {1, 0}, {1, 1}, {1, 2},
            {2, 0}, {2, 1}, {2, 2}
    });
    assertThrowsWithMessage(IllegalStateException.class, "Game is over",
            model::getCellTypes
    );
  }

  @Test
  public void getWinnerGameNotStarted() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    assertThrowsWithMessage(IllegalStateException.class, "Game has not started",
            model::getWinner
    );
  }

  @Test
  public void getWinnerGameNotOver() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    model.startGame(false);
    assertThrowsWithMessage(IllegalStateException.class, "Game is not over",
            model::getWinner
    );
  }

  @Test
  public void getGridGameNotStarted() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    assertThrowsWithMessage(IllegalStateException.class, "Game has not started",
            model::getGrid
    );
  }

  @Test
  public void getGridGameOver() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    model.startGame(false);
    placeCardsInGrid(model, new int[][]{
            {0, 0}, {0, 1}, {0, 2},
            {1, 0}, {1, 1}, {1, 2},
            {2, 0}, {2, 1}, {2, 2}
    });
    assertThrowsWithMessage(IllegalStateException.class, "Game is over",
            model::getGrid
    );
  }

  @Test
  public void getCurrentPlayerHandGameNotStarted() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    assertThrowsWithMessage(IllegalStateException.class, "Game has not started",
            model::getCurrentPlayerHand
    );
  }

  @Test
  public void getCurrentPlayerHandGameOver() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    model.startGame(false);
    placeCardsInGrid(model, new int[][]{
            {0, 0}, {0, 1}, {0, 2},
            {1, 0}, {1, 1}, {1, 2},
            {2, 0}, {2, 1}, {2, 2}
    });
    assertThrowsWithMessage(IllegalStateException.class, "Game is over",
            model::getCurrentPlayerHand
    );
  }

  @Test
  public void getHandGameNotStarted() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    assertThrowsWithMessage(IllegalStateException.class, "Game has not started",
            () -> model.getHand(GamePlayer.RED)
    );
  }

  @Test
  public void getHandGameOver() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    model.startGame(false);
    placeCardsInGrid(model, new int[][]{
            {0, 0}, {0, 1}, {0, 2},
            {1, 0}, {1, 1}, {1, 2},
            {2, 0}, {2, 1}, {2, 2}
    });
    assertThrowsWithMessage(IllegalStateException.class, "Game is over",
            () -> model.getHand(GamePlayer.RED)
    );
  }
}
