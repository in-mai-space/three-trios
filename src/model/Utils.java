package model;

import java.nio.file.Paths;

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
}
