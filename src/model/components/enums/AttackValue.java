package model.components.enums;

public enum AttackValue {
  ONE(1), TWO(2), THREE(3), FOUR(4),
  FIVE(5), SIX(6), SEVEN(7), EIGHT(8),
  NINE(9), A(10);

  private final int value;

  private AttackValue(int num) {
    this.value = num;
  }

  public int getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    return (this.value == 10) ? "A" : String.valueOf(this.value);
  }
}
