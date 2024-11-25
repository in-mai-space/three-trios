package provider.model;


/**
 * Strategies to be used by a computer player to find
 * the most optimal cell and card to be played to and with.
 */
public interface Strategy {

  /**
   * Finds the optimal cell for placing a card according the Strategy.
   *
   * @param player The player using the strategy.
   * @return An array with two elements, [row, col], for the optimal cell coordinates.
   */
  int[] findOptimalCell(Player player);

  /**
   * If no optimal cell is found, play the first card available to the upper
   * leftmost cell that's available.
   * @param player The player.
   */
  int[] passAndPlayFirstCard(Player player);
}
