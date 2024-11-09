package model.interfaces;

import model.enums.AttackValue;
import model.enums.Direction;

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
  AttackValue[] getAllAttackValues();
}
