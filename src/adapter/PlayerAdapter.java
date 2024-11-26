package adapter;

import java.util.ArrayList;

import provider.model.CardColor;
import provider.model.Player;
import provider.model.Strategy;
import provider.model.ThreeTriosCard;

/**
 * Represents a PlayerAdapter to work with the controller. This is more like a dummy class that
 * provides enough methods that are called by model.
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
   * Returns the current hand of the player. We do not have mechanism to get hand using a player,
   * so this method is not supported.
   *
   * @throws UnsupportedOperationException if method is called
   */
  @Override
  public ArrayList<ThreeTriosCard> getHand() {
    throw new UnsupportedOperationException("Method not supported");
  }

  /**
   * Tells us if the player is human or a machine. Our player does not have a way to check if a
   * player is machine player or not, so this method is unsupported.
   *
   * @throws UnsupportedOperationException if method is called
   */
  @Override
  public boolean isMachPlayer() {
    throw new UnsupportedOperationException("Method not supported");
  }

  /**
   * Keep track of the player turn coordinates. Since our model does not keep track of this data,
   * method is not supported.
   *
   * @param cardIdx card index
   * @param row row index (0-indexed)
   * @param col col index (0-indexed)
   * @throws UnsupportedOperationException if method is called
   */
  @Override
  public int[] playTurnCoords(int cardIdx, int row, int col) {
    throw new UnsupportedOperationException("Method not supported");
  }

  /**
   * Returns the strategy the player is using. Our player does not have the method strategy, so
   * this method is supported.
   *
   * @throws UnsupportedOperationException if method is called
   */
  @Override
  public Strategy getStrategy() {
    throw new UnsupportedOperationException("Method not supported");
  }
}
