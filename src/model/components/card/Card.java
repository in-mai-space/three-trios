package model.components.card;

import model.components.enums.Direction;
import model.components.enums.GamePlayer;

/**
 * Represents a card in the game.
 */
public interface Card {
  /**
   * Get the name of the card.
   *
   * @return name of the card as String
   */
  String getName();

  /**
   * Get the attack value on the card given a direction (north, south, east or west).
   *
   * @param direction to get the attack value on the card
   * @return the attack value on the card
   */
  int getAttackValue(Direction direction);

  /**
   * Checks if this card beats the other card's attack values given a direction.
   *
   * @param that the other card to battle with
   * @param direction direction to compare the attack value
   * @return true if this card beats other card's attack values in given direction
   */
  boolean beats(Card that, Direction direction);

  /**
   * Get the current owner of the card.
   *
   * @return the current owner of the card
   */
  GamePlayer getOwner();

  /**
   * Set the card's new owner.
   *
   * @param owner new owner of the card
   * @throws IllegalArgumentException if owner is null
   */
  void setOwner(GamePlayer owner);
}
