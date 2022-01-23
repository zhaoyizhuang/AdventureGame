package view;

import controller.Features;
import model.dungeon.ReadOnlyDungeonGame;

/**
 * This class represents the view for the Dungeon Game. Provide a visual user
 * interface for users to interact.
 */
public interface GameView {

  /**
   * refresh the view if user takes any action that leads to the change
   * of the user interface.
   */
  void refresh();

  /**
   * make the view visible to the user.
   */
  void makeVisible();

  /**
   * Set the controller for the view so that the view can use features.
   *
   * @param f Features that will be used.
   */
  void setFeatures(Features f);

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
   * restart the game with the same settings.
   *
   * @param same true if restart with a same game, false if restart with a new game.
   * @param model new ReadonlyDungeonGame model.
   */
  void restart(boolean same, ReadOnlyDungeonGame model);

  /**
   * display the information of smell. level 1 for pungent, 0 for faint
   * @param level represent the level of the smell.
   */
  void smell(int level);

  /**
   * display the information of meeting a monster.
   */
  void metMonster();

  /**
   * display the information of the dead monster.
   */
  void deadMonster();

  /**
   * display the game status.
   * @param status true for win, false for lose.
   */
  void gameStatus(boolean status);

  /**
   * display the information of picking up weapons.
   */
  void pickWeapon();

  /**
   * display the information of picking up treasures.
   */
  void pickTreasure();

  /**
   * display the error message.
   * @param msg the error message.
   */
  void errorMessage(String msg);

  /**
   * display the information of the result of shoot.
   * @param shot true if shot, false if missed.
   */
  void shoot(boolean shot);

  /**
   * start a new game with different settings.
   *
   * @param f The controller take control of creating a new model.
   */
  void newGame(Features f);

  /**
   * handle the mouse click in this game.
   * @param f the controller react to the moust click.
   */
  void handleClick(Features f);
}
