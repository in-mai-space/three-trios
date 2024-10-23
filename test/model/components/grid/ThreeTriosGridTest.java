package model.components.grid;

import org.junit.Before;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.Map;

import model.components.card.Card;
import model.components.card.ThreeTriosCard;
import model.components.enums.AttackValue;
import model.components.enums.CellType;
import model.components.enums.GamePlayer;

import static org.junit.Assert.*;

public class ThreeTriosGridTest {

  CellType[][] cellTypeList1;
  CellType[][] cellTypeList2;
  CellType[][] invalidTypeList;
  ThreeTriosGrid grid1;
  ThreeTriosGrid grid2;
  ThreeTriosCard testCard;

  @Before
  public void setUp() {
    cellTypeList1 = new CellType[][]{
            {CellType.CELL, CellType.CELL, CellType.CELL},
            {CellType.CELL, CellType.CELL, CellType.CELL},
            {CellType.CELL, CellType.CELL, CellType.CELL}
    };
    cellTypeList2 = new CellType[][]{
            {CellType.CELL, CellType.HOLE, CellType.HOLE, CellType.HOLE},
            {CellType.CELL, CellType.HOLE, CellType.HOLE, CellType.CELL},
            {CellType.CELL, CellType.CELL, CellType.CELL, CellType.CELL}
    };
    invalidTypeList = new CellType[][]{
            {CellType.CELL, CellType.HOLE},
            {CellType.HOLE, CellType.CELL}
    };
    grid1 = new ThreeTriosGrid(cellTypeList1);
    grid2 = new ThreeTriosGrid(cellTypeList2);
    testCard = new ThreeTriosCard(
            new AttackValue[]{AttackValue.ONE, AttackValue.TWO,
                    AttackValue.THREE, AttackValue.A}, "Card");
  }

  @Test
  public void testValidConstruction() {
    assertThrows(IllegalArgumentException.class, () -> new ThreeTriosGrid(invalidTypeList));
    assertThrows(IllegalArgumentException.class, () -> new ThreeTriosGrid(null));
  }

  @Test
  public void testGetNumberOfCells() {
    assertEquals(9, grid1.getNumberOfCells());
    assertEquals(7, grid2.getNumberOfCells());
  }

  @Test
  public void testIsCellEmpty() {
    assertTrue(grid1.isCellEmpty(0, 0));
    grid1.placeCard(testCard, 0 ,0);
    assertFalse(grid1.isCellEmpty(0, 0));
  }

  @Test
  public void getCellType() {
    assertEquals(CellType.CELL, grid1.getCellType(0, 0));
    assertEquals(CellType.HOLE, grid2.getCellType(1, 1));
    assertThrows(IllegalArgumentException.class, () -> grid1.getCellType(3, 3));
  }

  @Test
  public void getGrid() {
    Card[][] gridCopy = grid1.getGrid();
    assertNull(gridCopy[0][0]);
    gridCopy[0][0] = testCard; // modify original grid
    Card[][] originalGrid = grid1.getGrid();
    assertNull(originalGrid[0][0]); // check that adding card to copy didn't change original
  }

  @Test
  public void getCellTypesGrid() {
    assertEquals(cellTypeList1, grid1.getCellTypesGrid());
    assertEquals(cellTypeList2, grid2.getCellTypesGrid());
  }

  @Test
  public void placeCard() {
    assertTrue(grid2.isCellEmpty(0, 0));
    grid2.placeCard(testCard, 0 ,0);
    assertFalse(grid2.isCellEmpty(0, 0));
    assertEquals(testCard, grid2.getCardAt(0, 0));

    assertThrows("Card cannot be null",
            IllegalArgumentException.class, () -> grid2.placeCard(null, 1 ,0));
    assertThrows("Index cannot be out of bounds",
            IllegalArgumentException.class, () -> grid2.placeCard(testCard, 2 ,4));
    assertThrows("Cell must be empty",
            IllegalStateException.class, () -> grid2.placeCard(testCard, 0 ,0));
    assertThrows("Cell cannot be a hole",
            IllegalStateException.class, () -> grid2.placeCard(testCard, 1 ,1));
  }

  @Test
  public void testIsFilled() {
    assertFalse(grid1.isFilled());
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        grid1.placeCard(testCard, row ,col);
      }
    }
    assertTrue(grid1.isFilled());
  }

  @Test
  public void getAdjacentCards() {
    grid2.placeCard(testCard, 0 ,0);
    Card card = new ThreeTriosCard(
            new AttackValue[]{AttackValue.A, AttackValue.FIVE,
                    AttackValue.SEVEN, AttackValue.NINE}, "Testing");
    grid2.placeCard(card, 1 ,0);
    Map<Card, AbstractMap.SimpleEntry<Integer, Integer>> neighbors1 =
            grid2.getAdjacentCards(0, 0);
    assertEquals(card, neighbors1.entrySet().iterator().next().getKey());
    Map<Card, AbstractMap.SimpleEntry<Integer, Integer>> neighbors2 =
            grid2.getAdjacentCards(1, 0);
    assertEquals(testCard, neighbors2.entrySet().iterator().next().getKey());
  }

  @Test
  public void testCountPlayerCards() {
    assertEquals(0, grid1.countPlayerCards(GamePlayer.RED));
    assertEquals(0, grid1.countPlayerCards(GamePlayer.BLUE));
    assertThrows("Player cannot be null",
            IllegalArgumentException.class, () -> grid1.countPlayerCards(null));
    Card card1 = new ThreeTriosCard(
            new AttackValue[]{AttackValue.ONE, AttackValue.TWO,
                    AttackValue.THREE, AttackValue.A}, "Card1", GamePlayer.RED);
    Card card2 = new ThreeTriosCard(
            new AttackValue[]{AttackValue.A, AttackValue.FIVE,
                    AttackValue.SEVEN, AttackValue.NINE}, "Card2", GamePlayer.BLUE);
    Card card3 = new ThreeTriosCard(
            new AttackValue[]{AttackValue.FOUR, AttackValue.SIX,
                    AttackValue.EIGHT, AttackValue.A}, "Card3", GamePlayer.RED);
    grid1.placeCard(card1, 0 ,0);
    grid1.placeCard(card2, 1 ,1);
    grid1.placeCard(card3, 2 ,2);
    assertEquals(2, grid1.countPlayerCards(GamePlayer.RED));
    assertEquals(1, grid1.countPlayerCards(GamePlayer.BLUE));
  }

}