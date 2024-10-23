package model.components.enums;

/**
 * Enum representing attack values in a game.
 * Each enum constant corresponds to a specific attack value, with
 * the values ranging from 1 to 10 (where 10 is represented as "A").
 */
public enum AttackValue {
  ONE(1), TWO(2), THREE(3), FOUR(4),
  FIVE(5), SIX(6), SEVEN(7), EIGHT(8),
  NINE(9), A(10);

  private final int value;

  /**
   * Constructor for the AttackValue enum.
   *
   * @param num the numeric value associated with the attack value.
   */
  private AttackValue(int num) {
    this.value = num;
  }

  /**
   * Gets the integer value associated with this attack value.
   *
   * @return the integer representation of the attack value.
   */
  public int getValue() {
    return this.value;
  }

  /**
   * Returns the string representation of the attack value.
   * If the value is 10, it returns "A"; otherwise, it returns
   * the string representation of the numeric value.
   *
   * @return the string representation of the attack value.
   */
  @Override
  public String toString() {
    return (this.value == 10) ? "A" : String.valueOf(this.value);
  }
}
