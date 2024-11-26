package adapter;

import java.util.ArrayList;

import provider.model.CardColor;
import provider.model.Player;
import provider.model.Strategy;
import provider.model.ThreeTriosCard;

/**
 * Represents a PlayerAdapter to work with the controller.
 */
public class PlayerAdapter implements Player {
  private final CardColor color;

  /**
   * Construct a player adapter given a color.
   *
   * @param color card color
   */
  public PlayerAdapter(CardColor color) {
    if (color == null) {
      throw new IllegalArgumentException("Color cannot be null");
    }
    this.color = color;
  }

  /**
   * Returns the color of the player.
   *
   * @return a color
   */
  @Override
  public CardColor getPlayerColor() {
    return color;
  }

  /**
   * Returns the current hand of the player.
   *
   * @return a list of ThreeTrioCards
   */
  @Override
  public ArrayList<ThreeTriosCard> getHand() {
    throw new UnsupportedOperationException("Method not supported");
  }

  /**
   * Tells us if the player is human or a machine.
   *
   * @return true if the player is a machine
   */
  @Override
  public boolean isMachPlayer() {
    throw new UnsupportedOperationException("Method not supported");
  }

  @Override
  public int[] playTurnCoords(int cardIdx, int row, int col) {
    throw new UnsupportedOperationException("Method not supported");
  }

  /**
   * Returns the strategy the player is using.
   *
   * @return a Strategy the player is using.
   */
  @Override
  public Strategy getStrategy() {
    throw new UnsupportedOperationException("Method not supported");
  }
}
