package strategy;

import org.junit.Test;

import model.enums.AttackValue;
import model.enums.GamePlayer;
import model.implementation.ThreeTriosCell;

import static org.junit.Assert.assertEquals;


/**
 * Represent tests for Move.
 */
public class MoveTest {

  @Test(expected = IllegalArgumentException.class)
  public void nullCell() {
    new ThreeTriosMove(null, 0, 0);
  }

  @Test
  public void getCard() {
    Move move = new ThreeTriosMove(new ThreeTriosCell(new AttackValue[]{AttackValue.A,
            AttackValue.THREE, AttackValue.NINE, AttackValue.SEVEN},
            "CorruptKing", GamePlayer.BLUE), 0, 1);

    assertEquals(move.getCard(), new ThreeTriosCell(new AttackValue[]{AttackValue.A,
            AttackValue.THREE, AttackValue.NINE, AttackValue.SEVEN},
            "CorruptKing", GamePlayer.BLUE));
  }

  @Test
  public void getRow() {
    Move move = new ThreeTriosMove(new ThreeTriosCell(new AttackValue[]{AttackValue.A,
            AttackValue.THREE, AttackValue.NINE, AttackValue.SEVEN},
            "CorruptKing", GamePlayer.BLUE), 0, 1);

    assertEquals(0, move.getRow());
  }

  @Test
  public void getCol() {
    Move move = new ThreeTriosMove(new ThreeTriosCell(new AttackValue[]{AttackValue.A,
            AttackValue.THREE, AttackValue.NINE, AttackValue.SEVEN},
            "CorruptKing", GamePlayer.BLUE), 0, 1);

    assertEquals(1, move.getCol());
  }

  @Test
  public void testEquals() {
    Move move1 = new ThreeTriosMove(new ThreeTriosCell(new AttackValue[]{AttackValue.A,
            AttackValue.THREE, AttackValue.NINE, AttackValue.SEVEN},
            "CorruptKing", GamePlayer.BLUE), 0, 1);

    Move move2 = new ThreeTriosMove(new ThreeTriosCell(new AttackValue[]{AttackValue.A,
            AttackValue.THREE, AttackValue.NINE, AttackValue.SEVEN},
            "CorruptKing", GamePlayer.BLUE), 0, 1);

    assertEquals(move1, move2);
  }

  @Test
  public void testHashCode() {
    Move move1 = new ThreeTriosMove(new ThreeTriosCell(new AttackValue[]{AttackValue.A,
            AttackValue.THREE, AttackValue.NINE, AttackValue.SEVEN},
            "CorruptKing", GamePlayer.BLUE), 0, 1);

    Move move2 = new ThreeTriosMove(new ThreeTriosCell(new AttackValue[]{AttackValue.A,
            AttackValue.THREE, AttackValue.NINE, AttackValue.SEVEN},
            "CorruptKing", GamePlayer.BLUE), 0, 1);

    assertEquals(move1.hashCode(), move2.hashCode());
  }
}