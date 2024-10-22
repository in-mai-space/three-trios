package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import model.components.card.Card;
import model.components.card.ThreeTriosCard;
import model.components.enums.AttackValue;
import model.components.enums.CellType;

public class GameConfigParser {
  private CellType[][] cellTypes;
  private final List<Card> cards;

  public GameConfigParser() {
    cards = new ArrayList<>();
  }

  public CellType[][] getCellTypes(String filePath) {
    loadGridConfig(filePath);
    return cellTypes;
  }

  public List<Card> getCards(String filePath) {
    loadCardDatabase(filePath);
    return cards;
  }

  private void loadGridConfig(String filePath) {
    try (Scanner scanner = new Scanner(new FileReader(filePath))) {
      int rows = scanner.nextInt();
      int cols = scanner.nextInt();
      cellTypes = new CellType[rows][cols];
      for (int i = 0; i < rows; i++) {
        if (scanner.hasNextLine()) {
          String row = scanner.nextLine();
          for (int j = 0; j < cols; j++) {
            char cellChar = row.charAt(j);
            switch (cellChar) {
              case 'C':
                cellTypes[i][j] = CellType.CELL;
                break;
              case 'X':
                cellTypes[i][j] = CellType.HOLE;
                break;
              default:
                throw new IllegalStateException("Invalid character in grid config: " + cellChar);
            }
          }
        }
      }
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("Cannot find file");
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("Config file wrong format");
    }
  }

  private void loadCardDatabase(String filePath) {
    try (Scanner scanner = new Scanner(new FileReader(filePath))) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] cardInfo = line.split(" ");
        if (cardInfo.length != 5) {
          throw new IllegalStateException("Card entry must have 5 elements: " + line);
        }
        String cardName = cardInfo[0];
        AttackValue north = parseAttackValue(cardInfo[1]);
        AttackValue south = parseAttackValue(cardInfo[2]);
        AttackValue east = parseAttackValue(cardInfo[3]);
        AttackValue west = parseAttackValue(cardInfo[4]);

        Card card = new ThreeTriosCard(new AttackValue[]{north, south, east, west}, cardName);
        cards.add(card);
      }
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("Cannot find file");
    } catch (NumberFormatException e) {
      throw new IllegalStateException("Card values must be integers");
    }
  }

  private AttackValue parseAttackValue(String value) {
    if ("A".equals(value)) {
      return AttackValue.A;
    }

    try {
      int intValue = Integer.parseInt(value);
      if (intValue < 1 || intValue > 10) {
        throw new IllegalArgumentException("Invalid attack value: " + intValue);
      }
      return AttackValue.values()[intValue - 1];
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid attack value: " + value);
    }
  }
}
