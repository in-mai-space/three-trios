package model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Represent tests for Utils.
 */
public class UtilsTest {
  @Test
  public void testGetFilePath() {
    String fileName = "complex_grid.txt";
    String packageName = "grid";
    String expectedPath = "config/grid/complex_grid.txt";
    String actualPath = Utils.getFilePath(fileName, packageName);
    assertEquals(expectedPath, actualPath);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullFilePath() {
    String fileName = "complex_grid.txt";
    Utils.getFilePath(fileName, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullPackageName() {
    String packageName = "grid";
    Utils.getFilePath(null, packageName);
  }

  @Test
  public void testGetFilePathWithDifferentPackage() {
    String fileName = "big_cards.txt";
    String packageName = "cards";

    String expectedPath = "config/cards/big_cards.txt";
    String actualPath = Utils.getFilePath(fileName, packageName);
    assertEquals(expectedPath, actualPath);
  }
}