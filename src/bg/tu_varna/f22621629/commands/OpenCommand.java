package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.FileExceptionHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;

import java.io.IOException;

public class OpenCommand implements CommandHandler {
  private XMLFileHandler fileHandler;

  public OpenCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
  }

  @Override
  public void execute(String[] args) throws IOException {
    if (args.length < 2) {
      System.out.println("Usage: open <file>");
      return;
    }
    try {
      fileHandler.open(args[1]);
      fileHandler.setFileOpened(true);
      System.out.println("\nSuccessfully opened " + args[1] + " with " + fileHandler.getSessions().size() + " sessions!");
      System.out.println("Load a session using -> load <File name>");
    } catch (FileExceptionHandler e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
}

