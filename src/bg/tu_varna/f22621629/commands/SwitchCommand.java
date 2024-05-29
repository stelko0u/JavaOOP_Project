package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.FileExceptionHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;
import bg.tu_varna.f22621629.models.Command;
import bg.tu_varna.f22621629.models.Session;

import java.io.IOException;
import java.util.Set;
/**
 * The SwitchCommand class implements the CommandHandler interface to handle the switch command.
 * It switches to the specified session by ID.
 */
public class SwitchCommand implements CommandHandler {

  private final XMLFileHandler fileHandler;

  /**
   * Constructs a SwitchCommand object and initializes the XMLFileHandler instance.
   */
  public SwitchCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
  }

  /**
   * Executes the command to switch to the specified session by ID.
//   * @param args The command arguments containing the session ID to switch to.
   * @throws IOException if an I/O error occurs.
   * @throws FileExceptionHandler if an error related to file handling occurs.
   */
  @Override
  public void execute(Command command) throws IOException, FileExceptionHandler {
    if (command.getArguments().length != 1) {
      System.out.println("Usage: switch <session>");
      return;
    };
    String sessionId = command.getArguments()[0];

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
