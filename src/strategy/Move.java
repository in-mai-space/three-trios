package strategy;

import model.interfaces.Cell;

import java.util.Objects;

public class Move {
  private final Cell cell;
  private final int row;
  private final int col;

  public Move(Cell cell, int row, int col) {
    if (cell == null) {
      throw new IllegalArgumentException("Card should not be null");
    }
    this.cell = cell;
    this.row = row;
    this.col = col;
  }

  public Cell getCard() {
    return cell;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Move)) {
      return false;
    }
    Move other = (Move) obj;
    return Objects.equals(cell, other.cell) &&
            row == other.row &&
            col == other.col;
  }

  @Override
  public int hashCode() {
    return Objects.hash(cell, row, col);
  }
}
