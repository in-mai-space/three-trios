package strategy.fallible;

import java.util.Optional;

import model.enums.GamePlayer;
import model.interfaces.ReadOnlyGameModel;
import strategy.Move;
import strategy.Pair;

public interface FallibleGameStrategy {
  Optional<Pair<Move, Integer>> decideMove(ReadOnlyGameModel model, GamePlayer player);
}
