import model.Utils;
import model.implementation.ThreeTriosModel;
import model.interfaces.GameModel;
import view.GameView;
import view.ThreeTriosTextView;

/**
 * Main class to help with testing view manually.
 */
public class ConsoleViewTest {
  private static GameModel model;
  private static GameView<String> view;

  /**
   * The main method serves as the entry point for the ConsoleViewTest application.
   * It executes testing scenarios to validate the game model's behavior and
   * interactions with the view components.
   *
   * @param args command-line arguments (not used in this implementation)
   */
  public static void main(String[] args) {
    // please read the setup method below with setup guide
    setUp();
    System.out.println(view.render()); // initial grid state

    /*
    Can copy paste this method call multiple times to place cards in different grid location.
    Error message will appear in console if card cannot be placed in a grid position.
    */
    placeCardAndRender(0, 0, 0);
  }

  /**
   * Set up the game model and view using specified card and grid files.
   */
  private static void setUp() {
    /*
    Choose one card database file out of two, and put it in the empty string below:
    - "big_cards.txt" (10 cards)
    - "big_grid_cards.txt" (26 cards)
    */
    String cardsPath = Utils.getFilePath("", "cards");
    /*
    Choose one grid layout out of these, and put it in the empty string below:
    - "no_holes.txt" (3 x 3 cells with no holes)
    - "simple_grid.txt" (3 x 4 with holes & all card cells can reach each other)
    - "complex_grid.txt" (4 x 5 with holes, two groups of card cells can't reach each other)
    - "big_no_hole.txt" (5 x 5 cells with no holes)
    Note: big_grid_cards.txt can work on any grid, but big_cards can only work with no_holes,
    simple_grid and complex_grid. It will throw exception if you choose incorrectly.
    */
    String gridPath = Utils.getFilePath("", "grid");
    model = ThreeTriosModel.fromFiles(gridPath, cardsPath);
    model.startGame(true);
    view = new ThreeTriosTextView(model);
  }

  /**
   * Helper to place card and render the view.
   *
   * @param index index of cards in current player's hand (0-indexed)
   * @param row index of row (0-indexed)
   * @param col index of col (0-indexed)
   */
  private static void placeCardAndRender(int index, int row, int col) {
    try {
      model.placeCard(index, row, col);
      System.out.println(view.render());
    } catch (IllegalArgumentException | IllegalStateException e) {
      System.out.println("Error placing card: " + e.getMessage());
      System.out.println(view.render());
    }
  }
}
