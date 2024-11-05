package strategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import model.enums.Direction;
import model.enums.GamePlayer;
import model.interfaces.Cell;
import model.interfaces.ReadOnlyGameModel;

public class GoToCornerStrategy implements InfallibleGameStrategy {
  @Override
  public Pair<Move, Integer> decideMove(ReadOnlyGameModel model, GamePlayer player) {
    List<Pair<Integer, Integer>> cornerPositions = new ArrayList<>();
    Cell[][] grid = model.getGrid();
    int maxRow = grid.length - 1;
    int maxCol = grid[0].length - 1;

    // corner positions
    cornerPositions.add(new Pair<>(0, 0));
    cornerPositions.add(new Pair<>(0, maxCol));
    cornerPositions.add(new Pair<>(maxRow, 0));
    cornerPositions.add(new Pair<>(maxRow, maxCol));

    // remove unavailable corners
    cornerPositions.removeIf(corner -> !model.canPlaceCard(corner.getKey(), corner.getValue()));

    List<Cell> playerHand = model.getHand(player);

    if (cornerPositions.isEmpty()) {
      return chooseUpperLeftOpenPosition(model, playerHand);
    }
    else {
      Pair<Move, Integer> bestMove = null;

      for (Pair<Integer, Integer> corner : cornerPositions) {
        Direction verticalDir = (corner.getKey() == 0) ? Direction.SOUTH : Direction.NORTH;
        Direction horizontalDir = (corner.getValue() == 0) ? Direction.EAST : Direction.WEST;

        // find the best card based on attack values in the two exposed directions
        Cell bestCard = bestCard(verticalDir, horizontalDir, playerHand);
        if (bestCard != null) {
          int row = corner.getKey();
          int col = corner.getValue();
          int flips = model.countCardFlip(bestCard, row, col);

          Pair<Move, Integer> currentMove = new Pair<>(new Move(bestCard, row, col), flips);

          if (bestMove == null ||
                  flips > bestMove.getValue() ||
                  (flips == bestMove.getValue() &&
                          (row < bestMove.getKey().getRow() ||
                                  (row == bestMove.getKey().getRow() && col < bestMove.getKey().getCol())))) {
            bestMove = currentMove;
          }
        }
      }
      return bestMove;
    }
  }

  // chooses the best card based on the specified vertical and horizontal attack values
  private Cell bestCard(Direction vertical, Direction horizontal, List<Cell> hand) {
    return hand.stream()
            .max(Comparator.comparingInt((Cell cell) -> cell.getAttackValue(vertical))
                    .thenComparingInt(cell -> cell.getAttackValue(horizontal))
                    .thenComparingInt(hand::indexOf))  // break ties by index in hand
            .orElse(null);
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
