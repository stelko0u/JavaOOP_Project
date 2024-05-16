/**
 * The AddImageCommand class implements the CommandHandler interface to handle the add image command.
 * It adds an image element to the current session in the XML file.
 */
package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;

import java.io.IOException;

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
   * @param args The command arguments containing the image name.
   * @throws IOException if an I/O error occurs.
   */
  @Override
  public void execute(String[] args) throws IOException {
    if (args.length < 2) {
      System.out.println("Usage: add <image>");
      return;
    }

    String currentFile = fileHandler.getFileName();
    if (currentFile == null) {
      System.out.println("Error: No file is currently open.");
      return;
    }

    int currentSessionNumber = fileHandler.getCurrentSessionNumber();

    String newImageElement = "        <image name=\"" + args[1] + "\">\n" +
            "        </image>\n";
    fileHandler.setNextLocalImageForSession(currentSessionNumber, newImageElement);
    System.out.println("Image added to session " + currentSessionNumber + " successfully.");
  }
}
