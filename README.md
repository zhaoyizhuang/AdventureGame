# Graphical Adventure Game
Author: Zhaoyi Zhuang 
## About/Overview: <br>
  This is an Adventure Game which support both text-based mode and Graphical mode. User will control a player to explore and escape from a dungeon. The player can move from one place to another, shoot arrows, pick up weapons and treasures. There are Monsters live in some randomly picked caves. They can eat the player who enters those caves without any preparation. This program uses the Object-Oriented Programming and MVC design. <br><br>
  
## How to Run: <br>
  - Download the [AdventureGame.jar](AdventureGame.jar) and using command line to start the game.
  - Using "java -jar AdventureGame.jar" to run the Graphical game. (You might need to scroll the screen to locate the character.)
  - The game starts with a default 6 * 6 unwrapping dungeon with 0 interconnectivity, 50% possibility of treasure/arrow and 1 monster.
  - Using "java -jar AdventureGame.jar 6 6 6 60 6 T" to run the text-based game.
  - The command line "6 6 6 60 6 T" represents row, column, interconnectivity, possibility of treasure/arrow, number of monsters, and is the Dungeon wrapping? T for True, F for False
  - You can customize the command line as long as row and col are > 5, interconnectivity and possibility is >= 0 and number of monster >= 1. <br><br>


## List of features: <br>
  - The dungeon is represented on a 2-d grid.
  - There are wrapping and non-wrapping dungeons where a wrapping dungeon means when a player is at the edge of the dungeon, the player can go cross the edge to the another side of the dungeon.
  - There is a path from every cave in the dungeon to every other cave in the dungeon.
  - Each dungeon can be constructed with a degree of interconnectivity. An interconnectivity = 0 when there is exactly one path from every cave in the dungeon to every other cave in the dungeon. Increasing the degree of interconnectivity increases the number of paths between caves.
  - One cave is randomly selected as the start and one cave is randomly selected to be the end. The path between the start and the end locations should be at least of length 5.
  - There are three types of treasure: diamonds, rubies, and sapphires.
  - Treasure to be added to a specified percentage of caves. For example, the client should be able to ask the model to add treasure to 20% of the caves and the model should add a random treasure to at least 20% of the caves in the dungeon. A cave can have more than one treasure.
  - There is one type of weapon: crookedArrow
  - CrookedArrow will be added to anywhere including tunnels in the dungeon with the same percentage as treasures.
  - Player starts with 3 crooked arrows.
  - Player can shoot an crookedArrow by specifying the distance and the direction. Distance is defined as the number of caves (but not tunnels) that an arrow travels. Arrows travel freely down tunnels (even crooked ones) but only travel in a straight line through a cave.
  - Player can move from the current location and pick up treasures and weapons in the current location if there is any.
  - There are monsters living in only caves in the dungeon.
  - While the number of monsters are chosen by the user, there is always a monster living in the end cave of the dungeon.
  - Monsters can be detected by their smell. In general, the player can detect two levels of smell:a less pungent smell can be detected when there is a single Otyugh 2 positions from the player's current location. detecting a more pungent smell either means that there is a single Otyugh 1 position from the player's current location or that there are multiple Otyughs within 2 positions from the player's current location.
  - It takes 2 hits to kill an Otyugh. Player has a 50% chance of escaping if the Otyugh if they enter a cave of an injured Otyugh that has been hit by a single crooked arrow.
  - a player entering a cave with an Otyugh that has not been slayed or injured will be killed and eaten
  - Distances must be exact. For example, if you shoot an arrow a distance of 3 to the east and the Otyugh is at a distance of 2, you miss the Otyugh.
  - Game Settings are exposed to User through the menu button in GUI. 
  - Menu button also provides an option for  quitting the game, restarting the game as a new game with a new dungeon or reusing the same dungeon
  - Player only see the map they have visited in GUI.
  - Player can move by press arrow key or click the place they want to move to.
  - Player can get the cave information and player information through the menu button.
  - Player can pick up and shoot in GUI.
  - GUI will send feedback to users after each action has been conducted. <br><br>


## How to Use the Program: <br>
  - For text-based Game:
    - Run the jar file with specified command line.
    - Follow the instruction on the console.
    - Then enter M for move, S for shoot, P for pick, INFO for player's information and CAVE for cave information.
    - If enter M, enter W for west, E for east, N for north, S for south, Q for reselect an action.
    - If enter P, enter W for weapon, T for treasure, Q for reselect an action.
    - If enter S, enter a number for distance, then enter a direction for direction.
  - For Graphical Game:
    - Run the jar file.
    - Click Menu -> Tutorial to see instructions.
    - To move, press the corresponding arrow key or click the target cave. 
    - To pick up Treasures, press E. 
    - To pick up Weapon, press R 
    - To shoot, press S, then press an arrow key to indicate the direction, then enter the distance that arrow will travel.
    - To restart with the same dungeon, click Menu -> Restart
    - To start a new game with different dungeon, click Menu -> New Game
    - To Quit, click Menu -> Quit
    - To see Player information, click Menu -> Player Info
    - To see Cave information, click Menu -> Cave Info
    - To see current dungeon's properties, click Menu -> Current Settings <br><br>


## Description of Examples: <br>
![](sample_screenshot.jpg)

Above images are the sample screenshot from the game. I go through the game, move, shoot, pick up treasures, weapons. Meet dead monster, escape successfully, lose and win. <br><br>

## Design/Model Changes: <br>
  - Changes I made:
    - Add a View Package which represents the GUI view part of the MVC design pattern.
    - Add a Features interface which extends the DungeonController interface and represents the features and interactions that the game will have in the GUI.
    - Add DungeonViewController class which implements the Features interface. This is the controller in the MVC design pattern.
    - Add ReadOnlyDungeonGame interface for view to use. 
    - Add restart(), getSettings(), currLoc(), visited(), dungeonInfo(), pickAllTreasure(), setLoc() to the DungeonGame. 
    - Add new constructor for the Cave.
    - Add a new player description for the view to use.
    - Delete generate() from Controller interface. <br><br>


## Assumptions: <br>
  - When interconnectivity is bigger than the most possible paths. The dungeon will have all paths connected.
  - When the possibility goes beyond 100%, simply every non-tunnel caves have treasure and every place will have arrows.
  - If there is a health Monster in the end cave and player reaches the end cave, player loses.
  - If there is an injured Monster in the end cave and player reaches the end cave, player wins if the player escape the attack from the Monster and lose otherwise.
  - If there is a dead Monster in the end cave and player reaches the end cave, player wins.
  - If the number of monster is more than caves number, all caves except the start one will have a monster.
  - Row and Column number must be bigger than 5.
  - After escape from the injured monster, Player either wins or move to another place instead of doing any other action.
  - Shooting distance must be bigger than 0 and shooting distance does not count the current location.
  - Interconnectivity, treasure possibility are non-negative
  - Player will pick all arrows at once instead of picking one by one.
  - number of monsters is >= 1.
  - Cave must have openings.
  - A Cave can have mostly 1 monster.
  - Tunnel cannot have treasure.
  - The graphical game will start with a default 6*6 Non-Wrapping dungeon with 0 interconnectivity, 50% treasures and weapons and 1 monster. <br><br>


## Limitations: <br>
  - Not allow Dungeon less than 6*6 size.
  - Player cannot continue exploring the dungeon after winning.
  - Only three kinds of treasures and one kind of weapon.
  - May be slow if the dungeon is too big.
  - No music, nor animation.
