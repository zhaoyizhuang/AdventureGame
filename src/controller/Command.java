package controller;

import model.dungeon.DungeonGame;

/**
 * Command interface for all commands that will be used in the controller.
 */
public interface Command {

  /**
   * Execute actions on the model based on different command line.
   * @param m Dungeon Game model.
   */
  void play(DungeonGame m);
}
