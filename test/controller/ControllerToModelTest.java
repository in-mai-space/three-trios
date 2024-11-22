package controller;

import org.junit.Test;

import java.util.Optional;

import controller.mocks.MockGUIView;
import controller.mocks.MockModel;
import model.Utils;
import model.enums.GamePlayer;
import model.implementation.ThreeTriosModel;
import model.interfaces.GameModel;
import player.HumanPlayer;
import player.ThreeTriosPlayer;
import view.gui.GameGUIView;

import static org.junit.Assert.assertEquals;

public class ControllerToModelTest {

  @Test
  public void controllerConstructorWithMockModel() {
    Appendable out = new StringBuilder();
    GameModel model = new MockModel(out);
    GameGUIView view = new MockGUIView(model, out);
    ThreeTriosPlayer player = new HumanPlayer(model);
    new ThreeTriosController(model, player, view, GamePlayer.RED);
    model.startGame(false);
    // when controller is constructed, it adds itself as the observer
    assertEquals(out.toString(), "Model adds controller as observer\n" +
            "Model starts game\n");
  }

  @Test
  public void controllerPlaceCardModelCheckGameOverAndPlaceCard() {
    Appendable out = new StringBuilder();
    GameModel model = new MockModel(out);
    GameGUIView view = new MockGUIView(model, new StringBuilder());
    ThreeTriosPlayer player = new HumanPlayer(model);
    GameController controller = new ThreeTriosController(model, player, view, GamePlayer.RED);
    model.startGame(false);
    controller.selectCard(0, GamePlayer.RED);
    controller.placeCard(0, 0);
    // when controller is constructed, it adds itself as the observer
    assertEquals(out.toString(), "Model adds controller as observer\n" +
            "Model starts game\n" +
            "Check if game is over\n" +
            "Place card with index 0 into row 0 and col 0\n");
  }
}
