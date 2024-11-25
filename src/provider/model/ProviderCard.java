package provider.model;

/**
 * Interface representing a card in the ThreeTrios Game.
 */
public interface ProviderCard extends Card {

  /**
   * Returns the north value of the card.
   * @return a value
   */
  int getNorthVal();

  /**
   * Returns the north value of the card in String format.
   * @return a value
   */
  String getNorthValString();

  /**
   * Returns the east value of the card.
   * @return a value
   */
  int getEastVal();

  /**
   * Returns the east value of the card in String format.
   * @return a value
   */
  String getEastValString();

  /**
   * Returns the south value of the card.
   * @return a value
   */
  int getSouthVal();

  /**
   * Returns the south value of the card in String format.
   * @return a value
   */
  String getSouthValString();

  /**
   * Returns the west value of the card.
   * @return a value
   */
  int getWestVal();

  /**
   * Returns the west value of the card in String format.
   * @return a value
   */
  String getWestValString();

  /**
   * Returns the identifier (name) of the card.
   * @return a card's name
   */
  String getIdentifier();

  /**
   * Changes the color of the card.
   * @param color a new color to set to the card
   */
  void setCardColor(CardColor color);

  /**
   * Returns the color of the card.
   * @return a card's color
   */
  CardColor getColor();

  /**
   * Provides a string representation of the card.
   * @return the string representation of the card
   */
  String toString();
}

