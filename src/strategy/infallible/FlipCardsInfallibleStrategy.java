package strategy.infallible;

import java.util.Optional;

import model.enums.GamePlayer;
import model.interfaces.ReadOnlyGameModel;
import strategy.Move;
import strategy.Pair;
import strategy.fallible.FlipCardsFallibleStrategy;

/**
 * Represent the strategy that attempts to flip as many cards as possible. Must return a move
 * and fall back to upper left strategy if this strategy fails. This strategy will throw exception
 * if no strategies can be found.
 */
public class FlipCardsInfallibleStrategy implements InfallibleGameStrategy {
  private final FlipCardsFallibleStrategy flipCardsStrategy;
  private final UpperLeftInfallibleStrategy fallBackUpperLeft;

  /**
   * Construct a new instance of infallible flipping as many cards as possible strategy.
   */
  public FlipCardsInfallibleStrategy() {
    this.flipCardsStrategy = new FlipCardsFallibleStrategy();
    this.fallBackUpperLeft = new UpperLeftInfallibleStrategy();
  }

  /**
   * Find the next best move for a player in the game.
   *
   * @param model read only game model to track current state of game
   * @param player player to find the move for
   * @return a next best move for a player
   * @throws IllegalStateException if a move is not found
   */
  public Pair<Move, Integer> decideMove(ReadOnlyGameModel model, GamePlayer player) {
    Optional<Pair<Move, Integer>> flipCardsMove = flipCardsStrategy.decideMove(model, player);
    return flipCardsMove.orElseGet(() -> fallBackUpperLeft.decideMove(model, player));
  }
}
