package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;

import java.io.IOException;

public class DisplaySessionInfoCommand implements CommandHandler {
  private XMLFileHandler fileHandler;

  public DisplaySessionInfoCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
  }

  public void displayInfo(XMLFileHandler fileHandler) {
    fileHandler.printSessions();
  }

  @Override
  public void execute(String[] args) throws IOException {
    displayInfo(fileHandler);
  }
}
