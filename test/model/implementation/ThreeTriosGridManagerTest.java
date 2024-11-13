package model.implementation;

import org.junit.Before;
import org.junit.Test;

import controller.GameConfigParser;
import model.Utils;
import model.enums.AttackValue;
import model.interfaces.Cell;
import model.enums.CellType;
import model.enums.GamePlayer;
import model.interfaces.GridManager;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test class for ThreeTriosGridManager.
 */
public class ThreeTriosGridManagerTest {
  private GridManager noHoleManager;
  private GridManager simpleGridManager;
  private GridManager complexGridManager;
  private Cell cell527A;
  private Cell cell7253;
  private Cell cell4599;
  private Cell cell4623;
  private Cell cell2899;
  private Cell cell27A9;

  @Before
  public void setUp() {
    String noHoleFilePath = Utils.getFilePath("no_holes.txt", "grid");
    CellType[][] noHoleGrid = GameConfigParser.getCellTypes(noHoleFilePath);

    String simpleGridFilePath = Utils.getFilePath("simple_grid.txt", "grid");
    CellType[][] simpleGrid = GameConfigParser.getCellTypes(simpleGridFilePath);

    String complexGridFilePath = Utils.getFilePath("complex_grid.txt", "grid");
    CellType[][] complexGrid = GameConfigParser.getCellTypes(complexGridFilePath);

    noHoleManager = new ThreeTriosGridManager(noHoleGrid);
    simpleGridManager = new ThreeTriosGridManager(simpleGrid);
    complexGridManager = new ThreeTriosGridManager(complexGrid);

    cell527A = new ThreeTriosCell(new AttackValue[]{AttackValue.FIVE, AttackValue.TWO,
        AttackValue.SEVEN, AttackValue.A}, GamePlayer.RED);
    cell7253 = new ThreeTriosCell(new AttackValue[]{AttackValue.SEVEN, AttackValue.TWO,
        AttackValue.FIVE, AttackValue.THREE}, GamePlayer.BLUE);
    cell4599 = new ThreeTriosCell(new AttackValue[]{AttackValue.FOUR, AttackValue.FIVE,
        AttackValue.NINE, AttackValue.NINE}, GamePlayer.BLUE);
    cell4623 = new ThreeTriosCell(new AttackValue[]{AttackValue.FOUR, AttackValue.SIX,
        AttackValue.TWO, AttackValue.THREE}, GamePlayer.RED);
    cell2899 = new ThreeTriosCell(new AttackValue[]{AttackValue.TWO, AttackValue.EIGHT,
        AttackValue.NINE, AttackValue.NINE}, GamePlayer.RED);
    cell27A9 = new ThreeTriosCell(new AttackValue[]{AttackValue.TWO, AttackValue.SEVEN,
        AttackValue.A, AttackValue.NINE}, GamePlayer.BLUE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullConstructor() {
    new ThreeTriosGridManager(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOutOfBoundPlaceCard() {
    noHoleManager.placeCard(cell527A, 3, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeIndexPlaceCard() {
    noHoleManager.placeCard(cell527A, -1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullPlaceCard() {
    noHoleManager.placeCard(null, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBattleOutOfBound() {
    noHoleManager.executeBattle(3, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBattleNegativeIndex() {
    noHoleManager.executeBattle(-1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCanPlaceCardOutOfBound() {
    noHoleManager.canPlaceCard(3, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCanPlaceCardNegativeIndex() {
    noHoleManager.canPlaceCard(-1, 0);
  }

  @Test
  public void canPlaceCardNoHole() {
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        assertTrue(noHoleManager.canPlaceCard(row, col));
      }
    }
    // cannot place card in a cell that already has a card
    noHoleManager.placeCard(cell527A, 0, 0);
    assertFalse(noHoleManager.canPlaceCard(0, 0));
  }

  @Test
  public void canPlaceCardSimpleGrid() {
    boolean[][] expectedResults = {
            { true, false, false, false },
            { true, false, false, true },
            { true, true, true, true }
    };
    for (int row = 0; row < expectedResults.length; row++) {
      for (int col = 0; col < expectedResults[row].length; col++) {
        assertEquals(expectedResults[row][col], simpleGridManager.canPlaceCard(row, col));
      }
    }
    // cannot place card in a cell that already has a card
    simpleGridManager.placeCard(cell527A, 0, 0);
    assertFalse(simpleGridManager.canPlaceCard(0, 0));
  }

  @Test
  public void canPlaceCardComplexGrid() {
    boolean[][] expectedResults = {
            { true, false, true, true, true },
            { false, false, false, true, false },
            { false, true, false, false, false },
            { true, true, true, false, false }
    };
    for (int row = 0; row < expectedResults.length; row++) {
      for (int col = 0; col < expectedResults[row].length; col++) {
        assertEquals(expectedResults[row][col], complexGridManager.canPlaceCard(row, col));
      }
    }
    // cannot place card in a cell that already has a card
    complexGridManager.placeCard(cell527A, 0, 0);
    assertFalse(complexGridManager.canPlaceCard(0, 0));
  }

  @Test
  public void testPlaceCardNoHoles() {
    noHoleManager.placeCard(cell527A, 0, 0);
    assertEquals(cell527A, noHoleManager.getCardAt(0, 0));
    assertEquals(GamePlayer.RED, noHoleManager.getCardAt(0, 0).getOwner());

    noHoleManager.placeCard(cell7253, 1, 0);
    assertEquals(cell7253, noHoleManager.getCardAt(1, 0));
    assertEquals(GamePlayer.BLUE, noHoleManager.getCardAt(1, 0).getOwner());

    noHoleManager.placeCard(cell4599, 1, 2);
    assertEquals(cell4599, noHoleManager.getCardAt(1, 2));
    assertEquals(GamePlayer.BLUE, noHoleManager.getCardAt(1, 2).getOwner());
  }

  @Test
  public void testPlaceCardSimpleGrid() {
    simpleGridManager.placeCard(cell527A, 0, 0);
    assertEquals(cell527A, simpleGridManager.getCardAt(0, 0));
    assertEquals(GamePlayer.RED, simpleGridManager.getCardAt(0, 0).getOwner());

    simpleGridManager.placeCard(cell7253, 1, 0);
    assertEquals(cell7253, simpleGridManager.getCardAt(1, 0));
    assertEquals(GamePlayer.BLUE, simpleGridManager.getCardAt(1, 0).getOwner());

    simpleGridManager.placeCard(cell4599, 1, 3);
    assertEquals(cell4599, simpleGridManager.getCardAt(1, 3));
    assertEquals(GamePlayer.BLUE, simpleGridManager.getCardAt(1, 3).getOwner());
  }

  @Test
  public void testPlaceCardComplexGrid() {
    complexGridManager.placeCard(cell527A, 0, 0);
    assertEquals(cell527A, complexGridManager.getCardAt(0, 0));
    assertEquals(GamePlayer.RED, complexGridManager.getCardAt(0, 0).getOwner());

    complexGridManager.placeCard(cell7253, 0, 4);
    assertEquals(cell7253, complexGridManager.getCardAt(0, 4));
    assertEquals(GamePlayer.BLUE, complexGridManager.getCardAt(0, 4).getOwner());

    complexGridManager.placeCard(cell4599, 3, 2);
    assertEquals(cell4599, complexGridManager.getCardAt(3, 2));
    assertEquals(GamePlayer.BLUE, complexGridManager.getCardAt(3, 2).getOwner());
  }

  @Test(expected = IllegalStateException.class)
  public void testPlaceCardWhenThereIsCard() {
    simpleGridManager.placeCard(cell527A, 0, 0);
    assertEquals(cell527A, simpleGridManager.getCardAt(0, 0));
    assertEquals(GamePlayer.RED, simpleGridManager.getCardAt(0, 0).getOwner());

    simpleGridManager.placeCard(cell7253, 0, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlaceCardInAHole() {
    complexGridManager.placeCard(cell4599, 1, 0);
  }

  @Test
  public void executeBattleNoHole() {
    Object[][] cardPlacements = {
            {cell527A, 0, 0, GamePlayer.RED},
            {cell7253, 1, 0, GamePlayer.BLUE},
            {cell4623, 2, 1, GamePlayer.RED},
            {cell4599, 1, 2, GamePlayer.BLUE},
            {cell27A9, 0, 1, GamePlayer.BLUE},
            {cell2899, 1, 1, GamePlayer.RED}
    };
    GamePlayer[][] expectedGridOwners = {
            {GamePlayer.RED, GamePlayer.BLUE, null, null},
            {GamePlayer.RED, GamePlayer.RED, GamePlayer.BLUE, null},
            {null, GamePlayer.RED, null, null}
    };
    assertGridOwnerState(cardPlacements, expectedGridOwners, noHoleManager);
  }

  @Test
  public void testExecuteBattleSimpleGrid() {
    Object[][] cardPlacements = {
            {cell4623, 1, 0, GamePlayer.RED},
            {cell7253, 2, 0, GamePlayer.BLUE},
            {cell2899, 2, 2, GamePlayer.RED},
            {cell27A9, 2, 3, GamePlayer.BLUE},
            {cell527A, 2, 1, GamePlayer.RED},
            {cell4599, 0, 0, GamePlayer.BLUE}};
    GamePlayer[][] expectedGridOwners = {
            {GamePlayer.BLUE, null, null, null},
            {GamePlayer.BLUE, null, null, null},
            {GamePlayer.RED, GamePlayer.RED, GamePlayer.RED, GamePlayer.BLUE}};
    assertGridOwnerState(cardPlacements, expectedGridOwners, simpleGridManager);
  }

  @Test
  public void testExecuteBattleComplexGrid() {
    Object[][] cardPlacements = {
            {cell4623, 2, 1, GamePlayer.RED},
            {cell7253, 3, 0, GamePlayer.BLUE},
            {cell2899, 0, 0, GamePlayer.RED},
            {cell4599, 3, 2, GamePlayer.BLUE},
            {cell527A, 3, 1, GamePlayer.RED},
            {cell27A9, 0, 3, GamePlayer.BLUE}
    };
    GamePlayer[][] expectedGridOwners = {
            {GamePlayer.RED, null, null, GamePlayer.BLUE, null},
            {null, null, null, null, null},
            {null, GamePlayer.RED, null, null, null},
            {GamePlayer.RED, GamePlayer.RED, GamePlayer.BLUE, null, null}
    };
    assertGridOwnerState(cardPlacements, expectedGridOwners, complexGridManager);
  }

  private void assertGridOwnerState(Object[][] cardPlacements, GamePlayer[][] expectedOwners,
                                    GridManager manager) {
    for (Object[] cardPlacement : cardPlacements) {
      Cell cell = (Cell) cardPlacement[0];
      int row = (int) cardPlacement[1];
      int col = (int) cardPlacement[2];
      GamePlayer expectedOwner = (GamePlayer) cardPlacement[3];
      manager.placeCard(cell, row, col);
      assertEquals(expectedOwner, manager.getCardAt(row, col).getOwner());
      manager.executeBattle(row, col);
    }
    for (int row = 0; row < expectedOwners.length; row++) {
      for (int col = 0; col < expectedOwners[row].length; col++) {
        GamePlayer expectedOwner = expectedOwners[row][col];
        if (expectedOwner != null) {
          assertEquals(expectedOwner, manager.getCardAt(row, col).getOwner());
        }
      }
    }
  }

  @Test
  public void countPlayerCards() {
    assertEquals(0, noHoleManager.countPlayerCards(GamePlayer.RED));
    assertEquals(0, noHoleManager.countPlayerCards(GamePlayer.BLUE));

    noHoleManager.placeCard(cell527A, 0, 0);
    assertEquals(1, noHoleManager.countPlayerCards(GamePlayer.RED));
    assertEquals(0, noHoleManager.countPlayerCards(GamePlayer.BLUE));

    noHoleManager.placeCard(cell7253, 1, 0);
    assertEquals(1, noHoleManager.countPlayerCards(GamePlayer.RED));
    assertEquals(1, noHoleManager.countPlayerCards(GamePlayer.BLUE));

    noHoleManager.placeCard(cell4599, 1, 2);
    assertEquals(1, noHoleManager.countPlayerCards(GamePlayer.RED));
    assertEquals(2, noHoleManager.countPlayerCards(GamePlayer.BLUE));
  }

  @Test
  public void isGameOverNoHole() {
    assertFalse(noHoleManager.isGameOver());
    assertFalse(simpleGridManager.isGameOver());
    assertFalse(complexGridManager.isGameOver());

    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        assertFalse(noHoleManager.isGameOver());
        noHoleManager.placeCard(cell527A, row, col);
      }
    }
    assertTrue(noHoleManager.isGameOver());
  }

  @Test
  public void isGameOverSimpleGrid() {
    Object[][] cardPlacements = new Object[][] {
            {cell4623, 1, 0},
            {cell7253, 2, 0},
            {cell2899, 2, 2},
            {cell27A9, 2, 3},
            {cell527A, 2, 1},
            {cell4599, 0, 0},
            {cell4599, 1, 3},
    };
    for (Object[] cardPlacement : cardPlacements) {
      Cell cell = (Cell) cardPlacement[0];
      int row = (int) cardPlacement[1];
      int col = (int) cardPlacement[2];
      assertFalse(simpleGridManager.isGameOver());
      simpleGridManager.placeCard(cell, row, col);
    }
    assertTrue(simpleGridManager.isGameOver());
  }

  @Test
  public void isGameOverComplexGrid() {
    Object[][] cardPlacements = new Object[][] {
            {cell4623, 2, 1},
            {cell7253, 3, 0},
            {cell2899, 0, 0},
            {cell4599, 3, 2},
            {cell527A, 3, 1},
            {cell27A9, 0, 3},
            {cell27A9, 0, 2},
            {cell27A9, 0, 4},
            {cell27A9, 1, 3},
    };
    for (Object[] cardPlacement : cardPlacements) {
      Cell cell = (Cell) cardPlacement[0];
      int row = (int) cardPlacement[1];
      int col = (int) cardPlacement[2];
      assertFalse(complexGridManager.isGameOver());
      complexGridManager.placeCard(cell, row, col);
    }
    assertTrue(complexGridManager.isGameOver());
  }

  @Test
  public void getGridSimpleGrid() {
    Object[][] cardPlacements = {
            {cell4623, 1, 0},
            {cell7253, 2, 0},
            {cell2899, 2, 2},
            {cell27A9, 2, 3},
            {cell527A, 2, 1},
            {cell4599, 0, 0}
    };
    Cell[][] expectedCardsLayout = {
            {cell4599, null, null, null},
            {cell4623, null, null, null},
            {cell7253, cell527A, cell2899, cell27A9}
    };
    assertGridEquals(cardPlacements, expectedCardsLayout, simpleGridManager);
    // modify grid return from getGrid should not affect the original grid
    simpleGridManager.getGrid()[0][0] = null;
    assertArrayEquals(expectedCardsLayout, simpleGridManager.getGrid());
  }

  @Test
  public void getGridNoHoles() {
    Object[][] cardPlacements = {
            {cell527A, 0, 0},
            {cell7253, 1, 0},
            {cell4623, 2, 1},
            {cell4599, 1, 2},
            {cell27A9, 0, 1},
            {cell2899, 1, 1}
    };
    Cell[][] expectedCardsLayout = {
            {cell527A, cell27A9, null},
            {cell7253, cell2899, cell4599},
            {null, cell4623, null}
    };
    assertGridEquals(cardPlacements, expectedCardsLayout, noHoleManager);
    // modify grid return from getGrid should not affect the original grid
    noHoleManager.getGrid()[0][0] = null;
    assertArrayEquals(expectedCardsLayout, noHoleManager.getGrid());
  }

  @Test
  public void getGridComplexGrid() {
    Object[][] cardPlacements = {
            {cell4623, 2, 1},
            {cell7253, 3, 0},
            {cell2899, 0, 0},
            {cell4599, 3, 2},
            {cell527A, 3, 1},
            {cell27A9, 0, 3}
    };
    Cell[][] expectedCardsLayout = {
            {cell2899, null, null, cell27A9, null},
            {null, null, null, null, null},
            {null, cell4623, null, null, null},
            {cell7253, cell527A, cell4599, null, null}
    };
    assertGridEquals(cardPlacements, expectedCardsLayout, complexGridManager);
    // modify grid return from getGrid should not affect the original grid
    complexGridManager.getGrid()[0][0] = null;
    assertArrayEquals(expectedCardsLayout, complexGridManager.getGrid());
  }

  private void assertGridEquals(Object[][] cardPlacements, Cell[][] expectedCardsLayout,
                                GridManager manager) {
    for (Object[] cardPlacement : cardPlacements) {
      Cell cell = (Cell) cardPlacement[0];
      int row = (int) cardPlacement[1];
      int col = (int) cardPlacement[2];
      manager.placeCard(cell, row, col);
    }
    assertArrayEquals(expectedCardsLayout, manager.getGrid());
  }

  @Test
  public void getCellTypes() {
    assertSameCellTypeGridWithModification(new CellType[][] {
            {CellType.CELL, CellType.CELL, CellType.CELL},
            {CellType.CELL, CellType.CELL, CellType.CELL},
            {CellType.CELL, CellType.CELL, CellType.CELL},
    }, noHoleManager);


    assertSameCellTypeGridWithModification(new CellType[][]{
            {CellType.CELL, CellType.HOLE, CellType.HOLE, CellType.HOLE},
            {CellType.CELL, CellType.HOLE, CellType.HOLE, CellType.CELL},
            {CellType.CELL, CellType.CELL, CellType.CELL, CellType.CELL},
    }, simpleGridManager);


    assertSameCellTypeGridWithModification(new CellType[][]{
            {CellType.CELL, CellType.HOLE, CellType.CELL, CellType.CELL, CellType.CELL},
            {CellType.HOLE, CellType.HOLE, CellType.HOLE, CellType.CELL, CellType.HOLE},
            {CellType.HOLE, CellType.CELL, CellType.HOLE, CellType.HOLE, CellType.HOLE},
            {CellType.CELL, CellType.CELL, CellType.CELL, CellType.HOLE, CellType.HOLE}
    }, complexGridManager);
  }

  @Test
  public void testUnitBattleOneCard() {
    noHoleManager.placeCard(cell527A, 1, 1);
    noHoleManager.executeBattle(1, 1);
    Cell[][] expectedCardsLayout = {
            {null, null, null},
            {null, cell527A, null},
            {null, null, null}
    };
    assertArrayEquals(expectedCardsLayout, noHoleManager.getGrid());
  }

  @Test
  public void battleTwoCardsOwnerDoesNotSwitchToBlue() {
    noHoleManager.placeCard(cell527A, 1, 1); // red card
    noHoleManager.placeCard(cell7253, 1, 0); // blue card (smaller than red)
    noHoleManager.executeBattle(1, 0);
    Cell[][] expectedCardsLayout = {
            {null, null, null},
            {cell7253, cell527A, null},
            {null, null, null}
    };
    assertArrayEquals(expectedCardsLayout, noHoleManager.getGrid());
    assertEquals(GamePlayer.BLUE, noHoleManager.getCardAt(1, 0).getOwner());
    assertEquals(GamePlayer.RED, noHoleManager.getCardAt(1, 1).getOwner());
  }

  @Test
  public void battleTwoCardsOwnerSwitchToRed() {
    noHoleManager.placeCard(cell7253, 1, 0); // blue card
    noHoleManager.placeCard(cell527A, 1, 1); // red card
    noHoleManager.executeBattle(1, 1);
    Cell[][] expectedCardsLayout = {
            {null, null, null},
            {cell7253, cell527A, null},
            {null, null, null}
    };
    assertArrayEquals(expectedCardsLayout, noHoleManager.getGrid());
    assertEquals(GamePlayer.RED, noHoleManager.getCardAt(1, 0).getOwner());
    assertEquals(GamePlayer.RED, noHoleManager.getCardAt(1, 1).getOwner());
  }

  @Test
  public void battleTwoCardsOwnerSwitchToBlue() {
    noHoleManager.placeCard(cell527A, 0, 2);
    noHoleManager.placeCard(cell7253, 1, 2);
    noHoleManager.executeBattle(1, 2);
    Cell[][] expectedCardsLayout = {
            {null, null, cell527A},
            {null, null, cell7253},
            {null, null, null}
    };
    assertArrayEquals(expectedCardsLayout, noHoleManager.getGrid());
    assertEquals(GamePlayer.BLUE, noHoleManager.getCardAt(0, 2).getOwner());
    assertEquals(GamePlayer.BLUE, noHoleManager.getCardAt(1, 2).getOwner());
  }

  @Test
  public void battleTwoCardsTieAttackValue() {
    noHoleManager.placeCard(cell2899, 0, 1);
    noHoleManager.placeCard(cell27A9, 0, 2);
    noHoleManager.executeBattle(0, 2);
    Cell[][] expectedCardsLayout = {
            {null, cell2899, cell27A9},
            {null, null, null},
            {null, null, null}
    };
    assertArrayEquals(expectedCardsLayout, noHoleManager.getGrid());
    assertEquals(GamePlayer.RED, noHoleManager.getCardAt(0, 1).getOwner());
    assertEquals(GamePlayer.BLUE, noHoleManager.getCardAt(0, 2).getOwner());
  }

  @Test
  public void battleTwoCardsApartFromEachOther() {
    noHoleManager.placeCard(cell2899, 0, 0);
    noHoleManager.placeCard(cell27A9, 0, 2);
    noHoleManager.executeBattle(0, 2);
    Cell[][] expectedCardsLayout = {
            {cell2899, null, cell27A9},
            {null, null, null},
            {null, null, null}
    };
    assertArrayEquals(expectedCardsLayout, noHoleManager.getGrid());
    assertEquals(GamePlayer.RED, noHoleManager.getCardAt(0, 0).getOwner());
    assertEquals(GamePlayer.BLUE, noHoleManager.getCardAt(0, 2).getOwner());
  }

  @Test
  public void battleTwoCardsDiagonal() {
    noHoleManager.placeCard(cell2899, 0, 0);
    noHoleManager.placeCard(cell27A9, 1, 1);
    noHoleManager.executeBattle(1, 1);
    Cell[][] expectedCardsLayout = {
            {cell2899, null, null},
            {null, cell27A9, null},
            {null, null, null}
    };
    assertArrayEquals(expectedCardsLayout, noHoleManager.getGrid());
    assertEquals(GamePlayer.RED, noHoleManager.getCardAt(0, 0).getOwner());
    assertEquals(GamePlayer.BLUE, noHoleManager.getCardAt(1, 1).getOwner());
  }

  @Test
  public void battleTwoCardsSameColor() {
    noHoleManager.placeCard(cell4623, 1, 1);
    noHoleManager.placeCard(cell7253, 0, 0);
    noHoleManager.placeCard(cell2899, 1, 0);
    noHoleManager.executeBattle(1, 1);
    Cell[][] expectedCardsLayout = {
            {cell7253, null, null},
            {cell2899, cell4623, null},
            {null, null, null}
    };
    assertArrayEquals(expectedCardsLayout, noHoleManager.getGrid());
    assertEquals(GamePlayer.BLUE, noHoleManager.getCardAt(0, 0).getOwner());
    assertEquals(GamePlayer.RED, noHoleManager.getCardAt(1, 0).getOwner());
    assertEquals(GamePlayer.RED, noHoleManager.getCardAt(1, 1).getOwner());
  }

  private void assertSameCellTypeGridWithModification(CellType[][] expectedCellTypes,
                                                      GridManager manager) {
    assertArrayEquals(expectedCellTypes, manager.getCellTypes());
    // modify cell types return from getCellTypes should not affect the original grid
    manager.getCellTypes()[0][0] = CellType.HOLE;
    assertArrayEquals(expectedCellTypes, manager.getCellTypes());
  }

  @Test
  public void getWidthAndHeight() {
    assertEquals(noHoleManager.getWidth(), 3);
    assertEquals(noHoleManager.getHeight(), 3);

    assertEquals(simpleGridManager.getWidth(), 4);
    assertEquals(simpleGridManager.getHeight(), 3);

    assertEquals(complexGridManager.getWidth(), 5);
    assertEquals(complexGridManager.getHeight(), 4);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetCardAtNoCard() {
    noHoleManager.getCardAt(0, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetOwnerAtNoCard() {
    noHoleManager.getOwnerAt(0, 0);
  }

  @Test
  public void testGetCardAt() {
    noHoleManager.placeCard(cell527A, 0, 0);
    assertEquals(noHoleManager.getCardAt(0, 0), cell527A);
  }

  @Test
  public void testGetOwnerAt() {
    noHoleManager.placeCard(cell527A, 0, 0);
    assertEquals(noHoleManager.getOwnerAt(0, 0), cell527A.getOwner());
  }

  @Test(expected = IllegalStateException.class)
  public void countCardFlipCellOccupied() {
    assertEquals(noHoleManager.countCardFlip(cell4623, 0, 0), 0);
    noHoleManager.placeCard(cell4623, 0, 0);
    noHoleManager.countCardFlip(cell2899, 0, 0);
  }

  @Test
  public void countCardFlipTwoCards() {
    assertEquals(noHoleManager.countCardFlip(cell4623, 0, 0), 0);
    noHoleManager.placeCard(cell4623, 0, 0);
    // cannot flip color since two cards are the same color
    assertEquals(noHoleManager.countCardFlip(cell2899, 0, 1), 0);

    // can flip since this card is not same color and attack value is larger
    assertEquals(noHoleManager.countCardFlip(cell27A9, 0, 1), 1);
  }

  @Test
  public void countCardFlipDiagonalDifferentColors() {
    assertEquals(noHoleManager.countCardFlip(cell4623, 0, 0), 0);
    noHoleManager.placeCard(cell4623, 0, 0);
    assertEquals(noHoleManager.countCardFlip(cell27A9, 1, 1), 0);
  }

  @Test
  public void countCardFlipNoFlipSmallerOrEqual() {
    noHoleManager.placeCard(cell2899, 0, 0);
    // two cards are tied in attack values
    assertEquals(noHoleManager.countCardFlip(cell27A9, 0, 1), 0);
    // the to-be-placed card's attack value is less than that of card in grid
    assertEquals(noHoleManager.countCardFlip(cell7253, 0, 1), 0);
  }

  @Test
  public void flipMoreThanOneCard() {
    noHoleManager.placeCard(cell4623, 0, 0);
    noHoleManager.placeCard(cell2899, 0, 1);
    noHoleManager.placeCard(cell527A, 0, 2);
    assertEquals(noHoleManager.countCardFlip(cell7253, 1, 2), 3);
  }
}