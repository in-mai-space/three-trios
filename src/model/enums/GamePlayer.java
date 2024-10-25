package model.enums;

/**
 * Enum representing the players in the game.
 * This enum defines the two players: RED and BLUE.
 */
public enum GamePlayer {

  /** Represents the RED player. */
  RED("RED"),

  /** Represents the BLUE player. */
  BLUE("BLUE");

  private final String abbreviation;

  /**
   * Constructor for the GamePlayer enum.
   *
   * @param abbreviation the abbreviation for the game player.
   */
  private GamePlayer(String abbreviation) {
    this.abbreviation = abbreviation;
  }

  /**
   * Returns the string representation of the game player.
   *
   * @return the abbreviation of the game player.
   */
  @Override
  public String toString() {
    return this.abbreviation;
  }
}
