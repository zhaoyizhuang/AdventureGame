import java.util.Arrays;

import controller.Features;
import model.dungeon.ReadOnlyDungeonGame;
import view.GameView;

/**
 * This is a mock class for view, serve as a test for the graphical game.
 */
public class MockView implements GameView {
  private final StringBuilder log;
  private final int uniqueCode;

  private ReadOnlyDungeonGame model;

  /**
   * Constructor for the mock view.
   * @param log the log to record the input.
   * @param uniqueCode the uniqueCode.
   */
  public MockView(StringBuilder log, int uniqueCode, ReadOnlyDungeonGame model) {
    this.log = log;
    this.uniqueCode = uniqueCode;
    this.model = model;
  }

  @Override
  public void refresh() {
    // For Test Only
    log.append("refresh ");
  }

  @Override
  public void makeVisible() {
    // For Test Only
    log.append("makeVisible ");
  }

  @Override
  public void setFeatures(Features f) {
    // For Test Only
    log.append("setFeature").append(Arrays.deepToString(model.dungeonInfo()));

  }

  @Override
  public void playerInfo() {
    // For Test Only
    log.append("playerInfo ").append(model.playerView());
  }

  @Override
  public void caveInfo() {
    // For Test Only
    log.append("caveInfo ").append(model.cave());
  }

  @Override
  public void getSettings() {
    // For Test Only
    log.append("getSettings ").append(Arrays.toString(model.getSettings()));
  }

  @Override
  public void restart(boolean same, ReadOnlyDungeonGame model) {
    // For Test Only
    log.append("restart ").append(same).append(" ");
  }

  @Override
  public void smell(int level) {
    // For Test Only
    log.append("smell ").append(level).append(" ");
  }

  @Override
  public void metMonster() {
    // For Test Only
    log.append("metMonster ");
  }

  @Override
  public void deadMonster() {
    // For Test Only
    log.append("deadMonster ");
  }

  @Override
  public void gameStatus(boolean status) {
    // For Test Only
    log.append("gameStatus ").append(status).append(" ");
  }

  @Override
  public void pickWeapon() {
    // For Test Only
    log.append("pickWeapon ");
  }

  @Override
  public void pickTreasure() {
    // For Test Only
    log.append("pickTreasure ");
  }

  @Override
  public void errorMessage(String msg) {
    // For Test Only
    log.append("error msg: ").append(msg).append(" ");
  }

  @Override
  public void shoot(boolean shot) {
    log.append("View shoot, input: ").append(shot).append(", ");
    log.append(" UniqueCode: ").append(uniqueCode);
  }

  @Override
  public void newGame(Features f) {
    // For Test Only
    log.append("newGame ");
  }

  @Override
  public void handleClick(Features f) {
    // For Test Only
    log.append("handleClick ");
  }
}
