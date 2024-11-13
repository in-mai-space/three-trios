package model.interfaces;

import model.enums.AttackValue;
import model.enums.Direction;
import model.enums.GamePlayer;

/**
 * Represents a cell in the game.
 */
public interface Cell {
  /**
   * Get the name of the cell.
   *
   * @return name of the cell as String
   */
  String getName();

  /**
   * Get the attack value on the cell given a direction (north, south, east or west).
   *
   * @param direction to get the attack value on the cell
   * @return the attack value on the cell
   */
  int getAttackValue(Direction direction);

  /**
   * Get all attack values following order north, south, east, west. Modifying this list
   * does not change the values on the cell.
   *
   * @return list of attack values
   */
  AttackValue[] getAllAttackValues();

  /**
   * Checks if this card beats the other cell's attack values given a direction.
   *
   * @param that the other cell to battle with
   * @param direction direction to compare the attack value
   * @return true if this cell beats other cell's attack values in given direction
   * @throws IllegalArgumentException if cell is null
   */
  boolean beats(Cell that, Direction direction);

  /**
   * Get the current owner of the cell.
   *
   * @return the current owner of the cell
   * @throws IllegalStateException if cell currently does not have an owner
   */
  GamePlayer getOwner();

  /**
   * Set the cell's new owner.
   *
   * @param owner new owner of the cell
   * @throws IllegalArgumentException if owner is null
   */
  void setOwner(GamePlayer owner);

  /**
   * Get a copy of the cell.
   */
  Cell getCopy();
}
