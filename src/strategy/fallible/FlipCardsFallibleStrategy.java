package strategy.fallible;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import model.enums.GamePlayer;
import model.interfaces.Cell;
import model.interfaces.ReadOnlyGameModel;
import strategy.Move;
import strategy.Pair;

/**
 * Represent the strategy that can flip as many cards as possible.
 */
public class FlipCardsFallibleStrategy extends AbstractFallibleStrategy {
  @Override
  public Optional<Pair<Move, Integer>> decideMove(ReadOnlyGameModel model, GamePlayer player) {
    Map<Move, Integer> moves = new HashMap<>();
    List<Cell> playerHand = model.getHand(player);
    // get all moves and its score into the new hashmap
    getAllPossibleMoves(moves, playerHand, model);
    // filter out only max score moves
    Map<Move, Integer> maxMoves = filterMaxScoreMoves(moves);
    // resolve ties between the max moves
    return getBestMoveWithTieBreaking(maxMoves, playerHand);
  }

  /**
   * Get all possible placements in grid for all cards in hand and add them to hash map.
   *
   * @param map map of moves
   * @param playerHand all cards in player's hand
   * @param model game model
   */
  private void getAllPossibleMoves(Map<Move,Integer> map, List<Cell> playerHand,
                                   ReadOnlyGameModel model) {
    for (Cell card : playerHand) {
      checkCardPlacementInGrid(map, model, card);
    }
  }

  /**
   * Get all possible placements in grid for a card and add them to hash map.
   *
   * @param map map of moves
   * @param model all cards in player's hand
   * @param handCard game model
   */
  private void checkCardPlacementInGrid(Map<Move,Integer> map, ReadOnlyGameModel model,
                                        Cell handCard) {
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

  /**
   * Filter out the moves that can flip the most cards .
   *
   * @param moves map of all moves
   * @return map of moves that flip the most cards
   */
  private Map<Move, Integer> filterMaxScoreMoves(Map<Move, Integer> moves) {
    int maxFlippedCards = moves.values().stream().max(Integer::compare).orElse(0);
    return moves.entrySet().stream()
            .filter(entry -> entry.getValue() == maxFlippedCards)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }
}
