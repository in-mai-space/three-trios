package controller;

import org.junit.Test;

import model.Utils;
import model.enums.AttackValue;
import model.enums.GamePlayer;
import model.implementation.ThreeTriosCell;
import model.interfaces.GameModel;
import player.MachinePlayer;
import player.ThreeTriosPlayer;
import strategy.infallible.CornerInfallibleStrategy;
import view.gui.GameGUIView;
import view.gui.ThreeTriosView;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {
  @Test
  public void testPlayerPlayCardToController() {
    GameModel model = Utils.loadModel("no_holes.txt", "big_cards.txt");
    GameGUIView view = new ThreeTriosView(model);
    ThreeTriosPlayer player = new MachinePlayer(model, new CornerInfallibleStrategy());
    GameController controller = new ThreeTriosController(model, player, view, GamePlayer.RED);
    // check if player plays card actually get to model
    // corner strategy first places card in (0, 0)
    player.playCard();
    assertEquals(new ThreeTriosCell(new AttackValue[]{ AttackValue.SIX, AttackValue.TWO,
            AttackValue.NINE, AttackValue.THREE}, "CorruptKing", GamePlayer.RED),
            model.getCardAt(0,0));
  }
}
