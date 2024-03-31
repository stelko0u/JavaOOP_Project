package bg.tu_varna.f22621629.Commands;

import bg.tu_varna.f22621629.Handlers.CommandHandler;
import bg.tu_varna.f22621629.Handlers.FileExceptionHandler;
import bg.tu_varna.f22621629.Handlers.FileHandler;
import bg.tu_varna.f22621629.Handlers.XMLFileHandler;

import java.io.BufferedReader;
import java.io.FileReader;
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
      System.out.println("\nSuccessfully opened " + args[1] + " with " + fileHandler.getSessionCount() + " sessions!");
    } catch (FileExceptionHandler e) {
      System.out.println("Error opening the file: " + e.getMessage());
    }
  }
}

