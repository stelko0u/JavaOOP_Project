package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;
import bg.tu_varna.f22621629.models.Command;

import java.io.IOException;

/**
 * The DisplaySessionInfoCommand class represents a command to display information about sessions.
 */
public class DisplaySessionInfoCommand implements CommandHandler {
  private final XMLFileHandler fileHandler;

  /**
   * Constructs a DisplaySessionInfoCommand object.
   */
  public DisplaySessionInfoCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
  }

  /**
   * Displays information about sessions.
   *
   * @param fileHandler The XMLFileHandler object for handling XML files.
   */
  private void displayInfo(XMLFileHandler fileHandler) {
    fileHandler.printSessions();
  }

  /**
   * Executes the display session info command.
   *
   * @throws IOException If an I/O error occurs.
   */
  @Override
  public void execute(Command command) throws IOException {
    displayInfo(fileHandler);
  }
}
