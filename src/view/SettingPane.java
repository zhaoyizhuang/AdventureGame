package view;

import constant.Constant;

import java.awt.GridLayout;
import java.awt.Label;
import java.util.Objects;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * This class represents the panel for user to specify the properties for the game.
 * This class is package-private since it is only used within the package.
 */
class SettingPane extends JPanel {
  // 0 - row, 1 - col, 2 - inter, 3 - poss, 4 - num
  private final JTextField[] properties;
  private final JComboBox<String> wrap;

  /**
   * Constructor for this pane.
   */
  public SettingPane() {
    properties = new JTextField[5];
    String[] text = new String[]{"Row", "Column", "Interconnectivity", "Treasure Percentage(%)",
                                 "Number of Monsters", "Wrapping?"};
    wrap = new JComboBox<>(Constant.WRAP_LIST);
    setLayout(new GridLayout(2, 3));
    for (int i = 0; i < 5; i++) {
      properties[i] = new JTextField(5);
      add(new Label(text[i]));
      add(properties[i]);
    }
    add(new Label("Wrapping?"));
    add(wrap);
  }

  /**
   * Getting values from the input.
   * @return array of int which represents the input.
   * @throws IllegalArgumentException if invalid input.
   */
  public int[] getInput() throws IllegalArgumentException {
    int[] res = new int[6];
    for (int i = 0; i < properties.length; i++) {
      String input = properties[i].getText();
      if (input == null || input.isEmpty()) {
        throw new IllegalArgumentException("Please specify all properties");
      }
      if (!input.matches(Constant.INT_INPUT)) {
        throw new IllegalArgumentException("Please enter Integer for properties");
      }
      res[i] = Integer.parseInt(input);
    }

    res[5] = Objects.equals(wrap.getSelectedItem(), "true") ? 1 : 0;
    return res;
  }



}
