~~**Overview**
The ThreeTrios game is a competitive card game for two players, Red and Blue. Each player has a hand 
of cards, where each card is assigned 4 numerical values. The game proceeds as follows:
1. Gameplay: Players take turns playing their cards in a grid format. Each card's value determines its strength in battle against the opponent's card.
2. Objective: The goal is to fill the grid with cards while trying to outscore the opponent. The game continues until all cells in the grid are filled.
3. Outcome: At the end of the game, the results are evaluated to determine if there is a winner or if the game ends in a tie.

The codebase assumes that readers are familiar with the game logic, such as how the card battles each other, 
when the game is over, operations that a player can do. Readers are familiar with MVC model, basic data
structures and Java SDK and libraries.

**Codebase game assumptions**
* two players: Red and Blue
* card uniqueness (name and 4 numerical values)
* players strictly alternate turns with Red starting first
* game state can be started, in progress and over

**Extensibility of codebase**
1. Support for additional players
   * the architecture can support more than two players by representing players as a list, and get next player index can still be applied when there is more than two players
2. Customizable game rules
   * can create different game modes with varying rules (e.g., point scoring, additional card types) by extending or composing with the ThreeTriosGridManager
3. Special cards with unique abilities
   *  include special cards with unique abilities, which could be implemented by creating subclasses of Card that override specific methods
4. Different implementations of the view with generics
   * GameView interface can have multiple implementations for various platforms, such as console-based views and GUI.

**Key components**
The components of the system follow the Model-View-Controller.
Model: core of the game logic 
* responsibilities
  * Game Initialization: The model initializes the game by validating inputs and setting up the grid and players. It ensures that there are enough cards to play the game, and it handles shuffling if required.
  * Game State Management: The model tracks the current player, checks if the game is ongoing, and identifies when the game is over.
  * Card Placement: It handles logic for placing cards on the grid, enforcing rules regarding cell types (like holes), and managing the game's turn flow.
  * Winner Calculation: The model computes the scores and determines the winner, accounting for ties.
  * ReadOnlyGameModel interface: the view can only accept the ReadOnlyGameModel which allows it to retrieve observations on the game state. It does not expose mutating data to the view to ensure information security.
  * GameModel interface: this model will be used my controller, which allows the controller to access all methods available of the model
View (driven by the model): textual representation of the game's state for the players
* responsibilities:
  * view renders the current player's information and their cards, as well as the layout of the grid. It formats this data into a string that can be displayed to the user, thus acting as a bridge between the game state and the user interface.

**Key subcomponents of Model**
The implementation of Card, Grid, Hand, GridManager, GameConfigParser is package private, which only allows the model to access and use them, while the client (such as View) would not be exposed to the implementation details of these classes
*Card*
* purpose: players use cards to perform actions in the game
* AttackValue: represent the attack value on the card (1-9, A stands for 10)
* GamePlayer: owner of the card
* Direction: enumerates the possible directions for attacks (north, south, east, west)
*Grid*
* purpose: defines the layout and structure of the game grid, facilitating card placement and observation of cards in grid
* CellType: specifies the type of cell within the grid (cell or hole), which determines a card can be placed
* Card[][]: represent state of grid, which cards are placed in which cells to manage player's card placement
*Hand*
* purpose: represents a player's collection of cards they can use to play in the grid
* List<Card>: allow to add from cards, remove from cards (this is used instead of arrays to avoid shifting card indexes)
*GridManager*
* oversees interactions with the grid, contains game logic for validating moves, executing battles, and updating the game state

*GameConfigParser*
* reading and parsing of game configuration files to set up the game's grid layout and loading card data in the model

Source organization
* src
  * model
    * enums: AttackValue, CellType, Direction, GamePlayer
    * implementation: GameConfigParser, ThreeTriosCard, ThreeTriosGrid, ThreeTriosGridManager, ThreeTriosHand, ThreeTriosMode
    * interfaces: Card, GameModel, Grid, GridManager, Hand, ReadOnlyGameModel
  * view: interface GameView and class ThreeTriosTextView
* test
  * model
    * enums: tests for enums
    * implementation: tests for implementation classes
  * view: test for text view
  * suites: suites to run all model tests, all view tests, run all tests
* config
  * cards: config files for card database
  * grid: config files for grid structure