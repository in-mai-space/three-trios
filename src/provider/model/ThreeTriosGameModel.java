package provider.model;

import java.util.ArrayList;

/**
 * Behaviors for a two player game of Three Trios.
 * The game consists of three structures:
 * <ul>
 *   <li>A deck of cards to draw from</li>
 *   <li>A hand for each player to play from</li>
 *   <li>A grid to play to</li>
 * </ul>
 * The goal of the game is to fill the grid with cards from each players hand
 * in order to win a battle and flip the opponents cards.
 * Once the grid is filled whoever has ownership of the most cards on the grid wins.
 * @param <C> the type of cards used
 */
public interface ThreeTriosGameModel<C extends Card> extends ReadOnlyThreeTriosGameModel {
  /**
   * Starts the game with the given options. The deck given is used
   * to set up the player's hands. Modifying the deck given to this method
   * will not modify the game state in any way.
   *
   * @param grid the grid used to set up and play the game
   * @param deck the cards used to set up and play the game
   * @param handSize the maximum number of cards allowed in the player's hands
   * @param red player 1
   * @param blue player 2
   */
  void startGame(GridInt grid, ArrayList<C> deck, int handSize, Player red, Player blue);

  /**
   * Play the given card from the given player's hand to the grid.
   * The method can only be called once per turn.
   *
   * @param player    the player playing the card
   * @param cardIndex a 0-index number representing the card to play from the hand
   * @param row       the row of the grid the player wants to play to
   * @param col       the column of the grid the player wants to play to
   * @throws IllegalStateException    if the game has not started
   * @throws IllegalStateException    the game is over
   * @throws IllegalStateException    if a player tries to play twice in a row
   * @throws IllegalArgumentException if the row or column is out of bounds
   * @throws IllegalArgumentException if the cell being played to is a hole
   * @throws IllegalArgumentException if cardIndex < 0
   *                                  or greater/equal to the number of cards in hand
   */
  void playCard(Player player, int cardIndex, int row, int col);

  /**
   * Play the given card from the given player's hand to the grid.
   * The method can only be called once per turn.
   *
   * @param player the player drawing cards
   * @throws IllegalStateException if the deck is empty
   * @throws IllegalStateException if the player already has a full hand
   */
  void drawCard(Player player) throws Exception;

  void setModelActionFeatures(ModelStatusFeatures status);
}
