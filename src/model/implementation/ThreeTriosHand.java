package model.implementation;

import java.util.ArrayList;
import java.util.List;

import model.interfaces.Card;
import model.interfaces.Hand;

/**
 * Represents ThreeTriosHand for the game ThreeTrios.
 */
class ThreeTriosHand implements Hand {
  private final List<Card> cards;

  /**
   * Construct a new ThreeTriosHand.
   *
   * @param cards list of cards in hand
   * @throws IllegalArgumentException if cards is null
   */
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
   *
   * @throws IllegalArgumentException if the index is out of bounds
   * @throws IllegalStateException if the hand is empty
   */
  public Card removeCard(int index) {
    if (cards.isEmpty()) {
      throw new IllegalStateException("Cannot remove from an empty list");
    }
    validateIndex(index);
    return cards.remove(index);
  }

  /**
   * Return the current number of cards in hand.
   *
   * @return the number of cards in hand
   */
  public int handSize() {
    return cards.size();
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
}
