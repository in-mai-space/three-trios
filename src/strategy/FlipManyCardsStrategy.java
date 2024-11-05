package strategy;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import model.enums.GamePlayer;
import model.interfaces.Cell;
import model.interfaces.ReadOnlyGameModel;

public class FlipManyCardsStrategy implements InfallibleGameStrategy {
  @Override
  public Pair<Move, Integer> decideMove(ReadOnlyGameModel model, GamePlayer player) {
    Map<Move, Integer> moves = new HashMap<>();
    List<Cell> playerHand = model.getHand(player);
    getAllMoves(moves, playerHand, model);
    Map<Move, Integer> maxMoves = filterMaxMoves(moves);
    Optional<Pair<Move, Integer>> potentialBestMove = getBestMoveWithTieBreaking(moves, playerHand);
    if (potentialBestMove.isEmpty()) {
      return chooseUpperLeftOpenPosition(model, playerHand);
    }
    throw new IllegalStateException("Cannot find a move");
  }

  private Map<Move, Integer> filterMaxMoves(Map<Move, Integer> moves) {
    int maxFlippedCards = moves.values().stream().max(Integer::compare).orElse(0);
    return moves.entrySet().stream()
            .filter(entry -> entry.getValue() == maxFlippedCards)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  private void getAllMoves(Map<Move,Integer> map, List<Cell> playerHand, ReadOnlyGameModel model) {
    for (Cell card : playerHand) {
      checkCardPlacementInGrid(map, model, card);
    }
  }

  private void checkCardPlacementInGrid(Map<Move,Integer> map, ReadOnlyGameModel model, Cell handCard) {
    Cell[][] grid = model.getGrid();
    for (int row = 0; row < grid.length; row++) {
      for (int col = 0; col < grid[0].length; col++) {
        if (model.canPlaceCard(row, col)) {
          int flippedCards = model.countCardFlip(handCard, row, col);
          map.put(new Move(handCard, row, col), flippedCards);
        }
      }
    }
  }

  private Optional<Pair<Move, Integer>> getBestMoveWithTieBreaking(Map<Move, Integer> maxMoves, List<Cell> playerHand) {
    return maxMoves.keySet().stream()
            .sorted(Comparator.comparingInt(Move::getRow)
                    .thenComparingInt(Move::getCol)
                    .thenComparingInt(move -> playerHand.indexOf(move.getCard())))
            .map(bestMove -> new Pair<>(bestMove, maxMoves.get(bestMove)))
            .findFirst();
  }

  private Pair<Move, Integer> chooseUpperLeftOpenPosition(ReadOnlyGameModel model, List<Cell> playerHand) {
    Cell[][] grid = model.getGrid();
    for (int row = 0; row < grid.length; row++) {
      for (int col = 0; col < grid[0].length; col++) {
        if (model.canPlaceCard(row, col)) {
          return new Pair<>(new Move(playerHand.get(0), row, col),
                  model.countCardFlip(playerHand.get(0), row, col));
        }
      }
    }
    throw new IllegalStateException("Cannot find a move");
  }
}
