package strategy.infallible;

import java.util.Optional;

import model.enums.GamePlayer;
import model.interfaces.ReadOnlyGameModel;
import strategy.Move;
import strategy.Pair;
import strategy.fallible.CornerFallibleStrategy;

/**
 * Represent the strategy that attempts to go to the corners and expose attack values that are
 * most unlikely to be flipped (or very high exposed attack value). Must return a move and fall
 * back to upper left strategy if fail. This strategy will throw exception if no strategies can
 * be found.
 */
public class CornerInfallibleStrategy implements InfallibleGameStrategy {
  private final CornerFallibleStrategy cornerStrategy;
  private final UpperLeftInfallibleStrategy fallBackUpperLeft;

  /**
   * Construct a new instance of infallible filling corners strategy.
   */
  public CornerInfallibleStrategy() {
    this.cornerStrategy = new CornerFallibleStrategy();
    this.fallBackUpperLeft = new UpperLeftInfallibleStrategy();
  }

  /**
   * Find the next best move for a player in the game.
   *
   * @param model read only game model to track current state of game
   * @param player player to find the move for
   * @return a next best move for a player
   * @throws IllegalStateException if a move is not found
   * @throws IllegalArgumentException if model or player is null
   */
  public Pair<Move, Integer> decideMove(ReadOnlyGameModel model, GamePlayer player) {
    Optional<Pair<Move, Integer>> cornerMove = cornerStrategy.decideMove(model, player);
    return cornerMove.orElseGet(() -> fallBackUpperLeft.decideMove(model, player));
  }
}

