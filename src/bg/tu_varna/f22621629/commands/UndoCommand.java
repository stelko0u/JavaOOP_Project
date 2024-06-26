package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;
import bg.tu_varna.f22621629.models.Command;
import bg.tu_varna.f22621629.models.Session;
/**
 * The UndoCommand class implements the CommandHandler interface to handle the undo command.
 * It removes the last transformation from the current session.
 */
public class UndoCommand implements CommandHandler {

  private XMLFileHandler fileHandler;

  /**
   * Constructs an UndoCommand object and initializes the XMLFileHandler instance.
   */
  public UndoCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
  }

  /**
   * Executes the command to remove the last transformation from the current session.
   *
   * @param command The command object representing the undo command (not used in this command).
   */
  @Override
  public void execute(Command command) {
    Session currentSession = fileHandler.getCurrentSession();

    if (currentSession != null && currentSession.getTransformations() != null) {
      currentSession.setTransformations(null);
      System.out.println("Undo successful: Last transformation removed.");
    } else {
      System.out.println("No transformations to undo in the current session.");
    }
  }
}
