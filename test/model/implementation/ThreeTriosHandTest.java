package model.implementation;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import model.enums.AttackValue;
import model.interfaces.Cell;
import model.enums.GamePlayer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * Test class for ThreeTriosHand.
 */
public class ThreeTriosHandTest {

  private ThreeTriosHand hand;
  private ThreeTriosHand emptyHand;
  private Cell cell1;
  private Cell cell2;

  @Before
  public void setUp() {
    cell1 = new ThreeTriosCell(new AttackValue[] {AttackValue.ONE, AttackValue.TWO,
      AttackValue.THREE, AttackValue.FOUR}, "Card 1", GamePlayer.RED);
    cell2 = new ThreeTriosCell(new AttackValue[] {AttackValue.FIVE, AttackValue.SIX,
      AttackValue.SEVEN, AttackValue.EIGHT}, "Card 2", GamePlayer.RED);

    hand = new ThreeTriosHand(new ArrayList<>(List.of(cell1)));
    emptyHand = new ThreeTriosHand(new ArrayList<>());
  }

  @Test
  public void testGetCards() {
    List<Cell> cells = hand.getCards();
    assertEquals(1, cells.size());
    assertSame(cell1, cells.get(0));
    // modifying the list should not change the cards in hand
    hand.getCards().clear();
    assertEquals(1, cells.size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullConstructor() {
    new ThreeTriosHand(null);
  }

  @Test
  public void testAddCard() {
    hand.addCard(cell2);
    List<Cell> cells = hand.getCards();
    assertEquals(2, cells.size());
    assertSame(cell2, cells.get(1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddNullCard() {
    hand.addCard(null);
  }

  @Test
  public void testRemoveCard() {
    hand.addCard(cell2);
    Cell removedCell = hand.removeCard(0);
    assertSame(cell1, removedCell);
    assertEquals(1, hand.handSize());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveCardNegativeIndex() {
    hand.removeCard(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveCardIndexOutOfBound() {
    hand.removeCard(1);
  }

  @Test(expected = IllegalStateException.class)
  public void testRemoveCardEmptyHand() {
    emptyHand.removeCard(0);
  }

  @Test
  public void testHandSize() {
    assertEquals(1, hand.handSize());
    hand.addCard(cell2);
    assertEquals(2, hand.handSize());
    assertEquals(0, new ThreeTriosHand(new ArrayList<>()).handSize());
  }
}
