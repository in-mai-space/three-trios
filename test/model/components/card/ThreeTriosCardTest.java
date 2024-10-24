package model.components.card;

import org.junit.Before;
import org.junit.Test;

import model.components.enums.AttackValue;
import model.components.enums.Direction;
import model.components.enums.GamePlayer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for ThreeTriosCard.
 */
public class ThreeTriosCardTest {
  private Card firstCard;
  private Card firstCardRed;
  private Card secondCard;

  @Before
  public void setUp() {
    firstCard = new ThreeTriosCard(new AttackValue[]{
            AttackValue.THREE, AttackValue.FIVE, AttackValue.A, AttackValue.FOUR }, "Card 1",
            GamePlayer.BLUE
    );
    firstCardRed = new ThreeTriosCard(new AttackValue[]{
            AttackValue.THREE, AttackValue.FIVE, AttackValue.A, AttackValue.FOUR }, "Card 1",
            GamePlayer.RED
    );
    secondCard = new ThreeTriosCard(new AttackValue[]{
            AttackValue.FIVE, AttackValue.EIGHT, AttackValue.A, AttackValue.TWO }, "Card 2",
            GamePlayer.RED
    );
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullAttackValueInArray() {
    new ThreeTriosCard(new AttackValue[]{null, AttackValue.EIGHT,
            AttackValue.A, AttackValue.TWO}, "Card");
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullAttackValueArray() {
    new ThreeTriosCard(null, "Card", GamePlayer.RED);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullName() {
    new ThreeTriosCard(new AttackValue[]{AttackValue.THREE, AttackValue.FIVE,
            AttackValue.EIGHT, AttackValue.TWO}, null, GamePlayer.RED);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidAttackValueListSize() {
    new ThreeTriosCard(new AttackValue[]{AttackValue.THREE, AttackValue.FIVE,
            AttackValue.EIGHT}, "Card");
  }

  @Test
  public void getName() {
    assertEquals(firstCard.getName(), "Card 1");
    assertEquals(secondCard.getName(), "Card 2");
  }

  @Test
  public void getAttackValue() {
    assertEquals(firstCard.getAttackValue(Direction.NORTH), 3);
    assertEquals(firstCard.getAttackValue(Direction.SOUTH), 5);
    assertEquals(firstCard.getAttackValue(Direction.EAST), 10);
    assertEquals(firstCard.getAttackValue(Direction.WEST), 4);
    assertEquals(secondCard.getAttackValue(Direction.NORTH), 5);
    assertEquals(secondCard.getAttackValue(Direction.SOUTH), 8);
    assertEquals(secondCard.getAttackValue(Direction.EAST), 10);
    assertEquals(secondCard.getAttackValue(Direction.WEST), 2);
  }

  @Test
  public void getAttackValues() {
    assertEquals(firstCard.getAllAttackValues().get(0), AttackValue.THREE);
    assertEquals(firstCard.getAllAttackValues().get(1), AttackValue.FIVE);
    assertEquals(firstCard.getAllAttackValues().get(2), AttackValue.A);
    assertEquals(firstCard.getAllAttackValues().get(3), AttackValue.FOUR);
    assertEquals(secondCard.getAllAttackValues().get(0), AttackValue.FIVE);
    assertEquals(secondCard.getAllAttackValues().get(1), AttackValue.EIGHT);
    assertEquals(secondCard.getAllAttackValues().get(2), AttackValue.A);
    assertEquals(secondCard.getAllAttackValues().get(3), AttackValue.TWO);
    // modify the list has no effect on the card's attack value
    firstCard.getAllAttackValues().set(0, AttackValue.TWO);
    assertEquals(firstCard.getAllAttackValues().get(0), AttackValue.THREE);
  }

  @Test
  public void beats() {
    // south and north
    assertFalse(firstCard.beats(secondCard, Direction.SOUTH));
    assertFalse(firstCard.beats(secondCard, Direction.NORTH));
    assertFalse(secondCard.beats(firstCard, Direction.NORTH));
    assertTrue(secondCard.beats(firstCard, Direction.SOUTH));

    // east and west
    assertTrue(firstCard.beats(secondCard, Direction.EAST));
    assertFalse(firstCard.beats(secondCard, Direction.WEST));
    assertTrue(secondCard.beats(firstCard, Direction.EAST));
    assertFalse(secondCard.beats(firstCard, Direction.WEST));
  }

  @Test(expected = IllegalArgumentException.class)
  public void beatsWithNullCard() {
    firstCard.beats(null, Direction.NORTH);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullName() {
    new ThreeTriosCard(new AttackValue[]{AttackValue.THREE, AttackValue.FIVE,
    AttackValue.EIGHT, AttackValue.FOUR}, null, GamePlayer.BLUE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullAttackValue() {
    new ThreeTriosCard(new AttackValue[]{null, null, null, null}, "Card", GamePlayer.RED);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAttackValueNot4() {
    new ThreeTriosCard(new AttackValue[]{AttackValue.THREE, AttackValue.FIVE,
            AttackValue.EIGHT, AttackValue.FOUR, AttackValue.THREE},
            "Card", GamePlayer.RED);
  }

  @Test
  public void testGetOwner() {
    assertEquals(firstCard.getOwner(), GamePlayer.BLUE);
    assertEquals(secondCard.getOwner(), GamePlayer.RED);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetNullOwner() {
    Card card = new ThreeTriosCard(new AttackValue[]{AttackValue.THREE, AttackValue.FIVE,
            AttackValue.EIGHT, AttackValue.FOUR},
            "Card");
    card.getOwner();
  }

  @Test
  public void testSetOwner() {
    assertEquals(firstCard.getOwner(), GamePlayer.BLUE);
    firstCard.setOwner(GamePlayer.RED);
    assertEquals(firstCard.getOwner(), GamePlayer.RED);
    firstCard.setOwner(GamePlayer.BLUE);
    assertEquals(firstCard.getOwner(), GamePlayer.BLUE);
  }

  @Test
  public void testEquals() {
    assertTrue(firstCard.equals(firstCard));
    assertFalse(firstCard.equals(secondCard));
    assertFalse(secondCard.equals(firstCard));
    assertTrue(firstCard.equals(firstCardRed));
    assertTrue(firstCardRed.equals(firstCard));
  }

  @Test
  public void testHashCode() {
    assertEquals(firstCard.hashCode(), firstCardRed.hashCode());
    assertNotEquals(firstCard.hashCode(), secondCard.hashCode());
  }
}