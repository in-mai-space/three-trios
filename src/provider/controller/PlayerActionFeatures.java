package provider.controller;

/**
 * An interface that subscribes itself to the view to
 * give the controller updates.
 */
public interface PlayerActionFeatures {

  /**
   * A method that handles user clicks and turns them into
   * move on the board.
   */
  void onCardSelected(int cardSelected, int cellRow, int cellCol, int cardColor);
}
