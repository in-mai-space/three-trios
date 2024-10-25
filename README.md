---
## Overview
### Gameplay
The ThreeTrios game is a competitive card game for two players, Red and Blue. Each player has a hand
of cards, where each card is assigned 4 numerical values. The game proceeds as follows:
1. Gameplay: Players take turns playing their cards in a grid format. Each card's value determines its strength in battle against the opponent's card.
2. Objective: The goal is to fill the grid with cards while trying to outscore the opponent. The game continues until all cells in the grid are filled.
3. Outcome: At the end of the game, the results are evaluated to determine if there is a winner or if the game ends in a tie.

> This codebase assumes familiarity with core game mechanics (card battling, game completion, and player operations) and a working understanding of MVC architecture, basic data structures, and the Java SDK and libraries.

## Codebase game assumptions
- **two players**: Red and Blue
- **fixed amount of players**: amount of players is fixed throughout the game
- **card uniqueness**: name and 4 numerical values
- **alternate turns**: players strictly alternate turns with Red starting first
- **game states**: game state can be started, in progress and over

## Player Interface
- select a card
- place a card
- pause the game
- quit the game

## Extensibility of codebase
The design allows for potential expansion, including:
1. **Support for Additional Players**: The architecture supports more than two players by managing players in a list and using an index-based method to determine the next player.
2. **Customizable Game Rules**: Different game modes can be introduced by extending the `ThreeTriosGridManager` class, allowing for new scoring methods or additional card types.
3. **Special Cards with Unique Abilities**: Specialized cards with unique abilities can be added by subclassing `Card` and overriding specific methods.
4. **Different View Implementations**: The `GameView` interface can support multiple implementations for various platforms, such as console and GUI.

## Key components
The code follows the Model-View-Controller (MVC) pattern:

### Model (Core Game Logic)
The model manages the game's state and flow:
- **Game Initialization**: Validates inputs, sets up the grid and players, and shuffles cards if necessary.
- **Game State Management**: Tracks the current player and identifies when the game is over.
- **Card Placement**: Enforces rules for card placement, including cell-type restrictions.
- **Winner Calculation**: Computes scores and determines the winner, accounting for ties.

#### Model Details
- **ReadOnlyGameModel Interface**: Provides the view with access to game state information without exposing mutative methods.
- **GameModel Interface**: This model is accessed by the controller, allowing full access to the model's methods.

### View (Driven by the Model)
The view presents the game's state to the players:
- **Responsibilities**: The view renders the current player's information and cards, as well as the grid layout. It formats data into a displayable string, bridging the game state and user interface.

## Key Subcomponents of Model
The model's core classes include:

- **Card**: Represents the playable card in the game.
- **Attributes**: AttackValue (1-9, with 'A' for 10), GamePlayer (owner), Direction (attack directions).
- **Grid**: Defines the grid layout and manages card placement.
- **Attributes**: CellType (cell or hole) and Card[][] (grid state).
- **Hand**: Represents a player's collection of usable cards.
- **Attributes**: List of cards (managed via a list rather than an array).
- **GridManager**: Manages interactions with the grid, including move validation, battles, and game state updates.
- **GameConfigParser**: Reads and parses configuration files to set up the game’s grid layout and load card data.


## Source Organization

```plaintext
src
├── model
│   ├── enums: AttackValue, CellType, Direction, GamePlayer
│   ├── implementation: GameConfigParser, ThreeTriosCard, ThreeTriosGrid, ThreeTriosGridManager, ThreeTriosHand, ThreeTriosMode
│   ├── interfaces: Card, GameModel, Grid, GridManager, Hand, ReadOnlyGameModel
├── view: interface GameView and class ThreeTriosTextView
test
├── model
│   ├── enums: tests for enums
│   ├── implementation: tests for implementation classes
├── view: test for text view
├── suites: run all model tests, all view tests, run all tests
config
├── cards: config files for card database
├── grid: config files for grid structure
```