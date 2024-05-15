package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;
import bg.tu_varna.f22621629.models.Session;

public class UndoCommand implements CommandHandler {
  private XMLFileHandler fileHandler;

  public UndoCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
  }

  @Override
  public void execute(String[] args) {
    Session currentSession = fileHandler.getCurrentSession();

    if (currentSession != null && currentSession.getTransformations() != null) {
      currentSession.setTransformations(null);
      System.out.println("Undo successful: Last transformation removed.");
    } else {
      System.out.println("No transformations to undo in the current session.");
    }
  }
}
