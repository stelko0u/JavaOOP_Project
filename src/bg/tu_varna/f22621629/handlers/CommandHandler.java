package bg.tu_varna.f22621629.handlers;
import bg.tu_varna.f22621629.models.Command;

import java.io.IOException;
/**
 * The CommandHandler interface defines the contract for classes that handle commands in the application.
 */
public interface CommandHandler {
  /**
   * Executes a command based on the provided Command object.
   *
   * @param command the Command object representing the command to be executed
   * @throws IOException          if an I/O error occurs during command execution
   * @throws FileExceptionHandler if a file-related exception occurs during command execution
   * @throws InterruptedException if the execution is interrupted
   * @throws Exception            if an error occurs during command execution
   */

  void execute(Command command) throws IOException, FileExceptionHandler, InterruptedException, Exception;
}
