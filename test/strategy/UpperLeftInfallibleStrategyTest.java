package strategy;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import controller.GameConfigParser;
import model.Utils;
import model.enums.CellType;
import model.enums.GamePlayer;
import model.implementation.ThreeTriosModel;
import model.interfaces.Cell;
import model.interfaces.GameModel;
import strategy.Move;
import strategy.Pair;
import strategy.infallible.InfallibleGameStrategy;
import strategy.infallible.UpperLeftInfallibleStrategy;

import static org.junit.Assert.assertEquals;

public class UpperLeftInfallibleStrategyTest {

  @Test
  public void testNoHolesGridWithRealModel() {
    GameModel model = Utils.loadModel("no_holes.txt", "big_cards.txt");
    InfallibleGameStrategy mostUpperLeftestStrat = new UpperLeftInfallibleStrategy();
    Pair<Move, Integer> firstMove = mostUpperLeftestStrat.decideMove(model, GamePlayer.RED);
    assertEquals(firstMove, new Pair<>(new Move(model.getHand(GamePlayer.RED).get(0), 0,0), 0));

    model.placeCard(0, 0, 0);

    Pair<Move, Integer> secondMove = mostUpperLeftestStrat.decideMove(model, GamePlayer.RED);
    assertEquals(secondMove, new Pair<>(new Move(model.getHand(GamePlayer.RED).get(0), 0,1), 0));

    model.placeCard(0, 1, 1);

    assertEquals(secondMove, new Pair<>(new Move(model.getHand(GamePlayer.RED).get(0), 0,1), 0));

    model.placeCard(0, 0, 1);
    model.placeCard(0, 0, 2);

    Pair<Move, Integer> thirdMove = mostUpperLeftestStrat.decideMove(model, GamePlayer.RED);
    assertEquals(thirdMove, new Pair<>(new Move(model.getHand(GamePlayer.RED).get(0), 1,0), 1));

    model.placeCard(0, 1, 0);

    Pair<Move, Integer> fourthMove = mostUpperLeftestStrat.decideMove(model, GamePlayer.RED);
    assertEquals(fourthMove, new Pair<>(new Move(model.getHand(GamePlayer.RED).get(0), 1,2), 1));
  }

  @Test
  public void testSimpleGridWithRealModel() {
    GameModel model = Utils.loadModel("simple_grid.txt", "big_cards.txt");
    InfallibleGameStrategy mostUpperLeftestStrat = new UpperLeftInfallibleStrategy();
    Pair<Move, Integer> firstMove = mostUpperLeftestStrat.decideMove(model, GamePlayer.RED);
    assertEquals(firstMove, new Pair<>(new Move(model.getHand(GamePlayer.RED).get(0), 0,0), 0));
    // first empty cell

    model.placeCard(0, 0, 0);

    Pair<Move, Integer> secondMove = mostUpperLeftestStrat.decideMove(model, GamePlayer.RED);
    assertEquals(secondMove, new Pair<>(new Move(model.getHand(GamePlayer.RED).get(0), 1,0), 0));
    // second empty cell that is not a hole

    model.placeCard(0, 1, 0);

    Pair<Move, Integer> thirdMove = mostUpperLeftestStrat.decideMove(model, GamePlayer.RED);
    assertEquals(thirdMove, new Pair<>(new Move(model.getHand(GamePlayer.RED).get(0), 1,3), 0));
  }
}