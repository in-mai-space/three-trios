package model.components.hand;

import model.components.card.Card;
import model.components.card.ThreeTriosCard;
import model.components.enums.AttackValue;
import model.components.enums.GamePlayer;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ThreeTriosHandTest {

  private ThreeTriosHand hand;
  private Card card1;
  private Card card2;
  private Card card3;

  @Before
  public void setUp() {
    card1 = new ThreeTriosCard(new AttackValue[] {AttackValue.ONE, AttackValue.TWO, AttackValue.THREE, AttackValue.FOUR},
            "Card 1", GamePlayer.RED);
    card2 = new ThreeTriosCard(new AttackValue[] {AttackValue.FIVE, AttackValue.SIX, AttackValue.SEVEN, AttackValue.EIGHT},
            "Card 2", GamePlayer.RED);
    card3 = new ThreeTriosCard(new AttackValue[] {AttackValue.NINE, AttackValue.A, AttackValue.TWO, AttackValue.THREE},
            "Card 3", GamePlayer.RED);

    hand = new ThreeTriosHand(new ArrayList<>(List.of(card1)));
  }

  @Test
  public void testGetCards() {
    List<Card> cards = hand.getCards();
    assertEquals(1, cards.size());
    assertSame(card1, cards.get(0));
  }

  @Test
  public void testAddCard() {
    hand.addCard(card2);
    List<Card> cards = hand.getCards();
    assertEquals(2, cards.size());
    assertSame(card2, cards.get(1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddNullCard() {
    hand.addCard(null);
  }

  @Test
  public void testRemoveCard() {
    hand.addCard(card2);
    Card removedCard = hand.removeCard(0);
    assertSame(card1, removedCard);
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

  @Test
  public void testHandSize() {
    assertEquals(1, hand.handSize());
    hand.addCard(card2);
    assertEquals(2, hand.handSize());
  }
}
