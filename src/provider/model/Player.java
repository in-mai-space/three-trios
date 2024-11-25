package provider.model;

import java.util.ArrayList;

/**
 * An interface of the methods necessary to build a Player.
 */
public interface Player {

  /**
   * Returns the color of the player.
   *
   * @return a color
   */
  CardColor getPlayerColor();

  /**
   * Returns the current hand of the player.
   *
   * @return a list of ThreeTrioCards
   */
  ArrayList<ProviderCard> getHand();

  /**
   * Tells us if the player is human or a machine.
   *
   * @return true if the player is a machine
   */
  boolean isMachPlayer();


  int[] playTurnCoords(int cardIdx, int row, int col);

  /**
   * Returns the strategy the player is using.
   *
   * @return a Strategy the player is using.
   */
  Strategy getStrategy();
}

