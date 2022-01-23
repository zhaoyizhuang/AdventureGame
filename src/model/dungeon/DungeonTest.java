package model.dungeon;

/**
 * This class is the test class for Dungeon. Which inheritance from Dungeon and has basically same
 * thing as Dungeon class except it can modify the random number.
 */
public class DungeonTest extends Dungeon {
  /**
   * Constructor for a DungeonTest of a empty Dungeon.
   */
  public DungeonTest() {
    super(true);
  }

  /**
   * set random values in the rand.
   * @param i random values.
   */
  public void setRand(int... i) {
    rand.addTestNum(i);
  }

  /**
   * reset the cave for test purpose.
   * @param row row number
   * @param col column number
   * @param interconnectivity interconnectivity
   * @param wrap  if the dungeon is warping or not
   * @param treasure  possibility of the treasures.
   * @param num number of the monster
   * @throws IllegalArgumentException if enter invalid input for test.
   */
  public void setCaves(int row, int col,
                       int interconnectivity, boolean wrap, int treasure, int num)
          throws IllegalArgumentException {
    if (row < 3 || col < 3 || interconnectivity < 0 || treasure < 0 || num < 1) {
      throw new IllegalArgumentException("invalid input");
    }

    settings = new int[]{row, col, interconnectivity, wrap ? 1 : 0, treasure, num};
    player = new Player();
    caves = rand.caves(row, col, interconnectivity, treasure, wrap);
    int[] loc = rand.getPath();
    location = new int[]{loc[0], loc[1]};
    end = new int[]{loc[2], loc[3]};
    rand.setMonster(caves, num, location, end);
    visited.clear();
    addVisited();

    start = new int[]{loc[0], loc[1]};
    storedCave = new Cave[caves.length][caves[0].length];
    copyCaves(storedCave, caves);
  }

}
