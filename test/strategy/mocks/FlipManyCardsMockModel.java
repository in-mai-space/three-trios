package strategy.mocks;

import java.util.List;

import model.enums.CellType;
import model.interfaces.Cell;

public class FlipManyCardsMockModel extends AbstractMockModel {
  public FlipManyCardsMockModel(CellType[][] cellTypes, List<Cell> allCells) {
    super(cellTypes, allCells);
  }
}
