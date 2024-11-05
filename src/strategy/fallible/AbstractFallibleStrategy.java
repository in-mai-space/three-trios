package strategy.fallible;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import model.interfaces.Cell;
import strategy.Move;
import strategy.Pair;

abstract class AbstractFallibleStrategy implements FallibleGameStrategy {
  protected Optional<Pair<Move, Integer>> getBestMoveWithTieBreaking(Map<Move, Integer> maxMoves, List<Cell> playerHand) {
    return maxMoves.keySet().stream()
            .sorted(Comparator.comparingInt(Move::getRow) // most upper
                    .thenComparingInt(Move::getCol) // most left
                    .thenComparingInt(move -> playerHand.indexOf(move.getCard())))
            // index of cards in hand closest to 0
            .map(bestMove -> new Pair<>(bestMove, maxMoves.get(bestMove)))
            .findFirst();
  }
}
