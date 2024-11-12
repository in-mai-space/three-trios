package controller;

import org.junit.Test;

import java.util.List;

import model.Utils;
import model.enums.CellType;
import model.implementation.ThreeTriosModel;
import model.interfaces.Cell;
import model.interfaces.GameModel;

/**
 * Represents test for ThreeTriosController.
 */
public class ThreeTriosControllerTest {

  @Test(expected = IllegalArgumentException.class)
  public void setNullView() {
    CellType[][] cellTypes = GameConfigParser.getCellTypes(
            Utils.getFilePath("complex_grid.txt", "grid"));
    List<Cell> cells = GameConfigParser.getCells(Utils.getFilePath("big_cards.txt", "cards"));
    GameModel model = new ThreeTriosModel(cellTypes, cells);
    Appendable out = new StringBuilder();
    GameController controller = new ThreeTriosController(model, out);
    controller.setView(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullModel() {
    Appendable out = new StringBuilder();
    new ThreeTriosController(null, out);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullAppendable() {
    CellType[][] cellTypes = GameConfigParser.getCellTypes(
            Utils.getFilePath("complex_grid.txt", "grid"));
    List<Cell> cells = GameConfigParser.getCells(Utils.getFilePath("big_cards.txt", "cards"));
    GameModel model = new ThreeTriosModel(cellTypes, cells);
    new ThreeTriosController(model, null);
  }
}