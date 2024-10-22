package model.components.card;

import model.components.enums.AttackValue;
import model.components.enums.Direction;
import model.components.enums.GamePlayer;

/**
 * Represents a card in the ThreeTriosGame
 */
public class ThreeTriosCard implements Card {
  private final String name;
  private final AttackValue north;
  private final AttackValue south;
  private final AttackValue east;
  private final AttackValue west;
  private GamePlayer owner;

  /**
   * Construct a new ThreeTriosCard.
   *
   * @param values arrays of attack values of the card,
   *               following the order [north, south, east, west]
   * @param name name of each card
   *
   * @throws IllegalArgumentException if values are null or name is null
   */
  public ThreeTriosCard(AttackValue[] values, String name, GamePlayer owner) {
    validateCard(values, name);
    this.north = values[0];
    this.south = values[1];
    this.east = values[2];
    this.west = values[3];
    this.name = name;
    this.owner = owner;
  }

  /**
   * Validate the attack values and name arguments.
   *
   * @param values arrays of attack values of the card,
   *               following the order [north, south, east, west]
   * @param name name of each card
   * @throws IllegalArgumentException if values are null or name is null
   */
  private void validateCard(AttackValue[] values, String name) {
    if (values.length != 4) {
      throw new IllegalArgumentException("Must provide exactly 4 attack values for the card.");
    }
    for (AttackValue value : values) {
      if (value == null) {
        throw new IllegalArgumentException("Value cannot be null");
      }
    }
    if (name == null) {
      throw new IllegalArgumentException("Name or owner cannot be null");
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
   * Checks if this card beats the other card's attack values given a direction.
   *
   * @param that the other card to battle with
   * @param direction direction to compare the attack value
   * @return true if this card beats other card's attack values in given direction
   */
  public boolean beats(Card that, Direction direction) {
    return this.getAttackValue(direction) >
            that.getAttackValue(getAdjacentDirection(direction));
  }

  /**
   * Get the current owner of the card.
   *
   * @return the current owner of the card
   */
  public GamePlayer getOwner() {
    return this.owner;
  }

  /**
   * Set the card's new owner.
   *
   * @param owner new owner of the card
   * @throws IllegalArgumentException if owner is null
   */
  public void setOwner(GamePlayer owner) {
    if (owner == null) {
      throw new IllegalArgumentException("Owner cannot be null");
    }
    this.owner = owner;
  }

  /**
   * Get the adjacent direction given a direction. For example, if the given direction
   * is North, then adjacent direction should be South.
   *
   * @param direction to find adjacent direction
   * @return the adjacent direction
   * @throws IllegalArgumentException if direction is invalid
   */
  private static Direction getAdjacentDirection(Direction direction) {
    switch (direction) {
      case NORTH:
        return Direction.SOUTH;
      case SOUTH:
        return Direction.NORTH;
      case WEST:
        return Direction.EAST;
      case EAST:
        return Direction.WEST;
      default:
        throw new IllegalArgumentException("Invalid direction");
    }
  }
}
