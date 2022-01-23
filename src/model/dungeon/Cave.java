package model.dungeon;

import model.monster.Monster;
import model.monster.Otyughs;
import model.treasure.Treasure;
import model.weapon.Weapon;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a cave in the dungeon. Each cave has four directions where could be
 * walls or openings. Cave with two opening are tunnels. A cave can have treasures while a tunnel
 * can not. Both cave and tunnel can have arrows. Cave may also have monsters.
 * This is package-private to hide implementation and avoid illegal access.
 */
class Cave {
  private final boolean[] openings; // 0 - north, 1 - south, 2 - east, 3 - west
  private List<Treasure> treasures;
  private Monster monster;
  private List<Weapon> weapons;

  /**
   * Constructor for the cave in terms of opening of four directions.
   * @param north if north is open
   * @param south if south is open
   * @param east if east is open
   * @param west if west is open
   * @throws IllegalArgumentException if there is no openings.
   */
  public Cave(boolean north, boolean south, boolean east, boolean west)
          throws IllegalArgumentException {
    if (!north && !south && !east && !west) {
      throw new IllegalArgumentException("cave must have at least one opening.");
    }
    openings = new boolean[4];
    openings[0] = north;
    openings[1] = south;
    openings[2] = east;
    openings[3] = west;
    this.monster = null;
    this.weapons = new ArrayList<>();
    this.treasures = new ArrayList<>();
  }

  /**
   * Constructor for the cave in terms of another cave.
   * @param cave the another cave.
   */
  public Cave(Cave cave) {
    this.openings = cave.getOpenings();
    this.treasures = cave.getTreasures();
    this.weapons = cave.getWeapons();
    this.monster = cave.hasMonster() ? new Otyughs((Otyughs) cave.monster) : null;
  }

  // calculate how many opening in this cave
  private int openNum() {
    int check = 0;
    for (boolean b:openings) {
      if (b) {
        check++;
      }
    }
    return check;
  }

  /**
   * return openings of the cave or tunnel.
   * @return openings.
   */
  public boolean[] getOpenings() {
    return new boolean[]{openings[0], openings[1], openings[2], openings[3]};
  }

  /**
   * check if the cave is a tunnel.
   * @return true if it is a tunnel, false otherwise.
   */
  public boolean isTunnel() {
    return openNum() == 2;
  }

  /**
   * remove a treasure t from treasures.
   * @param t the treasure
   * @throws IllegalArgumentException when treasure t is null
   */
  public void removeTreasure(Treasure t) throws IllegalArgumentException {
    if (t == null) {
      throw new IllegalArgumentException("trying to pick a null treasure");
    }
    if (!treasures.contains(t)) {
      throw new IllegalArgumentException("trying to pick a non-exist treasure");
    }
    treasures.remove(t);
  }

  /**
   * Set the treasures of this cave.
   * @param t list of treasures
   */
  public void setTreasures(List<Treasure> t) throws IllegalStateException {
    if (t == null) {
      throw new IllegalArgumentException("Cannot set treasures to null");
    }
    if (openNum() == 2 && t.size() != 0) {
      throw new IllegalStateException("cannot add treasure to a tunnel");
    }
    this.treasures = t;
  }

  /**
   * remove all weapons.
   * @return weapons in the cave
   * @throws IllegalStateException when there is no weapons.
   */
  public List<Weapon> removeWeapon() throws IllegalStateException {
    if (weapons.size() == 0) {
      throw new IllegalStateException("No weapon found");
    }
    List<Weapon> res = new ArrayList<>(weapons);
    weapons = new ArrayList<>();
    return res;
  }

  /**
   * get the weapons in the cave.
   * @return the list of the weapon.
   */
  public List<Weapon> getWeapons() {
    return new ArrayList<>(weapons);
  }

  /**
   * return the another opening of a tunnel given one oepning.
   * @param come the given opening
   * @return     the another opening.
   * @throws IllegalStateException if it is not a tunnel.
   */
  public int[] tunnelNext(int come) throws IllegalStateException {
    if (openNum() != 2) {
      throw new IllegalStateException("Not a tunnel");
    }
    for (int i = 0; i < 4; i++) {
      if (openings[i] && i != come) {
        switch (i) {
          case 0 :
            return new int[]{-1, 0, 1};
          case 1 :
            return new int[]{1, 0, 0};
          case 2 :
            return new int[]{0, 1, 3};
          case 3 :
            return new int[]{0, -1, 2};
          default:
            break;
        }
      }
    }
    return new int[]{0, 0};
  }

  /**
   * add a monster to this cave.
   * @param m the monster to be added.
   * @throws IllegalArgumentException if monster is a null.
   */
  public void addMonster(Monster m) throws IllegalArgumentException {
    if (m == null) {
      throw new IllegalArgumentException("adding null monster to cave");
    }
    if (openNum() == 2) {
      throw new IllegalArgumentException("adding monster to tunnel");
    }
    monster = m;
  }

  /**
   * Check if the cave has a monster.
   * @return true if there exists a monster, false otherwise.
   */
  public boolean hasMonster() {
    return monster != null;
  }

  /**
   * check if the monster in the cave is injured.
   * @return true if it is injured, false otherwise.
   */
  public boolean isMonsterInjured() {
    return hasMonster() && monster.isHurt();
  }

  /**
   * check if the monster is dead.
   * @return true if it is dead, false otherwise.
   */
  public boolean isMonsterDead() {
    return hasMonster() && monster.isDead();
  }

  /**
   * the monster shot by the weapon. The monster is killed when it gets shot twice.
   */
  public void shot() {
    monster.shot();
  }

  /**
   * Set weapons of this cave.
   * @param w list of weapons
   * @throws IllegalArgumentException when weapon list is null
   */
  public void setWeapons(List<Weapon> w) throws IllegalArgumentException {
    if (w == null) {
      throw new IllegalArgumentException("null weapons added to cave");
    }
    this.weapons = w;
  }

  /**
   * return treasures in the cave.
   * @return list of treasures.
   */
  public List<Treasure> getTreasures() {
    return new ArrayList<>(treasures);
  }

  /**
   * check if north is open.
   * @return true if north is open.
   */
  public boolean isNorth() {
    return openings[0];
  }

  /**
   * check if south is open.
   * @return true if south is open.
   */
  public boolean isSouth() {
    return openings[1];
  }

  /**
   * check if east is open.
   * @return true if east is open.
   */
  public boolean isEast() {
    return openings[2];
  }

  /**
   * check if west is open.
   * @return true if west is open.
   */
  public boolean isWest() {
    return openings[3];
  }

  @Override
  public String toString() {
    StringBuilder res = new StringBuilder();
    if (isTunnel()) {
      res.append("Tunnel");
    } else {
      res.append("Cave.");
    }
    res.append(" Openings:");
    if (isEast()) {
      res.append(" East ");
    }
    if (isNorth()) {
      res.append(" North ");
    }
    if (isSouth()) {
      res.append(" South ");
    }
    if (isWest()) {
      res.append(" West ");
    }
    res.append("\n").append("treasures: ");

    for (Treasure t :  treasures) {
      res.append(t).append(" ");
    }
    res.append("\n").append("weapons: ");
    if (weapons.size() != 0) {
      res.append(weapons.size()).append(" * ").append(weapons.get(0));
    }
    res.append("\n").append("monsters: ");
    if (monster != null) {
      res.append(monster);
    } else {
      res.append("None");
    }
    return res.toString();
  }
}
