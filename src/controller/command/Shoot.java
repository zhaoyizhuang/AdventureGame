package controller.command;

import controller.Command;
import model.direction.Direction;
import model.dungeon.DungeonGame;

/**
 * The command class for shooting. Player can shoot an arrow to a specific direction and distance.
 */
public class Shoot implements Command {
  private final int distance;
  private final Direction direction;
  private boolean shot;

  /**
   * The constructor for the Shoot command in terms of the distance and direction.
   * @param dis the distance that arrow travel.
   * @param dir the direction the arrow shoot at.
   */
  public Shoot(int dis, String dir) throws IllegalArgumentException {
    switch (dir) {
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
        throw new IllegalArgumentException("Invalid Direction");
    }
    distance = dis;
    shot = false;
  }

  @Override
  public void play(DungeonGame m) {
    shot = m.shoot(distance, direction);
  }

  /**
   * check if the arrow hit the monster.
   * @return true if the arrow hit the monster, false otherwise.
   */
  public boolean isShot() {
    return shot;
  }
}
