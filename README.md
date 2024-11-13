## Overview
### Gameplay
The ThreeTrios game is a competitive cell game for two players, Red and Blue. Each player has a hand
of cells, where each cell is assigned 4 numerical values. The game proceeds as follows:
1. Gameplay: Players take turns playing their cells in a grid format. Each cell's value determines its strength in battle against the opponent's cell.
2. Objective: The goal is to fill the grid with cells while trying to outscore the opponent. The game continues until all cells in the grid are filled.
3. Outcome: At the end of the game, the results are evaluated to determine if there is a winner or if the game ends in a tie.

> This codebase assumes familiarity with core game mechanics (cell battling, game completion, and player operations) and a working understanding of MVC architecture, basic data structures, and the Java SDK and libraries.

## How to start using the codebase
- to try out the program: go to src/ConsoleViewTest and read how to setup the game view
```plaintext
public static void main(String[] args) {
    // please read the setup method below with setup guide
    setUp();
    System.out.println(view.render()); // initial grid state
    placeCardAndRender(0, 0, 0);
}
```
- to run the test: go to test/suites/AllTestsSuite
```plaintext
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ViewTestSuite.class,
        ModelTestSuite.class
})
public class AllTestsSuite {
}
```

## Codebase game assumptions
- **two players**: Red and Blue
- **fixed amount of players**: amount of players is fixed throughout the game
- **cell uniqueness**: name and 4 numerical values
- **alternate turns**: players strictly alternate turns with Red starting first
- **game states**: game state can be started, in progress and over

## Extensibility of codebase
The design allows for potential expansion, including:
1. **Support for Additional Players**: The architecture supports more than two players by managing players in an array and using an index-based approach to determine the next player.
2. **Customizable Game Rules**: Different game modes can be introduced by extending the `ThreeTriosGridManager` class, allowing for new scoring methods or additional cell types.
3. **Special Cards with Unique Abilities**: Specialized cells with unique abilities can be added by subclassing `Card` and overriding specific methods.
4. **Different View Implementations**: The `GameView` interface can support multiple implementations for various platforms, such as console and GUI.

## Key components
The code follows the Model-View-Controller (MVC) pattern:

### Model (Core Game Logic)
The model manages the game's state and flow:
- **Game Initialization**: Validates inputs, sets up the grid and players, and shuffles cells if necessary.
- **Game State Management**: Tracks the current player and identifies when the game is over.
- **Card Placement**: Enforces rules for cell placement, including cell-type restrictions.
- **Winner Calculation**: Computes scores and determines the winner, accounting for ties.

#### Model Details
- **ReadOnlyGameModel Interface**: Provides the view with access to game state information without exposing mutative methods.
- **GameModel Interface**: This model is accessed by the controller, allowing full access to the model's methods.

### View (Driven by the Model)
The view presents the game's state to the players:
- **Responsibilities**: The view renders the current player's information and cells, as well as the grid layout. It renders the state of a game for a player to see.

## Key Subcomponents of Model
The model's core classes include:

- **Card**: Represents the card in the game.
  - **Attributes**: AttackValue (1-9, with 'A' for 10), Direction (attack directions).
- **Cell**: Represents the playable card in the game.
    - **Attributes**: Card, Owner (Red or Blue).
- **Grid**: Defines the grid layout and manages cell placement.
  - **Attributes**: CellType (cell or hole) and Card[][] (grid state).
- **Hand**: Represents a player's collection of usable cells.
  - **Attributes**: List of cells (managed via a list rather than an array).
- **GridManager**: Manages interactions with the grid, including move validation, battles, and game state updates.



## Part 2 - Game Model and View Refactoring

### Model
1. **Game State and Behavior**
  - **New Model Methods**
    - `canPlaceCard`: Determines if a card can be placed at a given position.
    - `getGridWidth`: Returns the grid's width.
    - `getGridHeight`: Returns the grid's height.
    - `getOwnerAt`: Returns the owner of the cell at a specified position.
    - `getCardAt`: Retrieves the card located at a specified cell position.
    - `getScore`: Computes and returns the current score of a specific player.
    - `countCardFlip`: Counts number of cards that a card can flip when placed in a grid's position.

2. **Refactoring**
  - **ReadOnlyGameModel** (`ThreeTriosViewModel`):
    - **Adapter** that provides a read-only view of the model, exposing only necessary observational methods for the view.
  - **Decoupling**:
    - **Cell and Card Separation**: Refactor to ensure that a `Cell` knows only its `owner`, while a `Card` object does not depend on ownership. Each `Cell` is now composed of a `Card`.
  - **Relocating Configuration Parsing**:
    - **Game Configuration Parser**: Move to the `controller` package, as the controller manages input and output operations.

### Strategy
1. **Packages Split**
  - **Fallible Strategy**: Strategy variations where moves or decisions may return Optional empty move. A fallible strategy implemented is GoToCorner.
  - **Infallible Strategy**: Strategies that must always return a move and throw exceptions when it can't find a move. The two infallible strategies are FlipManyCards and UpperLeft.

2. **Move Class**
  - Move objects represents a cell and its position, defined by:
    - **Attributes**: `cell`, `row`, and `col`.

3. **Pair Utility**
  - **Generic `Pair<K, V>`**: Stores two values as a key-value pair, where both elements can be of any generic types.

### Controller
1. **Controller Interface**
  - **Attributes**:
    - `setView`: Associates a view with the controller.
    - Constructor accepts the model (temporary: expected to change based on future requirements).

