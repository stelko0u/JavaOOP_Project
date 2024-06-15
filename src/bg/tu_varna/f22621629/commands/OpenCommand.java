package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.FileExceptionHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;
import bg.tu_varna.f22621629.models.Command;

import java.io.IOException;

/**
 * The OpenCommand class represents a command to open a file.
 */
public class OpenCommand implements CommandHandler {
  private final XMLFileHandler fileHandler;

  /**
   * Constructs an OpenCommand object.
   */
  public OpenCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
  }

  /**
   * Executes the command to open a file. If a file is already open, it notifies the user.
   *
   * @param command The command containing the file name to open.
   * @throws IOException if an I/O error occurs during the file opening process.
   */
  @Override
  public void execute(Command command) throws IOException {
    if (command.getArguments().length < 1) {
      System.out.println("Usage: open <file>");
      return;
    }
    if (fileHandler.isFileOpened()) {
      System.out.println("Already opened a file!");
      return;
    }
    try {
      fileHandler.open(command.getArguments()[0]);
      fileHandler.setFileOpened(true);
      System.out.println("\nSuccessfully opened " + command.getArguments()[0] + " with " + fileHandler.getSessions().size() + " sessions!");
      System.out.println("Load a session using -> session info");
    } catch (FileExceptionHandler e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
}
