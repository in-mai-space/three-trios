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
import model.interfaces.ReadOnlyGameModel;
import player.HumanPlayer;
import player.MachinePlayer;
import player.ThreeTriosPlayer;
import strategy.infallible.CornerInfallibleStrategy;
import strategy.infallible.FlipCardsInfallibleStrategy;
import view.gui.GameGUIView;
import view.gui.ThreeTriosView;

/**
 * Main class to set up and start the Three Trios game.
 * It handles reading game configurations, setting up players,
 * and initializing the game model, view, and controllers.
 */
public class Main {

  /**
   * The main method that initializes the game, sets up the model,
   * and starts the game with the appropriate player configurations.
   * It processes the command-line arguments to configure the player types
   * (either human or machine with a specified strategy).
   *
   * @param args Command line arguments, where:
   *             - args[0] specifies the first player's type ("human", "strategy1", "strategy2")
   *             - args[1] specifies the second player's type ("human", "strategy1", "strategy2")
   *             If the arguments are not valid or there are errors constructing model, view or
   *             controller, the game will not start.
   */
  public static void main(String[] args) {
    String firstPlayer = args.length < 2 ? "human" : args[0];
    String secondPlayer = args.length < 2 ? "human" : args[1];

    try {
      CellType[][] cellTypes = GameConfigParser.getCellTypes(
              Utils.getFilePath("complex_grid.txt", "grid"));
      List<Cell> cells = GameConfigParser.getCells(Utils.getFilePath("big_cards.txt", "cards"));

      GameModel model = new ThreeTriosModel(cellTypes, cells);
      ReadOnlyGameModel viewModel = new ThreeTriosViewModel(model);

      ThreeTriosPlayer player1 = createPlayer(firstPlayer, viewModel);
      ThreeTriosPlayer player2 = createPlayer(secondPlayer, viewModel);

      GameGUIView player1View = new ThreeTriosView(viewModel);
      GameGUIView player2View = new ThreeTriosView(viewModel);

      new ThreeTriosController(model, player1, player1View, GamePlayer.RED);
      new ThreeTriosController(model, player2, player2View, GamePlayer.BLUE);

      model.startGame(true);
    }
    catch (IllegalStateException | IllegalArgumentException exception) {
      System.out.println("Game cannot be launched");
    }
  }

  /**
   * Creates a player based on the specified player type.
   * If the player type is "human", a HumanPlayer is created.
   * If the player type is "strategy1", a MachinePlayer with FlipCardsInfallibleStrategy is created.
   * If the player type is "strategy2", a MachinePlayer with CornerInfallibleStrategy is created.
   *
   * @param playerType The type of the player (either "human", "strategy1", or "strategy2")
   * @param model The read-only game model to be passed to the player
   * @return The corresponding ThreeTriosPlayer (either HumanPlayer or MachinePlayer)
   * @throws IllegalArgumentException if the player type is invalid
   */
  private static ThreeTriosPlayer createPlayer(String playerType, ReadOnlyGameModel model) {
    switch (playerType) {
      case "human":
        return new HumanPlayer(model);
      case "strategy1":
        return new MachinePlayer(model, new FlipCardsInfallibleStrategy());
      case "strategy2":
        return new MachinePlayer(model, new CornerInfallibleStrategy());
      default:
        throw new IllegalArgumentException("Player type not found");
    }
  }
}
