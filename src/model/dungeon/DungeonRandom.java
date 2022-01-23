package model.dungeon;

import constant.Constant;
import model.monster.Otyughs;
import model.treasure.Treasure;
import model.weapon.Weapon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

/**
 * This class represents how random works in the dungeon. Such as create random caves, locations,
 * and treasures.
 * This is package-private to hide implementation and avoid illegal access.
 */
class DungeonRandom {
  private final boolean testMode;
  private final List<Integer> testNum;
  private int index;
  private int[] path;

  /**
   * constructor for dungeonRandom.
   * @param b whether it is in test mode.
   */
  public DungeonRandom(boolean b) {
    testMode = b;
    testNum = new ArrayList<>();
    index = -1;
    path = new int[4];
  }

  /**
   * return a random number between the range of [min, max] inclusive.
   * @param min min number
   * @param max max number
   * @return  the random number.
   * @throws IllegalArgumentException if max or min number is negative.
   *                                    or min > max
   *                                    or no test number exist in test mode.
   */
  public int random(int min, int max) throws IllegalArgumentException {
    if (min < 0 || max < 0 || min > max) {
      throw new IllegalArgumentException("invalid min and max in random number");
    }
    if (!testMode) {
      return new Random().nextInt(max - min + 1) + min;
    } else {
      if (testNum.size() == 0) {
        throw new IllegalArgumentException("add some number to random list for test.");
      }
      index++;
      return testNum.get(index %= testNum.size());
    }
  }

  /**
   * Generate the testing random numbers.
   * @param ran list of number for testing.
   */
  public void addTestNum(int... ran) throws IllegalArgumentException {
    for (int i : ran) {
      if (i < 0) {
        throw new IllegalArgumentException("cannot be negative number");
      }
      testNum.add(i);
    }
  }

