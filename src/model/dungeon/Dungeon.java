package model.dungeon;

import model.direction.Direction;
import model.smell.Smell;
import model.treasure.Treasure;
import model.weapon.Weapon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class represents a Dungeon which is a 2-d grid consists of many caves. Each cave may
 * have treasures, arrow and monster in it. Arrow can also be found in tunnels.
 * Player can go from one cave to another one and pick up treasures and weapons in it.
 * The dungeon will have randomly selected start and end caves for the player where there is always
 * a monster live in the end cave.
 */
public class Dungeon implements DungeonGame {
  protected Cave[][] caves;
  protected int[] location;
  protected int[] end;
  protected Player player;
  protected DungeonRandom rand;
  protected Set<List<Integer>> visited;

  protected int[] start;
  protected Cave[][] storedCave;
  protected int[] settings;

  /**
   * Constructor for Dungeon in terms of size, interconnectivity, wrap, and treasure percentage.
   * @param row row of the dungeon
   * @param col column number of the dungeon
   * @param interconnectivity interconnectivity of the dungeon
   * @param wrap if the dungeon is wrapping
   * @param possibility treasure and arrow percentage.
   * @param num the number of monsters.
   * @throws IllegalArgumentException if treasure percentage or interconnectivity is < 0.
   *                                  or row * col < 5 .
   */
  public Dungeon(int row, int col, int interconnectivity, boolean wrap, int possibility, int num)
          throws IllegalArgumentException {
    if (possibility < 0 || interconnectivity < 0 || num < 1) {
      throw new IllegalArgumentException("treasure or arrow percentage, interconnectivity, "
              + "cannot be negative, and there is at least 1 monster.");
    }
    if (row <= 5 || col <= 5) {
      throw new IllegalArgumentException("At least 6 * 6 dungeon");
    }
    settings = new int[]{row, col, interconnectivity, wrap ? 1 : 0, possibility, num};
    player = new Player();
    visited = new HashSet<>();
    rand = new DungeonRandom(false);
    caves = rand.caves(row, col, interconnectivity, possibility, wrap);
    int[] loc = rand.getPath();
    location = new int[]{loc[0], loc[1]};
    end = new int[]{loc[2], loc[3]};
    rand.setMonster(caves, num, location, end);
    addVisited();

    start = new int[]{loc[0], loc[1]};
    storedCave = new Cave[caves.length][caves[0].length];
    copyCaves(storedCave, caves);
  }

  //add visited place to the visited set.
  protected void addVisited() {
    List<Integer> vis = new ArrayList<>();
    vis.add(location[0]);
    vis.add(location[1]);
    visited.add(vis);
  }

  /**
   * defensive copy of caves.
   * @param to the destination of this copy.
   * @param from the caves that is being copied.
   */
  protected void copyCaves(Cave[][] to, Cave[][] from) {
    for (int i = 0; i < from.length; i++) {
      for (int j = 0; j < from[0].length; j++) {
        to[i][j] = new Cave(from[i][j]);
      }
    }
  }

  /**
   * Constructor for an empty dungeon.
   * @param test if this is in test mode.
   */
  public Dungeon(boolean test) {
    this(6, 6, 0, false, 50, 1);
    rand = new DungeonRandom(test);
  }

  @Override
  public void pick(Treasure t) throws IllegalArgumentException, IllegalStateException {
    if (reachEnd() || isDead()) {
      throw new IllegalStateException("Game is over");
    }
    if (t == null) {
      throw new IllegalArgumentException("trying to pick a null treasure");
    }
    caves[location[0]][location[1]].removeTreasure(t);
    player.pick(t);
  }

  @Override
  public void pickAllTreasure() throws IllegalStateException, IllegalArgumentException {
    if (reachEnd() || isDead()) {
      throw new IllegalStateException("Game is over");
    }
    if (caves[location[0]][location[1]].getTreasures().size() == 0) {
      throw new IllegalArgumentException("No Treasure in the cave");
    }
    for (Treasure t : caves[location[0]][location[1]].getTreasures()) {
      pick(t);
    }
  }

  @Override
  public void pickWeapon() throws IllegalStateException {
    if (reachEnd() || isDead()) {
      throw new IllegalStateException("Game is over");
    }
    List<Weapon> weapons = caves[location[0]][location[1]].removeWeapon();
    player.pickWeapon(weapons);
  }

