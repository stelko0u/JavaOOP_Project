package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;
import bg.tu_varna.f22621629.models.Command;
import bg.tu_varna.f22621629.models.Image;

import java.io.IOException;

/**
 * The AddImageCommand class is responsible for handling the addition of images
 * to the current session in the XML file.
 * This class implements the CommandHandler interface.
 */
public class AddImageCommand implements CommandHandler {

  private XMLFileHandler fileHandler;

  /**
   * Constructs an AddImageCommand object and initializes the file handler.
   */
  public AddImageCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
  }

  /**
   * Executes the command to add an image to the current session.
   *
   * @param command the command to be executed
   * @throws IOException if an I/O error occurs
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

    String newImageElement = "        <image name=\"" + imageName + "\">\n" +
            "        </image>\n";
    Image newImage = new Image(newImageElement);
    newImage.setName(imageName);
    fileHandler.setNextLocalImageForSession(currentSessionNumber, newImage);
    System.out.println("Image added to session " + currentSessionNumber + " successfully.");
  }
}
