package model.dungeon;

import model.direction.Direction;
import model.treasure.Treasure;

/**
 * interface for dungeon serve as the main entry point into the code
 * and hide implementation from the user.
 */
public interface DungeonGame extends ReadOnlyDungeonGame {
  /**
   * let the player pick a treasure from a cave.
   * @param t the treasure
   * @throws IllegalArgumentException when pick a null or there is no treasure in cave.
   */
  void pick(Treasure t) throws IllegalArgumentException, IllegalStateException;

  /**
   * let the player pick all treasures from a cave.
   */
  void pickAllTreasure() throws IllegalStateException;

  /**
   * Let the player pick all weapons from either a cave or a tunnel.
   */
  void pickWeapon() throws IllegalStateException;

  /**
   * player move to whatever direction they want.
   * @param d direction the player wants to make.
   * @throws IllegalStateException if there is no way in the direction or game is over.
   * @throws IllegalArgumentException if d is invalid.
   */
  void move(Direction d) throws IllegalStateException, IllegalArgumentException;

  /**
   * Player can shoot an arrow at a specific direction and specific distance. A crooked arrow
   * travels freely down tunnels.
   * @param dis distance the arrow will travel
   * @param dir the direction arrow will travel to.
   * @return true if arrow shot the monster, false otherwise.
   * @throws IllegalArgumentException if invalid distance and direction.
   * @throws IllegalStateException if game is over
   */
  boolean shoot(int dis, Direction dir) throws IllegalArgumentException, IllegalStateException;

  /**
   * check if the player met the monster.
   * @return true if the player met the monster, false, otherwise.
   */
  boolean metMonster();

  /**
   * restart the game with the same settings.
   */
  void restart();

  /**
   * set the current location of the player.
   * @param x x position
   * @param y y position
   * @throws IllegalArgumentException if the position is out of range or unvisited.
   */
  void setLoc(int x, int y) throws IllegalArgumentException;
}
