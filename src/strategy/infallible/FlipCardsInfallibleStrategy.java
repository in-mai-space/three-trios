package strategy.infallible;

import java.util.Optional;

import model.enums.GamePlayer;
import model.interfaces.ReadOnlyGameModel;
import strategy.Move;
import strategy.Pair;
import strategy.fallible.FlipCardsFallibleStrategy;

public class FlipCardsInfallibleStrategy implements InfallibleGameStrategy {
  private final FlipCardsFallibleStrategy flipCardsStrategy;
  private final UpperLeftInfallibleStrategy fallBackUpperLeft;

  public FlipCardsInfallibleStrategy() {
    this.flipCardsStrategy = new FlipCardsFallibleStrategy();
    this.fallBackUpperLeft = new UpperLeftInfallibleStrategy();
  }

  public Pair<Move, Integer> decideMove(ReadOnlyGameModel model, GamePlayer player) {
    Optional<Pair<Move, Integer>> flipCardsMove = flipCardsStrategy.decideMove(model, player);
    return flipCardsMove.orElseGet(() -> fallBackUpperLeft.decideMove(model, player));
  }
}
