package model.implementation;

import java.util.ArrayList;
import java.util.List;

import model.interfaces.Cell;
import model.interfaces.Hand;

/**
 * Represents ThreeTriosHand for the game ThreeTrios.
 */
class ThreeTriosHand implements Hand {
  private final List<Cell> cells;
  // hand size changes as player places in grid, does not require shifting array index

  /**
   * Construct a new ThreeTriosHand.
   *
   * @param cells list of cards in hand
   * @throws IllegalArgumentException if cards is null
   */
  public ThreeTriosHand(List<Cell> cells) {
    if (cells == null) {
      throw new IllegalArgumentException("Cards cannot be null");
    }
    this.cells = cells;
  }

  /**
   * Retrieves list of cards in hands. Modifying the list will not change cards in hand.
   *
   * @return a copy of cards in hand
   */
  public List<Cell> getCards() {
    return new ArrayList<>(cells);
  }

  /**
   * Add a card to the hand.
   *
   * @param cell the card to be added to the hand
   * @throws IllegalArgumentException if the card is null
   */
  public void addCard(Cell cell) {
    if (cell == null) {
      throw new IllegalArgumentException("Card cannot be null");
    }
    cells.add(cell);
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
  public Cell removeCard(int index) {
    if (cells.isEmpty()) {
      throw new IllegalStateException("Cannot remove from an empty list");
    }
    validateIndex(index);
    return cells.remove(index);
  }

  /**
   * Return the current number of cards in hand.
   *
   * @return the number of cards in hand
   */
  public int handSize() {
    return cells.size();
  }

  /**
   * Check if the index is within bound of list of cards in hand.
   *
   * @param index index of card
   * @throws IllegalArgumentException if index is out of bound
   */
  private void validateIndex(int index) {
    if (index < 0 || index >= cells.size()) {
      throw new IllegalArgumentException("Index out of bound for card");
    }
  }
}
