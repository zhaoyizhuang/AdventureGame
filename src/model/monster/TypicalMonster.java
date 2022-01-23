package model.monster;

/**
 * Abstract class for monsters. represents the attribute that all kinds of monster will have.
 * This class is package pricate because it is a abstract class and is only used within
 * the package.
 */
abstract class TypicalMonster implements Monster {
  protected boolean hurt;
  protected boolean dead;

  /**
   * Constructor of a typical monster. It is healthy and alive.
   */
  public TypicalMonster() {
    hurt = false;
    dead = false;
  }

  /**
   * Constructor of a monster in terms of another monster.
   * @param monster another monster.
   */
  public TypicalMonster(Monster monster) {
    hurt = monster.isHurt();
    dead = monster.isDead();
  }

  @Override
  public boolean isHurt() {
    return hurt;
  }

  @Override
  public boolean isDead() {
    return dead;
  }
}
