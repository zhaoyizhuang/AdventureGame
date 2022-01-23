package controller;

import constant.Constant;
import controller.command.Move;
import controller.command.PickTreasure;
import controller.command.PickWeapon;
import controller.command.Shoot;
import model.dungeon.DungeonGame;
import model.smell.Smell;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Implementation of the Console controller for the Dungeon Game. Handle the actions required by
 * users.
 */
public class DungeonConsoleController implements DungeonController {
  private final Appendable out;
  private final Scanner scan;
  private final DungeonGame model;

  /**
   * Constructor for the controller.
   *
   * @param in  the source to read from
   * @param out the target to print to
   * @param model Dungeon Game model.
   * @throws IllegalArgumentException if inputs are null.
   */
  public DungeonConsoleController(Readable in, Appendable out, DungeonGame model)
          throws IllegalArgumentException {
    if (in == null || out == null || model == null) {
      throw new IllegalArgumentException("Input can't be null");
    }

    this.out = out;
    this.model = model;
    scan = new Scanner(in);
  }

  @Override
  public void play() {
    Command command;
    boolean check = true;
    while (!model.isDead()) {
      try {
        if (check) {
          out.append("\n");
          Smell smell = model.getSmell();
          if (model.isMonsterDead()) {
            out.append("You see a dead Monster! Seems the arrow works.\n");
          }
          if (model.reachEnd()) {
            out.append("You successfully escape from the Dungeon!\n");
            return;
          }
          switch (smell) {
            case PUNGENT:
              out.append("You smell something terrible nearby\n");
              break;
            case FAINT:
              out.append("You smell a strange odor\n");
              break;
            default:
              break;
          }
          out.append("Select an action: M for move, S for shoot, P for pick, ");
          out.append("INFO for player's information and CAVE for cave information.\n");
        }
        check = true;
        String input = scan.next();
        switch (input) {
          case "M":
            while (true) {
              out.append("Please specify a Direction.\n");
              out.append("W for west, E for east, N for north, S for south, ");
              out.append("Q for reselect an action.\n");
              String next = scan.next();
              if (next.equals("Q")) {
                break;
              }
              try {
                command = new Move(next);
                command.play(model);
                out.append("You moved to ").append(next).append("\n");
                if (model.metMonster()) {
                  if (model.isDead()) {
                    out.append("Chomp, chomp, chomp, you are eaten by an Otyugh!\n");
                    out.append("Better luck next time\n");
                    return;
                  } else {
                    out.append("You meet an Otyugh! He jumps at you.");
                    if (model.reachEnd()) {
                      out.append("You avoid his attack and escape successfully from the dungeon.");
                      return;
                    } else {
                      out.append("You avoid his attack, and can choose a way to escape.\n");
                      continue;
                    }
                  }
                }
                break;
              } catch (IllegalArgumentException | IllegalStateException e) {
                out.append("Invalid Movement.\n");
                out.append(e.getMessage()).append(" Try Again.\n");
              }
            }
            break;
          case "S":
            while (true) {
              out.append("Please specify a Distance\n");
              out.append("To reselect an action, enter any number and then enter Q\n");
              String dis = scan.next();
              if (!dis.matches(Constant.INT_INPUT)) {
                out.append("Invalid Distance\n");
                continue;
              }
              out.append("Please specify a Direction.\n");
              out.append("W for west, E for east, N for north, S for south, ");
              out.append("Q for reselect an action.\n");
              String next = scan.next();
              if (next.equals("Q")) {
                break;
              }
              try {
                command = new Shoot(Integer.parseInt(dis), next);
                command.play(model);
                if (((Shoot) command).isShot()) {
                  out.append("You hear a great howl in the distance\n");
                } else {
                  out.append("You shoot an arrow into the darkness\n");
                }
                break;
              } catch (IllegalArgumentException | IllegalStateException e) {
                out.append("Invalid Shoot.\n");
                out.append(e.getMessage()).append(", Please Select Again.\n");
              }
            }
            break;
          case "P":
            while (true) {
              out.append("Pick weapon or treasure? W for weapon, T for treasure, ");
              out.append("Q for reselect an action.\n");
              String next = scan.next();
              if (next.equals("Q")) {
                break;
              }
              boolean invalid = false;
              try {
                switch (next) {
                  case "W":
                    command = new PickWeapon();
                    command.play(model);
                    out.append("You picked up all weapons you see.\n");
                    break;
                  case "T":
                    out.append("Specify the Treasure, D for Diamond, S for Sapphire, R for Ruby\n");
                    command = new PickTreasure(scan.next());
                    command.play(model);
                    out.append("Pick up successfully.\n");
                    break;
                  default:
                    out.append("Invalid Command\n");
                    invalid = true;
                }
                if (invalid) {
                  continue;
                }
                break;
              } catch (IllegalArgumentException | IllegalStateException e) {
                out.append("Invalid Pick Action.\n");
                out.append(e.getMessage()).append(", Please Select Again.\n");
              }
            }
            break;
          case "INFO":
            out.append(model.player()).append("\n");
            break;
          case "CAVE":
            out.append(model.cave()).append("\n");
            break;
          default:
            out.append("Invalid command. Please choose again\n");
            throw new IllegalArgumentException();
        }
      } catch (IOException ioe) {
        throw new IllegalStateException("Append failed\n", ioe);
      } catch (IllegalArgumentException | IllegalStateException e) {
        check = false;
      } catch (NoSuchElementException e) {
        return;
      }
    }
  }

}