2. **Feature Interface**:
  - Contains temporary print methods to test and validate GUI.

### View
For the view, if the hand is not current player's hand, it will get gray out, but if you click on 
one of the cards, the controller should still be able to print out the index of the cards that is 
clicked for now. This will change based on the specification of the next homework.
1. **Package Structure**:
  - Split into `console` (text view) and `gui` (graphical user interface).

2. **GUI Components**
  - **Interfaces**:
    - `GamePanel`: Manages game panel behavior, with methods such as `addFeature` and `refresh`.
    - `GameGUIView`: Extends `GamePanel` with additional methods like `makeVisible`.
  - **AddFeature**: Used for controller integration via command callbacks.

3. **GUI Layout and Components**
  - **Cell Card**: Component responsible for drawing card representations.
  - **ThreeTriosView**: The main `JFrame` for the GUI, containing the MainPanel.
  - **Main Panel (`JPanel`)**: Contains three sub-panels:
    - `GridPanel`: Displays the game grid.
    - `HandPanel`: Displays the hand of each player (Red and Blue).

#### Execution
- **Main Class**: Entry point to run the application.


## Source Organization

```plaintext
├── assets
│   ├── Card from Blue.png
│   ├── Card from Red.png
│   ├── Game In Progress.png
│   └── Initial State.png
├── config
│   ├── cards
│   │   ├── big_cards.txt
│   │   ├── big_grid_cards.txt
│   │   ├── invalid_letter.txt
│   │   ├── invalid_number.txt
│   │   ├── no_name.txt
│   │   ├── not_enough_values.txt
│   │   ├── repeated_names.txt
│   │   └── small_cards.txt
│   └── grid
│       ├── big_no_hole.txt
│       ├── complex_grid.txt
│       ├── invalid_char.txt
│       ├── no_holes.txt
│       ├── not_enough_cols.txt
│       ├── not_enough_rows.txt
│       ├── simple_grid.txt
│       └── wrong_format.txt
├── README.md
├── strategy-transcript.txt
├── three-trios.jar
├── src
│   ├── Main.java
│   ├── controller
│   │   ├── Feature.java
│   │   ├── GameConfigParser.java
│   │   ├── GameController.java
│   │   └── ThreeTriosController.java
│   ├── model
│   │   ├── Utils.java
│   │   ├── enums
│   │   │   ├── AttackValue.java
│   │   │   ├── CellType.java
│   │   │   ├── Direction.java
│   │   │   └── GamePlayer.java
│   │   ├── implementation
│   │   │   ├── ThreeTriosCard.java
│   │   │   ├── ThreeTriosCell.java
│   │   │   ├── ThreeTriosGrid.java
│   │   │   ├── ThreeTriosGridManager.java
│   │   │   ├── ThreeTriosHand.java
│   │   │   ├── ThreeTriosModel.java
│   │   │   └── ThreeTriosViewModel.java
│   │   └── interfaces
│   │       ├── Card.java
│   │       ├── Cell.java
│   │       ├── GameModel.java
│   │       ├── Grid.java
│   │       ├── GridManager.java
│   │       ├── Hand.java
│   │       └── ReadOnlyGameModel.java
│   ├── strategy
│   │   ├── Move.java
│   │   ├── Pair.java
│   │   ├── ThreeTriosMove.java
│   │   ├── AbstractStrategy.java
│   │   ├── fallible
│   │   │   ├── FallibleGameStrategy.java
│   │   │   └── CornerFallibleStrategy.java
│   │   └── infallible
│   │       ├── CornerInfallibleStrategy.java
│   │       ├── FlipCardsInfallibleStrategy.java
│   │       ├── InfallibleGameStrategy.java
│   │       └── UpperLeftInfallibleStrategy.java
│   └── view
│       ├── console
│       │   ├── GameView.java
│       │   └── ThreeTriosTextView.java
│       └── gui
│           ├── CellCard.java
│           ├── GameGUIView.java
│           ├── GamePanel.java
│           ├── ThreeTriosGridPanel.java
│           ├── ThreeTriosHandPanel.java
│           ├── ThreeTriosMainPanel.java
│           ├── ThreeTriosView.java
│           └── GameViewConfig.java
├── test
│   ├── controller
|   │   ├── ThreeTriosControllerTest.java
│   │   └── GameConfigParserTest.java
│   ├── model
│   │   ├── UtilsTest.java
│   │   ├── enums
│   │   │   ├── AttackValueTest.java
│   │   │   └── GamePlayerTest.java
│   │   └── implementation
│   │       ├── ThreeTriosCellTest.java
│   │       ├── ThreeTriosGridManagerTest.java
│   │       ├── ThreeTriosGridTest.java
│   │       ├── ThreeTriosHandTest.java
│   │       └── ThreeTriosModelTest.java
│   ├── strategy
│   │   ├── CornerStrategyTest.java
│   │   ├── FlipCardsStrategyTest.java
│   │   ├── MoveTest.java
│   │   ├── PairTest.java
│   │   ├── UpperLeftStrategyTest.java
│   │   └── mocks
│   │       ├── AbstractMockModel.java
│   │       ├── FlipManyCardsMockModel.java
│   │       ├── GoToCornerMockModel.java
│   │       └── NoMoveFoundMockModel.java
│   ├── suites
│   │   ├── AllTestsSuite.java
│   │   ├── ModelTestSuite.java
│   │   ├── StrategyTestSuite.java
│   │   └── ViewTestSuite.java
│   └── view
│       └── ThreeTriosTextViewTest.java
```
