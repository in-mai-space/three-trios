package model.implementation;

import java.util.Objects;

import model.enums.AttackValue;
import model.interfaces.Card;
import model.enums.Direction;

/**
 * Represents a card in the ThreeTriosGame.
 */
public class ThreeTriosCard implements Card {
  private final String name;
  private final AttackValue north;
  private final AttackValue south;
  private final AttackValue east;
  private final AttackValue west;

  /**
   * Construct a new ThreeTriosCard.
   *
   * @param values arrays of attack values of the card,
   *               following the order [north, south, east, west]
   * @param name name of each card
   *
   * @throws IllegalArgumentException if values are null or name is null
   */
  public ThreeTriosCard(AttackValue[] values, String name) {
    validateCard(values, name);
    this.north = values[0];
    this.south = values[1];
    this.east = values[2];
    this.west = values[3];
    this.name = name;
  }

  /**
   * Validate the attack values and name arguments.
   *
   * @param values arrays of attack values of the card,
   *               following the order [north, south, east, west]
   * @param name name of each card
   * @throws IllegalArgumentException if values, attack values in array or name is null
   * @throws IllegalArgumentException if the attack values are not length of 4
   */
  private void validateCard(AttackValue[] values, String name) {
    if (name == null || values == null) {
      throw new IllegalArgumentException("Name or owner cannot be null");
    }
    if (values.length != 4) {
      throw new IllegalArgumentException("Must provide exactly 4 attack values for the card.");
    }
    for (AttackValue value : values) {
      if (value == null) {
        throw new IllegalArgumentException("Attack value cannot be null");
      }
    }
  }

  /**
   * Get the name of the card.
   *
   * @return name of the card as String
   */
  public String getName() {
    return this.name;
  }

  /**
   * Get the attack value on the card given a direction (north, south, east or west).
   *
   * @param direction to get the attack value on the card
   * @return the attack value on the card
   * @throws IllegalArgumentException if the direction is invalid
   */
  public int getAttackValue(Direction direction) {
    switch (direction) {
      case NORTH:
        return north.getValue();
      case EAST:
        return east.getValue();
      case WEST:
        return west.getValue();
      case SOUTH:
        return south.getValue();
      default:
        throw new IllegalArgumentException("Invalid direction");
    }
  }

  /**
   * Get all attack values following order north, south, east, west. Modifying this array
   * does not change the values on the card.
   *
   * @return array of attack values
   */
  public AttackValue[] getAllAttackValues() {
    return new AttackValue[]{north, south, east, west};
  }

  /**
   * Compares this ThreeTriosCard object with another object for equality.
   *
   * @param that the object to be compared for equality with this ThreeTriosCard
   * @return true if the specified object is equal to this ThreeTriosCard;
   *         false otherwise
   */
  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (that instanceof ThreeTriosCard) {
      ThreeTriosCard thatCard = (ThreeTriosCard) that;
      return this.name.equals(thatCard.name)
              && this.north.equals(thatCard.north)
              && this.south.equals(thatCard.south)
              && this.east.equals(thatCard.east)
              && this.west.equals(thatCard.west);
    }
    return false;
  }

  /**
   * Returns the hash code for this ThreeTriosCard, based on its fields.
   *
   * @return the hash code value for this ThreeTriosCard
   */
  @Override
  public int hashCode() {
    return Objects.hash(name, north, south, east, west);
  }
}
