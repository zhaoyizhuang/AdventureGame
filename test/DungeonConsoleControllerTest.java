import org.junit.Before;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

import controller.DungeonConsoleController;
import controller.DungeonController;
import model.dungeon.Dungeon;
import model.dungeon.DungeonGame;
import model.dungeon.DungeonTest;

import static org.junit.Assert.assertEquals;

/**
 * Test for the text-based controller and model.
 */
public class DungeonConsoleControllerTest {
  private Reader input;
  private DungeonGame model;
  private StringBuffer out;
  private StringBuilder playLine0;
  private StringBuilder playLine;
  private StringBuilder faintLine;
  private StringBuilder dirLine;
  private StringBuilder disLine;
  private StringBuilder pWLine;
  private StringBuilder pTLine;

  @Before
  public void setUp() throws Exception {
    model = TestDungeon.testDungeon1();
    out = new StringBuffer("");
    playLine0 = new StringBuilder();
    playLine0.append("Select an action: M for move, S for shoot, P for pick, ")
            .append("INFO for player's information and CAVE for cave information.\n");
    playLine = new StringBuilder();
    playLine.append("\nYou smell something terrible nearby\n")
            .append("Select an action: M for move, S for shoot, P for pick, ")
            .append("INFO for player's information and CAVE for cave information.\n");
    dirLine = new StringBuilder();
    dirLine.append("Please specify a Direction.\n")
            .append("W for west, E for east, N for north, S for south,")
            .append(" Q for reselect an action.\n");
    faintLine = new StringBuilder();
    faintLine.append("\nYou smell a strange odor\n")
            .append("Select an action: M for move, S for shoot, P for pick, ")
            .append("INFO for player's information and CAVE for cave information.\n");
    disLine = new StringBuilder();
    disLine.append("Please specify a Distance\n")
            .append("To reselect an action, enter any number and then enter Q\n");
    pWLine = new StringBuilder();
    pWLine.append("Pick weapon or treasure? W for weapon, ")
            .append("T for treasure, Q for reselect an action.\n");
    pTLine = new StringBuilder();
    pTLine.append("Specify the Treasure, D for Diamond, S for Sapphire, R for Ruby\n");
  }

