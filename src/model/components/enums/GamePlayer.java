package model.components.enums;

public enum GamePlayer {
  RED("RED"), BLUE("BLUE");

  private final String abbreviation;

  private GamePlayer(String abbreviation) {
    this.abbreviation = abbreviation;
  }

  @Override
  public String toString() {
    return this.abbreviation;
  }
}
