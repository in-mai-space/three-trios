package view;

import java.util.List;
import java.util.stream.Collectors;

import model.GameModel;
import model.components.card.Card;
import model.components.enums.AttackValue;
import model.components.enums.CellType;

/**
 * The ThreeTriosTextView class implements the GameView interface,
 * providing a text-based representation of the game state for
 * the Three Trios card game. It generates a string output that
 * includes information about the current player, their hand,
 * and the game grid.
 */
public class ThreeTriosTextView implements GameView<String> {
  private final GameModel model;

  /**
   * Constructs a ThreeTriosTextView instance with the specified GameModel.
   *
   * @param model the GameModel instance representing the current state of the game
   * @throws IllegalArgumentException if the model is null
   */
  public ThreeTriosTextView(GameModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
  }

  /**
   * Renders the current state of the game as a string. This includes
   * the player's information, their hand of cards, and the grid layout.
   *
   * @return a string representation of the game state
   */
  public String render() {
    return renderPlayer() + renderGrid() + renderHand();
  }

  /**
   * Renders the current player's information.
   *
   * @return a string representing the current player
   */
  private String renderPlayer() {
    return "Player: " + model.getCurrentPlayer().toString() + "\n";
  }

  /**
   * Renders the current player's hand of cards.
   *
   * @return a string representation of the player's hand, including
   *         each card's name and its attack values
   */
  private String renderHand() {
    List<Card> cards = model.getCurrentPlayerHand();
    String cardsAsString = "";

    for (Card card : cards) {
      List<AttackValue> attackValues = card.getAllAttackValues();
      String result = attackValues.stream()
              .map(AttackValue::toString)
              .collect(Collectors.joining(" "));
      cardsAsString += card.getName() + " " + result + "\n";
    }
    return "Hand: \n" + cardsAsString;
  }

  /**
   * Renders the game grid, displaying the current state of each cell.
   *
   * @return a string representation of the game grid
   */
  private String renderGrid() {
    Card[][] grid = model.getGrid();
    CellType[][] cellTypes = model.getCellTypes();
    StringBuilder gridBuilder = new StringBuilder();

    for (int row = 0; row < grid.length; row++) {
      for (int col = 0; col < grid[row].length; col++) {
        CellType cellType = cellTypes[row][col];
        if (cellType == CellType.HOLE) {
          gridBuilder.append(" ");
        } else if (grid[row][col] == null) {
          gridBuilder.append("_");
        } else {
          String cardString = grid[row][col].getOwner().toString();
          gridBuilder.append(cardString.charAt(0));
        }
      }
      gridBuilder.append("\n");
    }
    return gridBuilder.toString();
  }
}
