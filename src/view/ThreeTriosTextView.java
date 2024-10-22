package view;

import java.util.List;
import java.util.stream.Collectors;

import model.GameModel;
import model.components.card.Card;
import model.components.enums.AttackValue;
import model.components.enums.CellType;

public class ThreeTriosTextView implements GameView<String> {
  private final GameModel model;
  public ThreeTriosTextView(GameModel model) {
    this.model = model;
  }

  public String render() {
    return renderPlayer() + renderGrid() + renderHand();
  }

  private String renderPlayer() {
    return "Player: " + model.getCurrentPlayer().toString() + "\n";
  }

  private String renderHand() {
    List<Card> cards = model.getCurrentPlayerHand();
    String cardsAsString = "";
    for (Card card: cards) {
      List<AttackValue> attackValues = card.getAllAttackValues();
      String result = attackValues.stream()
              .map(AttackValue::toString)
              .collect(Collectors.joining(" "));
      cardsAsString += card.getName() + result + "\n";
    }
    return "Hand: \n" + cardsAsString;
  }

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
          String cardString = grid[row][col].toString();
          gridBuilder.append(cardString.charAt(0));
        }
      }
      gridBuilder.append("\n");
    }
    return gridBuilder.toString();
  }
}
