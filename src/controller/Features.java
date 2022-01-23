package controller;

import model.direction.Direction;

/**
 * The interface for the set of features that the program offers. Each method represents how the
 * program will react to a specific action has been conducted. Send the input received from
 * view to controller. And controller will decide what to do next.
 */
public interface Features extends DungeonController {

  /**
   * exit the program with status 0.
   */
  void exitProgram();

  /**
   * restart the game with the same settings.
   */
  void restart();

  /**
   * call this function to invoke the new game setting in the view.
   */
  void newGameTrigger();

  /**
   * restart the game with different settings.
   * @param row row number
   * @param col column number
   * @param interconnectivity interconnectivity
   * @param wrap if the dungeon is wrapping or not
   * @param possibility possibility of treasure and weapons
   * @param num number of monsters.
   */
  void newGame(int row, int col, int interconnectivity, boolean wrap, int possibility, int num);

  /**
   * move to another cave or tunnel.
   * @param direction the direction the player moves to.
   */
  void move(Direction direction);

  /**
   * pick up all treasures the user sees.
   */
  void pickTreasure();

  /**
   * pick up all weapons the user sees.
   */
  void pickWeapon();

  /**
   * shoot an arrow into a specific direction with specific distance.
   * @param direction direction the arrow shoot to
   * @param dis distance the arrow will travel.
   */
  void shoot(Direction direction, int dis);

  /**
   * get the player information.
   */
  void playerInfo();

  /**
   * get the cave information.
   */
  void caveInfo();

  /**
   * get the current settings of the dungeon game.
   */
  void getSettings();

  /**
   * move the player to the clicked cave.
   * @param x x position
   * @param y y position
   */
  void clickMove(int x, int y);

}
