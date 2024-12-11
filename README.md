# Battleship - Advanced Java

By Kayla Heady

# Rules of Battleship

- Each player begins with a 10x10 grid and place 5 ships on their grid. All ships are 1 unit wide,
  and with lengths of 5, 4, 3, 3, and 2. These ships can be placed either horizontally or vertically.
- The game starts with the player and their opponent not knowing where the opponent’s ships
  are.
- After the preparation stage of placing ships the game begins allowing
  players to select one new position on the opponent’s grid that has not yet been attacked.
- The positions are marked once they have been attacked, either with a white marker indicating it was a miss in open water, or a red marker indicating a ship was hit.
- Once a ship has been destroyed from all grid positions having been hit, the ship is revealed as destroyed to the other player.
- The game ends when all ships for either player’s side have been destroyed. The winner is the
  player with ships still not destroyed.

# The Computer

The computer uses AI to randomly selecting places to put the opponets ship. The easy mode will make entirely random moves while hard mode will focus on areas where its hit ships.

# The cell mark

- When hit cell will be red
- When missed cell will be white
- When a whole ship os destroyed it will show the ship behind the red markers and the ship count will decrease

# Controls

- At any time:
  - Escape to quit.
  - R to restart.
  - D to activate debug mode to cheat and view the opponent’s ships.
- During Placement Phase:
  - Click to place ship (only places the ship if it is a valid placement).
  - Z to rotate between vertical and horizontal for placement.
- During Attack Phase:
  - Click on the enemy’s grid in places that have not yet been marked to reveal the
    squares as hits or misses.

# Classes

- Marker: Represents the red or blue markers that show up when attacking on the grid.
  All 10x10 of these are created at the start and drawn if they have been marked. They
  keep track of the ship they would represent hitting to allow the AI to get the information.
- Position: represents a position with an x and y coordinate mostly for indicating the
  coordinates, but in many cases also to represent offsets for drawing.
- Rectangle: Used to represent a generic rectangle with a top corner, width, and height with
  collision detection against a single point.
- SelectionGrid: The actual grid that contains a collection of markers, ships, and draws these
  with a set of lines to show where cells are. Includes appropriate methods to manage the state
  of the grid.
- Ship: Defines a ship that keeps track of where it is located on the grid and should be drawn.

  - The ship can also track whether it has been destroyed to notify other classes when asked.

- Game: Creates the JFrame to contain the GamePanel and manages collection of the keyboard input.
- GamePanel: Controls the two selection grids for the players and manages all the player interaction with their grid. Controls all the information necessary to manage the game state.

- StatusPanel: Represents the text panel in that outputs the moves of each player and records how many ships left are in the game.

- BattleshipAI: Defines the template with methods for the other classes to override. This does not do anything useful by itself.

- SimpleRandomAI: Defines the Easy difficulty mode AI that takes the list of valid moves, randomises the order of the moves, and then selects the first option.
