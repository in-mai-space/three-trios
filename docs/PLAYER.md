## Player Design
We can have a player interface which contains these methods. There can be custom
class that implements the interface, such as a Human player or an AI player.
- startGame: this method will invoke the startGame method in the model and allow users to start playing the game
- selectCard: a player can select which card they want to play in the grid
- getCurrentPlayer: a player can see whose turn it is to play the game, they will be not be able to select card and place card unless it's their turn
- getThisPlayerHand: a player can only see their own hand and not other players' hand
- viewGameBoard: a player can see the current state of the grid to decide what moves to do next
- checkGameIsOver: a player is alerted when the game is over, and cannot do any other actions when game is over
- getWinner: a player know the outcome of the game