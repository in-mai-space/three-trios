package model.components.card;


import org.junit.Before;
import org.junit.Test;

import model.components.enums.AttackValue;
import model.components.enums.Direction;
import model.components.enums.GamePlayer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ThreeTriosCardTest {
  private Card firstCard;
  private Card secondCard;

  @Before
  public void setUp() {
    firstCard = new ThreeTriosCard(new AttackValue[]{
            AttackValue.THREE, AttackValue.FIVE, AttackValue.A, AttackValue.FOUR }, "Card 1",
            GamePlayer.BLUE
    );
    secondCard = new ThreeTriosCard(new AttackValue[]{
            AttackValue.FIVE, AttackValue.EIGHT, AttackValue.A, AttackValue.TWO }, "Card 2",
            GamePlayer.RED
    );
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
  public void testNullName() {
    new ThreeTriosCard(new AttackValue[]{AttackValue.THREE, AttackValue.FIVE,
    AttackValue.EIGHT, AttackValue.FOUR}, null, GamePlayer.BLUE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullAttackValue() {
    new ThreeTriosCard(new AttackValue[]{null, null, null, null}, "Card", GamePlayer.RED);
  }

  @Test
  public void testGetOwner() {
    assertEquals(firstCard.getOwner(), GamePlayer.BLUE);
    assertEquals(secondCard.getOwner(), GamePlayer.RED);
  }

  @Test
  public void testSetOwner() {
    assertEquals(firstCard.getOwner(), GamePlayer.BLUE);
    firstCard.setOwner(GamePlayer.RED);
    assertEquals(firstCard.getOwner(), GamePlayer.RED);
    firstCard.setOwner(GamePlayer.BLUE);
    assertEquals(firstCard.getOwner(), GamePlayer.BLUE);
  }
}