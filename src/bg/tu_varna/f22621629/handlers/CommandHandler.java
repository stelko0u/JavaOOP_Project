package bg.tu_varna.f22621629.handlers;
import bg.tu_varna.f22621629.models.Command;

import java.io.IOException;
/**
 * The CommandHandler interface represents a handler for executing commands.
 * Implementing classes are responsible for defining the logic of executing specific commands.
 */
public interface CommandHandler {

  /**
   * Executes the command with the given arguments.
//   * @param command The arguments needed to execute the command.
   * @throws IOException if an I/O error occurs.
   * @throws FileExceptionHandler if there is an exception related to file handling.
   * @throws InterruptedException if the thread executing the command is interrupted.
   * @throws Exception if any other exception occurs during command execution.
   */
  void execute(Command command) throws IOException, FileExceptionHandler, InterruptedException, Exception;
}
