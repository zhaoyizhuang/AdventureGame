package model.monster;

/**
 * Monster interface represents a monster in the dungeon. The monster can be hurt and killed.
 */
public interface Monster {
  /**
   * Monster get shot by the player.
   */
  void shot();

  /**
   * If monster is hurt.
   * @return true if monster is hurt, false otherwise.
   */
  boolean isHurt();

  /**
   * If monster is dead.
   * @return true if monster is dead, false otherwise.
   */
  boolean isDead();
}
