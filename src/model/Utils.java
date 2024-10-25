package model;

import java.nio.file.Paths;

public class Utils {

  /**
   * Helper method to get file path with package name.
   * @param fileName file name
   * @param packageName package name
   * @return file path
   */
  public static String getFilePath(String fileName, String packageName) {
    return Paths.get("config", packageName, fileName).toString();
  }
}
