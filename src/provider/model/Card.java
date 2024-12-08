package provider.model;

/**
 * Behaviors for a Card in the Game of Three Trios.
 * Any additional behaviors for cards must be made
 * creating a new interface that extends this one.
 */
public interface Card {
  /**
   * Provides a string representation of a card.
   * @return a card as a string
   */
  String toString();
}
