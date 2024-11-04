package model.interfaces;

import java.util.List;

/**
 * Represents a hand of cards of a player.
 */
public interface Hand {

  /**
   * Retrieves list of cards in hands. Modifying the list will not change cards in hand.
   *
   * @return a copy of cards in hand
   */
  List<Cell> getCards();

  /**
   * Add a card to the hand.
   *
   * @param cell the card to be added to the hand
   * @throws IllegalArgumentException if the card is null
   */
  void addCard(Cell cell);

  /**
   * Remove a card from hand given a 0-based index.
   *
   * @param index index of cards to be removed
   * @return the removed card
   *
   * @throws IllegalArgumentException if the index is out of bounds
   * @throws IllegalStateException if the hand is empty
   */
  Cell removeCard(int index);

  /**
   * Return the current number of cards in hand.
   *
   * @return the number of cards in hand
   */
  int handSize();
}
