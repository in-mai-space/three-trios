package model.implementation;

import org.junit.Before;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.Map;

import controller.GameConfigParser;
import model.Utils;
import model.enums.AttackValue;
import model.interfaces.Cell;
import model.enums.CellType;
import model.enums.GamePlayer;
import model.interfaces.Grid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Test class for ThreeTriosGrid.
 */
public class ThreeTriosGridTest {
  private CellType[][] simpleGridCellTypes;
  private CellType[][] complexGridCellTypes;
  private ThreeTriosGrid noHoleGrid;
  private ThreeTriosGrid simpleGrid;
  private ThreeTriosGrid complexGrid;
  private Cell cell527A;
  private Cell cell7253;
  private Cell cell4599;
  private Cell cell4623;
  private Cell cell2899;
  private Cell cell27A9;

  @Before
  public void setUp() {
    String noHoleFilePath = Utils.getFilePath("no_holes.txt", "grid");
    CellType[][] noHoleGridCellTypes = GameConfigParser.getCellTypes(noHoleFilePath);

    String simpleGridFilePath = Utils.getFilePath("simple_grid.txt", "grid");
    simpleGridCellTypes = GameConfigParser.getCellTypes(simpleGridFilePath);

    String complexGridFilePath = Utils.getFilePath("complex_grid.txt", "grid");
    complexGridCellTypes = GameConfigParser.getCellTypes(complexGridFilePath);

    noHoleGrid = new ThreeTriosGrid(noHoleGridCellTypes);
    simpleGrid = new ThreeTriosGrid(simpleGridCellTypes);
    complexGrid = new ThreeTriosGrid(complexGridCellTypes);

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
  public void testConstructorNull() {
    new ThreeTriosGrid(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorEven() {
    CellType[][] invalidCellList = new CellType[][]{
            {CellType.CELL, CellType.HOLE},
            {CellType.HOLE, CellType.CELL}};
    new ThreeTriosGrid(invalidCellList);
  }

  @Test
  public void testGetNumberOfCells() {
    assertEquals(9, noHoleGrid.getNumberOfCells());
    assertEquals(7, simpleGrid.getNumberOfCells());
    assertEquals(9, complexGrid.getNumberOfCells());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsCellEmptyInvalidRow() {
    noHoleGrid.isCellEmpty(3, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsCellEmptyInvalidCol() {
    noHoleGrid.isCellEmpty(0, -1);
  }

  @Test
  public void testIsCellEmptyNoHole() {
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        assertTrue(noHoleGrid.isCellEmpty(row, col));
      }
    }
    noHoleGrid.placeCard(cell527A, 0, 0);
    assertFalse(noHoleGrid.isCellEmpty(0, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCellTypeInvalidRow() {
    simpleGrid.getCellType(-1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCellTypeInvalidCol() {
    noHoleGrid.getCellType(2, 4);
  }

  @Test
  public void testGetCellType() {
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        assertEquals(CellType.CELL, noHoleGrid.getCellType(row, col));
      }
    }
    assertEquals(CellType.CELL, simpleGrid.getCellType(0, 0));
    assertEquals(CellType.HOLE, simpleGrid.getCellType(0, 1));
    assertEquals(CellType.CELL, complexGrid.getCellType(0, 4));
    assertEquals(CellType.HOLE, complexGrid.getCellType(1, 4));
  }

  @Test
  public void testGetGridModification() {
    Cell[][] gridCopy = noHoleGrid.getGrid();
    assertNull(gridCopy[0][0]);
    gridCopy[0][0] = cell527A; // modify this array
    // check that adding card to copy didn't change original
    assertTrue(noHoleGrid.canPlaceCard(0, 0));
    noHoleGrid.placeCard(cell2899, 0,0);
    assertEquals(cell2899.getOwner(), GamePlayer.RED);
    noHoleGrid.getGrid()[0][0].setOwner(GamePlayer.BLUE);
    // check that modifying owner of a copy of grid does not change actual data
    assertEquals(cell2899.getOwner(), GamePlayer.RED);
  }

  @Test
  public void testGetGridCopy() {
    Grid copy = noHoleGrid.getCopy();
    assertEquals(copy.getGrid(), noHoleGrid.getGrid());
    copy.placeCard(cell2899, 0, 0);
    // changing the copy does not affect the original grid
    assertEquals(copy.getCardAt(0, 0), cell2899);
    assertNull(noHoleGrid.getGrid()[0][0]);

    noHoleGrid.placeCard(cell2899.getCopy(), 0, 0);
    copy.getCardAt(0, 0).setOwner(GamePlayer.BLUE);
    assertEquals(noHoleGrid.getCardAt(0, 0).getOwner(), GamePlayer.RED);
  }

  @Test
  public void testGetGridNoHoleGrid() {
    assertEquals(3, noHoleGrid.getGrid().length);
    assertEquals(3, noHoleGrid.getGrid()[0].length);
    noHoleGrid.placeCard(cell527A, 1, 1);
    assertEquals(cell527A, noHoleGrid.getGrid()[1][1]);
  }

  @Test
  public void testGetGridSimpleGrid() {
    assertEquals(3, simpleGrid.getGrid().length);
    assertEquals(4, simpleGrid.getGrid()[0].length);
    simpleGrid.placeCard(cell2899, 1, 3);
    assertEquals(cell2899, simpleGrid.getGrid()[1][3]);
  }

  @Test
  public void testGetGridComplexGrid() {
    assertEquals(4, complexGrid.getGrid().length);
    assertEquals(5, complexGrid.getGrid()[0].length);
    complexGrid.placeCard(cell4599, 2, 1);
    assertEquals(cell4599, complexGrid.getGrid()[2][1]);
  }

  @Test
  public void testGetCellTypesGridModification() {
    CellType[][] gridCopy = noHoleGrid.getCellTypesGrid();
    assertEquals(CellType.CELL, gridCopy[1][1]);
    gridCopy[1][1] = CellType.HOLE; // modify this 2D array
    // check that adding hole to copy didn't change original
    assertEquals(CellType.CELL, noHoleGrid.getCellTypesGrid()[1][1]);
  }

  @Test
  public void testGetCellTypesGridNoHoleGrid() {
    assertEquals(3, noHoleGrid.getCellTypesGrid().length);
    assertEquals(3, noHoleGrid.getCellTypesGrid()[0].length);
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        assertEquals(CellType.CELL, noHoleGrid.getCellTypesGrid()[row][col]);
      }
    }
  }

  @Test
  public void testGetCellTypesGridSimpleGrid() {
    assertEquals(3, simpleGrid.getCellTypesGrid().length);
    assertEquals(4, simpleGrid.getCellTypesGrid()[0].length);
    assertEquals(CellType.CELL, simpleGrid.getCellTypesGrid()[0][0]);
    assertEquals(CellType.CELL, simpleGrid.getCellTypesGrid()[1][0]);
    assertEquals(CellType.HOLE, simpleGrid.getCellTypesGrid()[0][1]);
  }

  @Test
  public void testGetCellTypesGridComplexGrid() {
    assertEquals(4, complexGrid.getCellTypesGrid().length);
    assertEquals(5, complexGrid.getCellTypesGrid()[0].length);
    assertEquals(CellType.CELL, complexGrid.getCellTypesGrid()[0][0]);
    assertEquals(CellType.HOLE, complexGrid.getCellTypesGrid()[1][0]);
    assertEquals(CellType.CELL, complexGrid.getCellTypesGrid()[0][2]);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlaceCardNull() {
    noHoleGrid.placeCard(null, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlaceCardInvalidRow() {
    complexGrid.placeCard(cell527A, 4, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlaceCardInvalidCol() {
    complexGrid.placeCard(cell527A, 3, 5);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlaceCardHole() {
    simpleGrid.placeCard(cell527A, 1, 1);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlaceCardNotEmpty() {
    simpleGrid.placeCard(cell527A, 0, 0);
    simpleGrid.placeCard(cell4599, 0, 0); // cell already has a card
  }

  @Test
  public void testPlaceCardNoHoleGrid() {
    assertTrue(noHoleGrid.canPlaceCard(0, 0));
    noHoleGrid.placeCard(cell527A, 0, 0);
    assertEquals(cell527A, noHoleGrid.getCardAt(0, 0));
    assertEquals(GamePlayer.RED, noHoleGrid.getCardAt(0, 0).getOwner());

    assertTrue(noHoleGrid.canPlaceCard(0, 1));
    noHoleGrid.placeCard(cell7253, 0, 1);
    assertEquals(cell7253, noHoleGrid.getCardAt(0, 1));
    assertEquals(GamePlayer.BLUE, noHoleGrid.getCardAt(0, 1).getOwner());
  }

  @Test
  public void testPlaceCardSimpleGrid() {
    assertTrue(simpleGrid.canPlaceCard(0, 0));
    simpleGrid.placeCard(cell527A, 0, 0);
    assertEquals(cell527A, simpleGrid.getCardAt(0, 0));
    assertEquals(GamePlayer.RED, simpleGrid.getCardAt(0, 0).getOwner());

    assertTrue(simpleGrid.canPlaceCard(2, 3));
    simpleGrid.placeCard(cell4599, 2, 3);
    assertEquals(cell4599, simpleGrid.getCardAt(2, 3));
    assertEquals(GamePlayer.BLUE, simpleGrid.getCardAt(2, 3).getOwner());
  }

  @Test
  public void testPlaceCardComplexGrid() {
    assertTrue(complexGrid.canPlaceCard(0, 4));
    complexGrid.placeCard(cell4599, 0, 4);
    assertEquals(cell4599, complexGrid.getCardAt(0, 4));
    assertEquals(GamePlayer.BLUE, complexGrid.getCardAt(0, 4).getOwner());

    assertTrue(complexGrid.canPlaceCard(3, 0));
    complexGrid.placeCard(cell7253, 3, 0);
    assertEquals(cell7253, complexGrid.getCardAt(3, 0));
    assertEquals(GamePlayer.BLUE, complexGrid.getCardAt(3, 0).getOwner());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCanPlaceCardInvalidRow() {
    noHoleGrid.canPlaceCard(-1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCanPlaceCardInvalidCol() {
    noHoleGrid.canPlaceCard(1, -1);
  }

  @Test
  public void testCanPlaceCardNoHoleGrid() {
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        assertTrue(noHoleGrid.canPlaceCard(row, col));
      }
    }
    noHoleGrid.placeCard(cell4623, 0, 0);
    assertFalse(noHoleGrid.canPlaceCard(0, 0));

    noHoleGrid.placeCard(cell27A9, 0, 1);
    assertFalse(noHoleGrid.canPlaceCard(0, 1));
  }

  @Test
  public void testCanPlaceCardSimpleGrid() {
    assertTrue(simpleGrid.canPlaceCard(0, 0));
    simpleGrid.placeCard(cell27A9, 0, 0);
    assertFalse(simpleGrid.canPlaceCard(0, 0));

    assertTrue(simpleGrid.canPlaceCard(2, 3));
    simpleGrid.placeCard(cell4623, 2, 3);
    assertFalse(simpleGrid.canPlaceCard(2, 3));

    // holes
    assertFalse(simpleGrid.canPlaceCard(1, 1));
    assertFalse(simpleGrid.canPlaceCard(0, 3));
  }

  @Test
  public void testCanPlaceCardComplexGrid() {
    assertTrue(complexGrid.canPlaceCard(0, 4));
    complexGrid.placeCard(cell4599, 0, 4);
    assertFalse(complexGrid.canPlaceCard(0, 4));

    assertTrue(complexGrid.canPlaceCard(3, 0));
    complexGrid.placeCard(cell7253, 3, 0);
    assertFalse(complexGrid.canPlaceCard(3, 0));

    // holes
    assertFalse(complexGrid.canPlaceCard(1, 0));
    assertFalse(complexGrid.canPlaceCard(3, 4));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtInvalidRow() {
    simpleGrid.getCardAt(3, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtInvalidCol() {
    simpleGrid.getCardAt(2, -1);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetCardAtNoCard() {
    simpleGrid.getCardAt(0, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetCardAtHole() {
    simpleGrid.getCardAt(0, 1);
  }

  @Test
  public void testGetCardAt() {
    noHoleGrid.placeCard(cell4623, 1, 1);
    assertEquals(cell4623, noHoleGrid.getCardAt(1, 1));

    simpleGrid.placeCard(cell27A9, 2, 1);
    assertEquals(cell27A9, simpleGrid.getCardAt(2, 1));

    complexGrid.placeCard(cell2899, 1, 3);
    assertEquals(cell2899, complexGrid.getCardAt(1, 3));
  }

  @Test
  public void testIsFilledNoHoleGrid() {
    assertFalse(noHoleGrid.isFilled());
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        noHoleGrid.placeCard(cell2899, row, col);
      }
    }
    assertTrue(noHoleGrid.isFilled());
  }

  @Test
  public void testIsFilledSimpleGrid() {
    assertFalse(simpleGrid.isFilled());
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 4; col++) {
        if (simpleGridCellTypes[row][col] == CellType.CELL) {
          simpleGrid.placeCard(cell4623, row, col);
        }
      }
    }
    assertTrue(simpleGrid.isFilled());
  }

  @Test
  public void testIsFilledComplexGrid() {
    assertFalse(complexGrid.isFilled());
    for (int row = 0; row < 4; row++) {
      for (int col = 0; col < 5; col++) {
        if (complexGridCellTypes[row][col] == CellType.CELL) {
          complexGrid.placeCard(cell27A9, row, col);
        }
      }
    }
    assertTrue(complexGrid.isFilled());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetAdjacentCardsInvalidRow() {
    complexGrid.getCardAt(-4, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetAdjacentCardsInvalidCol() {
    complexGrid.getCardAt(3, -5);
  }

  @Test
  public void getAdjacentCardsNoHoleGrid() {
    noHoleGrid.placeCard(cell2899, 0, 0);
    noHoleGrid.placeCard(cell27A9, 1, 0);
    noHoleGrid.placeCard(cell7253, 0, 1);
    noHoleGrid.placeCard(cell4623, 1, 1);
    noHoleGrid.placeCard(cell4599, 2, 0);

    Map<Cell, AbstractMap.SimpleEntry<Integer, Integer>> neighborsOfTopLeft =
            noHoleGrid.getAdjacentCards(0, 0);
    assertTrue(neighborsOfTopLeft.containsKey(cell7253));
    assertTrue(neighborsOfTopLeft.containsKey(cell27A9));
    assertEquals(2, neighborsOfTopLeft.keySet().size());

    Map<Cell, AbstractMap.SimpleEntry<Integer, Integer>> neighborsOfMiddleLeft =
            noHoleGrid.getAdjacentCards(1, 0);
    assertTrue(neighborsOfMiddleLeft.containsKey(cell2899));
    assertTrue(neighborsOfMiddleLeft.containsKey(cell4599));
    assertTrue(neighborsOfMiddleLeft.containsKey(cell4623));
    assertEquals(3, neighborsOfMiddleLeft.keySet().size());
  }

  @Test
  public void getAdjacentCardsSimpleGrid() {
    simpleGrid.placeCard(cell2899, 0, 0);
    simpleGrid.placeCard(cell27A9, 1, 0);
    simpleGrid.placeCard(cell7253, 2, 0);
    simpleGrid.placeCard(cell4623, 2, 1);
    simpleGrid.placeCard(cell527A, 2, 2);
    simpleGrid.placeCard(cell4599, 2, 3);

    Map<Cell, AbstractMap.SimpleEntry<Integer, Integer>> neighborsOfTopLeft =
            simpleGrid.getAdjacentCards(0, 0);
    assertTrue(neighborsOfTopLeft.containsKey(cell27A9));

    Map<Cell, AbstractMap.SimpleEntry<Integer, Integer>> neighborsOfMiddleLeft =
            simpleGrid.getAdjacentCards(1, 0);
    assertTrue(neighborsOfMiddleLeft.containsKey(cell7253));
    assertTrue(neighborsOfMiddleLeft.containsKey(cell2899));

    Map<Cell, AbstractMap.SimpleEntry<Integer, Integer>> neighborsOfBottomLeft =
            simpleGrid.getAdjacentCards(2, 0);
    assertTrue(neighborsOfBottomLeft.containsKey(cell27A9));
    assertTrue(neighborsOfBottomLeft.containsKey(cell4623));

    Map<Cell, AbstractMap.SimpleEntry<Integer, Integer>> neighborsOfBottom2ndFromTheRight =
            simpleGrid.getAdjacentCards(2, 2);
    assertTrue(neighborsOfBottom2ndFromTheRight.containsKey(cell4599));
    assertTrue(neighborsOfBottom2ndFromTheRight.containsKey(cell4623));
  }

  @Test
  public void getAdjacentCardsComplexGrid() {
    complexGrid.placeCard(cell2899, 3, 0);
    complexGrid.placeCard(cell27A9, 3, 1);
    complexGrid.placeCard(cell7253, 2, 1);
    complexGrid.placeCard(cell4623, 3, 2);

    complexGrid.placeCard(cell2899, 0, 2);
    complexGrid.placeCard(cell27A9, 0, 3);
    complexGrid.placeCard(cell7253, 1, 3);
    complexGrid.placeCard(cell4623, 0, 4);

    Map<Cell, AbstractMap.SimpleEntry<Integer, Integer>> neighborsOfRow3Col1 =
            complexGrid.getAdjacentCards(3, 1);
    assertTrue(neighborsOfRow3Col1.containsKey(cell7253));
    assertTrue(neighborsOfRow3Col1.containsKey(cell2899));
    assertTrue(neighborsOfRow3Col1.containsKey(cell4623));

    Map<Cell, AbstractMap.SimpleEntry<Integer, Integer>> neighborsOfRow0Col3 =
            complexGrid.getAdjacentCards(0, 3);
    assertTrue(neighborsOfRow0Col3.containsKey(cell7253));
    assertTrue(neighborsOfRow0Col3.containsKey(cell2899));
    assertTrue(neighborsOfRow0Col3.containsKey(cell4623));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCountPlayerCardsNull() {
    noHoleGrid.countPlayerCards(null);
  }

  @Test
  public void countPlayerCardsNoHoleGrid() {
    assertEquals(0, noHoleGrid.countPlayerCards(GamePlayer.RED));
    assertEquals(0, noHoleGrid.countPlayerCards(GamePlayer.BLUE));

    noHoleGrid.placeCard(cell2899, 0, 0);
    noHoleGrid.placeCard(cell27A9, 1, 1);
    noHoleGrid.placeCard(cell4623, 2, 2);

    assertEquals(2, noHoleGrid.countPlayerCards(GamePlayer.RED));
    assertEquals(1, noHoleGrid.countPlayerCards(GamePlayer.BLUE));
  }

  @Test
  public void countPlayerCardsSimpleGrid() {
    assertEquals(0, simpleGrid.countPlayerCards(GamePlayer.RED));
    assertEquals(0, simpleGrid.countPlayerCards(GamePlayer.BLUE));

    simpleGrid.placeCard(cell2899, 0, 0);
    simpleGrid.placeCard(cell27A9, 2, 0);
    simpleGrid.placeCard(cell527A, 2, 2);

    assertEquals(2, simpleGrid.countPlayerCards(GamePlayer.RED));
    assertEquals(1, simpleGrid.countPlayerCards(GamePlayer.BLUE));
  }

  @Test
  public void countPlayerCardsComplexGrid() {
    assertEquals(0, complexGrid.countPlayerCards(GamePlayer.RED));
    assertEquals(0, complexGrid.countPlayerCards(GamePlayer.BLUE));

    complexGrid.placeCard(cell2899, 0, 0);
    complexGrid.placeCard(cell27A9, 1, 3);
    complexGrid.placeCard(cell527A, 2, 1);

    assertEquals(2, complexGrid.countPlayerCards(GamePlayer.RED));
    assertEquals(1, complexGrid.countPlayerCards(GamePlayer.BLUE));
  }

  @Test
  public void getWidthAndHeight() {
    assertEquals(noHoleGrid.getWidth(), 3);
    assertEquals(noHoleGrid.getHeight(), 3);

    assertEquals(simpleGrid.getWidth(), 4);
    assertEquals(simpleGrid.getHeight(), 3);

    assertEquals(complexGrid.getWidth(), 5);
    assertEquals(complexGrid.getHeight(), 4);
  }
}