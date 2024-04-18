package bg.tu_varna.f22621629.Commands;

import bg.tu_varna.f22621629.Handlers.CommandHandler;
import bg.tu_varna.f22621629.Handlers.XMLFileHandler;
import bg.tu_varna.f22621629.Models.Session;

import java.io.IOException;
import java.util.Set;

public class LoadCommand implements CommandHandler {
    private XMLFileHandler fileHandler;

  public LoadCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
  }

  @Override
  public void execute(String[] args) throws IOException {
    Set<Session> sessions = fileHandler.getSessions();
    String userInputFilename = args[1];
    boolean found = false;

    for (Session session : sessions) {
      if(userInputFilename.equals(session.getFileName())){
        found = true;
        break;
      }
    }
    if (found) {
      System.out.println("File found!");
    } else {
      System.out.println("File not found!");
    }
  }
}
