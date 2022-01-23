package model.dungeon;

import constant.Constant;
import model.treasure.Treasure;
import model.weapon.Weapon;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a player in the dungeon. A player can pick up treasures and arrows,
 * shoot arrows and be killed in the dungeon.
 * The class is package-private to hide implementation and avoid illegal access.
 */
class Player {
  private final List<Treasure> treasures;
  private final List<Weapon> weapons;
  private boolean dead;

  /**
   * Constructor for a player.
   */
  public Player() {
    treasures = new ArrayList<>();
    weapons = new ArrayList<>();
    for (int i = 0; i < Constant.START_ARROW; i++) {
      weapons.add(Weapon.CROOKEDARROW);
    }
    dead = false;
  }

  /**
   * add a treasure to treasures.
   * @param t treasure
   * @throws IllegalArgumentException when t is null.
   */
  public void pick(Treasure t) throws IllegalArgumentException {
    if (t == null) {
      throw new IllegalArgumentException("player is trying to pick a null treasure");
    }
    treasures.add(t);
  }

  /**
   * Player shoot one arrow and lost one arrow.
   * @throws IllegalStateException if there is no arrow left.
   */
  public void shoot() throws IllegalStateException {
    if (weapons.size() == 0) {
      throw new IllegalStateException("No arrow left");
    }
    weapons.remove(weapons.size() - 1);
  }

  /**
   * set player to death.
   */
  public void setDead() {
    dead = true;
  }

  /**
   * check whether the player is dead.
   * @return true if the player is dead, false otherwise.
   */
  public boolean isDead() {
    return dead;
  }

  /**
   * Player can pick up weapons.
   * @param w the weapon to be picked up.
   * @throws IllegalArgumentException if trying to pick up a null.
   */
  public void pickWeapon(List<Weapon> w) throws IllegalArgumentException {
    if (w == null) {
      throw new IllegalArgumentException("pick up null weapons");
    }
    this.weapons.addAll(w);
  }

  /**
   * return the player's info.
   * @return player's info.
   */
  public String info() {
    StringBuilder res = new StringBuilder("The player has treasures: ");
    int d = 0;
    int s = 0;
    int r = 0;
    for (Treasure t : treasures) {
      switch (t) {
        case DIAMOND:
          d++;
          break;
        case SAPPHIRE:
          s++;
          break;
        case RUBY:
          r++;
          break;
        default:
          break;
      }
    }
    res.append(d).append(" * ").append(Treasure.DIAMOND).append(", ");
    res.append(s).append(" * ").append(Treasure.SAPPHIRE).append(", ");
    res.append(r).append(" * ").append(Treasure.RUBY);
    res.append("\nThe player has weapons: ");
    res.append(weapons.size()).append(" * CROOKEDARROW");
    return res.toString();
  }

  @Override
  public String toString() {
    StringBuilder res = new StringBuilder("The player has treasures: ");
    for (Treasure t : treasures) {
      res.append(t).append(" ");
    }
    res.append("\nThe player has weapons: ");
    res.append(weapons.size()).append(" * CROOKEDARROW");
    return res.toString();
  }
}
