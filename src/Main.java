import model.Utils;
import model.implementation.ThreeTriosModel;
import view.gui.ThreeTriosView;

public class Main {
    public static void main(String[] args) {
        ThreeTriosModel model = ThreeTriosModel.fromFiles(
                Utils.getFilePath("complex_grid.txt", "grid"),
                Utils.getFilePath("big_cards.txt", "cards")
        );
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
