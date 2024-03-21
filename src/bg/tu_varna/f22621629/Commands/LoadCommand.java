package bg.tu_varna.f22621629.Commands;

import bg.tu_varna.f22621629.Handlers.CommandHandler;
import bg.tu_varna.f22621629.Handlers.XMLFileHandler;

import java.io.IOException;

public class LoadCommand implements CommandHandler {
    private XMLFileHandler fileHandler;

  public LoadCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
  }

  @Override
  public void execute(String[] args) throws IOException {
    System.out.println(XMLFileHandler.getInstance().getContent());
  }
}
