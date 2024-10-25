The codebase is divided into two directories:
* src
* test

Within src, the project follows the MVC model, with two packages:
* model which contains the interface for models as well as components nad gameConfigParser
  * GameConfigParser allows for parsing the filePath of grid and list of cards for model
* view contains interface for the view

Components of Model:
* card: represent a card in the game
* enums: attack value on the card, cell type (cell or hole) on the grid, direction of the card (north, south,
east, west), game player (red and blue)
* grid: represent the grid of the game an support operations to mutate or get data about the grid
* hand: represent list of cards for a player to play to the grid
* manager: rule keeper for the grid, which manages battle rule
