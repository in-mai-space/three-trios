package model.components.hand;

import java.util.ArrayList;
import java.util.List;

import model.components.card.Card;

/**
 * Represents ThreeTriosHand for the game ThreeTrios.
 */
public class ThreeTriosHand implements Hand {
  private final List<Card> cards;
  public ThreeTriosHand(List<Card> cards) {
    if (cards == null) {
      throw new IllegalArgumentException("Cards cannot be null");
    }
    this.cards = cards;
  }

  /**
   * Retrieves list of cards in hands. Modifying the list will not change cards in hand.
   *
   * @return a copy of cards in hand
   */
  public List<Card> getCards() {
    return new ArrayList<>(cards);
  }

  /**
   * Add a card to the hand.
   *
   * @param card the card to be added to the hand
   * @throws IllegalArgumentException if the card is null
   */
  public void addCard(Card card) {
    if (card == null) {
      throw new IllegalArgumentException("Card cannot be null");
    }
    cards.add(card);
  }

  /**
   * Remove a card from hand given a 0-based index.
   *
   * @param index index of cards to be removed
   * @return the removed card
   */
  public Card removeCard(int index) {
    validateIndex(index);
    return cards.remove(index);
  }

  /**
   * Get a card from hand given a 0-based index.
   *
   * @param index index of cards in hand
   * @return a copy of the card
   */
  public Card getCard(int index) {
    validateIndex(index);
    return cards.get(index);
  }

  /**
   * Check if the index is within bound of list of cards in hand.
   *
   * @param index index of card
   * @throws IllegalArgumentException if index is out of bound
   */
  private void validateIndex(int index) {
    if (index < 0 || index >= cards.size()) {
      throw new IllegalArgumentException("Index out of bound for card");
    }
  }

  /**
   * Return the max capacity of hand.
   *
   * @return the max capacity of the hand of cards
   */
  public int handSize() {
    return cards.size();
  }
}
