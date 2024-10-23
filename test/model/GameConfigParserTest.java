package model;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import model.components.card.Card;
import model.components.enums.AttackValue;
import model.components.enums.CellType;

import static org.junit.Assert.*;

public class GameConfigParserTest {
  private GameConfigParser parser;

  @Before
  public void setUp() {
    parser = new GameConfigParser();
  }

  @Test
  public void testGetCellTypesFromComplexGrid() {
    String filePath = Paths.get("src", "model", "config", "grid",
            "complex_grid.txt").toString();
    CellType[][] expectedCellTypes = {
            {CellType.CELL, CellType.HOLE, CellType.CELL, CellType.CELL, CellType.CELL},
            {CellType.HOLE, CellType.HOLE, CellType.HOLE, CellType.CELL, CellType.HOLE},
            {CellType.HOLE, CellType.CELL, CellType.HOLE, CellType.HOLE, CellType.HOLE},
            {CellType.CELL, CellType.CELL, CellType.CELL, CellType.HOLE, CellType.HOLE}
    };
    CellType[][] actualCellTypes = parser.getCellTypes(filePath);
    assertArrayEquals(expectedCellTypes, actualCellTypes);
  }

  private String getFilePath(String fileName, String packageName) {
    return Paths.get("src", "model", "config", packageName, fileName).toString();
  }

  @Test
  public void testGetCellTypesSimpleGrid() {
    String filePath = getFilePath("simple_grid.txt", "grid");
    CellType[][] expectedCellTypes = {
            {CellType.CELL, CellType.HOLE, CellType.HOLE, CellType.HOLE},
            {CellType.CELL, CellType.HOLE, CellType.HOLE, CellType.CELL},
            {CellType.CELL, CellType.CELL, CellType.CELL, CellType.CELL},
    };
    CellType[][] actualCellTypes = parser.getCellTypes(filePath);
    assertArrayEquals(expectedCellTypes, actualCellTypes);
  }

  @Test
  public void testGetCellTypesNoHolesGrid() {
    String filePath = getFilePath("no_holes.txt", "grid");
    CellType[][] expectedCellTypes = {
            {CellType.CELL, CellType.CELL, CellType.CELL},
            {CellType.CELL, CellType.CELL, CellType.CELL},
            {CellType.CELL, CellType.CELL, CellType.CELL},
    };
    CellType[][] actualCellTypes = parser.getCellTypes(filePath);
    assertArrayEquals(expectedCellTypes, actualCellTypes);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullFilePath() {
    parser.getCellTypes(null);
  }

  @Test
  public void testInvalidFilePath() {
    String filePath = Paths.get("src", "model", "grid", "no_holes.txt").toString();
    IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
      parser.getCellTypes(filePath);
    });
    assertEquals("Cannot find file: " + filePath, thrown.getMessage());
  }

  @Test
  public void testWrongFormatFile() {
    String filePath = getFilePath("wrong_format.txt", "grid");
    IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
      parser.getCellTypes(filePath);
    });
    assertEquals("Config file wrong format", thrown.getMessage());
  }

  @Test
  public void testInvalidCharFile() {
    String filePath = getFilePath("invalid_char.txt", "grid");
    IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
      parser.getCellTypes(filePath);
    });
    assertEquals("Invalid character in grid config: M", thrown.getMessage());
  }

  @Test
  public void testNotEnoughCols() {
    String filePath = getFilePath("not_enough_cols.txt", "grid");
    IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
      parser.getCellTypes(filePath);
    });
    assertEquals("Row 0 does not have 4 columns", thrown.getMessage());
  }

  @Test
  public void testNotEnoughRows() {
    String filePath = getFilePath("not_enough_rows.txt", "grid");
    IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
      parser.getCellTypes(filePath);
    });
    assertEquals("Insufficient rows in config file", thrown.getMessage());
  }

  @Test
  public void getCards() {
    String filePath = getFilePath("small_cards.txt", "cards");
    List<Card> cards = parser.getCards(filePath);
    assertEquals(8, cards.size());
    assertEquals("CorruptKing", cards.get(0).getName());
    assertEquals(new ArrayList<AttackValue>(List.of(AttackValue.SEVEN, AttackValue.THREE,
                    AttackValue.NINE, AttackValue.A)),
            cards.get(0).getAllAttackValues());
  }
}