/**
 * The ExitCommand class implements the CommandHandler interface to handle the exit command.
 * It terminates the program execution when executed.
 */
package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;

import java.io.IOException;

public class ExitCommand implements CommandHandler {

  /**
   * Executes the command to exit the program.
   * @param command The command arguments (not used in this command).
   * @throws IOException if an I/O error occurs.
   */
  @Override
  public void execute(String[] command) throws IOException {
    System.out.println("Exiting the program...");
    System.exit(0);
  }
}
