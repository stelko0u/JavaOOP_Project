package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;
import bg.tu_varna.f22621629.models.*;
import java.io.IOException;

/**
 * The AddImageCommand class is a concrete implementation of the CommandHandler interface.
 * It handles the addition of an image to the current session in an XML file managed by XMLFileHandler.
 */
public class AddImageCommand implements CommandHandler {
  private XMLFileHandler fileHandler;
  /**
   * Constructs an AddImageCommand instance and initializes the XMLFileHandler.
   */
  public AddImageCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
  }
  /**
   * Executes the command to add an image to the current session.
   *
   * @param command The command containing the arguments.
   * @throws IOException if an I/O error occurs during execution.
   */
  @Override
  public void execute(Command command) throws IOException {
    if (command.getArguments().length != 1) {
      System.out.println("Usage: add <image>");
      return;
    }

    if (fileHandler.getFileName() == null) {
      System.out.println("Error: No file is currently open.");
      return;
    }

    int currentSessionNumber = fileHandler.getCurrentSessionNumber();
    String imageName = command.getArguments()[0];

    if (fileHandler.isFileInCurrentSession("images/" + imageName)) {
      System.out.println("Error: Image already exists in session " + currentSessionNumber + ".");
      return;
    }
    Image newImage = fileHandler.getLoadedImage();

    fileHandler.setNextLocalImageForSession(currentSessionNumber, newImage);
    System.out.println("Image added to session " + currentSessionNumber + " successfully.");
  }
}