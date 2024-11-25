package adapter;

import model.Utils;
import model.enums.AttackValue;
import model.enums.Direction;
import model.interfaces.Cell;
import provider.model.CardColor;
import provider.model.ThreeTriosCard;

public class CardAdapter implements ThreeTriosCard {
  private final Cell card;

  public CardAdapter(Cell card) {
    this.card = card;
  }

  /**
   * Returns the north value of the card.
   *
   * @return a value
   */
  @Override
  public int getNorthVal() {
    return card.getAttackValue(Direction.NORTH);
  }

  /**
   * Returns the north value of the card in String format.
   *
   * @return a value
   */
  @Override
  public String getNorthValString() {
    return checkIfA(card.getAttackValue(Direction.NORTH));
  }

  private static String checkIfA(int value) {
    if (value == 10) {
      return AttackValue.A.toString();
    }
    return String.valueOf(value);
  }

  /**
   * Returns the east value of the card.
   *
   * @return a value
   */
  @Override
  public int getEastVal() {
    return card.getAttackValue(Direction.EAST);
  }

  /**
   * Returns the east value of the card in String format.
   *
   * @return a value
   */
  @Override
  public String getEastValString() {
    return checkIfA(card.getAttackValue(Direction.EAST));
  }

  /**
   * Returns the south value of the card.
   *
   * @return a value
   */
  @Override
  public int getSouthVal() {
    return card.getAttackValue(Direction.SOUTH);
  }

  /**
   * Returns the south value of the card in String format.
   *
   * @return a value
   */
  @Override
  public String getSouthValString() {
    return checkIfA(card.getAttackValue(Direction.SOUTH));
  }

  /**
   * Returns the west value of the card.
   *
   * @return a value
   */
  @Override
  public int getWestVal() {
    return card.getAttackValue(Direction.WEST);
  }

  /**
   * Returns the west value of the card in String format.
   *
   * @return a value
   */
  @Override
  public String getWestValString() {
    return checkIfA(card.getAttackValue(Direction.WEST));
  }

  /**
   * Returns the identifier (name) of the card.
   *
   * @return a card's name
   */
  @Override
  public String getIdentifier() {
    return card.getName();
  }

  /**
   * Changes the color of the card.
   *
   * @param color a new color to set to the card
   */
  @Override
  public void setCardColor(CardColor color) {
    card.setOwner(Utils.convertColor(color));
  }


  /**
   * Returns the color of the card.
   *
   * @return a card's color
   */
  @Override
  public CardColor getColor() {
    return Utils.convertColor(card.getOwner());
  }
}
