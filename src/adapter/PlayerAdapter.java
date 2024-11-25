package adapter;

import java.util.ArrayList;

import model.Utils;
import model.enums.GamePlayer;
import provider.model.CardColor;
import provider.model.Player;
import provider.model.Strategy;
import provider.model.ThreeTriosCard;

public class PlayerAdapter implements Player {
  private final GamePlayer color;

  public PlayerAdapter(GamePlayer color) {
    this.color = color;
  }

  /**
   * Returns the color of the player.
   *
   * @return a color
   */
  @Override
  public CardColor getPlayerColor() {
    return Utils.convertColor(color);
  }

  /**
   * Returns the current hand of the player.
   *
   * @return a list of ThreeTrioCards
   */
  @Override
  public ArrayList<ThreeTriosCard> getHand() {
    return null;
  }

  /**
   * Tells us if the player is human or a machine.
   *
   * @return true if the player is a machine
   */
  @Override
  public boolean isMachPlayer() {
    return false;
  }

  @Override
  public int[] playTurnCoords(int cardIdx, int row, int col) {
    return new int[0];
  }

  /**
   * Returns the strategy the player is using.
   *
   * @return a Strategy the player is using.
   */
  @Override
  public Strategy getStrategy() {
    return null;
  }
}
