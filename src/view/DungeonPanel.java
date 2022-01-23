package view;

import constant.Constant;
import model.dungeon.ReadOnlyDungeonGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


/**
 * The Dungeon Game user interface. This panel will show the Dungeon, include cave, treasure,
 * player and monster to the user. Serves as the main UI for the game.
 * This class is package-private because it is only used within the view package and the
 * implementation is hidden.
 */
class DungeonPanel extends JPanel {
  private final ReadOnlyDungeonGame model;

  /**
   * Constructor for the DungeonPanel.
   * @param model the read only model.
   */
  public DungeonPanel(ReadOnlyDungeonGame model) {
    if (model == null) {
      throw new IllegalArgumentException("null model");
    }
    this.model = model;
    setBackground(Color.BLACK);
    int[][][] dungeon = model.dungeonInfo();
    setPreferredSize(new Dimension(dungeon.length * 173, dungeon[0].length * 173));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D graph = (Graphics2D) g;

    List<List<Integer>> visited = model.visited();
    int[][][] dungeon = model.dungeonInfo();
    int[] loc = model.currLoc();
    // 0 - north, 1 - south, 2 - east, 3 - west, (1 -true, 0 - false)
    // 4 - diamond, 5 - ruby, 6 - sapphire
    // 7 - weapon, 8 - monster
    for (List<Integer> li : visited) {
      int x = li.get(0);
      int y = li.get(1);
      int[] arr = dungeon[x][y];
      StringBuilder cavePath = new StringBuilder();
      cavePath.append(arr[0] == 1 ? "N" : "").append(arr[2] == 1 ? "E" : "")
              .append(arr[1] == 1 ? "S" : "").append(arr[3] == 1 ? "W" : "");

      try {
        BufferedImage img = ImageIO.read(
                Objects.requireNonNull(
                        getClass().getClassLoader()
                                .getResource(Constant.IMAGE_PATH + cavePath + ".png")));
        if (loc[0] == x && loc[1] == y) {
          img = overlay(img, Constant.PLAYER, Constant.PLAYER_OFFSET[0], Constant.PLAYER_OFFSET[1]);
        }
        if (arr[4] > 0) {
          img = overlay(img, Constant.DIAMOND, Constant.DIAMOND_OFFSET[0],
                  Constant.DIAMOND_OFFSET[1]);
        }
        if (arr[5] > 0) {
          img = overlay(img, Constant.RUBY, Constant.RUBY_OFFSET, Constant.RUBY_OFFSET);
        }
        if (arr[6] > 0) {
          img = overlay(img, Constant.SAPPHIRE,
                  Constant.SAPPHIRE_OFFSET[0], Constant.SAPPHIRE_OFFSET[1]);
        }
        if (arr[8] > 0) {
          img = overlay(img, Constant.OTYUGH, Constant.OTYUGH_OFFSET[0], Constant.OTYUGH_OFFSET[1]);
        }
        if (arr[7] > 0) {
          String cavepath = cavePath.toString();
          if (cavepath.length() >= 3 || cavepath.length() == 1) {
            img = overlay(img, Constant.ARROW, Constant.ARROW_OFFSET[0], Constant.ARROW_OFFSET[1]);
          } else {
            if (cavepath.contains("E")) {
              img = overlay(img, Constant.ARROW,
                      Constant.ARROW_OFFSET_E[0], Constant.ARROW_OFFSET_E[1]);
            } else if (cavepath.contains("W")) {
              img = overlay(img, Constant.ARROW,
                      Constant.ARROW_OFFSET_W[0], Constant.ARROW_OFFSET_W[1]);
            } else {
              img = overlay(img, Constant.ARROW_UP,
                      Constant.ARROW_OFFSET_NS[0], Constant.ARROW_OFFSET_NS[1]);
            }
          }
        }

        g.drawImage(img, y * 173, x * 173, null);

      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }

  //Image overlay code from class
  private BufferedImage overlay(BufferedImage starting, String fpath, int offset, int offsety)
          throws IOException {
    BufferedImage overlay = ImageIO.read(Objects.requireNonNull(getClass()
            .getClassLoader().getResource(fpath)));
    int w = Math.max(starting.getWidth(), overlay.getWidth());
    int h = Math.max(starting.getHeight(), overlay.getHeight());
    BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics g = combined.getGraphics();
    g.drawImage(starting, 0, 0, null);
    g.drawImage(overlay, offset, offsety, null);
    return combined;
  }

}
