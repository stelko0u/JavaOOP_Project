package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;

import java.io.IOException;

public class CloseCommand implements CommandHandler {
  private XMLFileHandler fileHandler;

  public CloseCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
  }

  @Override
  public void execute(String[] command) throws IOException {


    if (fileHandler != null) {
      fileHandler.setFileOpened(false);
      fileHandler.setContent(null);
      fileHandler.close();
      fileHandler.setFileName(null);

      System.out.println("Successfully closed the file!");
    } else {
      System.out.println("No file is currently open.");
    }

  }

}
