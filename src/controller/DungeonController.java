package controller;

/**
 * The controller interface for the Dungeon game. The functions here have been
 * designed to give control to the controller. Handle user's action by executing them using model.
 */
public interface DungeonController {

  /**
   * Give control to controller. Execute a single game of dungeon game using dungeon game model.
   */
  void play();
}
