package view;

import constant.Constant;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


/**
 * this panel represents the interface of game status. When the user escape successfully,
 * this panel will show up to inform user that the game wins. When the user is eaten by
 * the monster, the interface will inform that the user loses.
 * This class is package-private since it is only used within the package.
 */
class StatusPanel extends JPanel {
  private final boolean status;

  public StatusPanel(boolean status) {
    this.status = status;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    setBackground(Color.BLACK);
    try {
      String path = status ? "win.png" : "lose.png";
      BufferedImage img = ImageIO.read(
              Objects.requireNonNull(
                      getClass().getClassLoader().getResource(Constant.IMAGE_PATH + path)));
      g.drawImage(img, 0, 0, null);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
