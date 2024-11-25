package model;

import java.nio.file.Paths;
import java.util.List;

import controller.GameConfigParser;
import model.enums.CellType;
import model.enums.GamePlayer;
import model.implementation.ThreeTriosModel;
import model.interfaces.Cell;
import model.interfaces.GameModel;
import provider.model.CardColor;

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

  /**
   * Helper method to load model using grid filepath and cards filepath.
   * @param gridFilePath file path to grid
   * @param cardsFilePath file path to cards
   * @return game model
   */
  public static GameModel loadModel(String gridFilePath, String cardsFilePath) {
    String gridFile = Utils.getFilePath(gridFilePath, "grid");
    String cardFile = Utils.getFilePath(cardsFilePath, "cards");
    CellType[][] cellTypes = GameConfigParser.getCellTypes(gridFile);
    List<Cell> cells = GameConfigParser.getCells(cardFile);
    GameModel model = new ThreeTriosModel(cellTypes, cells);
    model.startGame(false);
    return model;
  }

  /**
   * Helper method to load model using grid filepath and cards filepath.
   * @param gridFilePath file path to grid
   * @param cardsFilePath file path to cards
   * @return game model
   */
  public static GameModel loadModelNotStarted(String gridFilePath, String cardsFilePath) {
    String gridFile = Utils.getFilePath(gridFilePath, "grid");
    String cardFile = Utils.getFilePath(cardsFilePath, "cards");
    CellType[][] cellTypes = GameConfigParser.getCellTypes(gridFile);
    List<Cell> cells = GameConfigParser.getCells(cardFile);
    return new ThreeTriosModel(cellTypes, cells);
  }

  /**
   * Passes a message onto the appendable.
   * @param log appendable
   * @param message message in a string
   */
  public static void transmit(Appendable log, String message) {
    try {
      log.append(message).append("\n");
    } catch (Exception ignored) { }
  }

  public static GamePlayer convertColor(CardColor color) {
    if (color == CardColor.RED) {
      return GamePlayer.RED;
    }
    else if (color == CardColor.BLUE) {
      return GamePlayer.BLUE;
    }
    return null;
  }

  public static CardColor convertColor(GamePlayer color) {
    if (color == GamePlayer.RED) {
      return CardColor.RED;
    }
    else if (color == GamePlayer.BLUE) {
      return CardColor.BLUE;
    }
    return null;
  }
}
