package controller;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import model.Utils;
import model.enums.AttackValue;
import model.implementation.ThreeTriosCell;
import model.interfaces.Cell;
import model.enums.CellType;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Test class for GameConfigParser.
 */
public class GameConfigParserTest {
  private List<Cell> bigCells;
  private List<Cell> smallCells;

  @Before
  public void setUp() {
    Cell angryDragon97A2 = new ThreeTriosCell(new AttackValue[]{
        AttackValue.NINE, AttackValue.SEVEN, AttackValue.A, AttackValue.TWO}, "AngryDragon");
    Cell heroKnight4231 = new ThreeTriosCell(new AttackValue[]{
        AttackValue.FOUR, AttackValue.TWO, AttackValue.THREE, AttackValue.ONE}, "HeroKnight");
    Cell skyWhale4594 = new ThreeTriosCell(new AttackValue[]{
        AttackValue.FOUR, AttackValue.FIVE, AttackValue.NINE, AttackValue.FOUR}, "SkyWhale");
    Cell firePhoenix28A3 = new ThreeTriosCell(new AttackValue[]{
        AttackValue.TWO, AttackValue.EIGHT, AttackValue.A, AttackValue.THREE}, "FirePhoenix");
    Cell evilQueen1A45 = new ThreeTriosCell(new AttackValue[]{
        AttackValue.ONE, AttackValue.A, AttackValue.FOUR, AttackValue.FIVE}, "EvilQueen");
    Cell corruptKing6293 = new ThreeTriosCell(new AttackValue[]{
        AttackValue.SIX, AttackValue.TWO, AttackValue.NINE, AttackValue.THREE}, "CorruptKing");
    Cell windBird7253 = new ThreeTriosCell(new AttackValue[]{AttackValue.SEVEN, AttackValue.TWO,
        AttackValue.FIVE, AttackValue.THREE}, "WindBird");
    Cell worldDragon7253 = new ThreeTriosCell(
        new AttackValue[]{AttackValue.SEVEN, AttackValue.TWO, AttackValue.FIVE, AttackValue.THREE},
            "WorldDragon");
    Cell waterSeal3A74 = new ThreeTriosCell(new AttackValue[]{AttackValue.THREE, AttackValue.A,
        AttackValue.SEVEN, AttackValue.FOUR}, "WaterSeal");
    Cell earthLizard9166 = new ThreeTriosCell(new AttackValue[]{AttackValue.NINE, AttackValue.ONE,
        AttackValue.SIX, AttackValue.SIX}, "EarthLizard");
    smallCells = new ArrayList<>(List.of(corruptKing6293, angryDragon97A2, windBird7253,
        heroKnight4231, worldDragon7253, skyWhale4594, waterSeal3A74, firePhoenix28A3));
    bigCells = new ArrayList<>(List.of(corruptKing6293, angryDragon97A2, windBird7253,
        heroKnight4231, worldDragon7253, skyWhale4594, waterSeal3A74, firePhoenix28A3,
            earthLizard9166, evilQueen1A45));
  }

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
  public void getSmallAndBigCards() {
    String smallFilePath = Utils.getFilePath("small_cards.txt", "cards");
    List<Cell> actualSmallCells = GameConfigParser.getCells(smallFilePath);
    String bigFilePath = Utils.getFilePath("big_cards.txt", "cards");
    List<Cell> actualBigCells = GameConfigParser.getCells(bigFilePath);
    assertEquals(actualSmallCells, smallCells);
    assertEquals(actualBigCells, bigCells);
  }

  @Test
  public void noNameCards() {
    IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
      String filePath = Utils.getFilePath("no_name.txt", "cards");
      GameConfigParser.getCells(filePath);
    });
    assertEquals("Card entry must have 5 elements: 7 3 9 A", thrown.getMessage());
  }

  @Test
  public void invalidLetterAsAttackValue() {
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
      String filePath = Utils.getFilePath("invalid_letter.txt", "cards");
      GameConfigParser.getCells(filePath);
    });
    assertEquals("Invalid attack value: B", thrown.getMessage());
  }

  @Test
  public void invalidNumberAsAttackValue() {
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
      String filePath = Utils.getFilePath("invalid_number.txt", "cards");
      GameConfigParser.getCells(filePath);
    });
    assertEquals("Invalid attack value: 10", thrown.getMessage());
  }

  @Test
  public void notEnoughAttackValues() {
    IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
      String filePath = Utils.getFilePath("not_enough_values.txt", "cards");
      GameConfigParser.getCells(filePath);
    });
    assertEquals("Card entry must have 5 elements: HeroKnight 4 2 3", thrown.getMessage());
  }

  @Test
  public void cannotFindCardFile() {
    IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
      String filePath = Utils.getFilePath("nonexistent.txt", "cards");
      GameConfigParser.getCells(filePath);
    });
    assertEquals("Cannot find file", thrown.getMessage());
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCardsNullFilePath() {
    GameConfigParser.getCells(null);
  }

  @Test
  public void getCardsSameName() {
    IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
      String filePath = Utils.getFilePath("repeated_names.txt", "cards");
      GameConfigParser.getCells(filePath);
    });
    assertEquals("Cards cannot have the same name", thrown.getMessage());
  }
}