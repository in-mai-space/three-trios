import java.util.List;

import controller.GameConfigParser;
import controller.GameController;
import controller.ThreeTriosController;
import model.Utils;
import model.enums.CellType;
import model.implementation.ThreeTriosModel;
import model.implementation.ThreeTriosViewModel;
import model.interfaces.Cell;
import model.interfaces.GameModel;
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
                Utils.getFilePath("complex_grid.txt", "grid"));
        List<Cell> cells = GameConfigParser.getCells(Utils.getFilePath("big_cards.txt", "cards"));
        GameModel model = new ThreeTriosModel(cellTypes, cells);
        Appendable log = System.out;
        GameController controller = new ThreeTriosController(model, log);
        model.startGame(false);
        model.placeCard(0, 0, 0);
        model.placeCard(0, 0, 4);
        model.placeCard(2, 0, 3);
        model.placeCard(0, 3, 0);
        model.placeCard(2, 3, 1);
        model.placeCard(2, 2, 1);
        ThreeTriosView view = new ThreeTriosView(new ThreeTriosViewModel(model));
        controller.setView(view);
        view.makeVisible();
    }
}
