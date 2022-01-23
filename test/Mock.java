import model.direction.Direction;
import model.dungeon.Dungeon;

/**
 * Mock class for the Dungeon. For test purpose only.
 * Check if the Controller works correct independently.
 */
public class Mock extends Dungeon {
  private final StringBuilder log;
  private final int uniqueCode;

  /**
   * Constructor for the mock model.
   * @param log the log to record the input.
   * @param uniqueCode the uniqueCode.
   */
  public Mock(StringBuilder log, int uniqueCode) {
    super(false);
    this.log = log;
    this.uniqueCode = uniqueCode;
  }

  @Override
  public boolean shoot(int dis, Direction dir)
          throws IllegalArgumentException, IllegalStateException {
    log.append("Input :").append(dis).append(", ").append(dir);
    log.append(" UniqueCode: ").append(uniqueCode);
    return false;
  }
}
