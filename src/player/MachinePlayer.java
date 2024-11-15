package player;

import controller.ControllerFeature;
import model.enums.GamePlayer;
import model.interfaces.ReadOnlyGameModel;
import strategy.Move;
import strategy.Pair;
import strategy.infallible.InfallibleGameStrategy;

public class MachinePlayer implements ThreeTriosPlayer {
  private final InfallibleGameStrategy strategy;
  private final ReadOnlyGameModel model;
  private ControllerFeature observer;
  private GamePlayer player;

  public MachinePlayer(ReadOnlyGameModel model, InfallibleGameStrategy strategy) {
    if (model == null || strategy == null) {
      throw new IllegalArgumentException("Model and strategy cannot be null");
    }
    this.strategy = strategy;
    this.model = model;
  }

  public void playCard() {
    if (!model.gameOver() && player == model.getCurrentPlayer()) {
      Pair<Move, Integer> nextMove = strategy.decideMove(model, player);
      int cardIndex = model.getHand(player).indexOf(nextMove.getKey().getCard());
      int row = nextMove.getKey().getRow();
      int col = nextMove.getKey().getCol();
      observer.selectCard(cardIndex);
      observer.placeCard(row, col);
    }
  }

  @Override
  public boolean addObserver(ControllerFeature observer) {
    this.observer = observer;
    this.player = observer.getPlayer();
    return true;
  }
}
