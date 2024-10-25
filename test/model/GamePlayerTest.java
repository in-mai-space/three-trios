package model;

import org.junit.Test;

import model.enums.GamePlayer;

import static org.junit.Assert.assertEquals;

public class GamePlayerTest {
  @Test
  public void testToString() {
    assertEquals("RED", GamePlayer.RED.toString());
    assertEquals("BLUE", GamePlayer.BLUE.toString());
  }
}