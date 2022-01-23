import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.direction.Direction;
import model.dungeon.Dungeon;
import model.dungeon.DungeonGame;
import model.dungeon.DungeonTest;
import model.smell.Smell;
import model.treasure.Treasure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for the Dungeon Game model.
 */
public class DungeonGameModelTest {
  private DungeonGame model;

  @Before
  public void setUp() throws Exception {
    model = TestDungeon.testDungeon1();
    ((DungeonTest) model).setRand(0, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalid0Monster() {
    model = new Dungeon(6, 6, 10, false, 50, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMonsterNum() {
    model = new Dungeon(6, 6, 10, false, 50, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegrow() {
    model = new Dungeon(-1, 6, 2, false, 10, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegcol() {
    model = new Dungeon(6, -1, 2, false, 10, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void test1drow() {
    model = new Dungeon(1, 6, 2, false, 10, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void test1dcol() {
    model = new Dungeon(6, 1, 2, false, 10, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void test0row() {
    model = new Dungeon(0, 6, 2, false, 10, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void test0col() {
    model = new Dungeon(6, 0, 2, false, 10, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegInter() {
    model = new Dungeon(3, 3, -6, false, 10, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegTreasure() {
    model = new Dungeon(6, 2, 2, false, -10, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void setLoc1() {
    model.setLoc(-1, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void setLoc2() {
    model.setLoc(7, 7);
  }

  @Test(expected = IllegalArgumentException.class)
  public void setLoc3() {
    model.setLoc(0, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void testNoArrowLeft() {
    model.shoot(1, Direction.EAST);
    model.shoot(1, Direction.EAST);
    model.shoot(1, Direction.EAST);
    model.shoot(1, Direction.EAST);
  }

  @Test(expected = IllegalStateException.class)
  public void testNoWay() {
    model.move(Direction.WEST);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveNull() {
    model.move(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDistance() {
    model.shoot(-1, Direction.EAST);
  }

  @Test(expected = IllegalArgumentException.class)
  public void test0Distance() {
    model.shoot(0, Direction.EAST);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullDir() {
    model.shoot(2, null);
  }

  @Test(expected = IllegalStateException.class)
  public void testNoArrowInCave() {
    model.pickWeapon();
    model.pickWeapon();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNoSInCave() {
    model.pick(Treasure.DIAMOND);
    model.pick(Treasure.DIAMOND);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNoDInCave() {
    model.pick(Treasure.SAPPHIRE);
    model.pick(Treasure.SAPPHIRE);
    model.pick(Treasure.SAPPHIRE);
    model.pick(Treasure.SAPPHIRE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNoRInCave() {
    model.pick(Treasure.RUBY);
    model.pick(Treasure.RUBY);
    model.pick(Treasure.RUBY);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPickNullT() {
    model.pick(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void noTreasure() {
    model.pickAllTreasure();
    model.pickAllTreasure();
  }

  @Test(expected = IllegalStateException.class)
  public void testMoveAfterDead() {
    model.move(Direction.NORTH);
    model.metMonster();
    model.move(Direction.SOUTH);
  }

  @Test(expected = IllegalStateException.class)
  public void testShootAfterDead() {
    model.move(Direction.NORTH);
    model.metMonster();
    model.shoot(1, Direction.SOUTH);
  }

  @Test(expected = IllegalStateException.class)
  public void testPickAfterDead() {
    model.move(Direction.NORTH);
    model.metMonster();
    model.pickWeapon();
  }

  @Test(expected = IllegalStateException.class)
  public void testPickTreasureAfterDead() {
    model.move(Direction.NORTH);
    model.metMonster();
    model.pick(Treasure.DIAMOND);
  }

  @Test(expected = IllegalStateException.class)
  public void testMoveAfterWin() {
    model.shoot(1, Direction.NORTH);
    model.shoot(1, Direction.NORTH);
    model.pickWeapon();
    model.move(Direction.NORTH);
    model.move(Direction.WEST);
    model.move(Direction.NORTH);
    model.move(Direction.WEST);
    model.shoot(1, Direction.WEST);
    model.shoot(1, Direction.WEST);
    model.move(Direction.WEST);
    model.move(Direction.SOUTH);
  }

  @Test(expected = IllegalStateException.class)
  public void testShootAfterWin() {
    model.shoot(1, Direction.NORTH);
    model.shoot(1, Direction.NORTH);
    model.pickWeapon();
    model.move(Direction.NORTH);
    model.move(Direction.WEST);
    model.move(Direction.NORTH);
    model.move(Direction.WEST);
    model.shoot(1, Direction.WEST);
    model.shoot(1, Direction.WEST);
    model.move(Direction.WEST);
    model.shoot(1, Direction.SOUTH);
  }

  @Test(expected = IllegalStateException.class)
  public void testPickAfterWin() {
    model.shoot(1, Direction.NORTH);
    model.shoot(1, Direction.NORTH);
    model.pickWeapon();
    model.move(Direction.NORTH);
    model.move(Direction.WEST);
    model.move(Direction.NORTH);
    model.move(Direction.WEST);
    model.shoot(1, Direction.WEST);
    model.shoot(1, Direction.WEST);
    model.move(Direction.WEST);
    model.pickWeapon();
  }

  @Test(expected = IllegalStateException.class)
  public void testPickTreasureAfterWin() {
    model.shoot(1, Direction.NORTH);
    model.shoot(1, Direction.NORTH);
    model.pickWeapon();
    model.move(Direction.NORTH);
    model.move(Direction.WEST);
    model.move(Direction.NORTH);
    model.move(Direction.WEST);
    model.shoot(1, Direction.WEST);
    model.shoot(1, Direction.WEST);
    model.move(Direction.WEST);
    model.pick(Treasure.DIAMOND);
  }

  @Test
  public void start() {
    int[] exp2 = new int[]{6, 6, 10, 1, 100, 2};
    assertEquals(Arrays.toString(exp2), Arrays.toString(model.getSettings()));
    String exp = "The player has treasures: \n"
            + "The player has weapons: 3 * CROOKEDARROW";
    assertEquals(exp, model.player());
    exp = "Player's current location: 4-2Cave. Openings: East  North  South \n"
            + "treasures: DIAMOND SAPPHIRE RUBY RUBY \n"
            + "weapons: 2 * CROOKEDARROW\n"
            + "monsters: None";
    assertEquals(exp, model.cave());
    assertFalse(model.isMonsterDead());
    assertFalse(model.reachEnd());
    assertFalse(model.metMonster());
    assertFalse(model.isDead());
    model.move(Direction.NORTH);
    assertFalse(model.isMonsterDead());
    assertFalse(model.reachEnd());
    assertTrue(model.metMonster());
    assertTrue(model.isDead());
  }

  @Test
  public void MonsterAndArrowGeneration() {
    //todo
    DungeonGame m = new DungeonTest();
    ((DungeonTest) m).setRand(0 ,6 ,6 ,3 ,6 ,3 ,3 ,2 ,1 ,0 ,0 ,0 ,1 ,1 ,0 ,2 ,1 ,2 ,0 ,7 ,1 ,
            9 ,1 ,1 ,1 ,8 ,1);
    ((DungeonTest) m).setCaves(3, 3, 0, false, 20, 100);
    int[] exp2 = new int[]{3, 3, 0, 0, 20, 100};
    assertEquals(Arrays.toString(exp2), Arrays.toString(m.getSettings()));
    StringBuilder exp = new StringBuilder();
    exp.append("Dungeon: Player's current cave: cave1-2\n");
    exp.append("end cave is: cave0-0\n");
    exp.append("Player's info: The player has treasures: \n");
    exp.append("The player has weapons: 3 * CROOKEDARROW\n");
    exp.append("0-0Cave. Openings: South \n");
    exp.append("treasures: \n");
    exp.append("weapons: \n");
    exp.append("monsters: A healthy Otyughs\n");
    exp.append("0-1Cave. Openings: East \n");
    exp.append("treasures: \n");
    exp.append("weapons: \n");
    exp.append("monsters: A healthy Otyughs\n");
    exp.append("0-2Tunnel Openings: South  West \n");
    exp.append("treasures: \n");
    exp.append("weapons: \n");
    exp.append("monsters: None\n");
    exp.append("1-0Tunnel Openings: North  South \n");
    exp.append("treasures: \n");
    exp.append("weapons: \n");
    exp.append("monsters: None\n");
    exp.append("1-1Tunnel Openings: East  South \n");
    exp.append("treasures: \n");
    exp.append("weapons: \n");
    exp.append("monsters: None\n");
    exp.append("1-2Cave. Openings: North  South  West \n");
    exp.append("treasures: \n");
    exp.append("weapons: \n");
    exp.append("monsters: None\n");
    exp.append("2-0Tunnel Openings: East  North \n");
    exp.append("treasures: \n");
    exp.append("weapons: \n");
    exp.append("monsters: None\n");
    exp.append("2-1Tunnel Openings: North  West \n");
    exp.append("treasures: \n");
    exp.append("weapons: 1 * CROOKEDARROW\n");
    exp.append("monsters: None\n");
    exp.append("2-2Cave. Openings: North \n");
    exp.append("treasures: \n");
    exp.append("weapons: \n");
    exp.append("monsters: A healthy Otyughs\n");
    //No monster in Tunnel and start even player wants 100 monsters.
    //Treasure can be in different place with arrow.
    //arrow can be in tunnel
    assertEquals(exp.toString(), m.toString());
    ((DungeonTest) m).setCaves(3, 3, 0, false, 40, 1);
    exp = new StringBuilder();
    exp.append("Dungeon: Player's current cave: cave0-1\n").append("end cave is: cave2-1\n").
            append("Player's info: The player has treasures: \n")
            .append("The player has weapons: 3 * CROOKEDARROW\n")
            .append("0-0Tunnel Openings: East  South \n").append("treasures: \n")
            .append("weapons: 2 * CROOKEDARROW\n").append("monsters: None\n")
            .append("0-1Cave. Openings: West \n").append("treasures: DIAMOND DIAMOND DIAMOND "
                    + "DIAMOND DIAMOND DIAMOND DIAMOND DIAMOND DIAMOND SAPPHIRE RUBY \n")
            .append("weapons: 2 * CROOKEDARROW\n").append("monsters: None\n")
            .append("0-2Cave. Openings: South \n").append("treasures: DIAMOND DIAMOND DIAMOND "
                    + "DIAMOND DIAMOND DIAMOND DIAMOND DIAMOND SAPPHIRE \n")
            .append("weapons: 7 * CROOKEDARROW\n").append("monsters: None\n")
            .append("1-0Cave. Openings: East  North  South \n").append("treasures: \n")
            .append("weapons: \n").append("monsters: None\n")
            .append("1-1Tunnel Openings: East  West \n").append("treasures: \n")
            .append("weapons: \n").append("monsters: None\n")
            .append("1-2Cave. Openings: North  South  West \n").append("treasures: \n")
            .append("weapons: \n").append("monsters: None\n").append("2-0Cave. Openings: North \n")
            .append("treasures: \n").append("weapons: \n").append("monsters: None\n")
            .append("2-1Cave. Openings: East \n").append("treasures: \n").append("weapons: \n")
            .append("monsters: A healthy Otyughs\n").append("2-2Tunnel Openings: North  West \n")
            .append("treasures: \n").append("weapons: \n").append("monsters: None\n");
    //Monster at the end cave when there is only one monster in the dungeon.
    //treasure and arrow can be at the same place.
    assertEquals(exp.toString(), m.toString());
    int[][][] exp1 = new int[][][] {{{0, 1, 1, 0, 0, 0, 0, 2, 0}, {0, 0, 0, 1, 9, 1, 1, 2, 0},
            {0, 1, 0, 0, 8, 0, 1, 7, 0}}, {{1, 1, 1, 0, 0, 0, 0, 0, 0}, {0, 0, 1, 1, 0, 0, 0, 0, 0},
            {1, 1, 0, 1, 0, 0, 0, 0, 0}}, {{1, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 1, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 1, 0, 0, 0, 0, 0}}};
    assertEquals(Arrays.deepToString(exp1), Arrays.deepToString(m.dungeonInfo()));
  }


  @Test
  public void pickWeapon() {
    String exp = "The player has treasures: \n"
            + "The player has weapons: 3 * CROOKEDARROW";
    assertEquals(exp, model.player());
    exp = "Player's current location: 4-2Cave. Openings: East  North  South \n"
            + "treasures: DIAMOND SAPPHIRE RUBY RUBY \n"
            + "weapons: 2 * CROOKEDARROW\n"
            + "monsters: None";
    assertEquals(exp, model.cave());
    model.pickWeapon();
    exp = "The player has treasures: \n"
            + "The player has weapons: 5 * CROOKEDARROW";
    assertEquals(exp, model.player());
    exp = "Player's current location: 4-2Cave. Openings: East  North  South \n"
            + "treasures: DIAMOND SAPPHIRE RUBY RUBY \n"
            + "weapons: \n"
            + "monsters: None";
    assertEquals(exp, model.cave());
  }

  @Test
  public void pickAllTreasure() {
    String exp = "The player has treasures: \n"
            + "The player has weapons: 3 * CROOKEDARROW";
    assertEquals(exp, model.player());
    exp = "Player's current location: 4-2Cave. Openings: East  North  South \n"
            + "treasures: DIAMOND SAPPHIRE RUBY RUBY \n"
            + "weapons: 2 * CROOKEDARROW\n"
            + "monsters: None";
    assertEquals(exp, model.cave());
    model.pickAllTreasure();
    exp = "The player has treasures: DIAMOND SAPPHIRE RUBY RUBY \n"
            + "The player has weapons: 3 * CROOKEDARROW";
    assertEquals(exp, model.player());
    exp = "The player has treasures: 1 * DIAMOND, 1 * SAPPHIRE, 2 * RUBY\n"
            + "The player has weapons: 3 * CROOKEDARROW";
    assertEquals(exp, model.playerView());
    exp = "Player's current location: 4-2Cave. Openings: East  North  South \n"
            + "treasures: \n"
            + "weapons: 2 * CROOKEDARROW\n"
            + "monsters: None";
    assertEquals(exp, model.cave());
  }

  @Test
  public void setLoc() {
    model.move(Direction.EAST);
    model.setLoc(4, 2);
    String exp = "Player's current location: 4-2Cave. Openings: East  North  South \n"
            + "treasures: DIAMOND SAPPHIRE RUBY RUBY \n"
            + "weapons: 2 * CROOKEDARROW\n"
            + "monsters: None";
    assertEquals(exp, model.cave());
  }

  @Test
  public void shootHitMonster() {
    assertTrue(model.shoot(1, Direction.NORTH));// monster in north with 1 distance
    //arrow go through monster but did not cause damage.
    assertFalse(model.shoot(2, Direction.NORTH));
    assertFalse(model.shoot(2, Direction.WEST));//arrow hit the wall
  }

  @Test
  public void reachEnd() {
    model.shoot(1, Direction.NORTH);
    model.shoot(1, Direction.NORTH);
    model.pickWeapon();
    model.move(Direction.NORTH);
    model.move(Direction.WEST);
    model.move(Direction.NORTH);
    assertFalse(model.shoot(1, Direction.WEST));
    assertTrue(model.shoot(2, Direction.WEST));
    model.move(Direction.WEST);
    model.shoot(1, Direction.WEST);
    model.move(Direction.WEST);
    assertFalse(model.metMonster());
    assertFalse(model.isDead());
    assertTrue(model.reachEnd());
    String exp = "Player's current location: 2-5Cave. Openings: East  North  South \n"
            + "treasures: DIAMOND DIAMOND SAPPHIRE RUBY \n"
            + "weapons: 2 * CROOKEDARROW\n"
            + "monsters: A dead Otyughs";
    assertEquals(exp, model.cave());
    model.restart();
  }

  @Test
  public void isDead() {
    assertFalse(model.isDead());
    model.move(Direction.NORTH);
    String exp = "Player's current location: 3-2Cave. Openings: East  South  West \n"
            + "treasures: DIAMOND DIAMOND SAPPHIRE SAPPHIRE RUBY RUBY \n"
            + "weapons: 2 * CROOKEDARROW\n"
            + "monsters: A healthy Otyughs";
    assertEquals(exp, model.cave());
    assertTrue(model.metMonster());
    assertTrue(model.isDead());
  }

  @Test
  public void originalArrowTest() {
    String exp = "The player has treasures: \n"
            + "The player has weapons: 3 * CROOKEDARROW";
    assertEquals(exp, model.player());
    model.shoot(1, Direction.EAST);
    model.shoot(1, Direction.EAST);
    model.shoot(1, Direction.EAST);
    exp = "The player has treasures: \n"
            + "The player has weapons: 0 * CROOKEDARROW";
    assertEquals(exp, model.player());
  }

  @Test
  public void oneMonsterSmell() {
    assertEquals(Smell.PUNGENT, model.getSmell()); // 1-distance
    model.shoot(1, Direction.NORTH);
    model.move(Direction.NORTH);
    String exp = "Player's current location: 3-2Cave. Openings: East  South  West \n"
            + "treasures: DIAMOND DIAMOND SAPPHIRE SAPPHIRE RUBY RUBY \n"
            + "weapons: 2 * CROOKEDARROW\n"
            + "monsters: An injured Otyughs";
    assertEquals(exp, model.cave());
    model.move(Direction.SOUTH); // escape
    model.move(Direction.SOUTH); // 2-distance
    assertEquals(Smell.FAINT, model.getSmell());
    exp = "Player's current location: 5-2Tunnel Openings: East  North \n"
            + "treasures: \n"
            + "weapons: 1 * CROOKEDARROW\n"
            + "monsters: None";
    assertEquals(exp, model.cave());
    model.move(Direction.NORTH);
    model.move(Direction.EAST); // 2-distance
    assertEquals(Smell.FAINT, model.getSmell());
    exp = "Player's current location: 4-3Cave. Openings: North  South  West \n"
            + "treasures: DIAMOND DIAMOND SAPPHIRE RUBY \n"
            + "weapons: 2 * CROOKEDARROW\n"
            + "monsters: None";
    assertEquals(exp, model.cave());
    model.move(Direction.SOUTH); // 3-distance
    assertEquals(Smell.NONE, model.getSmell());
  }

  @Test
  public void smellWrapping() {
    model.shoot(1, Direction.NORTH);
    model.shoot(1, Direction.NORTH);
    model.move(Direction.NORTH);
    model.move(Direction.WEST);
    assertEquals(Smell.NONE, model.getSmell());
    model.move(Direction.NORTH);
    assertEquals(Smell.FAINT, model.getSmell()); // wrapping smell
    String exp = "Player's current location: 2-1Tunnel Openings: South  West \n"
            + "treasures: \n"
            + "weapons: 2 * CROOKEDARROW\n"
            + "monsters: None";
    assertEquals(exp, model.cave());
    model.move(Direction.WEST);
    assertEquals(Smell.PUNGENT, model.getSmell()); // wrapping smell not from east
    exp = "Player's current location: 2-0Cave. Openings: East  South  West \n"
            + "treasures: DIAMOND SAPPHIRE SAPPHIRE RUBY RUBY \n"
            + "weapons: 1 * CROOKEDARROW\n"
            + "monsters: None";
    assertEquals(exp, model.cave());
    model.move(Direction.SOUTH);
    assertEquals(Smell.FAINT, model.getSmell()); // smell not from South
    model.move(Direction.NORTH);
    model.move(Direction.WEST);
    assertEquals(Smell.PUNGENT, model.getSmell());
    exp = "Player's current location: 2-5Cave. Openings: East  North  South \n"
            + "treasures: DIAMOND DIAMOND SAPPHIRE RUBY \n"
            + "weapons: 2 * CROOKEDARROW\n"
            + "monsters: A healthy Otyughs";
    assertEquals(exp, model.cave());
  }

  @Test
  public void smellOnlyFromExistedPath() {
    model.shoot(1, Direction.NORTH);
    model.shoot(1, Direction.NORTH); // 4-2
    model.move(Direction.NORTH); //3-2
    model.move(Direction.EAST); //3-3
    assertEquals(Smell.NONE, model.getSmell());
    model.move(Direction.EAST); //3-4
    assertEquals(Smell.FAINT, model.getSmell());
    model.move(Direction.EAST); //3-5
    assertEquals(Smell.PUNGENT, model.getSmell()); // monster at 2-5
    model.move(Direction.WEST); //3-4
    model.move(Direction.NORTH); //2-4
    assertEquals(Smell.NONE, model.getSmell()); // monster at 2-5
  }

  @Test
  public void twoMonstersSmellAndShoot() {
    DungeonGame m = TestDungeon.testDungeon2();
    //Monster in 5-0, 5-1, 3-1, 3-0, 2-1, 3-2, 0-0
    String exp = "Player's current location: 4-1Cave. Openings: North  South  West \n"
            + "treasures: DIAMOND SAPPHIRE SAPPHIRE RUBY RUBY \n"
            + "weapons: 2 * CROOKEDARROW\n"
            + "monsters: None";
    assertEquals(exp, m.cave());
    exp = "The player has treasures: \n"
            + "The player has weapons: 3 * CROOKEDARROW";
    //player in 4-1 with initial 3 arrows.
    assertEquals(exp, m.player());
    assertEquals(Smell.PUNGENT, m.getSmell());
    m.pickWeapon();
    exp = "The player has treasures: \n"
            + "The player has weapons: 5 * CROOKEDARROW";
    //player in 4-1 with 5 arrows.
    assertEquals(exp, m.player());
    //shoot at west, arrow go through tunnel and change direction to South. Hit the monster in 5-0
    //tunnel did not count as a part of distance.
    assertTrue(m.shoot(1, Direction.WEST));
    //shoot at distance of 2, arrow go pass monster, hit the wall but do no damage.
    assertFalse(m.shoot(2, Direction.WEST));
    exp = "The player has treasures: \n"
            + "The player has weapons: 3 * CROOKEDARROW";
    //player in 4-1 with 3 arrows again.
    assertEquals(exp, m.player());
    m.move(Direction.WEST);
    m.pickWeapon();
    // still pungent in 4-0, because monster in 5-0, 5-1, 3-1
    assertEquals(Smell.PUNGENT, m.getSmell());
    m.move(Direction.SOUTH);
    exp = "Player's current location: 5-0Cave. Openings: North \n"
            + "treasures: DIAMOND DIAMOND SAPPHIRE RUBY RUBY \n"
            + "weapons: 2 * CROOKEDARROW\n"
            + "monsters: An injured Otyughs";
    //A injured monster, because onr arrow missed.
    assertEquals(exp, m.cave());
    m.move(Direction.NORTH); //escape
    assertTrue(m.shoot(1, Direction.SOUTH)); // kill the monster, shoot from tunnel
    m.move(Direction.SOUTH);
    exp = "Player's current location: 5-0Cave. Openings: North \n"
            + "treasures: DIAMOND DIAMOND SAPPHIRE RUBY RUBY \n"
            + "weapons: 2 * CROOKEDARROW\n"
            + "monsters: A dead Otyughs";
    //No monster and no smell
    assertEquals(exp, m.cave());
    assertEquals(Smell.NONE, m.getSmell());
    m.pickWeapon();
    m.move(Direction.NORTH);
    //PUNGENT smell, because monster in 3-1, 5-1. two monsters within distance of 2.
    assertEquals(Smell.PUNGENT, m.getSmell());
    m.move(Direction.EAST);
    //kill monster in 5-1
    assertTrue(m.shoot(1, Direction.SOUTH));
    m.shoot(1, Direction.SOUTH);
    //go back to 4-0, faint smell
    m.move(Direction.WEST);
    assertEquals(Smell.FAINT, m.getSmell());
    m.move(Direction.EAST);
    m.move(Direction.SOUTH);
    //no monster, faint smell in 5-1.
    assertEquals(Smell.FAINT, m.getSmell());
    m.pickWeapon();
    assertTrue(m.isMonsterDead());
    exp = "Player's current location: 5-1Cave. Openings: North \n"
            + "treasures: DIAMOND SAPPHIRE SAPPHIRE RUBY RUBY \n"
            + "weapons: \n"
            + "monsters: A dead Otyughs";
    assertEquals(exp, m.cave());
    m.shoot(2, Direction.NORTH);
    m.shoot(2, Direction.NORTH); // kill monster in 3-1
    assertEquals(Smell.NONE, m.getSmell());
    m.shoot(3, Direction.NORTH);
    assertTrue(m.shoot(3, Direction.NORTH)); // kill monster in 2-1
    m.move(Direction.NORTH);
    assertFalse(m.isMonsterDead());
    m.move(Direction.NORTH);
    assertTrue(m.isMonsterDead());
    m.move(Direction.NORTH);
    assertTrue(m.isMonsterDead());
    exp = "Player's current location: 2-1Cave. Openings: East  North  South  West \n"
            + "treasures: DIAMOND SAPPHIRE SAPPHIRE RUBY RUBY \n"
            + "weapons: 1 * CROOKEDARROW\n"
            + "monsters: A dead Otyughs";
    assertEquals(exp, m.cave());
    assertEquals(Smell.PUNGENT, m.getSmell());//smell from 3-2 and 3,0. Tow monsters within dis of 2
    assertTrue(m.shoot(1, Direction.EAST));
    // kill monster in 3-2, go through tunnel and change direction. Tunnel does not count.
    assertTrue(m.shoot(1, Direction.EAST));
    assertEquals(Smell.FAINT, m.getSmell());
    m.move(Direction.EAST);
    assertEquals(Smell.NONE, m.getSmell());
    m.move(Direction.SOUTH);
    exp = "Player's current location: 3-2Cave. Openings: North \n"
            + "treasures: DIAMOND SAPPHIRE SAPPHIRE RUBY \n"
            + "weapons: 1 * CROOKEDARROW\n"
            + "monsters: A dead Otyughs";
    assertEquals(exp, m.cave());
    m.pickWeapon();
    //arrow goes through tunnel, and cave, change direction twice and hit monster in 0-0
    assertTrue(m.shoot(2, Direction.NORTH));
    m.move(Direction.NORTH);
    m.move(Direction.WEST);
    assertEquals(Smell.FAINT, m.getSmell());// from 3-0
    m.move(Direction.WEST);
    assertEquals(Smell.FAINT, m.getSmell()); //from 0-0
    m.move(Direction.NORTH);
    assertEquals(Smell.PUNGENT, m.getSmell());
    m.move(Direction.NORTH);
    assertFalse(m.isMonsterDead());
    exp = "Player's current location: 0-0Cave. Openings: South \n"
            + "treasures: DIAMOND SAPPHIRE SAPPHIRE RUBY RUBY \n"
            + "weapons: 2 * CROOKEDARROW\n"
            + "monsters: An injured Otyughs";
    assertEquals(exp, m.cave());

    //Dungeon Dump
    StringBuilder expect = new StringBuilder();
    expect.append("Dungeon: Player's current cave: cave0-0\n");
    expect.append("end cave is: cave2-3\n");
    expect.append("Player's info: The player has treasures: \n");
    expect.append("The player has weapons: 0 * CROOKEDARROW\n");
    expect.append("0-0Cave. Openings: South \n");
    expect.append("treasures: DIAMOND SAPPHIRE SAPPHIRE RUBY RUBY \n");
    expect.append("weapons: 2 * CROOKEDARROW\n");
    expect.append("monsters: An injured Otyughs\n");
    expect.append("0-1Tunnel Openings: East  South \n");
    expect.append("treasures: \n");
    expect.append("weapons: 1 * CROOKEDARROW\n");
    expect.append("monsters: None\n");
    expect.append("0-2Cave. Openings: East  South  West \n");
    expect.append("treasures: DIAMOND SAPPHIRE RUBY \n");
    expect.append("weapons: 2 * CROOKEDARROW\n");
    expect.append("monsters: None\n");
    expect.append("0-3Tunnel Openings: East  West \n");
    expect.append("treasures: \n");
    expect.append("weapons: 1 * CROOKEDARROW\n");
    expect.append("monsters: None\n");
    expect.append("0-4Tunnel Openings: East  West \n");
    expect.append("treasures: \n");
    expect.append("weapons: 2 * CROOKEDARROW\n");
    expect.append("monsters: None\n");
    expect.append("0-5Cave. Openings: West \n");
    expect.append("treasures: DIAMOND DIAMOND SAPPHIRE SAPPHIRE RUBY RUBY \n");
    expect.append("weapons: 1 * CROOKEDARROW\n");
    expect.append("monsters: A healthy Otyughs\n");
    expect.append("1-0Tunnel Openings: North  South \n");
    expect.append("treasures: \n");
    expect.append("weapons: 1 * CROOKEDARROW\n");
    expect.append("monsters: None\n");
    expect.append("1-1Tunnel Openings: North  South \n");
    expect.append("treasures: \n");
    expect.append("weapons: 2 * CROOKEDARROW\n");
    expect.append("monsters: None\n");
    expect.append("1-2Tunnel Openings: East  North \n");
    expect.append("treasures: \n");
    expect.append("weapons: 1 * CROOKEDARROW\n");
    expect.append("monsters: None\n");
    expect.append("1-3Cave. Openings: East  South  West \n");
    expect.append("treasures: DIAMOND SAPPHIRE RUBY RUBY \n");
    expect.append("weapons: 1 * CROOKEDARROW\n");
    expect.append("monsters: A healthy Otyughs\n");
    expect.append("1-4Tunnel Openings: East  West \n");
    expect.append("treasures: \n");
    expect.append("weapons: 1 * CROOKEDARROW\n");
    expect.append("monsters: None\n");
    expect.append("1-5Cave. Openings: West \n");
    expect.append("treasures: DIAMOND SAPPHIRE RUBY RUBY \n");
    expect.append("weapons: 1 * CROOKEDARROW\n");
    expect.append("monsters: None\n");
    expect.append("2-0Tunnel Openings: East  North \n");
    expect.append("treasures: \n");
    expect.append("weapons: 1 * CROOKEDARROW\n");
    expect.append("monsters: None\n");
    expect.append("2-1Cave. Openings: East  North  South  West \n");
    expect.append("treasures: DIAMOND SAPPHIRE SAPPHIRE RUBY RUBY \n");
    expect.append("weapons: 1 * CROOKEDARROW\n");
    expect.append("monsters: A dead Otyughs\n");
    expect.append("2-2Tunnel Openings: South  West \n");
    expect.append("treasures: \n");
    expect.append("weapons: 1 * CROOKEDARROW\n");
    expect.append("monsters: None\n");
    expect.append("2-3Cave. Openings: East  North  South \n");
    expect.append("treasures: DIAMOND DIAMOND SAPPHIRE RUBY RUBY \n");
    expect.append("weapons: 2 * CROOKEDARROW\n");
    expect.append("monsters: A healthy Otyughs\n");
    expect.append("2-4Cave. Openings: East  South  West \n");
    expect.append("treasures: DIAMOND SAPPHIRE SAPPHIRE RUBY RUBY \n");
    expect.append("weapons: 2 * CROOKEDARROW\n");
    expect.append("monsters: A healthy Otyughs\n");
    expect.append("2-5Cave. Openings: West \n");
    expect.append("treasures: DIAMOND SAPPHIRE SAPPHIRE RUBY RUBY \n");
    expect.append("weapons: 1 * CROOKEDARROW\n");
    expect.append("monsters: A healthy Otyughs\n");
    expect.append("3-0Cave. Openings: East \n");
    expect.append("treasures: DIAMOND SAPPHIRE SAPPHIRE RUBY RUBY \n");
    expect.append("weapons: 2 * CROOKEDARROW\n");
    expect.append("monsters: A healthy Otyughs\n");
    expect.append("3-1Cave. Openings: North  South  West \n");
    expect.append("treasures: DIAMOND DIAMOND SAPPHIRE RUBY RUBY \n");
    expect.append("weapons: 1 * CROOKEDARROW\n");
    expect.append("monsters: A dead Otyughs\n");
    expect.append("3-2Cave. Openings: North \n");
    expect.append("treasures: DIAMOND SAPPHIRE SAPPHIRE RUBY \n");
    expect.append("weapons: \n");
    expect.append("monsters: A dead Otyughs\n");
    expect.append("3-3Cave. Openings: North \n");
    expect.append("treasures: DIAMOND SAPPHIRE SAPPHIRE RUBY RUBY \n");
    expect.append("weapons: 1 * CROOKEDARROW\n");
    expect.append("monsters: A healthy Otyughs\n");
    expect.append("3-4Tunnel Openings: East  North \n");
    expect.append("treasures: \n");
    expect.append("weapons: 2 * CROOKEDARROW\n");
    expect.append("monsters: None\n");
    expect.append("3-5Tunnel Openings: South  West \n");
    expect.append("treasures: \n");
    expect.append("weapons: 1 * CROOKEDARROW\n");
    expect.append("monsters: None\n");
    expect.append("4-0Tunnel Openings: East  South \n");
    expect.append("treasures: \n");
    expect.append("weapons: \n");
    expect.append("monsters: None\n");
    expect.append("4-1Cave. Openings: North  South  West \n");
    expect.append("treasures: DIAMOND SAPPHIRE SAPPHIRE RUBY RUBY \n");
    expect.append("weapons: \n");
    expect.append("monsters: None\n");
    expect.append("4-2Tunnel Openings: East  South \n");
    expect.append("treasures: \n");
    expect.append("weapons: 2 * CROOKEDARROW\n");
    expect.append("monsters: None\n");
    expect.append("4-3Tunnel Openings: South  West \n");
    expect.append("treasures: \n");
    expect.append("weapons: 1 * CROOKEDARROW\n");
    expect.append("monsters: None\n");
    expect.append("4-4Cave. Openings: South \n");
    expect.append("treasures: DIAMOND SAPPHIRE SAPPHIRE RUBY RUBY \n");
    expect.append("weapons: 2 * CROOKEDARROW\n");
    expect.append("monsters: A healthy Otyughs\n");
    expect.append("4-5Tunnel Openings: North  South \n");
    expect.append("treasures: \n");
    expect.append("weapons: 1 * CROOKEDARROW\n");
    expect.append("monsters: None\n");
    expect.append("5-0Cave. Openings: North \n");
    expect.append("treasures: DIAMOND DIAMOND SAPPHIRE RUBY RUBY \n");
    expect.append("weapons: \n");
    expect.append("monsters: A dead Otyughs\n");
    expect.append("5-1Cave. Openings: North \n");
    expect.append("treasures: DIAMOND SAPPHIRE SAPPHIRE RUBY RUBY \n");
    expect.append("weapons: \n");
    expect.append("monsters: A dead Otyughs\n");
    expect.append("5-2Cave. Openings: North \n");
    expect.append("treasures: DIAMOND SAPPHIRE RUBY RUBY \n");
    expect.append("weapons: 2 * CROOKEDARROW\n");
    expect.append("monsters: A healthy Otyughs\n");
    expect.append("5-3Tunnel Openings: East  North \n");
    expect.append("treasures: \n");
    expect.append("weapons: 1 * CROOKEDARROW\n");
    expect.append("monsters: None\n");
    expect.append("5-4Cave. Openings: East  North  West \n");
    expect.append("treasures: DIAMOND DIAMOND SAPPHIRE SAPPHIRE RUBY \n");
    expect.append("weapons: 2 * CROOKEDARROW\n");
    expect.append("monsters: None\n");
    expect.append("5-5Tunnel Openings: North  West \n");
    expect.append("treasures: \n");
    expect.append("weapons: 2 * CROOKEDARROW\n");
    expect.append("monsters: None\n");
    assertEquals(expect.toString(), m.toString());
  }

  @Test
  public void killedByMonster() {
    assertFalse(model.metMonster());
    assertFalse(model.isDead());
    assertEquals(Smell.PUNGENT, model.getSmell());
    model.move(Direction.NORTH);
    String exp = "Player's current location: 3-2Cave. Openings: East  South  West \n"
            + "treasures: DIAMOND DIAMOND SAPPHIRE SAPPHIRE RUBY RUBY \n"
            + "weapons: 2 * CROOKEDARROW\n"
            + "monsters: A healthy Otyughs";
    assertEquals(exp, model.cave());
    assertTrue(model.metMonster());
    assertTrue(model.isDead());
  }

  @Test
  public void killMonster() {
    assertFalse(model.metMonster());
    assertFalse(model.isDead());
    assertEquals(Smell.PUNGENT, model.getSmell()); // 1-distance
    model.shoot(1, Direction.NORTH);
    assertEquals(Smell.PUNGENT, model.getSmell());
    assertTrue(model.shoot(1, Direction.NORTH));
    assertEquals(Smell.NONE, model.getSmell()); // monster dead
    model.move(Direction.NORTH);
    String exp = "Player's current location: 3-2Cave. Openings: East  South  West \n"
            + "treasures: DIAMOND DIAMOND SAPPHIRE SAPPHIRE RUBY RUBY \n"
            + "weapons: 2 * CROOKEDARROW\n"
            + "monsters: A dead Otyughs";
    assertEquals(exp, model.cave());
    assertFalse(model.metMonster());
    assertFalse(model.isDead());
    assertTrue(model.isMonsterDead());
  }

  @Test
  public void escapeSuccessfully() {
    assertFalse(model.metMonster());
    assertFalse(model.isDead());
    model.shoot(1, Direction.NORTH);
    model.move(Direction.NORTH);
    String exp = "Player's current location: 3-2Cave. Openings: East  South  West \n"
            + "treasures: DIAMOND DIAMOND SAPPHIRE SAPPHIRE RUBY RUBY \n"
            + "weapons: 2 * CROOKEDARROW\n"
            + "monsters: An injured Otyughs";
    assertEquals(exp, model.cave());
    assertTrue(model.metMonster());
    assertFalse(model.isDead());
  }

  @Test
  public void escapeFailed() {
    assertFalse(model.metMonster());
    assertFalse(model.isDead());
    model.shoot(1, Direction.NORTH);
    model.move(Direction.NORTH);
    assertTrue(model.metMonster());
    assertFalse(model.isDead());
    assertFalse(model.isMonsterDead());
    model.move(Direction.SOUTH);
    assertFalse(model.metMonster());
    assertFalse(model.isDead());
    assertFalse(model.isMonsterDead());
    model.move(Direction.NORTH);
    assertTrue(model.metMonster());
    assertTrue(model.isDead());
    assertFalse(model.isMonsterDead());
    List<List<Integer>> exp = new ArrayList<>();
    exp.add(Arrays.asList(3, 2));
    exp.add(Arrays.asList(4, 2));
    assertTrue(exp.containsAll(model.visited()));
    assertTrue(model.visited().containsAll(exp));
  }

  @Test
  public void wrappingAndOutputTest() {
    model.move(Direction.EAST);
    String exp = "The player has treasures: \n"
            + "The player has weapons: 3 * CROOKEDARROW";
    assertEquals(exp, model.player());
    model.move(Direction.SOUTH);
    model.move(Direction.EAST);
    model.move(Direction.EAST);
    model.move(Direction.EAST);
    exp = "Player's current location: 5-0Cave. Openings: East  North  South  West \n"
            + "treasures: DIAMOND SAPPHIRE RUBY RUBY \n"
            + "weapons: 1 * CROOKEDARROW\n"
            + "monsters: None";
    assertEquals(exp, model.cave());
    model.move(Direction.WEST);
    exp = "Player's current location: 5-5Cave. Openings: East  North  South  West \n"
            + "treasures: DIAMOND DIAMOND SAPPHIRE SAPPHIRE RUBY \n"
            + "weapons: 1 * CROOKEDARROW\n"
            + "monsters: None";
    assertEquals(exp, model.cave());
    model.move(Direction.SOUTH);
    exp = "Player's current location: 0-5Cave. Openings: East  North  South \n"
            + "treasures: DIAMOND DIAMOND SAPPHIRE RUBY RUBY \n"
            + "weapons: 1 * CROOKEDARROW\n"
            + "monsters: None";
    assertEquals(exp, model.cave());
    model.move(Direction.NORTH);
    exp = "Player's current location: 5-5Cave. Openings: East  North  South  West \n"
            + "treasures: DIAMOND DIAMOND SAPPHIRE SAPPHIRE RUBY \n"
            + "weapons: 1 * CROOKEDARROW\n"
            + "monsters: None";
    assertEquals(exp, model.cave());
    model.move(Direction.SOUTH);
    exp = "Player's current location: 0-5Cave. Openings: East  North  South \n"
            + "treasures: DIAMOND DIAMOND SAPPHIRE RUBY RUBY \n"
            + "weapons: 1 * CROOKEDARROW\n"
            + "monsters: None";
    assertEquals(exp, model.cave());
  }

  @Test
  public void Overall() {
    StringBuilder exp = new StringBuilder();
    exp.append("Dungeon: Player's current cave: cave4-2\n");
    exp.append("end cave is: cave2-5\n");
    exp.append("Player's info: The player has treasures: \n");
    exp.append("The player has weapons: 3 * CROOKEDARROW\n");
    exp.append("0-0Cave. Openings: North  South  West \n");
    exp.append("treasures: DIAMOND SAPPHIRE RUBY \n");
    exp.append("weapons: 2 * CROOKEDARROW\n");
    exp.append("monsters: None\n");
    exp.append("0-1Cave. Openings: South \n");
    exp.append("treasures: DIAMOND DIAMOND SAPPHIRE RUBY RUBY \n");
    exp.append("weapons: 1 * CROOKEDARROW\n");
    exp.append("monsters: None\n");
    exp.append("0-2Tunnel Openings: East  South \n");
    exp.append("treasures: \n");
    exp.append("weapons: 1 * CROOKEDARROW\n");
    exp.append("monsters: None\n");
    exp.append("0-3Cave. Openings: North  South  West \n");
    exp.append("treasures: DIAMOND SAPPHIRE RUBY RUBY \n");
    exp.append("weapons: 2 * CROOKEDARROW\n");
    exp.append("monsters: None\n");
    exp.append("0-4Cave. Openings: North \n");
    exp.append("treasures: DIAMOND DIAMOND SAPPHIRE SAPPHIRE RUBY \n");
    exp.append("weapons: 1 * CROOKEDARROW\n");
    exp.append("monsters: None\n");
    exp.append("0-5Cave. Openings: East  North  South \n");
    exp.append("treasures: DIAMOND DIAMOND SAPPHIRE RUBY RUBY \n");
    exp.append("weapons: 1 * CROOKEDARROW\n");
    exp.append("monsters: None\n");
    exp.append("1-0Tunnel Openings: East  North \n");
    exp.append("treasures: \n");
    exp.append("weapons: 2 * CROOKEDARROW\n");
    exp.append("monsters: None\n");
    exp.append("1-1Tunnel Openings: North  West \n");
    exp.append("treasures: \n");
    exp.append("weapons: 1 * CROOKEDARROW\n");
    exp.append("monsters: None\n");
    exp.append("1-2Cave. Openings: East  North  South \n");
    exp.append("treasures: DIAMOND SAPPHIRE SAPPHIRE RUBY \n");
    exp.append("weapons: 2 * CROOKEDARROW\n");
    exp.append("monsters: None\n");
    exp.append("1-3Tunnel Openings: North  West \n");
    exp.append("treasures: \n");
    exp.append("weapons: 1 * CROOKEDARROW\n");
    exp.append("monsters: None\n");
    exp.append("1-4Cave. Openings: East \n");
    exp.append("treasures: DIAMOND DIAMOND SAPPHIRE RUBY \n");
    exp.append("weapons: 1 * CROOKEDARROW\n");
    exp.append("monsters: None\n");
    exp.append("1-5Cave. Openings: North  South  West \n");
    exp.append("treasures: DIAMOND DIAMOND SAPPHIRE SAPPHIRE RUBY \n");
    exp.append("weapons: 1 * CROOKEDARROW\n");
    exp.append("monsters: None\n");
    exp.append("2-0Cave. Openings: East  South  West \n");
    exp.append("treasures: DIAMOND SAPPHIRE SAPPHIRE RUBY RUBY \n");
    exp.append("weapons: 1 * CROOKEDARROW\n");
    exp.append("monsters: None\n");
    exp.append("2-1Tunnel Openings: South  West \n");
    exp.append("treasures: \n");
    exp.append("weapons: 2 * CROOKEDARROW\n");
    exp.append("monsters: None\n");
    exp.append("2-2Cave. Openings: North \n");
    exp.append("treasures: DIAMOND DIAMOND SAPPHIRE SAPPHIRE RUBY RUBY \n");
    exp.append("weapons: 1 * CROOKEDARROW\n");
    exp.append("monsters: None\n");
    exp.append("2-3Cave. Openings: East \n");
    exp.append("treasures: DIAMOND DIAMOND SAPPHIRE RUBY RUBY \n");
    exp.append("weapons: 2 * CROOKEDARROW\n");
    exp.append("monsters: None\n");
    exp.append("2-4Tunnel Openings: South  West \n");
    exp.append("treasures: \n");
    exp.append("weapons: 1 * CROOKEDARROW\n");
    exp.append("monsters: None\n");
    exp.append("2-5Cave. Openings: East  North  South \n");
    exp.append("treasures: DIAMOND DIAMOND SAPPHIRE RUBY \n");
    exp.append("weapons: 2 * CROOKEDARROW\n");
    exp.append("monsters: A healthy Otyughs\n");
    exp.append("3-0Cave. Openings: North  South  West \n");
    exp.append("treasures: DIAMOND SAPPHIRE RUBY \n");
    exp.append("weapons: 2 * CROOKEDARROW\n");
    exp.append("monsters: None\n");
    exp.append("3-1Cave. Openings: East  North  South \n");
    exp.append("treasures: DIAMOND SAPPHIRE SAPPHIRE RUBY \n");
    exp.append("weapons: 1 * CROOKEDARROW\n");
    exp.append("monsters: None\n");
    exp.append("3-2Cave. Openings: East  South  West \n");
    exp.append("treasures: DIAMOND DIAMOND SAPPHIRE SAPPHIRE RUBY RUBY \n");
    exp.append("weapons: 2 * CROOKEDARROW\n");
    exp.append("monsters: A healthy Otyughs\n");
    exp.append("3-3Cave. Openings: East  South  West \n");
    exp.append("treasures: DIAMOND SAPPHIRE SAPPHIRE RUBY RUBY \n");
    exp.append("weapons: 1 * CROOKEDARROW\n");
    exp.append("monsters: None\n");
    exp.append("3-4Cave. Openings: East  North  West \n");
    exp.append("treasures: DIAMOND DIAMOND SAPPHIRE SAPPHIRE RUBY \n");
    exp.append("weapons: 2 * CROOKEDARROW\n");
    exp.append("monsters: None\n");
    exp.append("3-5Cave. Openings: East  North  South  West \n");
    exp.append("treasures: DIAMOND SAPPHIRE SAPPHIRE RUBY RUBY \n");
    exp.append("weapons: 1 * CROOKEDARROW\n");
    exp.append("monsters: None\n");
    exp.append("4-0Tunnel Openings: North  South \n");
    exp.append("treasures: \n");
    exp.append("weapons: 2 * CROOKEDARROW\n");
    exp.append("monsters: None\n");
    exp.append("4-1Tunnel Openings: North  South \n");
    exp.append("treasures: \n");
    exp.append("weapons: 1 * CROOKEDARROW\n");
    exp.append("monsters: None\n");
    exp.append("4-2Cave. Openings: East  North  South \n");
    exp.append("treasures: DIAMOND SAPPHIRE RUBY RUBY \n");
    exp.append("weapons: 2 * CROOKEDARROW\n");
    exp.append("monsters: None\n");
    exp.append("4-3Cave. Openings: North  South  West \n");
    exp.append("treasures: DIAMOND DIAMOND SAPPHIRE RUBY \n");
    exp.append("weapons: 2 * CROOKEDARROW\n");
    exp.append("monsters: None\n");
    exp.append("4-4Cave. Openings: South \n");
    exp.append("treasures: DIAMOND SAPPHIRE RUBY RUBY \n");
    exp.append("weapons: 1 * CROOKEDARROW\n");
    exp.append("monsters: None\n");
    exp.append("4-5Tunnel Openings: North  South \n");
    exp.append("treasures: \n");
    exp.append("weapons: 2 * CROOKEDARROW\n");
    exp.append("monsters: None\n");
    exp.append("5-0Cave. Openings: East  North  South  West \n");
    exp.append("treasures: DIAMOND SAPPHIRE RUBY RUBY \n");
    exp.append("weapons: 1 * CROOKEDARROW\n");
    exp.append("monsters: None\n");
    exp.append("5-1Tunnel Openings: North  West \n");
    exp.append("treasures: \n");
    exp.append("weapons: 2 * CROOKEDARROW\n");
    exp.append("monsters: None\n");
    exp.append("5-2Tunnel Openings: East  North \n");
    exp.append("treasures: \n");
    exp.append("weapons: 1 * CROOKEDARROW\n");
    exp.append("monsters: None\n");
    exp.append("5-3Cave. Openings: East  North  South  West \n");
    exp.append("treasures: DIAMOND SAPPHIRE SAPPHIRE RUBY \n");
    exp.append("weapons: 1 * CROOKEDARROW\n");
    exp.append("monsters: None\n");
    exp.append("5-4Cave. Openings: East  North  South  West \n");
    exp.append("treasures: DIAMOND SAPPHIRE RUBY RUBY \n");
    exp.append("weapons: 2 * CROOKEDARROW\n");
    exp.append("monsters: None\n");
    exp.append("5-5Cave. Openings: East  North  South  West \n");
    exp.append("treasures: DIAMOND DIAMOND SAPPHIRE SAPPHIRE RUBY \n");
    exp.append("weapons: 1 * CROOKEDARROW\n");
    exp.append("monsters: None\n");
    assertEquals(exp.toString(), model.toString());
    model.shoot(1, Direction.NORTH);
    model.shoot(1, Direction.NORTH);
    model.pickWeapon();
    model.move(Direction.NORTH);
    model.move(Direction.WEST);
    model.move(Direction.NORTH);
    assertFalse(model.shoot(1, Direction.WEST));
    assertTrue(model.shoot(2, Direction.WEST));
    model.move(Direction.WEST);
    model.shoot(1, Direction.WEST);
    model.move(Direction.WEST);
    assertFalse(model.metMonster());
    assertFalse(model.isDead());
    assertTrue(model.reachEnd());
    assertNotEquals(exp.toString(), model.toString());
    model.restart();
    assertEquals(exp.toString(), model.toString());
  }
}