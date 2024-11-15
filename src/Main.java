import java.util.List;

import controller.GameConfigParser;
import controller.ThreeTriosController;
import model.Utils;
import model.enums.CellType;
import model.enums.GamePlayer;
import model.implementation.ThreeTriosModel;
import model.implementation.ThreeTriosViewModel;
import model.interfaces.Cell;
import model.interfaces.GameModel;
import player.HumanPlayer;
import player.MachinePlayer;
import player.ThreeTriosPlayer;
import strategy.infallible.FlipCardsInfallibleStrategy;
import strategy.infallible.UpperLeftInfallibleStrategy;
import view.gui.GameGUIView;
import view.gui.ThreeTriosView;

/**
 * Represents the main class to run the view.
 */
public class Main {
  /**
   * The main method that initializes the game and starts it.
   * It loads the grid configuration, sets up the model and controller,
   * places cards on the grid, and creates the view for the user to interact with.
   *
   * @param args Command line arguments (not used)
   */
  public static void main(String[] args) {
    CellType[][] cellTypes = GameConfigParser.getCellTypes(
            Utils.getFilePath("no_holes.txt", "grid"));
    List<Cell> cells = GameConfigParser.getCells(Utils.getFilePath("big_cards.txt", "cards"));
    GameModel model = new ThreeTriosModel(cellTypes, cells);

//    ThreeTriosPlayer player1 = new HumanPlayer(model);
//    ThreeTriosPlayer player2 = new HumanPlayer(model);

    ThreeTriosPlayer player1 = new MachinePlayer(model, new UpperLeftInfallibleStrategy());
    ThreeTriosPlayer player2 = new MachinePlayer(model, new FlipCardsInfallibleStrategy());

    GameGUIView player1View = new ThreeTriosView(new ThreeTriosViewModel(model));
    GameGUIView player2View = new ThreeTriosView(new ThreeTriosViewModel(model));

    new ThreeTriosController(model, player1, player1View, GamePlayer.RED);
    new ThreeTriosController(model, player2, player2View, GamePlayer.BLUE);

    model.startGame(true);
  }
}