import java.util.List;

import controller.GameConfigParser;
import model.Utils;
import model.enums.CellType;
import model.implementation.ThreeTriosModel;
import model.interfaces.Card;
import model.interfaces.GameModel;
import model.interfaces.Grid;
import view.gui.ThreeTriosView;

public class Main {
    public static void main(String[] args) {
        CellType[][] cellTypes = GameConfigParser.getCellTypes(
                Utils.getFilePath("complex_grid.txt", "grid"));
        List<Card> cards = GameConfigParser.getCards(Utils.getFilePath("big_cards.txt", "cards"));
        GameModel model = new ThreeTriosModel(cellTypes, cards);
        model.startGame(false);
        model.placeCard(0, 0, 0);
        model.placeCard(0, 0, 4);
        model.placeCard(2, 0, 3);
        model.placeCard(0, 3, 0);
        model.placeCard(2, 3, 1);
        model.placeCard(2, 2, 1);
        model.placeCard(0,0, 2);
        ThreeTriosView view = new ThreeTriosView(model);
        view.makeVisible();
    }
}