  /**
   * generate random dungeon of caves with random openings, paths and treasures.
   * @param row row number of the dungeon.
   * @param col column number of the dungeon.
   * @param interconnectivity interconnectivity of the dungeon.
   * @param possibility possibility of treasures in the dungeon.
   * @param wrap whether the dungeon is warping.
   * @return the random caves.
   */
  public Cave[][] caves(int row, int col, int interconnectivity,
                        int possibility, boolean wrap) {
    Cave[][] res = new Cave[row][col];
    boolean[][][] paths = kruskal(row, col, interconnectivity, wrap);
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        res[i][j] = new Cave(paths[i][j][0], paths[i][j][1], paths[i][j][2], paths[i][j][3]);
      }
    }
    randItem(res, possibility);
    return res;
  }

  // Using kruskal algorithm to generate caves.
  // throw IllegalArgumentException when there is no path > 5 between 2 caves.
  private boolean[][][] kruskal(int row, int col, int inter, boolean wrap)
          throws IllegalArgumentException {
    boolean[][][] res;
    while (true) {
      res = new boolean[row][col][4];
      List<int[]> edges = new ArrayList<>();
      edges(edges, row, col, wrap);
      openWalls(edges, res, inter);
      Set<List<Integer>> vis = new HashSet<>();
      while (vis.size() < row * col) {
        int x = random(0, row - 1);
        int y = random(0, col - 1);
        List<Integer> start = Arrays.asList(x, y);
        if (vis.contains(start) || isTunnel(res[x][y])) {
          //not visited and not tunnel.
          continue;
        }
        vis.add(start);
        List<int[]> paths = validPath(res, start);
        if (paths.size() > 0) {
          int end = random(0, paths.size() - 1);
          path = new int[]{x, y, paths.get(end)[0], paths.get(end)[1]};
          return res;
        }
      }
    }
  }

  // check if it is a tunnel
  private boolean isTunnel(boolean[] b) {
    int check = 0;
    for (boolean bol : b) {
      if (bol) {
        check++;
      }
    }
    return check == 2;
  }

  // find the available path of the cave at [x, y]
  private List<int[]> validPath(boolean[][][] cave, List<Integer> start) {
    int m = cave.length;
    int n = cave[0].length;
    int[][] dir = new int[][]{{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
    int[][] dis = new int[m][n];
    for (int[] row : dis) {
      Arrays.fill(row, Integer.MAX_VALUE);
    }
    dis[start.get(0)][start.get(1)] = 0;

    Queue<int[]> q = new LinkedList<>();
    q.add(new int[]{start.get(0), start.get(1)});
    while (!q.isEmpty()) {
      int[] curr = q.poll();
      for (int i = 0; i < 4; i++) {
        if (cave[curr[0]][curr[1]][i]) {
          int r = (((curr[0] + dir[i][0]) % m + m) % m);
          int c = (((curr[1] + dir[i][1]) % n + n) % n);
          if (dis[r][c] <= dis[curr[0]][curr[1]] + 1) {
            continue;
          }
          q.add(new int[]{r, c});
          dis[r][c] = dis[curr[0]][curr[1]] + 1;
        }
      }
    }

    List<int[]> res = new ArrayList<>();
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        // longer than 5 and end not tunnel.
        if (dis[i][j] >= Constant.MIN_DIST && !isTunnel(cave[i][j])) {
          res.add(new int[]{i, j});
        }
      }
    }

    return res;
  }

  // generate caves based on interconnectivity.
  private void openWalls(List<int[]> edges, boolean[][][] caves, int inter) {
    List<int[]> leftOver = new ArrayList<>();
    while (edges.size() > 0) {
      int i = random(0, edges.size() - 1);
      int[] curr = edges.get(i);
      if (existPath(caves, curr)) {
        leftOver.add(curr);
      }
      else {
        open(caves, curr);
      }
      edges.remove(i);
    }
    // add inter. If inter too big, connect all paths.
    if (inter > leftOver.size() - 1) {
      inter = leftOver.size();
    }
    for (int i = 0; i < inter; i++) {
      open(caves, leftOver.get(i));
    }
  }

  // Check if there exist a path between two caves using BFS.
  private boolean existPath(boolean[][][] caves, int[] curr) {
    int[][] dir = new int[][]{{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
    int rl = caves.length;
    int cl = caves[0].length;
    int[] start = new int[]{curr[0], curr[1]};
    int[] target = new int[]{curr[2], curr[3]};
    List<int[]> explored = new ArrayList<>();
    explored.add(start);
    Queue<int[]> queue = new LinkedList<>();
    queue.add(start);
    while (!queue.isEmpty()) {
      int[] step = queue.poll();
      if (Arrays.equals(step, target)) {
        return true;
      }
      for (int b = 0; b < 4; b++) {
        if (caves[step[0]][step[1]][b]) {
          int[] adj = new int[2];
          boolean exist = false;
          adj = new int[]{((step[0] + dir[b][0]) % rl + rl) % rl, (
                  (step[1] + dir[b][1]) % cl + cl) % cl};
          // when List<int[]> using contains, it check if two int[] has same reference, instead
          // of same values. So, I need to go over whole list to check if exists.
          for (int[] ele : explored) {
            if (Arrays.equals(ele, adj)) {
              exist = true;
              break;
            }
          }
          if (!exist) {
            explored.add(adj);
            queue.add(adj);
          }
        }
      }
    }
    return false;
  }

  // open a wall in a cave based on the edge.
  private void open(boolean[][][] caves, int[] curr) {
    // only two possible conditions, because edges generate from left top to right down.
    if (curr[0] < curr[2] || curr[0] > curr[2]) {
      caves[curr[0]][curr[1]][1] = true;
      caves[curr[2]][curr[3]][0] = true;
    } else {
      caves[curr[0]][curr[1]][2] = true;
      caves[curr[2]][curr[3]][3] = true;
    }
  }

  // get all edges in form int{x1, y1, x2, y2}
  private void edges(List<int[]> path, int row, int col, boolean wrap) {
    if (wrap) {
      for (int i = 0; i < row; i++) {
        path.add(new int[]{i, col - 1, i, 0});
      }
      for (int i = 0; i < col; i++) {
        path.add(new int[]{row - 1, i, 0, i});
      }
    }
    for (int count = 0; count < row * col; count++) {
      int x = count / col;
      int y = count % col;
      if (x < row - 1) {
        path.add(new int[]{x, y, x + 1, y});
      }
      if (y < col - 1) {
        path.add(new int[]{x, y, x, y + 1});
      }
    }
  }

  /**
   * to add p percentage treasure to cave and p percentage weapon to cave and tunnel.
   * @param cave cave to be added items.
   * @param p p percentage of items.
   */
  public void randItem(Cave[][] cave, int p) {
    int rlength = cave.length;
    int clength = cave[0].length;
    int all = rlength * clength;
    int num = (all * p) / 100;
    int weaponNum = Math.min(all, num);
    while (weaponNum > 0) {
      int index = random(0, all);
      while (cave[(index / clength) % rlength][index % clength].getWeapons().size() != 0) {
        index++;
      }
      List<Weapon> t = new ArrayList<>();
      int i1 = random(1, 2);
      for (int i = 0; i < i1; i++) {
        t.add(Weapon.CROOKEDARROW);
      }

      cave[(index / clength) % rlength][index % clength].setWeapons(t);
      weaponNum--;
    }
    int tunnelCount = tunnelCount(cave);
    num = Math.min(all - tunnelCount, ((all - tunnelCount) * p) / 100);
    while (num > 0) {
      int index = random(0, all);
      while (cave[(index / clength) % rlength][index % clength].getTreasures().size() != 0) {
        index++;
      }
      Cave curr = cave[(index / clength) % rlength][index % clength];
      if (curr.isTunnel()) {
        continue;
      }
      List<Treasure> t = new ArrayList<>();
      int i1 = random(1, 2);
      int i2 = random(1, 2);
      int i3 = random(1, 2);
      for (int i = 0; i < i1; i++) {
        t.add(Treasure.DIAMOND);
      }
      for (int i = 0; i < i2; i++) {
        t.add(Treasure.SAPPHIRE);
      }
      for (int i = 0; i < i3; i++) {
        t.add(Treasure.RUBY);
      }

      curr.setTreasures(t);
      num--;
    }
  }

  //find the number of tunnels.
  private int tunnelCount(Cave[][] caves) {
    int tunnelCount = 0;
    for (Cave[] c : caves) {
      for (Cave c1 : c) {
        //c1.setTreasures(new ArrayList<>());
        if (c1.isTunnel()) {
          tunnelCount++;
        }
      }
    }
    return tunnelCount;
  }

  /**
   * return random path.
   * @return random start and end cave.
   */
  public int[] getPath() {
    return path;
  }

  /**
   * set the monster's location in the cave.
   * @param caves the dungeon caves.
   * @param num   the number of monsters.
   * @param loc   the start location
   * @param end   the end location
   */
  public void setMonster(Cave[][] caves, int num, int[] loc, int[] end) {
    int rlength = caves.length;
    int clength = caves[0].length;
    int all = rlength * clength;
    caves[end[0]][end[1]].addMonster(new Otyughs());
    int tunnelCount = tunnelCount(caves);
    num = Math.min(all - tunnelCount - 1, num);
    num -= 1;
    while (num > 0) {
      int index = random(0, all);
      while (caves[(index / clength) % rlength][index % clength].hasMonster()) {
        index++;
      }
      Cave curr = caves[(index / clength) % rlength][index % clength];
      if (curr.isTunnel()
              || ((index / clength) % rlength == loc[0]) && (index % clength) == loc[1]) {
        continue;
      }
      curr.addMonster(new Otyughs());
      num--;
    }
  }

}
