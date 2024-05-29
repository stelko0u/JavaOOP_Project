package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.models.Command;

import java.io.IOException;
/**
 * The HelpCommand class implements the CommandHandler interface to display available commands and their descriptions.
 */
public class HelpCommand implements CommandHandler {

  /**
   * Executes the command to display available commands and their descriptions.
   * @param command The command arguments (not used in this command).
   * @throws IOException if an I/O error occurs.
   */
  @Override
  public void execute(Command command) throws IOException {
    StringBuilder menu = new StringBuilder();
    menu.append("The following commands are supported:\n");
    menu.append(" - open <file>\n");
    menu.append(" - close\n");
    menu.append(" - save\n");
    menu.append(" - saveas <file>\n");
    menu.append(" - help\n");
    menu.append(" - exit\n");
    System.out.println(menu.toString());
  }
}