  @Override
  public void move(Direction d) throws IllegalStateException, IllegalArgumentException {
    if (reachEnd() || isDead()) {
      throw new IllegalStateException("Game is over");
    }
    if (d == null) {
      throw new IllegalArgumentException("direction cannot be null");
    }
    switch (d) {
      case WEST :
        if (!caves[location[0]][location[1]].isWest()) {
          throw new IllegalStateException("No way to west.");
        }
        location[1] = modCol(location[1] - 1);
        break;
      case EAST :
        if (!caves[location[0]][location[1]].isEast()) {
          throw new IllegalStateException("No way to east.");
        }
        location[1] = modCol(location[1] + 1);
        break;
      case SOUTH :
        if (!caves[location[0]][location[1]].isSouth()) {
          throw new IllegalStateException("No way to south.");
        }
        location[0] = modRow(location[0] + 1);
        break;
      case NORTH :
        if (!caves[location[0]][location[1]].isNorth()) {
          throw new IllegalStateException("No way to north.");
        }
        location[0] = modRow(location[0] - 1);
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + d);
    }
    addVisited();
  }


  @Override
  public List<List<Integer>> visited() {
    List<List<Integer>> res = new ArrayList<>();
    for (List<Integer> li : visited) {
      List<Integer> curr = new ArrayList<>(li);
      res.add(curr);
    }
    return res;
  }

  @Override
  public int[][][] dungeonInfo() {
    // 0 - north, 1 - south, 2 - east, 3 - west, (1 -true, 0 - false)
    // 4 - diamond, 5 - ruby, 6 - sapphire
    // 7 - weapon, 8 - monster
    int[][][] res = new int[caves.length][caves[0].length][9];
    for (int i = 0; i < caves.length; i++) {
      for (int j = 0; j < caves[0].length; j++) {
        Cave curr = caves[i][j];
        for (int x = 0; x < 4; x++) {
          res[i][j][x] = curr.getOpenings()[x] ? 1 : 0;
        }
        for (Treasure t : curr.getTreasures()) {
          switch (t) {
            case DIAMOND:
              res[i][j][4]++;
              break;
            case SAPPHIRE:
              res[i][j][6]++;
              break;
            case RUBY:
              res[i][j][5]++;
              break;
            default:
              break;
          }
        }
        for (Weapon w : curr.getWeapons()) {
          res[i][j][7]++;
        }
        res[i][j][8] = curr.hasMonster() ? 1 : 0;
      }
    }
    return res;
  }

  @Override
  public boolean reachEnd() {
    return location[0] == end[0]
            && location[1] == end[1];
  }

  @Override
  public boolean isDead() {
    return player.isDead();
  }

  @Override
  public int[] getSettings() {
    return new int[]{settings[0], settings[1], settings[2], settings[3], settings[4], settings[5]};
  }

  @Override
  public Smell getSmell() {
    int[][] dir = new int[][]{{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
    Set<List<Integer>> vis = new HashSet<>();
    vis.add(Arrays.asList(location[0], location[1]));
    Cave curr = caves[location[0]][location[1]];
    if (curr.hasMonster() && !isMonsterDead()
            || smellHelper(location[0], location[1], vis, dir) > 0) {
      return Smell.PUNGENT;
    }
    int res = 0;
    for (int i = 0; i < 4; i++) {
      if (curr.getOpenings()[i]) {
        res += smellHelper(modRow(location[0] + dir[i][0]),
                modCol(location[1] + dir[i][1]), vis, dir);
      }
    }
    return res == 0 ? Smell.NONE : (res == 1 ? Smell.FAINT : Smell.PUNGENT);
  }


  //helper function for getSmell. return the number of monster in adjacent caves.
  private int smellHelper(int x, int y, Set<List<Integer>> vis, int[][] dir) {
    Cave curr = caves[x][y];
    int res = 0;
    for (int i = 0; i < 4; i++) {
      int m = modRow(x + dir[i][0]);
      int n = modCol(y + dir[i][1]);
      List<Integer> c = Arrays.asList(m, n);
      if (!vis.contains(c) && curr.getOpenings()[i]) {
        vis.add(c);
        if (caves[m][n].hasMonster() && !caves[m][n].isMonsterDead()) {
          res++;
        }
      }
    }
    return res;
  }

  //get the mod value of row number in case od wrap dungeon
  private int modRow(int r) {
    int rl = caves.length;
    return (r % rl + rl) % rl;
  }

  //get the mod value of col number in case od wrap dungeon
  private int modCol(int c) {
    int cl = caves[0].length;
    return (c % cl + cl) % cl;
  }

  @Override
  public boolean shoot(int dis, Direction dir)
          throws IllegalArgumentException, IllegalStateException {
    if (reachEnd() || isDead()) {
      throw new IllegalStateException("Game is over");
    }
    if (dis <= 0) {
      throw new IllegalArgumentException("distance should > 0");
    }
    if (dir == null) {
      throw new IllegalArgumentException("trying to shoot at direction null");
    }
    player.shoot();
    int[] temp = new int[]{location[0], location[1]};
    int[] come = {0};
    switch (dir) {
      case NORTH:
        come[0] = 1;
        break;
      case WEST:
        come[0] = 2;
        break;
      case EAST:
        come[0] = 3;
        break;
      default:
        break;
    }
    boolean start = true;
    int i = 0;
    while (true) {
      if (caves[temp[0]][temp[1]].isTunnel() && !start) {
        shotHelperTunnel(caves[temp[0]][temp[1]], temp, come);
        continue;
      }
      start = false;
      if (i >= dis && !caves[temp[0]][temp[1]].isTunnel()) {
        break;  //tunnel does not count.
      }
      i++;
      switch (come[0]) {
        case 1:
          if (caves[temp[0]][temp[1]].isNorth()) {
            shotHelperCave(temp, -1, 0);
          } else {
            return false;
          }
          break;
        case 0:
          if (caves[temp[0]][temp[1]].isSouth()) {
            shotHelperCave(temp, 1, 0);
          } else {
            return false;
          }
          break;
        case 3:
          if (caves[temp[0]][temp[1]].isEast()) {
            shotHelperCave(temp, 0, 1);
          } else {
            return false;
          }
          break;
        case 2:
          if (caves[temp[0]][temp[1]].isWest()) {
            shotHelperCave(temp, 0, -1);
          } else {
            return false;
          }
          break;
        default:
          break;
      }
    }

    if (caves[temp[0]][temp[1]].hasMonster() && !caves[temp[0]][temp[1]].isMonsterDead()) {
      caves[temp[0]][temp[1]].shot();
      return true;
    }
    return false;
  }

  // helper function for the shot() method. go to the same direction in cave.
  private void shotHelperCave(int[] temp, int i, int j) {
    temp[0] = modRow(temp[0] + i);
    temp[1] = modCol(temp[1] + j);
  }

  // helper function for the shot() method. go to the another opening in tunnel.
  private void shotHelperTunnel(Cave curr, int[] temp, int[] come) {
    int[] next = curr.tunnelNext(come[0]);
    temp[0] = modRow(temp[0] + next[0]);
    temp[1] = modCol(temp[1] + next[1]);
    come[0] = next[2];
  }

  @Override
  public boolean metMonster() {
    Cave curr = caves[location[0]][location[1]];
    if (curr.hasMonster() && !curr.isMonsterDead()) {
      if (curr.isMonsterInjured()) {
        int x = rand.random(0, 1);
        if (x == 0) {
          return true;
        }
      }
      player.setDead();
      return true;
    }
    return false;
  }

  @Override
  public void restart() {
    player = new Player();
    location = new int[]{start[0], start[1]};
    visited.clear();
    visited.add(Arrays.asList(start[0], start[1]));
    copyCaves(caves, storedCave);
  }

  @Override
  public void setLoc(int x, int y) throws IllegalArgumentException {
    if (x < 0 || x >= caves.length || y < 0 || y >= caves[0].length) {
      throw new IllegalArgumentException("Invalid place to move to.");
    }
    if (!visited.contains(Arrays.asList(x, y))) {
      throw new IllegalArgumentException("You cannot move to an unvisited place.");
    }
    location = new int[]{x, y};
  }

  @Override
  public int[] currLoc() {
    return new int[]{location[0], location[1]};
  }

  @Override
  public boolean isMonsterDead() {
    return caves[location[0]][location[1]].isMonsterDead();
  }

  @Override
  public String player() {
    return player.toString();
  }

  @Override
  public String playerView() {
    return player.info();
  }

  @Override
  public String cave() {
    return "Player's current location: "
            + location[0] + "-" + location[1] + caves[location[0]][location[1]].toString();
  }

  @Override
  public String toString() {
    StringBuilder res = new StringBuilder("Dungeon: Player's current cave: ");
    res.append("cave").append(location[0]).append("-").append(location[1]);
    res.append("\nend cave is: cave").append(end[0]).append("-").append(end[1]);
    res.append("\nPlayer's info: ");
    res.append(player.toString()).append("\n");
    for (int i = 0; i < caves.length; i++) {
      for (int j = 0; j < caves[0].length; j++) {
        res.append(i).append("-").append(j).append(caves[i][j].toString()).append("\n");
      }
    }
    return res.toString();
  }
}
