package bg.tu_varna.f22621629.Commands;

import bg.tu_varna.f22621629.Handlers.CommandHandler;
import bg.tu_varna.f22621629.Handlers.FileHandler;
import bg.tu_varna.f22621629.Handlers.XMLFileHandler;

import java.io.IOException;

public class CloseCommand implements CommandHandler {
  private XMLFileHandler fileHandler;

  public CloseCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
  }

  @Override
  public void execute(String[] command) throws IOException {
    System.out.println("Successfully closed the file!");
    fileHandler.setFileOpened(false);
    fileHandler.setContent("");
    System.out.println();
  }
}
