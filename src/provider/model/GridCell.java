package provider.model;

public interface GridCell {
  /**
   * Determines whether the cell is a hole or not.
   * @return true if the cell is a hole
   */
  boolean isHole();

  /**
   * Gives the current card in the cell.
   * @return a card
   */
  ThreeTriosCard getCard();

  /**
   * Fills the cell with the given card if the cell is playable.
   * @param card the card used to change the cell
   */
  void setCellCard(ThreeTriosCard card);
}
