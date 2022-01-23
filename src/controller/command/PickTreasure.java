package controller.command;

import controller.Command;
import model.dungeon.DungeonGame;
import model.treasure.Treasure;

/**
 * The command to pick up treasure. The player can pick up treasures by specifying which kind
 * he/she wants.
 */
public class PickTreasure implements Command {
  private final Treasure treasure;

  /**
   * Constructor fot the PickTreasure command in terms of the treasure.
   * @param treasure the treasure that picked by player.
   * @throws IllegalArgumentException if invalid treasure.
   */
  public PickTreasure(String treasure) throws IllegalArgumentException {
    switch (treasure) {
      case "D":
        this.treasure = Treasure.DIAMOND;
        break;
      case "S":
        this.treasure = Treasure.SAPPHIRE;
        break;
      case "R":
        this.treasure = Treasure.RUBY;
        break;
      default:
        throw new IllegalArgumentException("Invalid Treasure");
    }
  }

  @Override
  public void play(DungeonGame m) {
    m.pick(treasure);
  }
}
