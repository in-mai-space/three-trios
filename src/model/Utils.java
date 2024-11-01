package model;

import java.awt.*;
import java.nio.file.Paths;

import model.enums.GamePlayer;

/**
 * Represent class Utils.
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

  public static Color getCardColor(GamePlayer player) {
    switch (player) {
      case RED:
        return new Color(248, 131, 121);
      case BLUE:
        return new Color(137, 207, 240);
      default:
        throw new IllegalArgumentException("Invalid color");
    }
  }
}
