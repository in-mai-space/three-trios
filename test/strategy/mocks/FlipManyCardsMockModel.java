package strategy.mocks;

import java.util.List;

import model.enums.CellType;
import model.interfaces.Cell;

/**
 * Represents mock model that keeps track of whether the following methods are called:
 * canPlaceCard, countCardFLip.
 */
public class FlipManyCardsMockModel extends AbstractMockModel {
  public FlipManyCardsMockModel(CellType[][] cellTypes, List<Cell> allCells) {
    super(cellTypes, allCells);
  }
}
