package controller.command;

import controller.Command;
import model.dungeon.DungeonGame;

/**
 * The command class for picking up weapon. The player can pick up the weapons and equip them.
 */
public class PickWeapon implements Command {

  /**
   * Empty constructor since there is no field in the PickWeapon and nothing need to be specified.
   */
  public PickWeapon() {
     //Empty constructor since there is no field in the PickWeapon and nothing need to be specified.
  }

  @Override
  public void play(DungeonGame m) {
    m.pickWeapon();
  }
}
