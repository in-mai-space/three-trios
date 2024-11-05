package strategy.infallible;

import model.enums.GamePlayer;
import model.interfaces.ReadOnlyGameModel;
import strategy.Move;
import strategy.Pair;

/**
 * Represents the ThreeTriosStrategy for game strategy.
 */
public interface InfallibleGameStrategy {
  Pair<Move, Integer> decideMove(ReadOnlyGameModel model, GamePlayer player);
}
