package model;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.components.card.Card;
import model.components.card.ThreeTriosCard;
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
  public void getCellTypes() {
    CellType[][] cellTypes = parser.getCellTypes("src" +
            File.separator +
            "model" +
            File.separator +
            "config"
            + File.separator
            + "grid"
            + File.separator
            + "complex_grid.txt"
    );
    assertEquals(Arrays.deepToString(cellTypes), "");
  }



  @Test
  public void getCards() {
    List<Card> cards = parser.getCards("src" +
            File.separator +
            "model" +
            File.separator +
            "config"
            + File.separator
            + "cards"
            + File.separator
            + "small_cards.txt"
    );
    assertEquals(8, cards.size());
    assertEquals("CorruptKing", cards.get(0).getName());
    assertEquals(new ArrayList<AttackValue>(List.of(AttackValue.SEVEN, AttackValue.THREE,
                    AttackValue.NINE, AttackValue.A)),
            cards.get(0).getAllAttackValues());
  }
}