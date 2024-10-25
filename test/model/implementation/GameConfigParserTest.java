package model.implementation;

import org.junit.Test;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import model.enums.AttackValue;
import model.interfaces.Card;
import model.enums.CellType;
import model.Utils;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Test class for GameConfigParser.
 */
public class GameConfigParserTest {
  @Test
  public void testGetCellTypesFromComplexGrid() {
    String filePath = Utils.getFilePath("complex_grid.txt", "grid");
    CellType[][] expectedCellTypes = {
            {CellType.CELL, CellType.HOLE, CellType.CELL, CellType.CELL, CellType.CELL},
            {CellType.HOLE, CellType.HOLE, CellType.HOLE, CellType.CELL, CellType.HOLE},
            {CellType.HOLE, CellType.CELL, CellType.HOLE, CellType.HOLE, CellType.HOLE},
            {CellType.CELL, CellType.CELL, CellType.CELL, CellType.HOLE, CellType.HOLE}
    };
    CellType[][] actualCellTypes = GameConfigParser.getCellTypes(filePath);
    assertArrayEquals(expectedCellTypes, actualCellTypes);
  }

  @Test
  public void testGetCellTypesSimpleGrid() {
    String filePath = Utils.getFilePath("simple_grid.txt", "grid");
    CellType[][] expectedCellTypes = {
            {CellType.CELL, CellType.HOLE, CellType.HOLE, CellType.HOLE},
            {CellType.CELL, CellType.HOLE, CellType.HOLE, CellType.CELL},
            {CellType.CELL, CellType.CELL, CellType.CELL, CellType.CELL},
    };
    CellType[][] actualCellTypes = GameConfigParser.getCellTypes(filePath);
    assertArrayEquals(expectedCellTypes, actualCellTypes);
  }

  @Test
  public void testGetCellTypesNoHolesGrid() {
    String filePath = Utils.getFilePath("no_holes.txt", "grid");
    CellType[][] expectedCellTypes = {
            {CellType.CELL, CellType.CELL, CellType.CELL},
            {CellType.CELL, CellType.CELL, CellType.CELL},
            {CellType.CELL, CellType.CELL, CellType.CELL},
    };
    CellType[][] actualCellTypes = GameConfigParser.getCellTypes(filePath);
    assertArrayEquals(expectedCellTypes, actualCellTypes);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullFilePath() {
    GameConfigParser.getCellTypes(null);
  }

  @Test
  public void testInvalidFilePath() {
    String filePath = Paths.get("src", "model", "grid", "no_holes.txt").toString();
    IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
      GameConfigParser.getCellTypes(filePath);
    });
    assertEquals("Cannot find file: " + filePath, thrown.getMessage());
  }

  @Test
  public void testWrongFormatFile() {
    String filePath = Utils.getFilePath("wrong_format.txt", "grid");
    IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
      GameConfigParser.getCellTypes(filePath);
    });
    assertEquals("Config file wrong format", thrown.getMessage());
  }

  @Test
  public void testInvalidCharFile() {
    String filePath = Utils.getFilePath("invalid_char.txt", "grid");
    IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
      GameConfigParser.getCellTypes(filePath);
    });
    assertEquals("Invalid character in grid config: M", thrown.getMessage());
  }

  @Test
  public void testNotEnoughCols() {
    String filePath = Utils.getFilePath("not_enough_cols.txt", "grid");
    IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
      GameConfigParser.getCellTypes(filePath);
    });
    assertEquals("Row 0 does not have 4 columns", thrown.getMessage());
  }

  @Test
  public void testNotEnoughRows() {
    String filePath = Utils.getFilePath("not_enough_rows.txt", "grid");
    IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
      GameConfigParser.getCellTypes(filePath);
    });
    assertEquals("Insufficient rows in config file", thrown.getMessage());
  }

  @Test
  public void getCards() {
    String filePath = Utils.getFilePath("small_cards.txt", "cards");
    List<Card> cards = GameConfigParser.getCards(filePath);
    assertEquals(8, cards.size());
    assertEquals("CorruptKing", cards.get(0).getName());
    assertEquals(new ArrayList<AttackValue>(List.of(AttackValue.SEVEN, AttackValue.THREE,
                    AttackValue.NINE, AttackValue.A)),
            cards.get(0).getAllAttackValues());
  }
}