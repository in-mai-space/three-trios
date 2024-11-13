package strategy.infallible;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import model.enums.GamePlayer;
import model.interfaces.Cell;
import model.interfaces.ReadOnlyGameModel;
import strategy.AbstractStrategy;
import strategy.Move;
import strategy.Pair;
import strategy.ThreeTriosMove;

/**
 * Represent the strategy that attempts to flip as many cards as possible. Must return a move.
 * This strategy will throw exception if no strategies can be found.
 */
public class FlipCardsInfallibleStrategy extends AbstractStrategy
        implements InfallibleGameStrategy {
  /**
   * Decide what is the next best move to play given the model and the player.
   *
   * @param model read only game model
   * @param player player in the game, Red or Blue
   * @return a next best move if it can find one, if not return empty
   * @throws IllegalArgumentException if model is null or player is null
   * @throws IllegalStateException if no move can be found
   */
  @Override
  public Pair<Move, Integer> decideMove(ReadOnlyGameModel model, GamePlayer player) {
    if (model == null || player == null) {
      throw new IllegalArgumentException("Model or player cannot be null");
    }
    Map<Move, Integer> moves = new HashMap<>();
    List<Cell> playerHand = model.getHand(player);
    // get all moves and its score into the new hashmap
    getAllPossibleMoves(moves, playerHand, model);
    // filter out only max score moves
    Map<Move, Integer> maxMoves = filterMaxScoreMoves(moves);
    // resolve ties between the max moves
    Optional<Pair<Move, Integer>> bestMove = getBestMoveWithTieBreaking(maxMoves, playerHand);
    if (bestMove.isEmpty()) {
      throw new IllegalStateException("Cannot find move");
    }
    return bestMove.get();
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
          map.put(new ThreeTriosMove(handCard, row, col), flippedCards +
                  model.getScore(model.getCurrentPlayer()));
        }
      }
    }
  }

  /**
   * Filter out the moves that can give the highest scores.
   *
   * @param moves map of all moves
   * @return map of moves that give the highest scores
   */
  private Map<Move, Integer> filterMaxScoreMoves(Map<Move, Integer> moves) {
    int maxScores = moves.values().stream().max(Integer::compare).orElse(0);
    return moves.entrySet().stream()
            .filter(entry -> entry.getValue() == maxScores)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }
}
