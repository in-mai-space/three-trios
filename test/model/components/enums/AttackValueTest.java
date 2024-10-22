package model.components.enums;

import org.junit.Test;

import static org.junit.Assert.*;

public class AttackValueTest {
  @Test
  public void getValue() {
    assertEquals(AttackValue.A.getValue(), 10);
    assertEquals(AttackValue.ONE.getValue(), 1);
    assertEquals(AttackValue.FIVE.getValue(), 5);
  }

  @Test
  public void testToString() {
    assertEquals(AttackValue.A.toString(), "A");
    assertEquals(AttackValue.ONE.toString(), "1");
    assertEquals(AttackValue.FIVE.toString(), "5");
  }
}