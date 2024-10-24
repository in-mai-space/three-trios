package model.components.enums;

import org.junit.Test;

import static org.junit.Assert.*;

public class GamePlayerTest {
  @Test
  public void testToString() {
    assertEquals("RED", GamePlayer.RED.toString());
    assertEquals("BLUE", GamePlayer.BLUE.toString());
  }
}