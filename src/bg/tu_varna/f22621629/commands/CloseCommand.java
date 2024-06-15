package bg.tu_varna.f22621629.commands;
import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;
import bg.tu_varna.f22621629.models.Command;

import java.io.IOException;
/**
 * The CloseCommand class implements the CommandHandler interface to handle the closing of a file.
 * It interacts with an XMLFileHandler instance to close the file and reset file-related attributes.
 */
public class CloseCommand implements CommandHandler {
  private XMLFileHandler fileHandler;

  /**
   * Constructs a CloseCommand object and initializes the XMLFileHandler instance.
   */
  public CloseCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
  }
  /**
   * Executes the close operation on the file.
   * @param command The command to execute.
   * @throws IOException if an I/O error occurs.
   */
  @Override
  public void execute(Command command) throws IOException {
    if (fileHandler != null) {
      fileHandler.setFileOpened(false);
      fileHandler.close();
      fileHandler.setFileName(null);
      System.out.println("Successfully closed the file!");
    } else {
      System.out.println("No file is currently open.");
    }
  }
}
