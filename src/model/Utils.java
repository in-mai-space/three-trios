package model;

import java.nio.file.Paths;
import java.util.List;

import controller.GameConfigParser;
import model.enums.CellType;
import model.implementation.ThreeTriosModel;
import model.interfaces.Cell;
import model.interfaces.GameModel;

/**
 * Represent class model.Utils.
 */
public class Utils {
  /**
   * Helper method to get file path with package name.
   * @param fileName file name
   * @param packageName package name
   * @return file path
   * @throws IllegalArgumentException if fileName and packageName is null
   */
  public static String getFilePath(String fileName, String packageName) {
    if (fileName == null || packageName == null) {
      throw new IllegalArgumentException("File name and package name can't be null");
    }
    return Paths.get("config", packageName, fileName).toString();
  }

  public static GameModel loadModel(String gridFilePath, String cardsFilePath) {
    String gridFile = Utils.getFilePath(gridFilePath, "grid");
    String cardFile = Utils.getFilePath(cardsFilePath, "cards");
    CellType[][] cellTypes = GameConfigParser.getCellTypes(gridFile);
    List<Cell> cells = GameConfigParser.getCells(cardFile);
    GameModel model = new ThreeTriosModel(cellTypes, cells);
    model.startGame(false);
    return model;
  }
}
