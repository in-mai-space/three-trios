package model.components.hand;

import java.util.List;

import model.components.card.Card;

/**
 * Represents a hand of cards of a player.
 */
public interface Hand {

  /**
   * Retrieves list of cards in hands. Modifying the list will not change cards in hand.
   *
   * @return a copy of cards in hand
   */
  List<Card> getCards();

  /**
   * Add a card to the hand.
   *
   * @param card the card to be added to the hand
   * @throws IllegalArgumentException if the card is null
   */
  void addCard(Card card);

  /**
   * Remove a card from hand given a 0-based index.
   *
   * @param index index of cards to be removed
   * @return the removed card
   *
   * @throws IllegalArgumentException if the index is out of bounds
   * @throws IllegalStateException if the hand is empty
   */
  Card removeCard(int index);

  /**
   * Return the current number of cards in hand.
   *
   * @return the number of cards in hand
   */
  int handSize();
}
