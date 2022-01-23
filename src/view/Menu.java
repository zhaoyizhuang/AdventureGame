package view;

import constant.Constant;
import controller.Features;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
 * The menu user interface for the dungeon game. User will initialize the
 * dungeon game through this panel.
 * This class is package-private because it is only used on within the package.
 */
class Menu extends JPanel {

  public JMenuBar createMenu(Features f) {
    if (f == null) {
      throw new IllegalArgumentException("null controller");
    }
    JMenuBar menuBar = new JMenuBar();
    JMenu menu;
    JMenuItem menuItem;

    menu = new JMenu("Menu");
    menuBar.add(menu);

    menuItem = new JMenuItem("Tutorial");
    menuItem.addActionListener(l ->
            JOptionPane.showMessageDialog(null, Constant.TUTORIAL, "Tutorial", -1));
    menu.add(menuItem);

    menu.addSeparator();
    menuItem = new JMenuItem("Player Info");
    menuItem.addActionListener(l -> f.playerInfo());
    menu.add(menuItem);
    menuItem = new JMenuItem("Cave Info");
    menuItem.addActionListener(l -> f.caveInfo());
    menu.add(menuItem);
    menuItem = new JMenuItem("Current Settings");
    menuItem.addActionListener(l -> f.getSettings());
    menu.add(menuItem);

    menu.addSeparator();
    menuItem = new JMenuItem("New Game");
    menuItem.addActionListener(l -> f.newGameTrigger());
    menu.add(menuItem);
    menuItem = new JMenuItem("Restart");
    menuItem.addActionListener(l -> f.restart());
    menu.add(menuItem);

    menu.addSeparator();
    menuItem = new JMenuItem("Quit");
    menuItem.addActionListener(l -> f.exitProgram());
    menu.add(menuItem);

    return menuBar;
  }
}
