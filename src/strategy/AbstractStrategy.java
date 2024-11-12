package strategy;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import model.interfaces.Cell;

/**
 * Represent abstract class for strategy, which shares the breaking tie method between infallible
 * and fallible strategies.
 */
abstract public class AbstractStrategy {

  /**
   * Break ties between the moves if there are more than one next best moves. It will prioritize
   * the most upper card (smaller row index), leftest card (smaller column index), smaller index
   * of card in hand.
   *
   * @param maxMoves all the moves that increase score of a player
   * @param playerHand list of cells in player hand
   * @return the best move after breaking tie if it can find one, else return empty
   */
  protected Optional<Pair<Move, Integer>> getBestMoveWithTieBreaking(Map<Move, Integer> maxMoves,
                                                                     List<Cell> playerHand) {
    if (maxMoves == null || playerHand == null) {
      throw new IllegalArgumentException("Moves and hand cannot be null");
    }
    return maxMoves.keySet().stream()
            .sorted(Comparator.comparingInt(Move::getRow) // most upper
                    .thenComparingInt(Move::getCol) // most left
                    .thenComparingInt(move -> playerHand.indexOf(move.getCard())))
            // index of cards in hand closest to 0
            .map(bestMove -> new Pair<>(bestMove, maxMoves.get(bestMove)))
            .findFirst();
  }
}
