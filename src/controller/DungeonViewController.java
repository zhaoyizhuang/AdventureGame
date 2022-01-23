package controller;

import model.direction.Direction;
import model.dungeon.Dungeon;
import model.dungeon.DungeonGame;
import view.GameView;

/**
 * This class represents the controller for the visual view.
 */
public class DungeonViewController implements Features {

  private DungeonGame model;
  private final GameView view;

  /**
   * The constructor for the DungeonViewController in terms of model and view.
   * @param model the model
   * @param view the view
   */
  public DungeonViewController(DungeonGame model, GameView view) throws IllegalArgumentException {
    if (model == null || view == null) {
      throw new IllegalArgumentException("model and view cannot be null");
    }
    this.model = model;
    this.view = view;
  }

  @Override
  public void play() {
    view.setFeatures(this);
    view.handleClick(this);
    view.makeVisible();
    smellHelper();
  }

  @Override
  public void exitProgram() {
    System.exit(0);
  }

  @Override
  public void restart() {
    model.restart();
    view.restart(true, model);
    view.handleClick(this);
    view.refresh();
    smellHelper();
  }

  @Override
  public void newGameTrigger() {
    view.newGame(this);
  }

  @Override
  public void newGame(int row, int col,
                      int interconnectivity, boolean wrap, int possibility, int num) {
    try {
      model = new Dungeon(row, col, interconnectivity, wrap, possibility, num);
      view.restart(false, model);
      view.handleClick(this);
      view.refresh();
      smellHelper();
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.errorMessage(e.getMessage());
    }
  }

  @Override
  public void move(Direction direction) {
    try {
      model.move(direction);
      view.refresh();
      if (moveHelper()) {
        return;
      }

      smellHelper();

    } catch (IllegalArgumentException | IllegalStateException e) {
      view.errorMessage(e.getMessage());
    }
  }

  // helper function for move, true if game ends, false otherwise.
  private boolean moveHelper() {
    if (model.metMonster()) {
      if (model.isDead()) {
        view.gameStatus(false);
        return true;
      }
      view.metMonster();
    }
    if (model.isMonsterDead()) {
      view.deadMonster();
    }
    if (model.reachEnd()) {
      view.gameStatus(true);
      return true;
    }
    return false;
  }

  //helper function to detect the smell.
  private void smellHelper() {
    switch (model.getSmell()) {
      case PUNGENT:
        view.smell(1);
        break;
      case FAINT:
        view.smell(0);
        break;
      default:
        break;
    }
  }

  @Override
  public void pickTreasure() {
    try {
      model.pickAllTreasure();
      view.refresh();
      view.pickTreasure();
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.errorMessage(e.getMessage());
    }
  }

  @Override
  public void pickWeapon() {
    try {
      model.pickWeapon();
      view.refresh();
      view.pickWeapon();
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.errorMessage(e.getMessage());
    }
  }

  @Override
  public void shoot(Direction direction, int dis) {
    try {
      if (model.shoot(dis, direction)) {
        view.shoot(true);
        view.refresh();
      } else {
        view.shoot(false);
        view.refresh();
      }
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.errorMessage(e.getMessage());
    }

  }

  @Override
  public void playerInfo() {
    view.playerInfo();
  }

  @Override
  public void caveInfo() {
    view.caveInfo();
  }

  @Override
  public void getSettings() {
    view.getSettings();
  }

  @Override
  public void clickMove(int x, int y) {
    try {
      int r = x < 0 ? -1 : x / 173;
      int c = y < 0 ? -1 : y / 173;
      model.setLoc(c, r);
      view.refresh();
      if (moveHelper()) {
        return;
      }
      smellHelper();
    } catch (IllegalArgumentException e) {
      view.errorMessage(e.getMessage());
    }
  }
}
