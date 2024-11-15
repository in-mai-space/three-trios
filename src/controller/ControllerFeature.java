package controller;

import java.util.Optional;

import model.enums.GamePlayer;

/**
 * Represents the feature set of a game. This interface is intended to be implemented by the
 * game controller. It defines the actions and interactions between the controller, view, model,
 * and player.
 */
public interface ControllerFeature {

  /**
   * Retrieves that player that the controller represents for.
   *
   * @return the color of player controlled by this controller
   */
  GamePlayer getPlayer();

  /**
   * Handles the selection of a card by the player. The selection is identified by its index
   * in the player's hand or the game context.
   *
   * @param index the index of the card to be selected in hand (0-indexed)
   */
  void selectCard(int index);

  /**
   * Places a card at the specified position on the game grid.
   * This method is called to perform a card placement action, which may depend on game rules.
   *
   * @param row row index in grid (0-indexed)
   * @param col column index in grid (0-indexed)
   */
  void placeCard(int row, int col);

  /**
   * Announces the end of the game, specifying the winner and the final score.
   *
   * @param winner an Optional containing the winning player, or empty if the game ends in a tie
   * @param score the score of winner, or score of one of players is there is a tie
   */
  void announceGameOver(Optional<GamePlayer> winner, int score);

  /**
   * Initiates the start of the game. This method is responsible for performing
   * any setup necessary to begin gameplay.
   */
  void gameStart();

  /**
   * Notifies that it is a particular player's turn. This method is used to update
   * the game's state or inform the player that they can now take an action.
   *
   * @param player the player whose turn it is.
   */
  void notifyPlayerTurn(GamePlayer player);
}
