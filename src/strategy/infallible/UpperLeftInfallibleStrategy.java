package strategy.infallible;

import java.util.List;

import model.enums.GamePlayer;
import model.interfaces.Cell;
import model.interfaces.ReadOnlyGameModel;
import strategy.Move;
import strategy.Pair;

public class UpperLeftInfallibleStrategy implements InfallibleGameStrategy {
  public Pair<Move, Integer> decideMove(ReadOnlyGameModel model, GamePlayer player) {
    Cell[][] grid = model.getGrid();
    List<Cell> playerHand = model.getHand(player);
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
