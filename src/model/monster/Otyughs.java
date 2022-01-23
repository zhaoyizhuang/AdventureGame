package model.monster;

/**
 * This class represents a monster called Otyughs. It lives in a cave and will eat player who enter
 * the cave. It can be killed by getting shot 2 times by arrow. It may miss the attack when it is
 * injured.
 */
public class Otyughs extends TypicalMonster {

  /**
   * The constructor for a health and alive Otyughs.
   */
  public Otyughs() {
    super();
  }

  /**
   * The construcoter for a Otyugh in terms of another Otyugh.
   * @param monster another Otyugh.
   */
  public Otyughs(Otyughs monster) {
    super(monster);
  }

  @Override
  public void shot() {
    if (hurt) {
      dead = true;
    }
    hurt = true;
  }

  @Override
  public String toString() {
    StringBuilder res = new StringBuilder();
    if (isDead()) {
      res.append("A dead ");
    } else if (isHurt()) {
      res.append("An injured ");
    } else {
      res.append("A healthy ");
    }
    res.append("Otyughs");
    return res.toString();
  }
}
