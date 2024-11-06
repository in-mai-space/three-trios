package strategy.infallible;

import java.util.Optional;

import model.enums.GamePlayer;
import model.interfaces.ReadOnlyGameModel;
import strategy.Move;
import strategy.Pair;
import strategy.fallible.CornerFallibleStrategy;

/**
 * Represent the game strategy of occupying 4 corners of the grid.
 */
public class CornerInfallibleStrategy implements InfallibleGameStrategy {
  private final CornerFallibleStrategy cornerStrategy;
  private final UpperLeftInfallibleStrategy fallBackUpperLeft;

  public CornerInfallibleStrategy() {
    this.cornerStrategy = new CornerFallibleStrategy();
    this.fallBackUpperLeft = new UpperLeftInfallibleStrategy();
  }

  public Pair<Move, Integer> decideMove(ReadOnlyGameModel model, GamePlayer player) {
    Optional<Pair<Move, Integer>> cornerMove = cornerStrategy.decideMove(model, player);
    return cornerMove.orElseGet(() -> fallBackUpperLeft.decideMove(model, player));
  }
}

