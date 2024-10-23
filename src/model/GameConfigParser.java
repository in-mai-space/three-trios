package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import model.components.card.Card;
import model.components.card.ThreeTriosCard;
import model.components.enums.AttackValue;
import model.components.enums.CellType;

/**
 * The GameConfigParser class is responsible for parsing game configuration files
 * that define the grid layout and card database for a card game.
 * It reads a grid configuration from a specified file and loads the card data
 * into a list of Card objects.
 */
public class GameConfigParser {
  private CellType[][] cellTypes; // 2D array representing the grid layout
  private final List<Card> cards; // list of cards loaded from the configuration file

  /**
   * Constructs a new GameConfigParser instance.
   */
  public GameConfigParser() {
    cards = new ArrayList<>();
  }

  /**
   * Retrieves the cell types for the game grid from a specified file.
   *
   * @param filePath the path to the grid configuration file
   *
   * @return a 2D array of CellType representing the game grid
   * @throws IllegalArgumentException if the file path is null
   */
  public CellType[][] getCellTypes(String filePath) {
    validateFilepath(filePath);
    loadGridConfig(filePath);
    return cellTypes;
  }

  /**
   * Retrieves the list of cards from a specified file.
   *
   * @param filePath the path to the card database file
   *
   * @return a list of Card objects
   * @throws IllegalArgumentException if the file path is null
   */
  public List<Card> getCards(String filePath) {
    validateFilepath(filePath);
    loadCardDatabase(filePath);
    return cards;
  }

  /**
   * Validates the file path to ensure it is not null.
   *
   * @param filePath the file path to validate
   * @throws IllegalArgumentException if the file path is null
   */
  private void validateFilepath(String filePath) {
    if (filePath == null) {
      throw new IllegalArgumentException("Filepath should not be null");
    }
  }

  /**
   * Loads the grid configuration from a specified file.
   *
   * @param filePath the path to the grid configuration file
   * @throws IllegalStateException if file cannot be found
   * @throws IllegalStateException if file is wrongly formatted
   * @throws IllegalStateException if there is not enough rows or cols
   * @throws IllegalStateException if cellType char is not 'C' or 'X'
   */
  private void loadGridConfig(String filePath) {
    File file = new File(filePath);
    try (Scanner scanner = new Scanner(file)) {
      int rows = scanner.nextInt();
      int cols = scanner.nextInt();
      scanner.nextLine();
      cellTypes = new CellType[rows][cols];

      for (int i = 0; i < rows; i++) {
        String row = readNextRow(scanner);
        fillRowWithCellTypes(row, i, cols);
      }
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("Cannot find file: " + filePath, e);
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("Config file wrong format", e);
    }
  }

  /**
   * Reads the next row from the scanner.
   *
   * @param scanner the scanner to read from
   * @return the next row as a String
   *
   * @throws IllegalStateException if there is not enough rows
   */
  private String readNextRow(Scanner scanner) {
    if (!scanner.hasNextLine()) {
      throw new IllegalStateException("Insufficient rows in config file");
    }
    return scanner.nextLine();
  }

  /**
   * Fills a specific row of the cellTypes array with CellType values.
   *
   * @param row the row string from the configuration file
   * @param rowIndex the index of the row in the cellTypes array
   * @param expectedCols the expected number of columns in the row
   *
   * @throws IllegalStateException if there is not enough cols
   */
  private void fillRowWithCellTypes(String row, int rowIndex, int expectedCols) {
    if (row.length() != expectedCols) {
      throw new IllegalStateException("Row " + rowIndex + " does not have "
              + expectedCols + " columns");
    }
    for (int j = 0; j < expectedCols; j++) {
      cellTypes[rowIndex][j] = parseCellType(row.charAt(j));
    }
  }

  /**
   * Parses a character to determine its corresponding CellType.
   *
   * @param cellChar the character representing a cell type
   * @return the corresponding CellType
   *
   * @throws IllegalStateException if there is invalid character
   */
  private CellType parseCellType(char cellChar) {
    switch (cellChar) {
      case 'C':
        return CellType.CELL;
      case 'X':
        return CellType.HOLE;
      default:
        throw new IllegalStateException("Invalid character in grid config: " + cellChar);
    }
  }

  /**
   * Loads the card database from a specified file.
   *
   * @param filePath the path to the card database file
   *
   * @throws IllegalStateException if the file is wrongly formatted
   * @throws IllegalStateException if file cannot be found
   * @throws IllegalStateException if card values are not integer
   */
  private void loadCardDatabase(String filePath) {
    File file = new File(filePath);
    try (Scanner scanner = new Scanner(file)) {
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

  /**
   * Parses a string to determine its corresponding AttackValue.
   *
   * @param value the string representation of the attack value
   * @return the corresponding AttackValue
   *
   * @throws IllegalArgumentException if the value is not valid
   */
  private AttackValue parseAttackValue(String value) {
    if (value.equals("A")) {
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
