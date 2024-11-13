package strategy.infallible;

import java.util.List;

import model.enums.GamePlayer;
import model.interfaces.Cell;
import model.interfaces.ReadOnlyGameModel;
import strategy.Move;
import strategy.Pair;
import strategy.ThreeTriosMove;

/**
 * Represent the strategy that return moves in the first empty cell it can be found. This strategy
 * will throw exception if it cannot find any move.
 */
public class UpperLeftInfallibleStrategy implements InfallibleGameStrategy {

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
    if (model == null || player == null) {
      throw new IllegalArgumentException("Model or player cannot be null");
    }
    Cell[][] grid = model.getGrid();
    List<Cell> playerHand = model.getHand(player);
    for (int row = 0; row < grid.length; row++) {
      for (int col = 0; col < grid[0].length; col++) {
        if (model.canPlaceCard(row, col)) {
          return new Pair<>(new ThreeTriosMove(playerHand.get(0), row, col),
                  model.countCardFlip(playerHand.get(0), row, col));
        }
      }
    }
    throw new IllegalStateException("Cannot find a move");
  }
}
