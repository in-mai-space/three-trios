package strategy.infallible;

import model.enums.GamePlayer;
import model.interfaces.ReadOnlyGameModel;
import strategy.Move;
import strategy.Pair;

/**
 * Represents the ThreeTriosStrategy for game strategy.
 */
public interface InfallibleGameStrategy {

  /**
   * Find the next best move for a player in the game.
   *
   * @param model read only game model to track current state of game
   * @param player player to find the move for
   * @return a next best move for a player
   * @throws IllegalStateException if a move is not found
   * @throws IllegalArgumentException if model or player is null
   */
  Pair<Move, Integer> decideMove(ReadOnlyGameModel model, GamePlayer player);
}
