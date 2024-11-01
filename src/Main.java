import model.Utils;
import model.implementation.ThreeTriosModel;
import view.GameFrame;

public class Main {
    public static void main(String[] args) {
        ThreeTriosModel model = ThreeTriosModel.fromFiles(
                Utils.getFilePath("complex_grid.txt", "grid"),
                Utils.getFilePath("big_cards.txt", "cards")
        );
        model.startGame(false);
        new GameFrame(model);
    }
}
