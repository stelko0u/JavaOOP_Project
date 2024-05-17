package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;

import java.io.IOException;
/**
 * The GraphicOperations class implements the CommandHandler interface to handle graphic operations command.
 * It provides a menu of supported graphic operations commands.
 */
public class GraphicOperations implements CommandHandler {

  /**
   * Executes the command to display the menu of supported graphic operations commands.
   * @param command The command arguments (not used in this command).
   * @throws IOException if an I/O error occurs.
   */
  @Override
  public void execute(String[] command) throws IOException {
    StringBuilder menu = new StringBuilder();
    menu.append("The following commands are supported:\n");
    menu.append(" - grayscale\n");
    menu.append(" - monochrome\n");
    menu.append(" - negative\n");
    menu.append(" - rotate <direction>\n");
    menu.append(" - undo\n");
    menu.append(" - add <image>\n");
    menu.append(" - session info\n");
    menu.append(" - switch <session>\n");
    menu.append(" - collage <direction> <image1> <image2> <outimage>");
    System.out.println(menu.toString());
  }
}
