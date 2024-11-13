package strategy.mocks;

import java.util.List;

import model.enums.CellType;
import model.interfaces.Cell;

/**
 * Represents mock model when canPlaceCard is only true when the row pos and col pos are both 2.
 */
public class GoToCornerMockModel extends AbstractMockModel {
  public GoToCornerMockModel(CellType[][] cellTypes, List<Cell> allCells) {
    super(cellTypes, allCells);
  }

  @Override
  public boolean canPlaceCard(int row, int col) {
    super.canPlaceCard(row, col);
    return row == 2 && col == 2;
  }

  @Override
  public CellType[][] getCellTypes() {
    return ruleKeeper.getCellTypes();
  }
}
