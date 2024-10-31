package model.interfaces;

import java.util.List;

import model.enums.AttackValue;
import model.enums.Direction;
import model.enums.GamePlayer;

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
   * Get all attack values following order north, south, east, west. Modifying this list
   * does not change the values on the card.
   *
   * @return list of attack values
   */
  List<AttackValue> getAllAttackValues();

  /**
   * Checks if this card beats the other card's attack values given a direction.
   *
   * @param that the other card to battle with
   * @param direction direction to compare the attack value
   * @return true if this card beats other card's attack values in given direction
   * @throws IllegalArgumentException if card is null
   */
  boolean beats(Card that, Direction direction);

  /**
   * Get the current owner of the card.
   *
   * @return the current owner of the card
   * @throws IllegalStateException if card currently does not have an owner
   */
  GamePlayer getOwner();

  /**
   * Set the card's new owner.
   *
   * @param owner new owner of the card
   * @throws IllegalArgumentException if owner is null
   */
  void setOwner(GamePlayer owner);

  /**
   * Get a copy of the card.
   */
  Card getCopy();
}
