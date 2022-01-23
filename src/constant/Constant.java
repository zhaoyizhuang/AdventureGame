package constant;

/**
 * this class represents the constants in the Dungeon game.
 */
public final class Constant {
  public static final String INT_INPUT = "^-?\\d+$";
  public static final int MIN_DIST = 5;
  public static final int START_ARROW = 3;
  public static final int WINDOW_HEIGHT = 650;
  public static final int WINDOW_WIDTH = 650;

  // Caves and Tunnels
  public static final String IMAGE_PATH = "imgs/";
  public static final String PLAYER = IMAGE_PATH + "player.png";

  // Treasure and Weapon
  public static final String DIAMOND = IMAGE_PATH + "diamond.png";
  public static final String SAPPHIRE = IMAGE_PATH + "emerald.png";
  public static final String RUBY = IMAGE_PATH + "ruby.png";
  public static final String OTYUGH = IMAGE_PATH + "otyugh.png";
  public static final String ARROW = IMAGE_PATH + "arrow-black.png";
  public static final String ARROW_UP = IMAGE_PATH + "arrow_updown.png";
  public static final int RUBY_OFFSET = 30;
  public static final int[] DIAMOND_OFFSET = new int[]{40, 120};
  public static final int[] SAPPHIRE_OFFSET = new int[]{80, 20};
  public static final int[] OTYUGH_OFFSET = new int[]{20, 60};
  public static final int[] ARROW_OFFSET = new int[]{80, 120};
  public static final int[] ARROW_OFFSET_E = new int[]{120, 80};
  public static final int[] ARROW_OFFSET_W = new int[]{5, 80};
  public static final int[] ARROW_OFFSET_NS = new int[]{80, 0};
  public static final int[] PLAYER_OFFSET = new int[]{70, 60};

  //Tutorial
  public static final String TUTORIAL =
          "Welcome to the Dungeon Game.\nTo win this game, you simply need to find a way to escape "
                  + "from this Dungeon.\nBe careful, there are monsters in the darkness.\n"
                  + "Tips:\nYou may survive from an injured monster.\n"
                  + "To move, press the corresponding arrow key or click the target cave.\n"
                  + "To pick up Treasures, press E.\nTo pick up Weapon, press R\nTo shoot, press S"
                  + ", then press an arrow key to indicate the direction, then enter the distance."
                  + "that arrow will travel.\nDistance is defined as the number of caves, Arrows "
                  + "travel freely down tunnels but only travel in a straight line through a cave."
                  + "\nClick the menu button to see more options.";

  public static final String PUNGENT = "You smell something terrible nearby";
  public static final String FAINT = "You smell a strange odor";
  public static final String ESCAPE = "You meet an Otyugh! He jumps at you.\n"
          + "You avoid his attack";
  public static final String DEAD = "Chomp, chomp, chomp, you are eaten by an Otyugh!\n"
          + "Better luck next time";
  public static final String DEAD_MONSTER = "You see a dead Monster! Seems the arrow works.";
  public static final String WIN = "You successfully escape from the Dungeon!";
  public static final String PICK_WEAPON = "You pick up all weapons you see.";
  public static final String PICK_TREASURE = "You pick up all treasure you see.";
  public static final String SHOT = "You hear a great howl in the distance.";
  public static final String MISS = "You shoot an arrow into the darkness.";

  public static final String[] WRAP_LIST = {"true", "false"};
}
