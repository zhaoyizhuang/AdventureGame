package view;

import constant.Constant;
import controller.Features;
import model.direction.Direction;
import model.dungeon.ReadOnlyDungeonGame;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

/**
 * This class is the implementation for the Dungeon Game view. It is a user interface
 * that users can interact with.
 */
public class GameSwingView extends JFrame implements GameView {
  private ReadOnlyDungeonGame model;
  private DungeonPanel mainPanel;

  /**
   * Constructor for the GameSwingView in terms of the model.
   * @param model the read only model.
   */
  public GameSwingView(ReadOnlyDungeonGame model) {
    super("Dungeon Game");
    if (model == null) {
      throw new IllegalArgumentException("null model for the view");
    }
    this.model = model;
    setSize(Constant.WINDOW_WIDTH, Constant.WINDOW_HEIGHT);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setResizable(true);
    setFocusable(true);

    drawMainPanel(this.model);
  }

  // draw the main panel
  private void drawMainPanel(ReadOnlyDungeonGame model) {
    this.model = model;
    mainPanel = new DungeonPanel(model);
    JScrollPane dungeon = new JScrollPane(mainPanel);
    mainPanel.setAutoscrolls(true);
    this.setContentPane(dungeon);
    int[][][] d = model.dungeonInfo();
    setMaximumSize(new Dimension(d.length * 173, d[0].length * 173));
    validate();
  }

  @Override
  public void refresh() {
    repaint();
  }

  @Override
  public void makeVisible() {
    setVisible(true);
  }

  @Override
  public void setFeatures(Features f) {
    Menu menu = new Menu();
    this.setJMenuBar(menu.createMenu(f));

    this.addKeyListener(new KeyListener() {
      boolean flag = false;
      @Override
      public void keyTyped(KeyEvent e) {
        switch (e.getKeyChar()) {
          case 'e':
            f.pickTreasure();
            break;
          case 'r':
            f.pickWeapon();
            break;
          case 's':
            flag = true;
            break;
          default:
            break;
        }
      }

      private void help(Direction dir) {
        if (flag) {
          flag = false;
          String input = JOptionPane.showInputDialog("Specify the distance.");
          if (input == null) {
            return;
          }
          if (!input.matches(Constant.INT_INPUT)) {
            JOptionPane.showMessageDialog(null, "Please enter an integer");
          } else {
            int dis = Integer.parseInt(input);
            f.shoot(dir, dis);
          }
        } else {
          f.move(dir);
        }
      }

      @Override
      public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
          case KeyEvent.VK_UP:
            help(Direction.NORTH);
            break;
          case KeyEvent.VK_DOWN:
            help(Direction.SOUTH);
            break;
          case KeyEvent.VK_LEFT:
            help(Direction.WEST);
            break;
          case KeyEvent.VK_RIGHT:
            help(Direction.EAST);
            break;
          default:
            break;
        }
      }

      @Override
      public void keyReleased(KeyEvent e) {
        //unused
      }
    });
  }

  @Override
  public void playerInfo() {
    JOptionPane.showMessageDialog(null, model.playerView(), "Info", 1);
  }

  @Override
  public void caveInfo() {
    JOptionPane.showMessageDialog(null, model.cave(), "Info", 1);
  }

  @Override
  public void getSettings() {
    int[] settings = model.getSettings();
    StringBuilder out = new StringBuilder();
    out.append("This ").append(settings[3] == 1 ? "Wrapping" : "NonWrapping")
            .append(" Dungeon has ").append(settings[0]).append(" rows and ").append(settings[1])
            .append(" columns.\n").append("The interconnectivity is ").append(settings[2])
            .append("\nThe percentage treasure and weapon is ").append(settings[4]).append("%")
            .append("\nThe Dungeon has ").append(settings[5]).append(" Monsters");
    JOptionPane.showMessageDialog(null, out.toString(), "Info", 1);
  }

  @Override
  public void restart(boolean same, ReadOnlyDungeonGame model) {
    drawMainPanel(model);
    if (same) {
      JOptionPane.showMessageDialog(null, "Same Dungeon Restarted", "Info", 1);
    } else {
      JOptionPane.showMessageDialog(null, "Welcome to the new Dungeon", "Info", 1);
    }
  }

  @Override
  public void smell(int level) {
    if (level == 1) {
      JOptionPane.showMessageDialog(null, Constant.PUNGENT, "Info", 1);
    } else {
      JOptionPane.showMessageDialog(null, Constant.FAINT, "Info", 1);
    }
  }

  @Override
  public void metMonster() {
    JOptionPane.showMessageDialog(null, Constant.ESCAPE, "Info", 1);
  }

  @Override
  public void deadMonster() {
    JOptionPane.showMessageDialog(null, Constant.DEAD_MONSTER, "Info", 1);
  }

  @Override
  public void gameStatus(boolean status) {
    if (status) {
      JOptionPane.showMessageDialog(null, Constant.WIN, "Info", 1);
    } else {
      JOptionPane.showMessageDialog(null, Constant.DEAD, "Info", 1);
    }
    setContentPane(new StatusPanel(status));
    validate();
    refresh();
  }

  @Override
  public void pickWeapon() {
    JOptionPane.showMessageDialog(null, Constant.PICK_WEAPON, "Info", 1);
  }

  @Override
  public void pickTreasure() {
    JOptionPane.showMessageDialog(null, Constant.PICK_TREASURE, "Info", 1);
  }

  @Override
  public void errorMessage(String msg) {
    JOptionPane.showMessageDialog(null, msg, "Info", 1);
  }

  @Override
  public void shoot(boolean shot) {
    if (shot) {
      JOptionPane.showMessageDialog(null, Constant.SHOT, "Info", 1);
    } else {
      JOptionPane.showMessageDialog(null, Constant.MISS, "Info", 1);
    }
  }

  @Override
  public void newGame(Features f) {
    SettingPane pane = new SettingPane();
    JOptionPane.showConfirmDialog(null, pane, "Specify Properties.", JOptionPane.DEFAULT_OPTION);
    try {
      int[] input = pane.getInput();
      f.newGame(input[0], input[1], input[2], input[5] == 1, input[3], input[4]);
    } catch (IllegalArgumentException e) {
      errorMessage(e.getMessage());
    }
  }

  @Override
  public void handleClick(Features f) {
    MouseAdapter click = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        int x = e.getX();
        int y = e.getY();
        f.clickMove(x, y);
      }
    };
    mainPanel.addMouseListener(click);
  }


}
