package model.smell;

/**
 * The smell in the cave. a less pungent smell can be detected when there is a single
 * Otyugh 2 positions from the player's current location
 * detecting a more pungent smell either means that there is a single Otyugh 1 position
 * from the player's current location or that there are multiple Otyughs within 2 positions
 * from the player's current location
 */
public enum Smell {
  NONE, FAINT, PUNGENT
}
