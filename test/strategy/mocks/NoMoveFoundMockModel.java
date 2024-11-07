package strategy.mocks;

import java.util.List;

import model.enums.CellType;
import model.interfaces.Cell;

public class NoMoveFoundMockModel extends AbstractMockModel {
  public NoMoveFoundMockModel(CellType[][] cellTypes, List<Cell> allCells) {
    super(cellTypes, allCells);
  }

  @Override
  public boolean canPlaceCard(int row, int col) {
    super.canPlaceCard(row, col);
    return false;
  }
}
