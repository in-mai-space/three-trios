package view;

import org.junit.Before;
import org.junit.Test;

import model.GameConfigParserTest;
import model.GameModel;
import model.ThreeTriosModel;

import static org.junit.Assert.*;

public class ThreeTriosTextViewTest {
  private GameModel noHolesModel;
  private GameModel simpleModel;
  private GameModel complexModel;

  @Before
  public void setUp() {
    String noHolesGrid = GameConfigParserTest.getFilePath("no_holes.txt", "grid");
    String simpleGrid = GameConfigParserTest.getFilePath("simple_grid.txt", "grid");
    String complexGrid = GameConfigParserTest.getFilePath("complex_grid.txt", "grid");
    String cardsFilePath = GameConfigParserTest.getFilePath("big_cards.txt", "cards");

    noHolesModel = ThreeTriosModel.fromFiles(noHolesGrid, cardsFilePath);
    simpleModel = ThreeTriosModel.fromFiles(simpleGrid, cardsFilePath);
    complexModel = ThreeTriosModel.fromFiles(complexGrid, cardsFilePath);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullModelConstructor() {
    new ThreeTriosTextView(null);
  }

  @Test
  public void renderInitialStateNoHoles() {
    ThreeTriosTextView view = new ThreeTriosTextView(noHolesModel);
    noHolesModel.startGame(false);
    String textView = view.render();
    assertEquals("Player: RED\n"
            + "___\n"
            + "___\n"
            + "___\n"
            + "Hand: \n"
            + "CorruptKing 6 2 9 3\n"
            + "WindBird 7 2 5 3\n"
            + "WorldDragon 7 2 5 3\n"
            + "WaterSeal 3 A 7 4\n"
            + "EarthLizard 9 1 6 6\n", textView);
  }

  @Test
  public void renderInitialStateSimpleGrid() {
    ThreeTriosTextView view = new ThreeTriosTextView(simpleModel);
    simpleModel.startGame(false);
    String textView = view.render();
    assertEquals("Player: RED\n"
            + "_   \n"
            + "_  _\n"
            + "____\n"
            + "Hand: \n"
            + "CorruptKing 6 2 9 3\n"
            + "WindBird 7 2 5 3\n"
            + "WorldDragon 7 2 5 3\n"
            + "WaterSeal 3 A 7 4\n", textView);
  }

  @Test
  public void renderInitialStateComplexGrid() {
    ThreeTriosTextView view = new ThreeTriosTextView(complexModel);
    complexModel.startGame(false);
    String textView = view.render();
    assertEquals("Player: RED\n"
            + "_ ___\n"
            + "   _ \n"
            + " _   \n"
            + "___  \n"
            + "Hand: \n"
            + "CorruptKing 6 2 9 3\n"
            + "WindBird 7 2 5 3\n"
            + "WorldDragon 7 2 5 3\n"
            + "WaterSeal 3 A 7 4\n"
            + "EarthLizard 9 1 6 6\n", textView);
  }

  @Test(expected = IllegalStateException.class)
  public void testModelGameNotStarted() {
    ThreeTriosTextView view = new ThreeTriosTextView(noHolesModel);
    view.render();
  }

  @Test(expected = IllegalStateException.class)
  public void testModelGameOver() {
    ThreeTriosTextView view = new ThreeTriosTextView(noHolesModel);
    noHolesModel.startGame(false);
    int[][] cardPlacement = new int[][]{
            {0, 0}, {0, 1}, {0, 2},
            {1, 0}, {1, 1}, {1, 2},
            {2, 0}, {2, 1}, {2, 2}
    };
    for (int[] pos : cardPlacement) {
      noHolesModel.placeCard(0, pos[0], pos[1]);
    }
    view.render();
  }
}