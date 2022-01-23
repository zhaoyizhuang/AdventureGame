package controller.command;

import controller.Command;
import model.direction.Direction;
import model.dungeon.DungeonGame;

/**
 * Move command. The player can choose to move to four different direction.
 */
public class Move implements Command {
  private final Direction direction;

  /**
   * Constructor for the Move command in terms of the direction.
   * @param input W for west, E for east, N for north, S for south.
   * @throws IllegalArgumentException if invalid input
   * */
  public Move(String input) throws IllegalArgumentException {
    switch (input) {
      case "W":
        direction = Direction.WEST;
        break;
      case "S":
        direction = Direction.SOUTH;
        break;
      case "N":
        direction = Direction.NORTH;
        break;
      case "E":
        direction = Direction.EAST;
        break;
      default:
        throw new IllegalArgumentException("invalid command");
    }
  }

  @Override
  public void play(DungeonGame m) {
    m.move(direction);
  }
}
