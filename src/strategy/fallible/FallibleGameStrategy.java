package strategy.fallible;

import java.util.Optional;

import model.enums.GamePlayer;
import model.interfaces.ReadOnlyGameModel;
import strategy.Move;
import strategy.Pair;

/**
 * Represents the interface for FallibleGameStrategy.
 */
public interface FallibleGameStrategy {

  /**
   * Decide what is the next best move to play given the model and the player.
   *
   * @param model read only game model
   * @param player player in the game, Red or Blue
   * @return a next best move if it can find one, if not return empty
   * @throws IllegalArgumentException if model or player is null
   */
  Optional<Pair<Move, Integer>> decideMove(ReadOnlyGameModel model, GamePlayer player);
}
