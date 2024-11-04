package view;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import controller.GameConfigParser;
import model.Utils;
import model.interfaces.GameModel;
import model.implementation.ThreeTriosModel;
import model.implementation.ThreeTriosModelTest;
import view.console.GameView;
import view.console.ThreeTriosTextView;

import static org.junit.Assert.assertEquals;

/**
 * Represent tests for ThreeTriosTextView.
 */
public class ThreeTriosTextViewTest {
  private GameModel noHolesModel;
  private GameModel simpleModel;
  private GameModel complexModel;
  private ArrayList<String> noHolesRender;
  private ArrayList<String> complexGridRender;

  @Before
  public void setUp() {
    String noHolesGrid = Utils.getFilePath("no_holes.txt", "grid");
    String simpleGrid = Utils.getFilePath("simple_grid.txt", "grid");
    String complexGrid = Utils.getFilePath("complex_grid.txt", "grid");
    String cardsFilePath = Utils.getFilePath("big_cards.txt", "cards");

    noHolesModel = new ThreeTriosModel(GameConfigParser.getCellTypes(noHolesGrid),
            GameConfigParser.getCells(cardsFilePath));
    simpleModel = new ThreeTriosModel(GameConfigParser.getCellTypes(simpleGrid),
            GameConfigParser.getCells(cardsFilePath));
    complexModel = new ThreeTriosModel(GameConfigParser.getCellTypes(complexGrid),
            GameConfigParser.getCells(cardsFilePath));
    noHolesRender = new ArrayList<>(List.of(
            "Player: BLUE\n" +
                    "___\n" +
                    "_R_\n" +
                    "___\n" +
                    "Hand: \n" +
                    "AngryDragon 9 7 A 2\n" +
                    "HeroKnight 4 2 3 1\n" +
                    "SkyWhale 4 5 9 4\n" +
                    "FirePhoenix 2 8 A 3\n" +
                    "EvilQueen 1 A 4 5\n",
            "Player: RED\n" +
                    "_B_\n" +
                    "_B_\n" +
                    "___\n" +
                    "Hand: \n" +
                    "CorruptKing 6 2 9 3\n" +
                    "WorldDragon 7 2 5 3\n" +
                    "WaterSeal 3 A 7 4\n" +
                    "EarthLizard 9 1 6 6\n",
            "Player: BLUE\n" +
                    "RR_\n" +
                    "_R_\n" +
                    "___\n" +
                    "Hand: \n" +
                    "AngryDragon 9 7 A 2\n" +
                    "HeroKnight 4 2 3 1\n" +
                    "SkyWhale 4 5 9 4\n" +
                    "FirePhoenix 2 8 A 3\n",
            "Player: RED\n" +
                    "BB_\n" +
                    "BB_\n" +
                    "___\n" +
                    "Hand: \n" +
                    "WorldDragon 7 2 5 3\n" +
                    "WaterSeal 3 A 7 4\n" +
                    "EarthLizard 9 1 6 6\n",
            "Player: BLUE\n" +
                    "RR_\n" +
                    "RR_\n" +
                    "R__\n" +
                    "Hand: \n" +
                    "AngryDragon 9 7 A 2\n" +
                    "HeroKnight 4 2 3 1\n" +
                    "FirePhoenix 2 8 A 3\n",
            "Player: RED\n" +
                    "RR_\n" +
                    "RB_\n" +
                    "RB_\n" +
                    "Hand: \n" +
                    "WaterSeal 3 A 7 4\n" +
                    "EarthLizard 9 1 6 6\n",
            "Player: BLUE\n" +
                    "RRR\n" +
                    "RB_\n" +
                    "RB_\n" +
                    "Hand: \n" +
                    "AngryDragon 9 7 A 2\n" +
                    "FirePhoenix 2 8 A 3\n",
            "Player: RED\n" +
                    "RBB\n" +
                    "RBB\n" +
                    "RB_\n" +
                    "Hand: \n" +
                    "WaterSeal 3 A 7 4\n",
            "Player: BLUE\n" +
                    "RRR\n" +
                    "RRR\n" +
                    "RRR\n" +
                    "Hand: \n" +
                    "AngryDragon 9 7 A 2\n"));
    complexGridRender = new ArrayList<>(List.of("Player: BLUE\n" +
            "_ ___\n" +
            "   _ \n" +
            " _   \n" +
            "_R_  \n" +
            "Hand: \n" +
            "AngryDragon 9 7 A 2\n" +
            "HeroKnight 4 2 3 1\n" +
            "SkyWhale 4 5 9 4\n" +
            "FirePhoenix 2 8 A 3\n" +
            "EvilQueen 1 A 4 5\n", "Player: RED\n" +
            "_ ___\n" +
            "   _ \n" +
            " _   \n" +
            "BB_  \n" +
            "Hand: \n" +
            "WindBird 7 2 5 3\n" +
            "WorldDragon 7 2 5 3\n" +
            "WaterSeal 3 A 7 4\n" +
            "EarthLizard 9 1 6 6\n", "Player: BLUE\n" +
            "_ ___\n" +
            "   _ \n" +
            " R   \n" +
            "BB_  \n" +
            "Hand: \n" +
            "AngryDragon 9 7 A 2\n" +
            "HeroKnight 4 2 3 1\n" +
            "SkyWhale 4 5 9 4\n" +
            "EvilQueen 1 A 4 5\n", "Player: RED\n" +
            "_ ___\n" +
            "   _ \n" +
            " R   \n" +
            "BBB  \n" +
            "Hand: \n" +
            "WorldDragon 7 2 5 3\n" +
            "WaterSeal 3 A 7 4\n" +
            "EarthLizard 9 1 6 6\n", "Player: BLUE\n" +
            "_ _R_\n" +
            "   _ \n" +
            " R   \n" +
            "BBB  \n" +
            "Hand: \n" +
            "HeroKnight 4 2 3 1\n" +
            "SkyWhale 4 5 9 4\n" +
            "EvilQueen 1 A 4 5\n", "Player: RED\n" +
            "_ _R_\n" +
            "   B \n" +
            " R   \n" +
            "BBB  \n" +
            "Hand: \n" +
            "WorldDragon 7 2 5 3\n" +
            "WaterSeal 3 A 7 4\n", "Player: BLUE\n" +
            "_ _RR\n" +
            "   B \n" +
            " R   \n" +
            "BBB  \n" +
            "Hand: \n" +
            "HeroKnight 4 2 3 1\n" +
            "SkyWhale 4 5 9 4\n", "Player: RED\n" +
            "_ BBB\n" +
            "   B \n" +
            " R   \n" +
            "BBB  \n" +
            "Hand: \n" +
            "WorldDragon 7 2 5 3\n", "Player: BLUE\n" +
            "R BBB\n" +
            "   B \n" +
            " R   \n" +
            "BBB  \n" +
            "Hand: \n" +
            "HeroKnight 4 2 3 1\n"));
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

  @Test
  public void testRenderNoHoles() {
    GameModel model = ThreeTriosModelTest.loadModel("no_holes.txt", "big_cards.txt");
    model.startGame(false);
    Object[][] cardPlacement = new Object[][]{
            {"WindBird", 1, 1}, {"EvilQueen", 0, 1}, {"CorruptKing", 0, 0},
            {"SkyWhale", 1, 0}, {"WorldDragon", 2, 0}, {"HeroKnight", 2, 1},
            {"EarthLizard", 0, 2}, {"FirePhoenix", 1, 2}, {"WaterSeal", 2, 2}
    };
    assertViewRenders(cardPlacement, model, new ThreeTriosTextView(model), noHolesRender);
  }

  @Test
  public void testRenderComplexGrid() {
    GameModel model = ThreeTriosModelTest.loadModel("complex_grid.txt", "big_cards.txt");
    model.startGame(false);
    Object[][] cardPlacement = new Object[][]{
            {"CorruptKing", 3, 1}, {"FirePhoenix", 3, 0}, {"WindBird", 2, 1},
            {"AngryDragon", 3, 2}, {"EarthLizard", 0, 3}, {"EvilQueen", 1, 3},
            {"WaterSeal", 0, 4}, {"SkyWhale", 0, 2}, {"WorldDragon", 0, 0}
    };
    assertViewRenders(cardPlacement, model, new ThreeTriosTextView(model), complexGridRender);
  }

  /**
   * Assert that for each card placement in the model, the view will render a specific String.
   *
   * @param cardPlacement cards to be placed in the grid
   * @param model model to be rendered
   * @param view view that renders model
   * @param expectedRenders list of expected render string by view
   */
  private void assertViewRenders(Object[][] cardPlacement, GameModel model, GameView<String> view,
                                 List<String> expectedRenders) {
    AtomicInteger renderIndex = new AtomicInteger();
    ThreeTriosModelTest.placeCardsIntoGrid(cardPlacement, model, () -> {
      assertEquals(view.render(), expectedRenders.get(renderIndex.getAndIncrement()));
    });
  }
}