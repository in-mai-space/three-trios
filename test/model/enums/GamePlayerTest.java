package model.enums;

import org.junit.Test;

import model.enums.GamePlayer;

import static org.junit.Assert.assertEquals;

/**
 * Represents tests for GamePlayers.
 */
public class GamePlayerTest {
  @Test
  public void testToString() {
    assertEquals("RED", GamePlayer.RED.toString());
    assertEquals("BLUE", GamePlayer.BLUE.toString());
  }
}