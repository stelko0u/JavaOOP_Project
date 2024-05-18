package bg.tu_varna.f22621629.handlers;
import java.io.IOException;
/**
 * The CommandHandler interface represents a handler for executing commands.
 * Implementing classes are responsible for defining the logic of executing specific commands.
 */
public interface CommandHandler {

  /**
   * Executes the command with the given arguments.
   * @param args The arguments needed to execute the command.
   * @throws IOException if an I/O error occurs.
   * @throws FileExceptionHandler if there is an exception related to file handling.
   */
  void execute(String[] args) throws IOException, FileExceptionHandler, InterruptedException, Exception;
}
