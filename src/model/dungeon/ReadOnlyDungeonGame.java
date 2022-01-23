package model.dungeon;

import model.smell.Smell;

import java.util.List;



/**
 * This interface is needed for a read-only model for Dungeon Game.
 */
public interface ReadOnlyDungeonGame {

  /**
   * return the coordination of the caves or tunnels that have been visited.
   * @return a list of positions.
   */
  List<List<Integer>> visited();

  /**
   * return the information of every cave in the dungeon.
   * @return a three-dimensional array reveals the information of the dungeon.
   */
  int[][][] dungeonInfo();

  /**
   * whether the player reaches the end cave.
   * @return true if player reaches the end, false otherwise.
   */
  boolean reachEnd();

  /**
   * whether the player is eaten by the monster.
   * @return true if player is dead, false otherwise.
   */
  boolean isDead();

  /**
   * get the current settings.
   * @return the properties of the current dungeon.
   */
  int[] getSettings();

  /**
   * check if the monster in the current cave is dead.
   * @return true if the monster in the current cave is dead, false otherwise.
   */
  boolean isMonsterDead();

  /**
   * get the smell in the current cave.
   * @return the smell in the current cave.
   */
  Smell getSmell();

  /**
   * return the description of the player.
   * @return a string describes the player.
   */
  String player();

  /**
   * return the description of the player to the view.
   * @return a string describes the player.
   */
  String playerView();

  /**
   * return a description of the cave.
   * @return a string describes the cave.
   */
  String cave();

  /**
   * return the current cave location of the player.
   * @return the current cave position.
   */
  int[] currLoc();
}
