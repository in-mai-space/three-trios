package strategy;

import model.enums.GamePlayer;
import model.interfaces.ReadOnlyGameModel;

/**
 * Represents the ThreeTriosStrategy for game strategy.
 */
public interface InfallibleGameStrategy {
  Pair<Move, Integer> decideMove(ReadOnlyGameModel model, GamePlayer player);
}
