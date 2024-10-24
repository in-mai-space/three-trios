package model.components.manager;

import org.junit.Before;
import org.junit.Test;

import model.GameConfigParser;
import model.GameConfigParserTest;
import model.components.card.Card;
import model.components.card.ThreeTriosCard;
import model.components.enums.AttackValue;
import model.components.enums.CellType;
import model.components.enums.GamePlayer;
import model.components.grid.Grid;
import model.components.grid.ThreeTriosGrid;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test class for ThreeTriosGridManager.
 */
public class ThreeTriosGridManagerTest {
  private Grid noHoleGrid;
  private Grid simpleGrid;
  private Grid complexGrid;
  private GridManager noHoleManager;
  private GridManager simpleGridManager;
  private GridManager complexGridManager;
  private Card card527A;
  private Card card7253;
  private Card card4599;
  private Card card4623;
  private Card card2899;
  private Card card27A9;

  @Before
  public void setUp() {
    String noHoleFilePath = GameConfigParserTest.getFilePath("no_holes.txt", "grid");
    noHoleGrid = new ThreeTriosGrid(GameConfigParser.getCellTypes(noHoleFilePath));

    String simpleGridFilePath = GameConfigParserTest.getFilePath("simple_grid.txt", "grid");
    simpleGrid = new ThreeTriosGrid(GameConfigParser.getCellTypes(simpleGridFilePath));

    String complexGridFilePath = GameConfigParserTest.getFilePath("complex_grid.txt", "grid");
    complexGrid = new ThreeTriosGrid(GameConfigParser.getCellTypes(complexGridFilePath));

    noHoleManager = new ThreeTriosGridManager(noHoleGrid);
    simpleGridManager = new ThreeTriosGridManager(simpleGrid);
    complexGridManager = new ThreeTriosGridManager(complexGrid);

    card527A = new ThreeTriosCard(new AttackValue[]{AttackValue.FIVE, AttackValue.TWO,
            AttackValue.SEVEN, AttackValue.A}, GamePlayer.RED);
    card7253 = new ThreeTriosCard(new AttackValue[]{AttackValue.SEVEN, AttackValue.TWO,
            AttackValue.FIVE, AttackValue.THREE}, GamePlayer.BLUE);
    card4599 = new ThreeTriosCard(new AttackValue[]{AttackValue.FOUR, AttackValue.FIVE,
            AttackValue.NINE, AttackValue.NINE}, GamePlayer.BLUE);
    card4623 = new ThreeTriosCard(new AttackValue[]{AttackValue.FOUR, AttackValue.SIX,
            AttackValue.TWO, AttackValue.THREE}, GamePlayer.RED);
    card2899 = new ThreeTriosCard(new AttackValue[]{AttackValue.TWO, AttackValue.EIGHT,
            AttackValue.NINE, AttackValue.NINE}, GamePlayer.RED);
    card27A9 = new ThreeTriosCard(new AttackValue[]{AttackValue.TWO, AttackValue.SEVEN,
            AttackValue.A, AttackValue.NINE}, GamePlayer.BLUE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullConstructor() {
    new ThreeTriosGridManager(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOutOfBoundPlaceCard() {
    noHoleManager.placeCard(card527A, 3, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeIndexPlaceCard() {
    noHoleManager.placeCard(card527A, -1, 0);
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
    noHoleManager.placeCard(card527A, 0, 0);
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
    simpleGridManager.placeCard(card527A, 0, 0);
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
    complexGridManager.placeCard(card527A, 0, 0);
    assertFalse(complexGridManager.canPlaceCard(0, 0));
  }

  @Test
  public void testPlaceCardNoHoles() {
    noHoleManager.placeCard(card527A, 0, 0);
    assertEquals(card527A, noHoleGrid.getCardAt(0, 0));
    assertEquals(GamePlayer.RED, noHoleGrid.getCardAt(0, 0).getOwner());

    noHoleManager.placeCard(card7253, 1, 0);
    assertEquals(card7253, noHoleGrid.getCardAt(1, 0));
    assertEquals(GamePlayer.BLUE, noHoleGrid.getCardAt(1, 0).getOwner());

    noHoleManager.placeCard(card4599, 1, 2);
    assertEquals(card4599, noHoleGrid.getCardAt(1, 2));
    assertEquals(GamePlayer.BLUE, noHoleGrid.getCardAt(1, 2).getOwner());
  }

  @Test
  public void testPlaceCardSimpleGrid() {
    simpleGridManager.placeCard(card527A, 0, 0);
    assertEquals(card527A, simpleGrid.getCardAt(0, 0));
    assertEquals(GamePlayer.RED, simpleGrid.getCardAt(0, 0).getOwner());

    simpleGridManager.placeCard(card7253, 1, 0);
    assertEquals(card7253, simpleGrid.getCardAt(1, 0));
    assertEquals(GamePlayer.BLUE, simpleGrid.getCardAt(1, 0).getOwner());

    simpleGridManager.placeCard(card4599, 1, 3);
    assertEquals(card4599, simpleGrid.getCardAt(1, 3));
    assertEquals(GamePlayer.BLUE, simpleGrid.getCardAt(1, 3).getOwner());
  }

  @Test
  public void testPlaceCardComplexGrid() {
    complexGridManager.placeCard(card527A, 0, 0);
    assertEquals(card527A, complexGrid.getCardAt(0, 0));
    assertEquals(GamePlayer.RED, complexGrid.getCardAt(0, 0).getOwner());

    complexGridManager.placeCard(card7253, 0, 4);
    assertEquals(card7253, complexGrid.getCardAt(0, 4));
    assertEquals(GamePlayer.BLUE, complexGrid.getCardAt(0, 4).getOwner());

    complexGridManager.placeCard(card4599, 3, 2);
    assertEquals(card4599, complexGrid.getCardAt(3, 2));
    assertEquals(GamePlayer.BLUE, complexGrid.getCardAt(3, 2).getOwner());
  }

  @Test(expected = IllegalStateException.class)
  public void testPlaceCardWhenThereIsCard() {
    simpleGridManager.placeCard(card527A, 0, 0);
    assertEquals(card527A, simpleGrid.getCardAt(0, 0));
    assertEquals(GamePlayer.RED, simpleGrid.getCardAt(0, 0).getOwner());

    simpleGridManager.placeCard(card7253, 0, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlaceCardInAHole() {
    complexGridManager.placeCard(card4599, 1, 0);
  }

  @Test
  public void executeBattleNoHole() {
    Object[][] cardPlacements = {
            {card527A, 0, 0, GamePlayer.RED},
            {card7253, 1, 0, GamePlayer.BLUE},
            {card4623, 2, 1, GamePlayer.RED},
            {card4599, 1, 2, GamePlayer.BLUE},
            {card27A9, 0, 1, GamePlayer.BLUE},
            {card2899, 1, 1, GamePlayer.RED}
    };
    GamePlayer[][] expectedGridOwners = {
            {GamePlayer.RED, GamePlayer.BLUE, null, null},
            {GamePlayer.RED, GamePlayer.RED, GamePlayer.BLUE, null},
            {null, GamePlayer.RED, null, null}
    };
    assertGridOwnerState(cardPlacements, expectedGridOwners, noHoleGrid, noHoleManager);
  }

  @Test
  public void testExecuteBattleSimpleGrid() {
      Object[][] cardPlacements = {
              {card4623, 1, 0, GamePlayer.RED},
              {card7253, 2, 0, GamePlayer.BLUE},
              {card2899, 2, 2, GamePlayer.RED},
              {card27A9, 2, 3, GamePlayer.BLUE},
              {card527A, 2, 1, GamePlayer.RED},
              {card4599, 0, 0, GamePlayer.BLUE}
    };
    GamePlayer[][] expectedGridOwners = {
            {GamePlayer.BLUE, null, null, null},
            {GamePlayer.BLUE, null, null, null},
            {GamePlayer.RED, GamePlayer.RED, GamePlayer.RED, GamePlayer.BLUE}
    };
    assertGridOwnerState(cardPlacements, expectedGridOwners, simpleGrid, simpleGridManager);
  }

  @Test
  public void testExecuteBattleComplexGrid() {
    Object[][] cardPlacements = {
            {card4623, 2, 1, GamePlayer.RED},
            {card7253, 3, 0, GamePlayer.BLUE},
            {card2899, 0, 0, GamePlayer.RED},
            {card4599, 3, 2, GamePlayer.BLUE},
            {card527A, 3, 1, GamePlayer.RED},
            {card27A9, 0, 3, GamePlayer.BLUE}
    };
    GamePlayer[][] expectedGridOwners = {
            {GamePlayer.RED, null, null, GamePlayer.BLUE, null},
            {null, null, null, null, null},
            {null, GamePlayer.RED, null, null, null},
            {GamePlayer.RED, GamePlayer.RED, GamePlayer.BLUE, null, null}
    };
    assertGridOwnerState(cardPlacements, expectedGridOwners, complexGrid, complexGridManager);
  }

  private void assertGridOwnerState(Object[][] cardPlacements, GamePlayer[][] expectedOwners,
                                    Grid grid, GridManager manager) {
    for (Object[] cardPlacement : cardPlacements) {
      Card card = (Card) cardPlacement[0];
      int row = (int) cardPlacement[1];
      int col = (int) cardPlacement[2];
      GamePlayer expectedOwner = (GamePlayer) cardPlacement[3];
      manager.placeCard(card, row, col);
      assertEquals(expectedOwner, grid.getCardAt(row, col).getOwner());
      manager.executeBattle(row, col);
    }
    for (int row = 0; row < expectedOwners.length; row++) {
      for (int col = 0; col < expectedOwners[row].length; col++) {
        GamePlayer expectedOwner = expectedOwners[row][col];
        if (expectedOwner != null) {
          assertEquals(expectedOwner, grid.getCardAt(row, col).getOwner());
        }
      }
    }
  }

  @Test
  public void countPlayerCards() {
    assertEquals(0, noHoleManager.countPlayerCards(GamePlayer.RED));
    assertEquals(0, noHoleManager.countPlayerCards(GamePlayer.BLUE));

    noHoleManager.placeCard(card527A, 0, 0);
    assertEquals(1, noHoleManager.countPlayerCards(GamePlayer.RED));
    assertEquals(0, noHoleManager.countPlayerCards(GamePlayer.BLUE));

    noHoleManager.placeCard(card7253, 1, 0);
    assertEquals(1, noHoleManager.countPlayerCards(GamePlayer.RED));
    assertEquals(1, noHoleManager.countPlayerCards(GamePlayer.BLUE));

    noHoleManager.placeCard(card4599, 1, 2);
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
        noHoleManager.placeCard(card527A, row, col);
      }
    }
    assertTrue(noHoleManager.isGameOver());
  }

  @Test
  public void isGameOverSimpleGrid() {
    Object[][] cardPlacements = new Object[][] {
            {card4623, 1, 0},
            {card7253, 2, 0},
            {card2899, 2, 2},
            {card27A9, 2, 3},
            {card527A, 2, 1},
            {card4599, 0, 0},
            {card4599, 1, 3},
    };
    for (Object[] cardPlacement : cardPlacements) {
      Card card = (Card) cardPlacement[0];
      int row = (int) cardPlacement[1];
      int col = (int) cardPlacement[2];
      assertFalse(simpleGridManager.isGameOver());
      simpleGridManager.placeCard(card, row, col);
    }
    assertTrue(simpleGridManager.isGameOver());
  }

  @Test
  public void isGameOverComplexGrid() {
    Object[][] cardPlacements = new Object[][] {
            {card4623, 2, 1},
            {card7253, 3, 0},
            {card2899, 0, 0},
            {card4599, 3, 2},
            {card527A, 3, 1},
            {card27A9, 0, 3},
            {card27A9, 0, 2},
            {card27A9, 0, 4},
            {card27A9, 1, 3},
    };
    for (Object[] cardPlacement : cardPlacements) {
      Card card = (Card) cardPlacement[0];
      int row = (int) cardPlacement[1];
      int col = (int) cardPlacement[2];
      assertFalse(complexGridManager.isGameOver());
      complexGridManager.placeCard(card, row, col);
    }
    assertTrue(complexGridManager.isGameOver());
  }

  @Test
  public void getGridSimpleGrid() {
    Object[][] cardPlacements = {
            {card4623, 1, 0},
            {card7253, 2, 0},
            {card2899, 2, 2},
            {card27A9, 2, 3},
            {card527A, 2, 1},
            {card4599, 0, 0}
    };
    Card[][] expectedCardsLayout = {
            {card4599, null, null, null},
            {card4623, null, null, null},
            {card7253, card527A, card2899, card27A9}
    };
    assertGridEquals(cardPlacements, expectedCardsLayout, simpleGridManager);
    // modify grid return from getGrid should not affect the original grid
    simpleGridManager.getGrid()[0][0] = null;
    assertArrayEquals(expectedCardsLayout, simpleGridManager.getGrid());
  }

  @Test
  public void getGridNoHoles() {
    Object[][] cardPlacements = {
            {card527A, 0, 0},
            {card7253, 1, 0},
            {card4623, 2, 1},
            {card4599, 1, 2},
            {card27A9, 0, 1},
            {card2899, 1, 1}
    };
    Card[][] expectedCardsLayout = {
            {card527A, card27A9, null},
            {card7253, card2899, card4599},
            {null, card4623, null}
    };
    assertGridEquals(cardPlacements, expectedCardsLayout, noHoleManager);
    // modify grid return from getGrid should not affect the original grid
    noHoleManager.getGrid()[0][0] = null;
    assertArrayEquals(expectedCardsLayout, noHoleManager.getGrid());
  }

  @Test
  public void getGridComplexGrid() {
    Object[][] cardPlacements = {
            {card4623, 2, 1},
            {card7253, 3, 0},
            {card2899, 0, 0},
            {card4599, 3, 2},
            {card527A, 3, 1},
            {card27A9, 0, 3}
    };
    Card[][] expectedCardsLayout = {
            {card2899, null, null, card27A9, null},
            {null, null, null, null, null},
            {null, card4623, null, null, null},
            {card7253, card527A, card4599, null, null}
    };
    assertGridEquals(cardPlacements, expectedCardsLayout, complexGridManager);
    // modify grid return from getGrid should not affect the original grid
    complexGridManager.getGrid()[0][0] = null;
    assertArrayEquals(expectedCardsLayout, complexGridManager.getGrid());
  }

  private void assertGridEquals(Object[][] cardPlacements, Card[][] expectedCardsLayout,
                                GridManager manager) {
    for (Object[] cardPlacement : cardPlacements) {
      Card card = (Card) cardPlacement[0];
      int row = (int) cardPlacement[1];
      int col = (int) cardPlacement[2];
      manager.placeCard(card, row, col);
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
    noHoleManager.placeCard(card527A, 1, 1);
    noHoleManager.executeBattle(1, 1);
    Card[][] expectedCardsLayout = {
            {null, null, null},
            {null, card527A, null},
            {null, null, null}
    };
    assertArrayEquals(expectedCardsLayout, noHoleManager.getGrid());
  }

  @Test
  public void battleTwoCardsOwnerDoesNotSwitchToBlue() {
    noHoleManager.placeCard(card527A, 1, 1); // red card
    noHoleManager.placeCard(card7253, 1, 0); // blue card (smaller than red)
    noHoleManager.executeBattle(1, 0);
    Card[][] expectedCardsLayout = {
            {null, null, null},
            {card7253, card527A, null},
            {null, null, null}
    };
    assertArrayEquals(expectedCardsLayout, noHoleManager.getGrid());
    assertEquals(GamePlayer.BLUE, noHoleGrid.getCardAt(1, 0).getOwner());
    assertEquals(GamePlayer.RED, noHoleGrid.getCardAt(1, 1).getOwner());
  }

  @Test
  public void battleTwoCardsOwnerSwitchToRed() {
    noHoleManager.placeCard(card7253, 1, 0); // blue card
    noHoleManager.placeCard(card527A, 1, 1); // red card
    noHoleManager.executeBattle(1, 1);
    Card[][] expectedCardsLayout = {
            {null, null, null},
            {card7253, card527A, null},
            {null, null, null}
    };
    assertArrayEquals(expectedCardsLayout, noHoleManager.getGrid());
    assertEquals(GamePlayer.RED, noHoleGrid.getCardAt(1, 0).getOwner());
    assertEquals(GamePlayer.RED, noHoleGrid.getCardAt(1, 1).getOwner());
  }

  @Test
  public void battleTwoCardsOwnerSwitchToBlue() {
    noHoleManager.placeCard(card527A, 0, 2);
    noHoleManager.placeCard(card7253, 1, 2);
    noHoleManager.executeBattle(1, 2);
    Card[][] expectedCardsLayout = {
            {null, null, card527A},
            {null, null, card7253},
            {null, null, null}
    };
    assertArrayEquals(expectedCardsLayout, noHoleManager.getGrid());
    assertEquals(GamePlayer.BLUE, noHoleGrid.getCardAt(0, 2).getOwner());
    assertEquals(GamePlayer.BLUE, noHoleGrid.getCardAt(1, 2).getOwner());
  }

  @Test
  public void battleTwoCardsTieAttackValue() {
    noHoleManager.placeCard(card2899, 0, 1);
    noHoleManager.placeCard(card27A9, 0, 2);
    noHoleManager.executeBattle(0, 2);
    Card[][] expectedCardsLayout = {
            {null, card2899, card27A9},
            {null, null, null},
            {null, null, null}
    };
    assertArrayEquals(expectedCardsLayout, noHoleManager.getGrid());
    assertEquals(GamePlayer.RED, noHoleGrid.getCardAt(0, 1).getOwner());
    assertEquals(GamePlayer.BLUE, noHoleGrid.getCardAt(0, 2).getOwner());
  }

  @Test
  public void battleTwoCardsApartFromEachOther() {
    noHoleManager.placeCard(card2899, 0, 0);
    noHoleManager.placeCard(card27A9, 0, 2);
    noHoleManager.executeBattle(0, 2);
    Card[][] expectedCardsLayout = {
            {card2899, null, card27A9},
            {null, null, null},
            {null, null, null}
    };
    assertArrayEquals(expectedCardsLayout, noHoleManager.getGrid());
    assertEquals(GamePlayer.RED, noHoleGrid.getCardAt(0, 0).getOwner());
    assertEquals(GamePlayer.BLUE, noHoleGrid.getCardAt(0, 2).getOwner());
  }

  @Test
  public void battleTwoCardsDiagonal() {
    noHoleManager.placeCard(card2899, 0, 0);
    noHoleManager.placeCard(card27A9, 1, 1);
    noHoleManager.executeBattle(1, 1);
    Card[][] expectedCardsLayout = {
            {card2899, null, null},
            {null, card27A9, null},
            {null, null, null}
    };
    assertArrayEquals(expectedCardsLayout, noHoleManager.getGrid());
    assertEquals(GamePlayer.RED, noHoleGrid.getCardAt(0, 0).getOwner());
    assertEquals(GamePlayer.BLUE, noHoleGrid.getCardAt(1, 1).getOwner());
  }

  @Test
  public void battleTwoCardsSameColor() {
    noHoleManager.placeCard(card4623, 1, 1);
    noHoleManager.placeCard(card7253, 0, 0);
    noHoleManager.placeCard(card2899, 1, 0);
    noHoleManager.executeBattle(1, 1);
    Card[][] expectedCardsLayout = {
            {card7253, null, null},
            {card2899, card4623, null},
            {null, null, null}
    };
    assertArrayEquals(expectedCardsLayout, noHoleManager.getGrid());
    assertEquals(GamePlayer.BLUE, noHoleGrid.getCardAt(0, 0).getOwner());
    assertEquals(GamePlayer.RED, noHoleGrid.getCardAt(1, 0).getOwner());
    assertEquals(GamePlayer.RED, noHoleGrid.getCardAt(1, 1).getOwner());
  }

  private void assertSameCellTypeGridWithModification(CellType[][] expectedCellTypes,
                                                      GridManager manager) {
    assertArrayEquals(expectedCellTypes, manager.getCellTypes());
    // modify cell types return from getCellTypes should not affect the original grid
    manager.getCellTypes()[0][0] = CellType.HOLE;
    assertArrayEquals(expectedCellTypes, manager.getCellTypes());
  }
}