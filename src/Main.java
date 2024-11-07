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

public class Main {
    public static void main(String[] args) {
        CellType[][] cellTypes = GameConfigParser.getCellTypes(
                Utils.getFilePath("complex_grid.txt", "grid"));
        List<Cell> cells = GameConfigParser.getCells(Utils.getFilePath("big_cards.txt", "cards"));
        GameModel model = new ThreeTriosModel(cellTypes, cells);
        GameController controller = new ThreeTriosController(model);
        model.startGame(false);
        ThreeTriosView view = new ThreeTriosView(new ThreeTriosViewModel(model));
        controller.setView(view);
        view.makeVisible();
    }
}
