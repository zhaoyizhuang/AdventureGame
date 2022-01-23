import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import controller.DungeonController;
import controller.DungeonViewController;
import controller.Features;
import model.direction.Direction;
import model.dungeon.Dungeon;
import model.dungeon.DungeonGame;
import model.dungeon.DungeonTest;
import view.GameSwingView;
import view.GameView;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This class is the test class for the view controller.
 */
public class DungeonViewControllerTest {
  private StringBuilder log;
  private DungeonGame model;
  private DungeonGame modelActual;
  private GameView view;
  private Features controller;
  private int[][][] dungeon;


  @Before
  public void setUp() throws Exception {
    log = new StringBuilder();
    model = TestDungeon.testDungeon1();
    ((DungeonTest) model).setRand(0, 1);
    modelActual = TestDungeon.testDungeon1();
    ((DungeonTest) modelActual).setRand(0, 1);
    view = new MockView(log, 1234321, model);
    controller = new DungeonViewController(model, view);
    dungeon = model.dungeonInfo();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testViewConst() {
    GameView view = new GameSwingView(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testControllerConst() {
    DungeonController view1 = new DungeonViewController(null, view);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testControllerConst2() {
    DungeonController view = new DungeonViewController(model, null);
  }

  @Test
  public void ControllerIndependentTest() {
    Mock mock = new Mock(log, 1234567);
    view = new MockView(log, 1234321, mock);
    controller = new DungeonViewController(mock, view);
    controller.shoot(Direction.NORTH, 1);
    assertEquals("Input :1, NORTH UniqueCode: 1234567"
            + "View shoot, input: false,  UniqueCode: 1234321refresh ", log.toString());
  }

  @Test
  public void play() {
    controller.play();
    assertEquals("setFeature" + Arrays.deepToString(dungeon)
            + "handleClick makeVisible smell 1 ", log.toString());
  }

  @Test
  public void restart() {
    controller.pickTreasure();
    assertFalse(Arrays.deepEquals(model.dungeonInfo(), modelActual.dungeonInfo()));
    controller.restart();
    assertEquals("refresh pickTreasure restart "
            + "true handleClick refresh smell 1 ", log.toString());
    assertTrue(Arrays.deepEquals(model.dungeonInfo(), modelActual.dungeonInfo()));
  }

  @Test
  public void newGame() {
    controller.newGame(6, 6, 10, true, 100, 2);
    controller.getSettings();
    assertTrue(log.toString().contains("getSettings [6, 6, 10, 1, 100, 2]"));
    assertTrue(log.toString().contains("restart false"));
    modelActual = new Dungeon(6, 6, 10, true, 100, 2);
    assertArrayEquals(model.getSettings(), modelActual.getSettings());
  }

  @Test
  public void moveUsingClickAndUsingArrowKey() {
    int fac = 173;
    controller.clickMove(4 * fac, 3 * fac);
    assertEquals("error msg: You cannot move to an unvisited place. ", log.toString());
    assertEquals(model.cave(), modelActual.cave());

    controller.move(Direction.EAST);
    assertEquals("error msg: You cannot move to an unvisited place. refresh smell 0 ",
            log.toString());
    modelActual.move(Direction.EAST);
    assertEquals(model.cave(), modelActual.cave());

    controller.clickMove(2 * fac + 1, 4 * fac + 1);
    assertEquals("error msg: You cannot move to an unvisited place. "
            + "refresh smell 0 refresh smell 1 ", log.toString());
    modelActual.move(Direction.WEST);
    assertEquals(model.cave(), modelActual.cave());

    controller.move(Direction.WEST);
    assertEquals("error msg: You cannot move to an unvisited place. "
            + "refresh smell 0 refresh smell 1 error msg: No way to west. ", log.toString());
    assertEquals(model.cave(), modelActual.cave());
  }

  @Test
  public void pickTreasure() {
    controller.pickTreasure();
    assertEquals("refresh pickTreasure ", log.toString());
    modelActual.pickAllTreasure();
    assertTrue(Arrays.deepEquals(model.dungeonInfo(), modelActual.dungeonInfo()));
    dungeon[4][2][4] = 0;
    dungeon[4][2][5] = 0;
    dungeon[4][2][6] = 0;
    assertTrue(Arrays.deepEquals(dungeon, modelActual.dungeonInfo()));
    controller.pickTreasure();
    assertEquals("refresh pickTreasure "
            + "error msg: No Treasure in the cave ", log.toString());
    assertTrue(Arrays.deepEquals(model.dungeonInfo(), modelActual.dungeonInfo()));
    assertTrue(Arrays.deepEquals(dungeon, modelActual.dungeonInfo()));
  }

  @Test
  public void pickWeapon() {
    controller.pickWeapon();
    assertEquals("refresh pickWeapon ", log.toString());
    modelActual.pickWeapon();
    assertTrue(Arrays.deepEquals(model.dungeonInfo(), modelActual.dungeonInfo()));
    dungeon[4][2][7] = 0;
    assertTrue(Arrays.deepEquals(dungeon, modelActual.dungeonInfo()));
    controller.pickWeapon();
    assertEquals("refresh pickWeapon "
            + "error msg: No weapon found ", log.toString());
    assertTrue(Arrays.deepEquals(model.dungeonInfo(), modelActual.dungeonInfo()));
    assertTrue(Arrays.deepEquals(dungeon, modelActual.dungeonInfo()));
  }

  @Test
  public void shoot() {
    controller.shoot(Direction.NORTH, 1);
    assertTrue(modelActual.shoot(1, Direction.NORTH));
    assertEquals(model.playerView(), modelActual.playerView());
    assertEquals("View shoot, input: true,  UniqueCode: 1234321refresh ", log.toString());

    controller.shoot(Direction.NORTH, 2);
    assertFalse(modelActual.shoot(2, Direction.NORTH));
    assertEquals(model.playerView(), modelActual.playerView());
    assertEquals("View shoot, input: true,  UniqueCode: 1234321refresh "
            + "View shoot, input: false,  UniqueCode: 1234321refresh ", log.toString());

    controller.shoot(Direction.NORTH, 1);
    assertTrue(modelActual.shoot(1, Direction.NORTH));
    assertEquals(model.playerView(), modelActual.playerView());
    assertEquals("View shoot, input: true,  UniqueCode: 1234321refresh "
            + "View shoot, input: false,  UniqueCode: 1234321refresh "
            + "View shoot, input: true,  UniqueCode: 1234321refresh ", log.toString());

    controller.shoot(Direction.NORTH, 1);
    assertEquals(model.playerView(), modelActual.playerView());
    assertEquals("View shoot, input: true,  UniqueCode: 1234321refresh "
            + "View shoot, input: false,  UniqueCode: 1234321refresh "
            + "View shoot, input: true,  UniqueCode: 1234321refresh "
            + "error msg: No arrow left ", log.toString());
    assertTrue(Arrays.deepEquals(model.dungeonInfo(), modelActual.dungeonInfo()));
  }

  @Test
  public void metMonsterLose() {
    //Dead
    controller.move(Direction.NORTH);
    assertEquals("refresh gameStatus false ", log.toString());
    modelActual.move(Direction.NORTH);
    assertTrue(modelActual.metMonster());
    assertTrue(Arrays.deepEquals(model.dungeonInfo(), modelActual.dungeonInfo()));
    assertTrue(model.isDead());
    assertFalse(model.isMonsterDead());
    assertFalse(modelActual.isMonsterDead());
    assertTrue(modelActual.isDead());

  }

  @Test
  public void metDeadMonster() {
    //Dead Monster
    controller.shoot(Direction.NORTH, 1);
    controller.shoot(Direction.NORTH, 1);
    controller.move(Direction.NORTH);
    assertEquals("View shoot, input: true,  UniqueCode: 1234321refresh "
            + "View shoot, input: true,  UniqueCode: 1234321refresh "
            + "refresh deadMonster ", log.toString());
    modelActual.shoot(1, Direction.NORTH);
    modelActual.shoot(1, Direction.NORTH);
    modelActual.move(Direction.NORTH);
    assertFalse(modelActual.metMonster());
    assertTrue(Arrays.deepEquals(model.dungeonInfo(), modelActual.dungeonInfo()));
    assertFalse(model.isDead());
    assertTrue(model.isMonsterDead());
    assertTrue(modelActual.isMonsterDead());
    assertFalse(modelActual.isDead());

  }

  @Test
  public void metInjured() {
    controller.shoot(Direction.NORTH, 1);
    modelActual.shoot(1, Direction.NORTH);
    controller.move(Direction.NORTH);
    modelActual.move(Direction.NORTH);
    assertFalse(model.isDead());
    assertTrue(modelActual.metMonster());
    assertFalse(modelActual.isDead());
    controller.clickMove(2 * 173, 4 * 173);
    modelActual.move(Direction.SOUTH);
    assertEquals(modelActual.cave(), model.cave());
    controller.move(Direction.NORTH);
    modelActual.move(Direction.NORTH);
    assertEquals("View shoot, input: true,  UniqueCode: 1234321refresh refresh "
            + "metMonster smell 1 refresh smell 1 refresh gameStatus false ", log.toString());
    assertTrue(modelActual.metMonster());
    assertTrue(Arrays.deepEquals(model.dungeonInfo(), modelActual.dungeonInfo()));
    assertTrue(model.isDead());
    assertFalse(model.isMonsterDead());
    assertFalse(modelActual.isMonsterDead());
    assertTrue(modelActual.isDead());
  }

  @Test
  public void gameOver() {
    controller.move(Direction.NORTH);
    controller.move(Direction.NORTH);
    assertEquals("refresh gameStatus false error msg: Game is over ", log.toString());
  }

  @Test
  public void playerInfo() {
    controller.playerInfo();
    assertEquals("playerInfo The player has treasures: 0 * DIAMOND, "
            + "0 * SAPPHIRE, 0 * RUBY\n"
            + "The player has weapons: 3 * CROOKEDARROW", log.toString());
    assertEquals("The player has treasures: 0 * DIAMOND, "
            + "0 * SAPPHIRE, 0 * RUBY\n"
            + "The player has weapons: 3 * CROOKEDARROW", modelActual.playerView());
    assertEquals(model.playerView(), modelActual.playerView());
  }

  @Test
  public void caveInfo() {
    controller.caveInfo();
    assertEquals("caveInfo Player's current location: 4-2Cave. Openings: East  North  South \n"
            + "treasures: DIAMOND SAPPHIRE RUBY RUBY \n"
            + "weapons: 2 * CROOKEDARROW\n"
            + "monsters: None", log.toString());
    assertEquals("Player's current location: 4-2Cave. Openings: East  North  South \n"
            + "treasures: DIAMOND SAPPHIRE RUBY RUBY \n"
            + "weapons: 2 * CROOKEDARROW\n"
            + "monsters: None", modelActual.cave());
    assertEquals(model.cave(), modelActual.cave());
  }

  @Test
  public void winRun() {
    controller.shoot(Direction.NORTH, 1);
    controller.shoot(Direction.NORTH, 1);
    controller.pickWeapon();
    controller.move(Direction.NORTH);
    controller.move(Direction.WEST);
    controller.move(Direction.NORTH);
    controller.move(Direction.WEST);
    controller.shoot(Direction.WEST, 1);
    controller.shoot(Direction.WEST, 1);
    controller.move(Direction.WEST);
    assertEquals("View shoot, input: true,  UniqueCode: 1234321refresh View shoot, "
            + "input: true,  UniqueCode: 1234321refresh refresh pickWeapon refresh deadMonster "
            + "refresh refresh smell 0 refresh smell 1 View shoot, input: true,  UniqueCode: "
            + "1234321refresh View shoot, input: true,  UniqueCode: 1234321refresh refresh "
            + "deadMonster gameStatus true ", log.toString());
  }

  @Test
  public void winRun2() {
    controller.shoot(Direction.NORTH, 1);
    controller.shoot(Direction.NORTH, 1);
    controller.pickWeapon();
    controller.move(Direction.NORTH);
    controller.move(Direction.WEST);
    controller.move(Direction.NORTH);
    controller.move(Direction.WEST);
    controller.shoot(Direction.WEST, 1);
    controller.move(Direction.WEST);
    assertEquals("View shoot, input: true,  UniqueCode: 1234321refresh View shoot, "
            + "input: true,  UniqueCode: 1234321refresh refresh pickWeapon refresh deadMonster "
            + "refresh refresh smell 0 refresh smell 1 View shoot, input: true,  UniqueCode: "
            + "1234321refresh refresh "
            + "metMonster gameStatus true ", log.toString());
  }

}