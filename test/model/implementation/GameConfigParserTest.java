package model.implementation;

import org.junit.Before;
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
  private Card angryDragon97A2;
  private Card heroKnight4231;
  private Card skyWhale4594;
  private Card firePhoenix28A3;
  private Card evilQueen1A45;
  private Card corruptKing6293;
  private Card windBird7253;
  private Card worldDragon7253;
  private Card waterSeal3A74;
  private Card earthLizard9166;
  private List<Card> bigCards;
  private List<Card> smallCards;

  @Before
  public void setUp() {
    /**
     * CorruptKing 6 2 9 3
     * AngryDragon 9 7 A 2
     * WindBird 7 2 5 3
     * HeroKnight 4 2 3 1
     * WorldDragon 7 2 5 3
     * SkyWhale 4 5 9 4
     * WaterSeal 3 A 7 4
     * FirePhoenix 2 8 A 3
     * EarthLizard 9 1 6 6
     * EvilQueen 1 A 4 5
     */
    angryDragon97A2 = new ThreeTriosCard(new AttackValue[]{ AttackValue.NINE, AttackValue.SEVEN, AttackValue.A,
            AttackValue.TWO}, "AngryDragon");
    heroKnight4231 = new ThreeTriosCard(new AttackValue[]{ AttackValue.FOUR, AttackValue.TWO, AttackValue.THREE,
            AttackValue.ONE}, "HeroKnight");
    skyWhale4594 = new ThreeTriosCard(new AttackValue[]{ AttackValue.FOUR, AttackValue.FIVE, AttackValue.NINE,
            AttackValue.FOUR}, "SkyWhale");
    firePhoenix28A3 = new ThreeTriosCard(new AttackValue[]{ AttackValue.TWO, AttackValue.EIGHT, AttackValue.A,
            AttackValue.THREE}, "FirePhoenix");
    evilQueen1A45 = new ThreeTriosCard(new AttackValue[]{ AttackValue.ONE, AttackValue.A, AttackValue.FOUR,
            AttackValue.FIVE}, "EvilQueen");
    corruptKing6293 = new ThreeTriosCard(new AttackValue[]{ AttackValue.SIX, AttackValue.TWO, AttackValue.NINE,
            AttackValue.THREE}, "CorruptKing");
    windBird7253 = new ThreeTriosCard(new AttackValue[]{ AttackValue.SEVEN, AttackValue.TWO, AttackValue.FIVE,
            AttackValue.THREE}, "WindBird");
    worldDragon7253 = new ThreeTriosCard(new AttackValue[]{ AttackValue.SEVEN, AttackValue.TWO, AttackValue.FIVE,
            AttackValue.THREE}, "WorldDragon");
    waterSeal3A74 = new ThreeTriosCard(new AttackValue[]{ AttackValue.THREE, AttackValue.A, AttackValue.SEVEN,
            AttackValue.FOUR}, "WaterSeal");
    earthLizard9166 = new ThreeTriosCard(new AttackValue[]{ AttackValue.NINE, AttackValue.ONE, AttackValue.SIX,
            AttackValue.SIX}, "EarthLizard");
    smallCards = new ArrayList<>(List.of(corruptKing6293, angryDragon97A2, windBird7253, heroKnight4231,
            worldDragon7253, skyWhale4594, waterSeal3A74, firePhoenix28A3));
    bigCards = new ArrayList<>(List.of(corruptKing6293, angryDragon97A2, windBird7253, heroKnight4231,
            worldDragon7253, skyWhale4594, waterSeal3A74, firePhoenix28A3, earthLizard9166, evilQueen1A45));
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
    List<Card> actualSmallCards = GameConfigParser.getCards(smallFilePath);
    String bigFilePath = Utils.getFilePath("big_cards.txt", "cards");
    List<Card> actualBigCards = GameConfigParser.getCards(bigFilePath);
    assertEquals(actualSmallCards, smallCards);
    assertEquals(actualBigCards, bigCards);
  }

  @Test
  public void noNameCards() {
    IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
      String filePath = Utils.getFilePath("no_name.txt", "cards");
      GameConfigParser.getCards(filePath);
    });
    assertEquals("Card entry must have 5 elements: 7 3 9 A", thrown.getMessage());
  }

  @Test
  public void invalidLetterAsAttackValue() {
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
      String filePath = Utils.getFilePath("invalid_letter.txt", "cards");
      GameConfigParser.getCards(filePath);
    });
    assertEquals("Invalid attack value: B", thrown.getMessage());
  }

  @Test
  public void invalidNumberAsAttackValue() {
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
      String filePath = Utils.getFilePath("invalid_number.txt", "cards");
      GameConfigParser.getCards(filePath);
    });
    assertEquals("Invalid attack value: 10", thrown.getMessage());
  }

  @Test
  public void notEnoughAttackValues() {
    IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
      String filePath = Utils.getFilePath("not_enough_values.txt", "cards");
      GameConfigParser.getCards(filePath);
    });
    assertEquals("Card entry must have 5 elements: HeroKnight 4 2 3", thrown.getMessage());
  }

  @Test
  public void cannotFindCardFile() {
    IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
      String filePath = Utils.getFilePath("nonexistent.txt", "cards");
      GameConfigParser.getCards(filePath);
    });
    assertEquals("Cannot find file", thrown.getMessage());
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCardsNullFilePath() {
    GameConfigParser.getCards(null);
  }

  @Test
  public void getCardsSameName() {
    IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
      String filePath = Utils.getFilePath("repeated_names.txt", "cards");
      GameConfigParser.getCards(filePath);
    });
    assertEquals("Cards cannot have the same name", thrown.getMessage());
  }
}