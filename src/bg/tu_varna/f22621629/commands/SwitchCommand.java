package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.FileExceptionHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;
import bg.tu_varna.f22621629.models.Session;

import java.io.IOException;
import java.util.Set;

public class SwitchCommand implements CommandHandler {
  private final XMLFileHandler fileHandler;
  public SwitchCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
  }

  @Override
  public void execute(String[] args) throws IOException, FileExceptionHandler {
    if (args.length < 2) {
      System.out.println("Usage: switch <session>");
      return;
    };
    String sessionId = args[1];



    Set<Session> sessions = fileHandler.getSessions();

    boolean sessionFound = false;
    fileHandler.setSessionLoaded(false);
    for (Session session : sessions) {
      if (Integer.toString(session.getId()).equals(sessionId)) {
        sessionFound = true;
        fileHandler.setCurrentSessionNumber(Integer.parseInt(sessionId));
        fileHandler.setCurrentSession(session);
        fileHandler.setSessionLoaded(true);
        System.out.println("Switched to session with ID: " + sessionId);
        break;
      }
    }

    if (!sessionFound) {
      System.out.println("Session with ID " + sessionId + " not found.");
    }
  }
}
