package model.implementation;

import org.junit.Before;
import org.junit.Test;

import model.enums.AttackValue;
import model.interfaces.Cell;
import model.enums.Direction;
import model.enums.GamePlayer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for ThreeTriosCard.
 */
public class ThreeTriosCellTest {
  private Cell firstCell;
  private Cell firstCellRed;
  private Cell secondCell;

  @Before
  public void setUp() {
    firstCell = new ThreeTriosCell(new AttackValue[]{
        AttackValue.THREE, AttackValue.FIVE, AttackValue.A, AttackValue.FOUR }, "Card 1",
        GamePlayer.BLUE);
    firstCellRed = new ThreeTriosCell(new AttackValue[]{
        AttackValue.THREE, AttackValue.FIVE, AttackValue.A, AttackValue.FOUR}, "Card 1",
        GamePlayer.RED
    );
    secondCell = new ThreeTriosCell(new AttackValue[]{
        AttackValue.FIVE, AttackValue.EIGHT, AttackValue.A, AttackValue.TWO}, "Card 2",
        GamePlayer.RED
    );

  }

  @Test(expected = IllegalArgumentException.class)
  public void nullAttackValueInArray() {
    new ThreeTriosCell(new AttackValue[]{null, AttackValue.EIGHT,
      AttackValue.A, AttackValue.TWO}, "Card");
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullAttackValueArray() {
    new ThreeTriosCell(null, "Card", GamePlayer.RED);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullName() {
    new ThreeTriosCell(new AttackValue[]{AttackValue.THREE, AttackValue.FIVE,
      AttackValue.EIGHT, AttackValue.TWO}, null, GamePlayer.RED);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidAttackValueListSize() {
    new ThreeTriosCell(new AttackValue[]{AttackValue.THREE, AttackValue.FIVE,
      AttackValue.EIGHT}, "Card");
  }

  @Test
  public void getName() {
    assertEquals(firstCell.getName(), "Card 1");
    assertEquals(secondCell.getName(), "Card 2");
  }

  @Test
  public void getAttackValue() {
    assertEquals(firstCell.getAttackValue(Direction.NORTH), 3);
    assertEquals(firstCell.getAttackValue(Direction.SOUTH), 5);
    assertEquals(firstCell.getAttackValue(Direction.EAST), 10);
    assertEquals(firstCell.getAttackValue(Direction.WEST), 4);
    assertEquals(secondCell.getAttackValue(Direction.NORTH), 5);
    assertEquals(secondCell.getAttackValue(Direction.SOUTH), 8);
    assertEquals(secondCell.getAttackValue(Direction.EAST), 10);
    assertEquals(secondCell.getAttackValue(Direction.WEST), 2);
  }

  @Test
  public void getAttackValues() {
    assertEquals(firstCell.getAllAttackValues()[0], AttackValue.THREE);
    assertEquals(firstCell.getAllAttackValues()[1], AttackValue.FIVE);
    assertEquals(firstCell.getAllAttackValues()[2], AttackValue.A);
    assertEquals(firstCell.getAllAttackValues()[3], AttackValue.FOUR);
    assertEquals(secondCell.getAllAttackValues()[0], AttackValue.FIVE);
    assertEquals(secondCell.getAllAttackValues()[1], AttackValue.EIGHT);
    assertEquals(secondCell.getAllAttackValues()[2], AttackValue.A);
    assertEquals(secondCell.getAllAttackValues()[3], AttackValue.TWO);
    // modify the list has no effect on the card's attack value
    firstCell.getAllAttackValues()[0] = AttackValue.TWO;
    assertEquals(firstCell.getAllAttackValues()[0], AttackValue.THREE);
  }

  @Test
  public void beats() {
    // south and north
    assertFalse(firstCell.beats(secondCell, Direction.SOUTH));
    assertFalse(firstCell.beats(secondCell, Direction.NORTH));
    assertFalse(secondCell.beats(firstCell, Direction.NORTH));
    assertTrue(secondCell.beats(firstCell, Direction.SOUTH));

    // east and west
    assertTrue(firstCell.beats(secondCell, Direction.EAST));
    assertFalse(firstCell.beats(secondCell, Direction.WEST));
    assertTrue(secondCell.beats(firstCell, Direction.EAST));
    assertFalse(secondCell.beats(firstCell, Direction.WEST));
  }

  @Test(expected = IllegalArgumentException.class)
  public void beatsWithNullCard() {
    firstCell.beats(null, Direction.NORTH);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullName() {
    new ThreeTriosCell(new AttackValue[]{AttackValue.THREE, AttackValue.FIVE,
      AttackValue.EIGHT, AttackValue.FOUR}, null, GamePlayer.BLUE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullAttackValue() {
    new ThreeTriosCell(new AttackValue[]{null, null, null, null}, "Card", GamePlayer.RED);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAttackValueNot4() {
    new ThreeTriosCell(new AttackValue[]{AttackValue.THREE, AttackValue.FIVE,
      AttackValue.EIGHT, AttackValue.FOUR, AttackValue.THREE}, "Card", GamePlayer.RED);
  }

  @Test
  public void testGetOwner() {
    assertEquals(firstCell.getOwner(), GamePlayer.BLUE);
    assertEquals(secondCell.getOwner(), GamePlayer.RED);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetNullOwner() {
    Cell cell = new ThreeTriosCell(new AttackValue[]{AttackValue.THREE, AttackValue.FIVE,
        AttackValue.EIGHT, AttackValue.FOUR}, "Card");
    cell.getOwner();
  }

  @Test
  public void testSetOwner() {
    assertEquals(firstCell.getOwner(), GamePlayer.BLUE);
    firstCell.setOwner(GamePlayer.RED);
    assertEquals(firstCell.getOwner(), GamePlayer.RED);
    firstCell.setOwner(GamePlayer.BLUE);
    assertEquals(firstCell.getOwner(), GamePlayer.BLUE);
  }

  @Test
  public void testEquals() {
    assertTrue(firstCell.equals(firstCell));
    assertFalse(firstCell.equals(secondCell));
    assertFalse(secondCell.equals(firstCell));
    assertTrue(firstCell.equals(firstCellRed));
    assertTrue(firstCellRed.equals(firstCell));
  }

  @Test
  public void testHashCode() {
    assertEquals(firstCell.hashCode(), firstCellRed.hashCode());
    assertNotEquals(firstCell.hashCode(), secondCell.hashCode());
  }

  @Test
  public void testGetCardCopy() {
    assertEquals(firstCell.getCopy(), firstCell);
    assertEquals(firstCell.getCopy().getOwner(), GamePlayer.BLUE);
    assertEquals(firstCell.getOwner(), GamePlayer.BLUE);
    // modify the copy of the card does not affect the original
    firstCell.getCopy().setOwner(GamePlayer.RED);
    assertEquals(firstCell.getOwner(), GamePlayer.BLUE);
  }
}