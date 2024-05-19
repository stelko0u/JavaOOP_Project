package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.FileExceptionHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;

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
   * Executes the open command to open a file.
   *
   * @param args Command arguments.
   * @throws IOException If an I/O error occurs.
   */
  @Override
  public void execute(String[] args) throws IOException {
    if (args.length < 2) {
      System.out.println("Usage: open <file>");
      return;
    }
    if (fileHandler.isFileOpened()) {
      System.out.println("Already opened a file!");
      return;
    }
    try {
      fileHandler.open(args[1]);
      fileHandler.setFileOpened(true);
      System.out.println("\nSuccessfully opened " + args[1] + " with " + fileHandler.getSessions().size() + " sessions!");
      System.out.println("Load a session using -> session info");
    } catch (FileExceptionHandler e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
}
