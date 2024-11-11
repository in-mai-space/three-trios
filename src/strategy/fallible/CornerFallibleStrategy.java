package strategy.fallible;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import model.enums.CellType;
import model.enums.Direction;
import model.enums.GamePlayer;
import model.interfaces.Cell;
import model.interfaces.ReadOnlyGameModel;
import strategy.Move;
import strategy.Pair;
import strategy.ThreeTriosMove;

/**
 * Represent the strategy that go to corner of the grid to expose the least amount
 * of attack values as possible. It will search for cards in hand that are hardest to flip,
 * meaning it has a high attack value in the direction that attack value is exposed, since there
 * is low probability of having another high card to flip that card.
 */
public class CornerFallibleStrategy extends AbstractFallibleStrategy {

  /**
   * Decide what is the next best move to play given the model and the player.
   *
   * @param model read only game model
   * @param player player in the game, Red or Blue
   * @return a next best move if it can find one, if not return empty
   * @throws IllegalArgumentException if model or player is null
   */
  @Override
  public Optional<Pair<Move, Integer>> decideMove(ReadOnlyGameModel model, GamePlayer player) {
    if (model == null || player == null) {
      throw new IllegalArgumentException("Model or player cannot be null");
    }
    List<Pair<Integer, Integer>> cornerPositions = getCornerPositions(model);
    List<Cell> playerHand = model.getHand(player);

    if (cornerPositions.isEmpty()) {
      return Optional.empty();
    }
    Map<Move, Integer> potentialMoves = getMovesForAllCorners(model, playerHand, cornerPositions);
    return getBestMoveWithTieBreaking(potentialMoves, playerHand);
  }

  /**
   * Get a list of corner position coordinates of the model's grid.
   *
   * @param model game model
   * @return list of all corner positions
   */
  private List<Pair<Integer, Integer>> getCornerPositions(ReadOnlyGameModel model) {
    List<Pair<Integer, Integer>> corners = new ArrayList<>();
    int maxRow = model.getGrid().length - 1;
    int maxCol = model.getGrid()[0].length - 1;

    corners.add(new Pair<>(0, 0));
    corners.add(new Pair<>(0, maxCol));
    corners.add(new Pair<>(maxRow, 0));
    corners.add(new Pair<>(maxRow, maxCol));

    corners.removeIf(corner -> !model.canPlaceCard(corner.getKey(), corner.getValue()));
    return corners;
  }

  /**
   * Find the best card for each corner and then add it to the map of moves.
   *
   * @param model game model
   * @param playerHand list of cards in player's hand
   * @param cornerPositions list of corners
   * @return a map of moves to its score
   */
  private Map<Move, Integer> getMovesForAllCorners(ReadOnlyGameModel model, List<Cell> playerHand,
                                                  List<Pair<Integer, Integer>> cornerPositions) {
    Map<Move, Integer> potentialMoves = new HashMap<>();
    for (Pair<Integer, Integer> corner : cornerPositions) {
      Cell bestCard = bestCardWithCorner(playerHand, model, corner);
      int flippedCount = model.countCardFlip(bestCard, corner.getKey(), corner.getValue());
      potentialMoves.put(new ThreeTriosMove(bestCard, corner.getKey(), corner.getValue()),
              flippedCount + model.getScore(model.getCurrentPlayer()));
    }
    return potentialMoves;
  }

  /**
   * Find the best card a specific corner coordinate.
   *
   * @param hand list of cards in player's hand
   * @param model game model
   * @param corner a specific corner of the grid
   * @return the best card from player's hand to be placed in this corner
   */
  private static Cell bestCardWithCorner(List<Cell> hand, ReadOnlyGameModel model,
                                         Pair<Integer, Integer> corner) {
    if (hand.isEmpty()) {
      throw new IllegalArgumentException("Hand cannot be empty");
    }
    Direction vertical = getVerticalDirection(corner);
    Direction horizontal = getHorizontalDirection(corner);
    boolean isVerticalBlocked = isDirectionBlocked(model, corner, vertical);
    boolean isHorizontalBlocked = isDirectionBlocked(model, corner, horizontal);

    Comparator<Cell> comparator = getComparator(isVerticalBlocked,
            isHorizontalBlocked, vertical, horizontal);

    return hand.stream()
            .max(comparator)
            .orElse(null);
  }

  /**
   * Get open vertical direction of a corner. For example if a corner has coordinate (0, 0),
   * then the open vertical direction is south, since north is blocked by the border of grid.
   *
   * @param corner corner coordinate in grid
   * @return vertical opened direction of the corner
   */
  private static Direction getVerticalDirection(Pair<Integer, Integer> corner) {
    return (corner.getKey() == 0) ? Direction.SOUTH : Direction.NORTH;
  }

  /**
   * Get open horizontal direction of a corner. For example if a corner has coordinate (0, 0),
   * then the open horizontal direction is east, since west is blocked by the border of grid.
   *
   * @param corner corner coordinate in grid
   * @return horizontal opened direction of the corner
   */
  private static Direction getHorizontalDirection(Pair<Integer, Integer> corner) {
    return (corner.getValue() == 0) ? Direction.EAST : Direction.WEST;
  }

  /**
   * Check if a direction of attack value in a corner is blocked or not.
   *
   * @param model game model
   * @param corner corner coordinate
   * @param direction direction of the corner coordinate
   * @return true if direction of attack value in a card is blocked, false otherwise
   */
  private static boolean isDirectionBlocked(ReadOnlyGameModel model, Pair<Integer, Integer> corner,
                                            Direction direction) {
    int row = corner.getKey();
    int col = corner.getValue();

    switch (direction) {
      case NORTH:
        return row > 0 && (model.getGrid()[row - 1][col] != null
                || model.getCellTypes()[row - 1][col] == CellType.HOLE);
      case SOUTH:
        return row < model.getGrid().length - 1 && (model.getGrid()[row + 1][col] != null
                || model.getCellTypes()[row + 1][col] == CellType.HOLE);
      case WEST:
        return col > 0 && (model.getGrid()[row][col - 1] != null
                || model.getCellTypes()[row][col - 1] == CellType.HOLE);
      case EAST:
        return col < model.getGrid()[0].length - 1 && (model.getGrid()[row][col + 1] != null
                || model.getCellTypes()[row][col + 1] == CellType.HOLE);
      default:
        return false;
    }
  }

  /**
   * Comparator to sort the best moves for this strategy
   *
   * @param verticalBlocked true if a corner is vertically blocked, false otherwise
   * @param horizontalBlocked true if a corner is horizontally blocked, false otherwise
   * @param vertical direction of vertical attack value (north or south)
   * @param horizontal direction of horizontal attack value (east or west)
   * @return comparator to sort the best moves
   */
  private static Comparator<Cell> getComparator(boolean verticalBlocked, boolean horizontalBlocked,
                                                Direction vertical, Direction horizontal) {
    if (verticalBlocked && !horizontalBlocked) {
      return Comparator.comparingInt((Cell cell) -> cell.getAttackValue(horizontal));
    }
    else if (horizontalBlocked && !verticalBlocked) {
      return Comparator.comparingInt((Cell cell) -> cell.getAttackValue(vertical));
    }
    else {
      return Comparator.comparingInt((Cell cell) -> cell.getAttackValue(vertical))
              .thenComparingInt(cell -> cell.getAttackValue(horizontal));
    }
  }
}
