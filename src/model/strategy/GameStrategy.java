package model.strategy;

import java.util.Map;

import model.enums.GamePlayer;
import model.interfaces.ReadOnlyGameModel;

/**
 * Represents the ThreeTriosStrategy for game strategy.
 */
public interface GameStrategy {
  Map<Move, Integer> decideMove(ReadOnlyGameModel model, GamePlayer player);
}
