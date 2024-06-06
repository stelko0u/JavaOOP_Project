package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;
import bg.tu_varna.f22621629.models.Command;
import bg.tu_varna.f22621629.models.Image;

import java.io.IOException;

/**
 * The AddImageCommand class implements the CommandHandler interface to handle the add image command.
 * It adds an image element to the current session in the XML file.
 */
public class AddImageCommand implements CommandHandler {

  private XMLFileHandler fileHandler;

  /**
   * Constructs an AddImageCommand object and initializes the XMLFileHandler instance.
   */
  public AddImageCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
  }

  /**
   * Executes the command to add an image element to the current session in the XML file.
//   * @param args The command arguments containing the image name.
   * @throws IOException if an I/O error occurs.
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

    String newImageElement = "        <image name=\"" + command.getArguments()[0] + "\">\n" +
            "        </image>\n";
    Image newImage = new Image(newImageElement);
    newImage.setName(command.getArguments()[0]);
    fileHandler.setNextLocalImageForSession(currentSessionNumber, newImage);
    System.out.println("Image added to session " + currentSessionNumber + " successfully.");
  }
}