  private String addBetween(StringBuilder out, StringBuilder s) {
    StringBuilder res = new StringBuilder();
    return res.append(out).append(s).append(out).toString();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidModel() {
    StringBuffer out = new StringBuffer("");
    DungeonController c = new DungeonConsoleController(input, out, null);
  }

  @Test(expected = IllegalStateException.class)
  public void testFailingAppendable() {
    // Testing when something goes wrong with the Appendable
    // Here we are passing it a mock of an Appendable that always fails
    DungeonGame m = new Dungeon(false);
    StringReader input = new StringReader("8 8 8 8 8");
    Appendable gameLog = new FailingAppendable();
    DungeonController c = new DungeonConsoleController(input, gameLog, m);
    c.play();
  }

  @Test
  public void ControllerIndependentTest() {
    StringBuilder log = new StringBuilder();
    input = new StringReader("S 1 N");
    Mock mock = new Mock(log, 1234321);
    DungeonController controller = new DungeonConsoleController(input, out, mock);
    controller.play();
    assertEquals("Input :1, NORTH UniqueCode: 1234321", log.toString());
  }

  @Test
  public void InvalidCommandLine() {
    input = new StringReader("L");
    DungeonController controller = new DungeonConsoleController(input, out, model);
    controller.play();
    assertEquals(new StringBuilder().append(playLine)
                    .append("Invalid command. Please choose again\n").toString(),
            out.toString());
  }

  @Test
  public void move() {
    input = new StringReader("M L");
    DungeonController controller = new DungeonConsoleController(input, out, model);
    controller.play();
    assertEquals(new StringBuilder().append(playLine)
                    .append(addBetween(dirLine, new StringBuilder().append("Invalid Movement.\n")
                            .append("invalid command Try Again.\n"))).toString(),
            out.toString());
    out = new StringBuffer("");

    input = new StringReader("M W");
    ((DungeonTest) model).setCaves(6, 6, 10, true, 100, 2);
    controller = new DungeonConsoleController(input, out, model);
    controller.play();
    assertEquals(new StringBuilder().append(playLine)
                    .append(addBetween(dirLine, new StringBuilder().append("Invalid Movement.\n")
                            .append("No way to west. Try Again.\n"))).toString(),
            out.toString());
    out = new StringBuffer("");

    input = new StringReader("M E");
    ((DungeonTest) model).setCaves(6, 6, 10, true, 100, 2);
    controller = new DungeonConsoleController(input, out, model);
    controller.play();
    assertEquals(new StringBuilder().append(playLine).append(dirLine).append("You moved to E\n")
                            .append(faintLine).toString(),
            out.toString());
    out = new StringBuffer("");

    input = new StringReader("M S");
    ((DungeonTest) model).setCaves(6, 6, 10, true, 100, 2);
    controller = new DungeonConsoleController(input, out, model);
    controller.play();
    assertEquals(new StringBuilder().append(playLine).append(dirLine).append("You moved to S\n")
                    .append(faintLine).toString(),
            out.toString());
    out = new StringBuffer("");

    input = new StringReader("M Q"); //reselect
    ((DungeonTest) model).setCaves(6, 6, 10, true, 100, 2);
    controller = new DungeonConsoleController(input, out, model);
    controller.play();
    assertEquals(new StringBuilder().append(playLine).append(dirLine)
                    .append(playLine).toString(),
            out.toString());
    out = new StringBuffer("");
  }

  @Test
  public void shoot() {
    input = new StringReader("S L");
    DungeonController controller = new DungeonConsoleController(input, out, model);
    controller.play();
    assertEquals(new StringBuilder().append(playLine)
                    .append(addBetween(disLine, new StringBuilder()
                            .append("Invalid Distance\n"))).toString(),
            out.toString());
    out = new StringBuffer("");

    input = new StringReader("S 1 1");
    ((DungeonTest) model).setCaves(6, 6, 10, true, 100, 2);
    controller = new DungeonConsoleController(input, out, model);
    controller.play();
    assertEquals(new StringBuilder().append(playLine)
                    .append(addBetween(disLine, new StringBuilder().append(dirLine)
                            .append("Invalid Shoot.\n")
                            .append("Invalid Direction, Please Select Again.\n"))).toString(),
            out.toString());
    out = new StringBuffer("");

    input = new StringReader("S -1 W");
    ((DungeonTest) model).setCaves(6, 6, 10, true, 100, 2);
    controller = new DungeonConsoleController(input, out, model);
    controller.play();
    assertEquals(new StringBuilder().append(playLine).append(disLine).append(dirLine)
                    .append("Invalid Shoot.\n")
                    .append("distance should > 0, Please Select Again.\n")
                    .append(disLine).toString(),
            out.toString());
    out = new StringBuffer("");

    input = new StringReader("S 0 W");
    ((DungeonTest) model).setCaves(6, 6, 10, true, 100, 2);
    controller = new DungeonConsoleController(input, out, model);
    controller.play();
    assertEquals(new StringBuilder().append(playLine).append(disLine).append(dirLine)
                    .append("Invalid Shoot.\n")
                    .append("distance should > 0, Please Select Again.\n")
                    .append(disLine).toString(),
            out.toString());
    out = new StringBuffer("");

    input = new StringReader("S 1 E");
    ((DungeonTest) model).setCaves(6, 6, 10, true, 100, 2);
    controller = new DungeonConsoleController(input, out, model);
    controller.play();
    assertEquals(new StringBuilder().append(playLine).append(disLine).append(dirLine)
                    .append("You shoot an arrow into the darkness\n")
                    .append(playLine).toString(),
            out.toString());
    out = new StringBuffer("");

    input = new StringReader("S 1 W S 1 W S 1 W S 1 W");
    ((DungeonTest) model).setCaves(6, 6, 10, true, 100, 2);
    controller = new DungeonConsoleController(input, out, model);
    controller.play();
    assertEquals(new StringBuilder().append(playLine).append(disLine).append(dirLine)
                    .append("You shoot an arrow into the darkness\n")
                    .append(playLine).append(disLine).append(dirLine)
                    .append("You shoot an arrow into the darkness\n")
                    .append(playLine).append(disLine).append(dirLine)
                    .append("You shoot an arrow into the darkness\n")
                    .append(playLine).append(disLine).append(dirLine)
                    .append("Invalid Shoot.\n").append("No arrow left, Please Select Again.\n")
                    .append(disLine)
                    .toString(),
            out.toString());
    out = new StringBuffer("");

    input = new StringReader("S 1 Q"); //RESELECT
    ((DungeonTest) model).setCaves(6, 6, 10, true, 100, 2);
    controller = new DungeonConsoleController(input, out, model);
    controller.play();
    assertEquals(new StringBuilder().append(playLine).append(disLine).append(dirLine)
                    .append(playLine).toString(),
            out.toString());
  }

  @Test
  public void pick() {
    input = new StringReader("P 1");
    DungeonController controller = new DungeonConsoleController(input, out, model);
    controller.play();
    assertEquals(new StringBuilder().append(playLine)
                    .append(addBetween(pWLine, new StringBuilder()
                            .append("Invalid Command\n"))).toString(),
            out.toString());
    out = new StringBuffer("");

    input = new StringReader("P W");
    ((DungeonTest) model).setCaves(6, 6, 10, true, 100, 2);
    controller = new DungeonConsoleController(input, out, model);
    controller.play();
    assertEquals(new StringBuilder()
                    .append(addBetween(playLine, new StringBuilder().append(pWLine)
                            .append("You picked up all weapons you see.\n"))).toString(),
            out.toString());
    out = new StringBuffer("");

    input = new StringReader("P Q"); //RESELECT
    ((DungeonTest) model).setCaves(6, 6, 10, true, 100, 2);
    controller = new DungeonConsoleController(input, out, model);
    controller.play();
    assertEquals(new StringBuilder()
                    .append(addBetween(playLine, new StringBuilder().append(pWLine))).toString(),
            out.toString());
    out = new StringBuffer("");

    input = new StringReader("P W P W");
    ((DungeonTest) model).setCaves(6, 6, 10, true, 100, 2);
    controller = new DungeonConsoleController(input, out, model);
    controller.play();
    assertEquals(new StringBuilder()
                    .append(addBetween(playLine, new StringBuilder().append(pWLine)
                            .append("You picked up all weapons you see.\n")))
                    .append(addBetween(pWLine, new StringBuilder().append("Invalid Pick Action.\n")
                            .append("No weapon found, Please Select Again.\n"))).toString(),
            out.toString());
    out = new StringBuffer("");

    input = new StringReader("P T 1");
    ((DungeonTest) model).setCaves(6, 6, 10, true, 100, 2);
    controller = new DungeonConsoleController(input, out, model);
    controller.play();
    assertEquals(new StringBuilder().append(playLine)
                    .append(addBetween(pWLine, new StringBuilder().append(pTLine)
                            .append("Invalid Pick Action.\n")
                            .append("Invalid Treasure, Please Select Again.\n"))).toString(),
            out.toString());
    out = new StringBuffer("");

    input = new StringReader("P T D");
    ((DungeonTest) model).setCaves(6, 6, 10, true, 100, 2);
    controller = new DungeonConsoleController(input, out, model);
    controller.play();
    assertEquals(new StringBuilder()
                    .append(addBetween(playLine, new StringBuilder().append(pWLine).append(pTLine)
                            .append("Pick up successfully.\n"))).toString(),
            out.toString());
    out = new StringBuffer("");

    input = new StringReader("P T S");
    ((DungeonTest) model).setCaves(6, 6, 10, true, 100, 2);
    controller = new DungeonConsoleController(input, out, model);
    controller.play();
    assertEquals(new StringBuilder()
                    .append(addBetween(playLine, new StringBuilder().append(pWLine).append(pTLine)
                            .append("Pick up successfully.\n"))).toString(),
            out.toString());
    out = new StringBuffer("");

    input = new StringReader("P T R");
    ((DungeonTest) model).setCaves(6, 6, 10, true, 100, 2);
    controller = new DungeonConsoleController(input, out, model);
    controller.play();
    assertEquals(new StringBuilder()
                    .append(addBetween(playLine, new StringBuilder().append(pWLine).append(pTLine)
                            .append("Pick up successfully.\n"))).toString(),
            out.toString());
    out = new StringBuffer("");

    input = new StringReader("P T D P T D");
    ((DungeonTest) model).setCaves(6, 6, 10, true, 100, 2);
    controller = new DungeonConsoleController(input, out, model);
    controller.play();
    assertEquals(new StringBuilder()
                    .append(addBetween(playLine, new StringBuilder().append(pWLine).append(pTLine)
                            .append("Pick up successfully.\n")))
                    .append(addBetween(pWLine, new StringBuilder().append(pTLine)
                            .append("Invalid Pick Action.\n")
                            .append("trying to pick a non-exist treasure, Please Select Again.\n")))
                    .toString(),
            out.toString());
    out = new StringBuffer("");
  }

  @Test
  public void monster() {
    //killed by a monster
    input = new StringReader("M N");
    DungeonController controller = new DungeonConsoleController(input, out, model);
    controller.play();
    assertEquals(new StringBuilder().append(playLine).append(dirLine).append("You moved to N\n")
                    .append("Chomp, chomp, chomp, you are eaten by an Otyugh!\n")
                    .append("Better luck next time\n").toString(),
            out.toString());
    out = new StringBuffer("");

    //kill a monster
    input = new StringReader("S 1 N S 1 N M N");
    ((DungeonTest) model).setCaves(6, 6, 10, true, 100, 2);
    controller = new DungeonConsoleController(input, out, model);
    controller.play();
    assertEquals(new StringBuilder().append(playLine).append(disLine).append(dirLine)
                    .append("You hear a great howl in the distance\n")
                    .append(playLine).append(disLine).append(dirLine)
                    .append("You hear a great howl in the distance\n\n")
                    .append(playLine0).append(dirLine).append("You moved to N\n")
                    .append("\nYou see a dead Monster! Seems the arrow works.\n")
                    .append(playLine0).toString(),
            out.toString());
    out = new StringBuffer("");

    //escape successfully
    input = new StringReader("S 1 N M N S");
    ((DungeonTest) model).setCaves(6, 6, 10, true, 100, 2);
    ((DungeonTest) model).setRand(0);
    controller = new DungeonConsoleController(input, out, model);
    controller.play();
    assertEquals(new StringBuilder().append(playLine).append(disLine).append(dirLine)
                    .append("You hear a great howl in the distance\n")
                    .append(playLine).append(dirLine).append("You moved to N\n")
                    .append("You meet an Otyugh! He jumps at you.You avoid his attack, ")
                    .append("and can choose a way to escape.\n").append(dirLine)
                    .append("You moved to S\n")
                    .append(playLine).toString(),
            out.toString());
    out = new StringBuffer("");
  }

  @Test
  public void escapeFailed() {
    input = new StringReader("S 1 N M N S");
    ((DungeonTest) model).setCaves(6, 6, 10, true, 100, 2);
    ((DungeonTest) model).setRand(1);
    DungeonController controller = new DungeonConsoleController(input, out, model);
    controller.play();
    assertEquals(new StringBuilder().append(playLine).append(disLine).append(dirLine)
                    .append("You hear a great howl in the distance\n")
                    .append(playLine).append(dirLine).append("You moved to N\n")
                    .append("Chomp, chomp, chomp, you are eaten by an Otyugh!\n")
                    .append("Better luck next time\n").toString(),
            out.toString());
  }

  @Test
  public void info() {
    input = new StringReader("INFO");
    DungeonController controller = new DungeonConsoleController(input, out, model);
    controller.play();
    assertEquals(addBetween(playLine, new StringBuilder()
                    .append("The player has treasures: \n")
                    .append("The player has weapons: 3 * CROOKEDARROW\n")),
            out.toString());
    out = new StringBuffer("");
  }

  @Test
  public void cave() {
    input = new StringReader("CAVE");
    DungeonController controller = new DungeonConsoleController(input, out, model);
    controller.play();
    assertEquals(addBetween(playLine, new StringBuilder()
                    .append("Player's current location: 4-2Cave. Openings: East  North  South \n")
                    .append("treasures: DIAMOND SAPPHIRE RUBY RUBY \n")
                    .append("weapons: 2 * CROOKEDARROW\n")
                    .append("monsters: None\n")),
            out.toString());
  }

  @Test
  public void actionCaveInfo() {
    input = new StringReader("INFO CAVE P W INFO CAVE");
    DungeonController controller = new DungeonConsoleController(input, out, model);
    controller.play();
    assertEquals(addBetween(playLine, new StringBuilder()
                    .append("The player has treasures: \n")
                    .append("The player has weapons: 3 * CROOKEDARROW\n")
                    .append(playLine)
                    .append("Player's current location: 4-2Cave. Openings:")
                    .append(" East  North  South \n")
                    .append("treasures: DIAMOND SAPPHIRE RUBY RUBY \n")
                    .append("weapons: 2 * CROOKEDARROW\n")
                    .append("monsters: None\n")
                    .append(addBetween(playLine, new StringBuilder().append(pWLine)
                            .append("You picked up all weapons you see.\n")))
                    .append("The player has treasures: \n")
                    .append("The player has weapons: 5 * CROOKEDARROW\n")
                    .append(playLine)
                    .append("Player's current location: 4-2Cave. Openings:")
                    .append(" East  North  South \n")
                    .append("treasures: DIAMOND SAPPHIRE RUBY RUBY \n")
                    .append("weapons: \n")
                    .append("monsters: None\n")),
            out.toString());
    out = new StringBuffer("");

    input = new StringReader("INFO CAVE P W P W Q INFO CAVE");
    ((DungeonTest) model).setCaves(6, 6, 10, true, 100, 2);
    controller = new DungeonConsoleController(input, out, model);
    controller.play();
    assertEquals(addBetween(playLine, new StringBuilder()
                    .append("The player has treasures: \n")
                    .append("The player has weapons: 3 * CROOKEDARROW\n")
                    .append(playLine)
                    .append("Player's current location: 4-2Cave. Openings:")
                    .append(" East  North  South \n")
                    .append("treasures: DIAMOND SAPPHIRE RUBY RUBY \n")
                    .append("weapons: 2 * CROOKEDARROW\n")
                    .append("monsters: None\n")
                    .append(addBetween(playLine, new StringBuilder().append(pWLine)
                            .append("You picked up all weapons you see.\n")))
                    .append(addBetween(pWLine, new StringBuilder().append("Invalid Pick Action.\n")
                            .append("No weapon found, Please Select Again.\n")))
                    .append(playLine)
                    .append("The player has treasures: \n")
                    .append("The player has weapons: 5 * CROOKEDARROW\n")
                    .append(playLine)
                    .append("Player's current location: 4-2Cave. Openings:")
                    .append(" East  North  South \n")
                    .append("treasures: DIAMOND SAPPHIRE RUBY RUBY \n")
                    .append("weapons: \n")
                    .append("monsters: None\n")),
            out.toString());
    out = new StringBuffer("");

    input = new StringReader("INFO CAVE P Q INFO CAVE");
    ((DungeonTest) model).setCaves(6, 6, 10, true, 100, 2);
    controller = new DungeonConsoleController(input, out, model);
    controller.play();
    assertEquals(addBetween(playLine, new StringBuilder()
                    .append("The player has treasures: \n")
                    .append("The player has weapons: 3 * CROOKEDARROW\n")
                    .append(playLine)
                    .append("Player's current location: 4-2Cave. Openings:")
                    .append(" East  North  South \n")
                    .append("treasures: DIAMOND SAPPHIRE RUBY RUBY \n")
                    .append("weapons: 2 * CROOKEDARROW\n")
                    .append("monsters: None\n")
                    .append(addBetween(playLine, new StringBuilder().append(pWLine)))
                    .append("The player has treasures: \n")
                    .append("The player has weapons: 3 * CROOKEDARROW\n")
                    .append(playLine)
                    .append("Player's current location: 4-2Cave. Openings:")
                    .append(" East  North  South \n")
                    .append("treasures: DIAMOND SAPPHIRE RUBY RUBY \n")
                    .append("weapons: 2 * CROOKEDARROW\n")
                    .append("monsters: None\n")),
            out.toString());
    out = new StringBuffer("");

    input = new StringReader("INFO CAVE P T D INFO CAVE");
    ((DungeonTest) model).setCaves(6, 6, 10, true, 100, 2);
    controller = new DungeonConsoleController(input, out, model);
    controller.play();
    assertEquals(addBetween(playLine, new StringBuilder()
                    .append("The player has treasures: \n")
                    .append("The player has weapons: 3 * CROOKEDARROW\n")
                    .append(playLine)
                    .append("Player's current location: 4-2Cave. Openings:")
                    .append(" East  North  South \n")
                    .append("treasures: DIAMOND SAPPHIRE RUBY RUBY \n")
                    .append("weapons: 2 * CROOKEDARROW\n")
                    .append("monsters: None\n")
                    .append(addBetween(playLine, new StringBuilder().append(pWLine).append(pTLine)
                            .append("Pick up successfully.\n")))
                    .append("The player has treasures: DIAMOND \n")
                    .append("The player has weapons: 3 * CROOKEDARROW\n")
                    .append(playLine)
                    .append("Player's current location: 4-2Cave. Openings:")
                    .append(" East  North  South \n")
                    .append("treasures: SAPPHIRE RUBY RUBY \n")
                    .append("weapons: 2 * CROOKEDARROW\n")
                    .append("monsters: None\n")),
            out.toString());
    out = new StringBuffer("");

    input = new StringReader("INFO CAVE P T D P T D Q INFO CAVE");
    ((DungeonTest) model).setCaves(6, 6, 10, true, 100, 2);
    controller = new DungeonConsoleController(input, out, model);
    controller.play();
    assertEquals(addBetween(playLine, new StringBuilder()
                    .append("The player has treasures: \n")
                    .append("The player has weapons: 3 * CROOKEDARROW\n")
                    .append(playLine)
                    .append("Player's current location: 4-2Cave. Openings:")
                    .append(" East  North  South \n")
                    .append("treasures: DIAMOND SAPPHIRE RUBY RUBY \n")
                    .append("weapons: 2 * CROOKEDARROW\n")
                    .append("monsters: None\n")
                    .append(addBetween(playLine, new StringBuilder().append(pWLine).append(pTLine)
                            .append("Pick up successfully.\n")))
                    .append(addBetween(pWLine, new StringBuilder().append(pTLine)
                            .append("Invalid Pick Action.\n")
                            .append("trying to pick a non-exist treasure, Please Select Again.\n")))
                    .append(playLine)
                    .append("The player has treasures: DIAMOND \n")
                    .append("The player has weapons: 3 * CROOKEDARROW\n")
                    .append(playLine)
                    .append("Player's current location: 4-2Cave. Openings:")
                    .append(" East  North  South \n")
                    .append("treasures: SAPPHIRE RUBY RUBY \n")
                    .append("weapons: 2 * CROOKEDARROW\n")
                    .append("monsters: None\n")),
            out.toString());
    out = new StringBuffer("");

    input = new StringReader("INFO S 1 N INFO");
    ((DungeonTest) model).setCaves(6, 6, 10, true, 100, 2);
    controller = new DungeonConsoleController(input, out, model);
    controller.play();
    assertEquals(addBetween(playLine, new StringBuilder()
                    .append("The player has treasures: \n")
                    .append("The player has weapons: 3 * CROOKEDARROW\n")
                    .append(playLine).append(disLine).append(dirLine)
                    .append("You hear a great howl in the distance\n")
                    .append(playLine)
                    .append("The player has treasures: \n")
                    .append("The player has weapons: 2 * CROOKEDARROW\n")),
            out.toString());
    out = new StringBuffer("");

    input = new StringReader("INFO S 1 N S 1 N S 1 N S 1 N 1 Q INFO");
    ((DungeonTest) model).setCaves(6, 6, 10, true, 100, 2);
    controller = new DungeonConsoleController(input, out, model);
    controller.play();
    assertEquals(new StringBuilder().append(playLine)
                    .append("The player has treasures: \n")
                    .append("The player has weapons: 3 * CROOKEDARROW\n")
                    .append(playLine).append(disLine).append(dirLine)
                    .append("You hear a great howl in the distance\n")
                    .append(playLine).append(disLine).append(dirLine)
                    .append("You hear a great howl in the distance\n\n")
                    .append(playLine0).append(disLine).append(dirLine)
                    .append("You shoot an arrow into the darkness\n\n")
                    .append(playLine0).append(disLine).append(dirLine)
                    .append("Invalid Shoot.\n").append("No arrow left, Please Select Again.\n")
                    .append(disLine).append(dirLine).append("\n").append(playLine0)
                    .append("The player has treasures: \n")
                    .append("The player has weapons: 0 * CROOKEDARROW\n\n").append(playLine0)
                    .toString(),
            out.toString());
    out = new StringBuffer("");
  }

  @Test
  public void NonWrapping() {

    StringBuilder shootCommand = new StringBuilder()
            .append(playLine).append(disLine).append(dirLine)
            .append("You hear a great howl in the distance\n");
    DungeonGame model = TestDungeon.testDungeon2();
    input = new StringReader("P W S 1 N S 1 S S 1 W M E W S 2 S S 1 S CAVE M S");
    DungeonController controller = new DungeonConsoleController(input, out, model);
    controller.play();
    assertEquals(new StringBuilder().append(playLine).append(pWLine)
                    .append("You picked up all weapons you see.\n")
                    .append(shootCommand).append(shootCommand).append(shootCommand)
                    .append(playLine).append(dirLine).append("Invalid Movement.\n")
                    .append("No way to east. Try Again.\n")
                    .append(dirLine).append("You moved to W\n")
                    .append(playLine).append(disLine).append(dirLine)
                    .append("You shoot an arrow into the darkness\n")
                    .append(shootCommand).append(playLine)
                    .append("Player's current location: 4-0Tunnel Openings: East  South \n")
                    .append("treasures: \n").append("weapons: 2 * CROOKEDARROW\n")
                    .append("monsters: None\n").append(playLine).append(dirLine)
                    .append("You moved to S\n\n")
                    .append("You see a dead Monster! Seems the arrow works.\n").append(playLine0)
                    .toString(),
            out.toString());
  }

  @Test
  public void reachEnd() {
    input = new StringReader("P W S 1 N S 1 N M N M W M N M W S 1 W S 1 W M W");
    DungeonController controller = new DungeonConsoleController(input, out, model);
    controller.play();
    assertEquals(new StringBuilder().append(addBetween(playLine, new StringBuilder().append(pWLine)
                    .append("You picked up all weapons you see.\n")))
                    .append(disLine).append(dirLine)
                    .append("You hear a great howl in the distance\n")
                    .append(playLine).append(disLine).append(dirLine)
                    .append("You hear a great howl in the distance\n\n")
                    .append(playLine0).append(dirLine).append("You moved to N\n")
                    .append("\nYou see a dead Monster! Seems the arrow works.\n")
                    .append(playLine0).append(dirLine).append("You moved to W\n\n")
                    .append(playLine0).append(dirLine).append("You moved to N\n")
                    .append(faintLine).append(dirLine)
                    .append("You moved to W\n").append(playLine)
                    .append(disLine).append(dirLine)
                    .append("You hear a great howl in the distance\n")
                    .append(playLine).append(disLine).append(dirLine)
                    .append("You hear a great howl in the distance\n\n")
                    .append(playLine0).append(dirLine).append("You moved to W\n\n")
                    .append("You see a dead Monster! Seems the arrow works.\n")
                    .append("You successfully escape from the Dungeon!\n")
                    .toString(),
            out.toString());
  }
}