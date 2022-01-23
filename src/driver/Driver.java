package driver;

import constant.Constant;
import controller.DungeonConsoleController;
import controller.DungeonController;
import controller.DungeonViewController;
import model.dungeon.Dungeon;
import model.dungeon.DungeonGame;
import view.GameSwingView;
import view.GameView;

import java.io.InputStreamReader;


/**
 * Driver class for the Dungeon game. Shows a sample run of how to interact with the game.
 */
public class Driver {

  /**
   * main method to execute ont run.
   * @param args input argument
   */
  public static void main(String[] args) {

    if (args.length == 0) {
      DungeonGame model = new Dungeon(false);
      GameView view = new GameSwingView(model);
      DungeonController controller = new DungeonViewController(model, view);
      controller.play();
      return;
    }

    Readable input = new InputStreamReader(System.in);
    Appendable output = System.out;
    try {
      if (args.length != 6) {
        throw new IllegalArgumentException("Please specify all properties.");
      }
      int[] p = new int[5];
      boolean wrap = false;
      for (int i = 0; i < 5; i++) {
        String str = args[i];
        if (!str.matches(Constant.INT_INPUT)) {
          throw new IllegalArgumentException();
        }
        p[i] = Integer.parseInt(str);
      }
      switch (args[5]) {
        case "T":
          wrap = true;
          break;
        case "F":
          break;
        default:
          throw new IllegalArgumentException();
      }
      DungeonGame model = new Dungeon(p[0], p[1], p[2], wrap, p[3], p[4]);
      DungeonController controller = new DungeonConsoleController(input, output, model);
      //DungeonController controller = new DungeonConsoleController(input, output, modeltest);
      System.out.println("You enter the dungeon.");
      controller.play();
    } catch (IllegalArgumentException e) {
      System.out.println("invalid input, error msg: " + e.getMessage());
      System.out.print("Please specify row, column, interconnectivity");
      System.out.println(", possibility of treasure/arrow, number of monsters");
      System.out.println("and is the Dungeon wrapping? T for True, F for False\n");
    }
  }

}

