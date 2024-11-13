package model.implementation;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import controller.GameConfigParser;
import model.Utils;
import model.enums.AttackValue;
import model.interfaces.Cell;
import model.enums.CellType;
import model.enums.Direction;
import model.interfaces.GameModel;
import model.enums.GamePlayer;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Represent the test for the model.
 */
public class ThreeTriosModelTest {
  private List<Cell> redHands;
  private List<Cell> blueHands;
  private Cell angryDragon97A2;
  private Cell heroKnight4231;
  private Cell skyWhale4594;
  private Cell firePhoenix28A3;
  private Cell evilQueen1A45;
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
    evilQueen1A45 = new ThreeTriosCell(new AttackValue[]{ AttackValue.ONE, AttackValue.A,
        AttackValue.FOUR, AttackValue.FIVE}, "EvilQueen", GamePlayer.BLUE);
    blueHands = new ArrayList<>(List.of(angryDragon97A2, heroKnight4231, skyWhale4594,
            firePhoenix28A3, evilQueen1A45));

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
    redHands = new ArrayList<>(List.of(corruptKing6293, windBird7253, worldDragon7253,
        waterSeal3A74, earthLizard9166));
  }

  /**
   * Create a new model from given file path of grid and card.
   *
   * @param gridFile grid file name
   * @param cardsFile card file name
   * @return the model created
   */
  public static ThreeTriosModel loadModel(String gridFile, String cardsFile) {
    String gridFilePath = Utils.getFilePath(gridFile, "grid");
    String cardsFilePath = Utils.getFilePath(cardsFile, "cards");
    CellType[][] cellTypes = GameConfigParser.getCellTypes(gridFilePath);
    List<Cell> cells = GameConfigParser.getCells(cardsFilePath);
    return new ThreeTriosModel(cellTypes, cells);
  }

  private void assertThrowsWithMessage(Class<? extends Throwable> expectedException,
                                       String expectedMessage, Runnable executable) {
    Throwable thrown = assertThrows(expectedException, executable::run);
    assertEquals(expectedMessage, thrown.getMessage());
  }

  @Test
  public void invalidGameConfigurationTests() {
    List<Cell> cells = GameConfigParser.getCells(Utils.getFilePath("big_cards.txt", "cards"));

    assertThrowsWithMessage(IllegalArgumentException.class,
        "Cell types must be at least 1x1", () -> new ThreeTriosModel(new CellType[][]{}, cells)
    );

    assertThrowsWithMessage(IllegalArgumentException.class,
        "The number of non-hole cells must be odd.", () ->
            new ThreeTriosModel(new CellType[][]{
                    {CellType.CELL, CellType.CELL},
                    {CellType.CELL, CellType.CELL}
            }, cells)
    );
  }

  @Test
  public void nonUniqueCards() {
    Cell cell = new ThreeTriosCell(new AttackValue[]{AttackValue.A, AttackValue.FIVE,
        AttackValue.NINE, AttackValue.SEVEN}, "card");
    List<Cell> cells = new ArrayList<>(Collections.nCopies(10, cell));

    assertThrowsWithMessage(IllegalArgumentException.class, "Cards must be unique", () ->
            new ThreeTriosModel(new CellType[][]{
                    {CellType.CELL, CellType.CELL, CellType.CELL},
                    {CellType.CELL, CellType.CELL, CellType.CELL},
                    {CellType.CELL, CellType.CELL, CellType.CELL}
            }, cells)
    );
  }

  @Test
  public void startGameTest() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    model.startGame(false);

    // player Red's hand
    assertEquals(5, model.getCurrentPlayerHand().size());
    model.placeCard(0, 0, 0);
    // player Blue's hand
    assertEquals(5, model.getCurrentPlayerHand().size());
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
    assertNotEquals(modelOne.getCurrentPlayerHand(), modelTwo.getCurrentPlayerHand());
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

  @Test(expected = IllegalStateException.class)
  public void getHandGameNotStarted() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    model.getCurrentPlayer();
  }

  @Test
  public void getHand() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    model.startGame(false);
    assertEquals(new ArrayList<>(Arrays.asList(corruptKing6293, windBird7253, worldDragon7253,
                    waterSeal3A74, earthLizard9166)), model.getHand(GamePlayer.RED));
    assertEquals(new ArrayList<>(
            Arrays.asList(angryDragon97A2, heroKnight4231, skyWhale4594, firePhoenix28A3,
                    evilQueen1A45)), model.getHand(GamePlayer.BLUE));
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

  @Test(expected = IllegalArgumentException.class)
  public void canPlaceCardGridPositionRowOutOfBound() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    model.startGame(false);
    model.canPlaceCard(-1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void canPlaceCardGridPositionColOutOfBound() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    model.startGame(false);
    model.canPlaceCard(2, 3);
  }

  @Test
  public void canPlaceCardSimpleGrid() {
    ThreeTriosModel model = loadModel("simple_grid.txt", "big_cards.txt");
    model.startGame(false);
    assertTrue(model.canPlaceCard(0, 0));
    assertFalse(model.canPlaceCard(0, 1));
    assertTrue(model.canPlaceCard(1, 0));
    assertFalse(model.canPlaceCard(1, 1));
  }

  @Test
  public void canPlaceCardComplexGrid() {
    ThreeTriosModel model = loadModel("complex_grid.txt", "big_cards.txt");
    model.startGame(false);
    assertTrue(model.canPlaceCard(0, 0));
    assertFalse(model.canPlaceCard(0, 1));
    assertTrue(model.canPlaceCard(0, 2));
    assertFalse(model.canPlaceCard(1, 0));
    assertFalse(model.canPlaceCard(1, 1));
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
  public void getWinnerGameNotStarted() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    assertThrowsWithMessage(IllegalStateException.class, "Game has not started",
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
  public void getCurrentPlayerHandGameNotStarted() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    assertThrowsWithMessage(IllegalStateException.class, "Game has not started",
            model::getCurrentPlayerHand
    );
  }

  @Test
  public void testGetCurrentPlayerHand() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    model.startGame(false);

    assertEquals(model.getCurrentPlayerHand(), redHands);
    // modifying this list does not affect the actual cards in hand
    model.getCurrentPlayerHand().remove(0);
    assertEquals(model.getCurrentPlayerHand(), redHands);
    // place the first card on (0, 0) cell
    model.placeCard(0, 0, 0);

    assertEquals(model.getCurrentPlayerHand(), blueHands);
    // modifying this list does not affect the actual cards in hand
    model.getCurrentPlayerHand().remove(0);
    assertEquals(model.getCurrentPlayerHand(), blueHands);
    model.placeCard(0, 1, 0);

    // cards of player Red should no longer have the first card
    redHands.remove(0);
    assertEquals(model.getCurrentPlayerHand(), redHands);
  }

  @Test
  public void getGrid() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    model.startGame(false);
    assertNull(model.getGrid()[0][0]);
    model.placeCard(0, 0, 0);
    Cell[][] gridState = new Cell[][]{
            {corruptKing6293, null, null},
            {null, null, null},
            {null, null, null}
    };
    assertArrayEquals(gridState, model.getGrid());
    assertEquals(model.getGrid()[0][0], corruptKing6293);
    // attempt to set the card to null again
    model.getGrid()[0][0] = null;
    model.getGrid()[1] = null;
    // modification did not affect the state of the game
    assertArrayEquals(gridState, model.getGrid());
    assertEquals(model.getGrid()[0][0], corruptKing6293);
  }

  @Test
  public void getWinnerWithGamePlayNoHolesRedWins() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    model.startGame(false);
    Object[][] cardPlacement = new Object[][]{
        {"WindBird", 1, 1}, {"EvilQueen", 0, 1}, {"CorruptKing", 0, 0},
        {"SkyWhale", 1, 0}, {"WorldDragon", 2, 0}, {"HeroKnight", 2, 1},
        {"EarthLizard", 0, 2}, {"FirePhoenix", 1, 2}, {"WaterSeal", 2, 2}
    };
    placeCardsIntoGrid(cardPlacement, model, () -> { } );
    assertEquals(GamePlayer.RED, model.getWinner().get());

    Cell[][] finalCellLayout = new Cell[][]{
            {corruptKing6293, evilQueen1A45, earthLizard9166},
            {skyWhale4594, windBird7253, firePhoenix28A3},
            {worldDragon7253, heroKnight4231, waterSeal3A74}
    };
    Cell[][] actualGridLayout = model.getGrid();
    // check the grid layout are equal
    assertArrayEquals(finalCellLayout, actualGridLayout);
    GamePlayer[][] finalCardOwnerLayout = new GamePlayer[][]{
            {GamePlayer.RED, GamePlayer.RED, GamePlayer.RED},
            {GamePlayer.RED, GamePlayer.RED, GamePlayer.RED},
            {GamePlayer.RED, GamePlayer.RED, GamePlayer.RED}
    };
    // check that the final owners of cards when game end is correct
    assertCardsOwnershipOnGrid(actualGridLayout, finalCardOwnerLayout);
  }

  @Test
  public void getWinnerWithGamePlaySimpleGridBTie() {
    ThreeTriosModel model = loadModel("simple_grid.txt", "big_cards.txt");
    model.startGame(false);
    Object[][] cardPlacement = new Object[][]{
            {"WindBird", 0, 0}, {"AngryDragon", 1, 0}, {"WorldDragon", 2, 0},
            {"SkyWhale", 2, 1}, {"CorruptKing", 1, 3}, {"HeroKnight", 2, 3},
            {"WaterSeal", 2, 2}
    };
    placeCardsIntoGrid(cardPlacement, model, () -> { } );
    assertEquals(Optional.empty(), model.getWinner());

    Cell[][] finalCellLayout = new Cell[][]{
            {windBird7253, null, null, null},
            {angryDragon97A2, null, null, corruptKing6293},
            {worldDragon7253, skyWhale4594, waterSeal3A74, heroKnight4231}
    };
    Cell[][] actualGridLayout = model.getGrid();
    // check the grid layout are equal
    assertArrayEquals(finalCellLayout, actualGridLayout);
    GamePlayer[][] finalCardOwnerLayout = new GamePlayer[][]{
            {GamePlayer.BLUE, null, null, null},
            {GamePlayer.BLUE, null, null, GamePlayer.RED},
            {GamePlayer.RED, GamePlayer.BLUE, GamePlayer.RED, GamePlayer.RED}
    };

    // check that the final owners of cards when game end is correct
    assertCardsOwnershipOnGrid(actualGridLayout, finalCardOwnerLayout);
  }

  @Test
  public void getWinnerWithGamePlayComplexGridBlueWins() {
    ThreeTriosModel model = loadModel("complex_grid.txt", "big_cards.txt");
    model.startGame(false);
    Object[][] cardPlacement = new Object[][]{
            {"CorruptKing", 3, 1}, {"FirePhoenix", 3, 0}, {"WindBird", 2, 1},
            {"AngryDragon", 3, 2}, {"EarthLizard", 0, 3}, {"EvilQueen", 1, 3},
            {"WaterSeal", 0, 4}, {"SkyWhale", 0, 2}, {"WorldDragon", 0, 0}
    };
    placeCardsIntoGrid(cardPlacement, model, () -> { } );
    assertEquals(GamePlayer.BLUE, model.getWinner().get());

    Cell[][] finalCellLayout = new Cell[][]{
            {worldDragon7253, null, skyWhale4594, earthLizard9166, waterSeal3A74},
            {null, null, null, evilQueen1A45, null},
            {null, windBird7253, null, null, null},
            {firePhoenix28A3, corruptKing6293, angryDragon97A2, null, null}
    };
    Cell[][] actualGridLayout = model.getGrid();
    // check the grid layout are equal
    assertArrayEquals(finalCellLayout, actualGridLayout);
    GamePlayer[][] finalCardOwnerLayout = new GamePlayer[][]{
            {GamePlayer.RED, null, GamePlayer.BLUE, GamePlayer.BLUE, GamePlayer.BLUE},
            {null, null, null, GamePlayer.BLUE, null},
            {null, GamePlayer.RED, null, null, null},
            {GamePlayer.BLUE, GamePlayer.BLUE, GamePlayer.BLUE, null, null}
    };

    // check that the final owners of cards when game end is correct
    assertCardsOwnershipOnGrid(actualGridLayout, finalCardOwnerLayout);
  }

  /**
   * Asserts that each card on the grid has the same owner as the expected owner.
   *
   * @param grid the card grid layout of the game
   * @param expectedOwner the layout of expected owners of cards on grid
   */
  private void assertCardsOwnershipOnGrid(Cell[][] grid, GamePlayer[][] expectedOwner) {
    for (int row = 0; row < grid.length; row++) {
      for (int col = 0; col < grid[0].length; col++) {
        if (grid[row][col] != null) {
          assertEquals(expectedOwner[row][col], grid[row][col].getOwner());
        }
      }
    }
  }

  /**
   * Place cards into the grid's row and col using its name.
   *
   * @param cardPlacement 2d arrays that contains card name, its row and col position on grid
   * @param model model of the game
   */
  public static void placeCardsIntoGrid(Object[][] cardPlacement, GameModel model, Runnable
          runnable) {
    for (Object[] cardNameAndPosition : cardPlacement) {
      String cardName = (String) cardNameAndPosition[0];
      Cell searchedCell = model.getCurrentPlayerHand().stream()
              .filter(card -> card.getName().equals(cardName)).collect(Collectors.toList()).get(0);
      int cardIndex = model.getCurrentPlayerHand().indexOf(searchedCell);
      model.placeCard(cardIndex, (int) cardNameAndPosition[1], (int) cardNameAndPosition[2]);
      runnable.run();
    }
  }

  @Test
  public void getWidthAndHeight() {
    ThreeTriosModel model = loadModel("simple_grid.txt", "big_cards.txt");
    model.startGame(true);
    assertEquals(model.getGridWidth(), 4);
    assertEquals(model.getGridHeight(), 3);
  }

  @Test
  public void getWidthGameNotStarted() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    IllegalStateException exception = assertThrows(IllegalStateException.class,
            model::getGridWidth);
    assertEquals("Game has not started", exception.getMessage());
  }

  @Test
  public void geHeightGameNotStarted() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    IllegalStateException exception = assertThrows(IllegalStateException.class,
            model::getGridHeight);
    assertEquals("Game has not started", exception.getMessage());
  }

  @Test
  public void getCardAtGameNotStarted() {
    ThreeTriosModel model = loadModel("simple_grid.txt", "big_cards.txt");
    IllegalStateException exception = assertThrows(IllegalStateException.class,
            () -> model.getCardAt(0, 0));
    assertEquals("Game has not started", exception.getMessage());
  }

  @Test
  public void getOwnerAtGameNotStarted() {
    ThreeTriosModel model = loadModel("simple_grid.txt", "big_cards.txt");
    IllegalStateException exception = assertThrows(IllegalStateException.class,
            () -> model.getOwnerAt(0, 0));
    assertEquals("Game has not started", exception.getMessage());
  }

  @Test
  public void getCardAtOutOfBound() {
    ThreeTriosModel model = loadModel("simple_grid.txt", "big_cards.txt");
    model.startGame(true);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> model.getCardAt(0, 4));
    assertEquals("Column index is out of bounds.", exception.getMessage());
  }

  @Test
  public void getOwnerAtOutOfBound() {
    ThreeTriosModel model = loadModel("simple_grid.txt", "big_cards.txt");
    model.startGame(true);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> model.getOwnerAt(3, 0));
    assertEquals("Row index is out of bounds.", exception.getMessage());
    // Replace with actual expected message
  }

  @Test
  public void getCardAtInvalidPosition() {
    ThreeTriosModel model = loadModel("simple_grid.txt", "big_cards.txt");
    model.startGame(true);
    IllegalStateException exception = assertThrows(IllegalStateException.class, () -> model.getCardAt(0, 0));
    assertEquals("Cannot get card at this position", exception.getMessage());
  }

  @Test
  public void getOwnerAtInvalidPosition() {
    ThreeTriosModel model = loadModel("simple_grid.txt", "big_cards.txt");
    model.startGame(true);
    IllegalStateException exception = assertThrows(IllegalStateException.class,
            () -> model.getOwnerAt(0, 0));
    assertEquals("Cannot get card at this position", exception.getMessage());
  }

  @Test
  public void getScoreGameNotStarted() {
    ThreeTriosModel model = loadModel("simple_grid.txt", "big_cards.txt");
    IllegalStateException exception = assertThrows(IllegalStateException.class,
            () -> model.getScore(GamePlayer.BLUE));
    assertEquals("Game has not started", exception.getMessage());
  }

  @Test
  public void countCardFlipGameNotStarted() {
    ThreeTriosModel model = loadModel("simple_grid.txt", "big_cards.txt");
    IllegalStateException exception = assertThrows(IllegalStateException.class,
            () -> model.countCardFlip(windBird7253, 0, 0));
    assertEquals("Game has not started", exception.getMessage());
  }

  @Test
  public void countCardFlipGameOver() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    model.startGame(false);
    placeCardsInGrid(model, new int[][]{
            {0, 0}, {0, 1}, {0, 2},
            {1, 0}, {1, 1}, {1, 2},
            {2, 0}, {2, 1}, {2, 2}
    });
    IllegalStateException exception = assertThrows(IllegalStateException.class,
            () -> model.countCardFlip(windBird7253, 0, 0));
    assertEquals("Game is over", exception.getMessage());
  }


  @Test
  public void getCardAndOwnerSuccess() {
    ThreeTriosModel model = loadModel("simple_grid.txt", "big_cards.txt");
    model.startGame(false);
    model.placeCard(0, 0, 0);
    assertEquals(model.getCardAt(0, 0), corruptKing6293);
    assertEquals(model.getOwnerAt(0, 0), GamePlayer.RED);
  }

  @Test
  public void countCardFlipOccupiedCell() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    model.startGame(false);
    model.placeCard(0, 0, 0);
    IllegalStateException exception = assertThrows(IllegalStateException.class,
            () -> model.countCardFlip(windBird7253, 0, 0));
    assertEquals("Cannot place card into row 0 and column 0", exception.getMessage());
  }

  @Test
  public void countCardFlip() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    model.startGame(false);
    assertEquals(model.countCardFlip(windBird7253, 1, 1), 0);
    model.placeCard(1, 1, 1);
    assertEquals(model.countCardFlip(evilQueen1A45, 0, 1), 1);
    model.placeCard(4, 0, 1);
    assertEquals(model.countCardFlip(corruptKing6293, 0, 0), 2);
    model.placeCard(0, 0, 0);
    assertEquals(model.countCardFlip(skyWhale4594, 1, 0), 3);
    model.placeCard(2, 1, 0);
    assertEquals(model.countCardFlip(worldDragon7253, 2, 0), 4);
    model.placeCard(0, 2, 0);
    assertEquals(model.countCardFlip(heroKnight4231, 2, 1), 1);
  }

  @Test
  public void getScore() {
    ThreeTriosModel model = loadModel("no_holes.txt", "big_cards.txt");
    model.startGame(false);
    assertEquals(model.getScore(GamePlayer.BLUE), 5);
    assertEquals(model.getScore(GamePlayer.RED), 5);

    model.placeCard(1, 1, 1);
    assertEquals(model.getScore(GamePlayer.BLUE), 5);
    assertEquals(model.getScore(GamePlayer.RED), 5);

    model.placeCard(4, 0, 1);
    assertEquals(model.getScore(GamePlayer.BLUE), 6);
    assertEquals(model.getScore(GamePlayer.RED), 4);

    model.placeCard(0, 0, 0);
    assertEquals(model.getScore(GamePlayer.BLUE), 4);
    assertEquals(model.getScore(GamePlayer.RED), 6);

    model.placeCard(2, 1, 0);
    assertEquals(model.getScore(GamePlayer.BLUE), 7);
    assertEquals(model.getScore(GamePlayer.RED), 3);

    model.placeCard(0, 2, 0);
    assertEquals(model.getScore(GamePlayer.BLUE), 3);
    assertEquals(model.getScore(GamePlayer.RED), 7);
  }
}
