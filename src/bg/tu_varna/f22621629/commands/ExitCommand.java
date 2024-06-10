package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.models.Command;

import java.io.IOException;
/**
 * The ExitCommand class implements the CommandHandler interface to handle the exit command.
 * It terminates the program execution when executed.
 */
public class ExitCommand implements CommandHandler {

  /**
   * Executes the command to exit the program.
   * @param command The command arguments (not used in this command).
   * @throws IOException if an I/O error occurs.
   */
  @Override
  public void execute(Command command) throws IOException, Exception  {
    Thread.sleep(600);
    animateText("Exiting the program...");
    System.exit(0);
  }


  /**
   * Animates the provided text by printing it character by character with a delay.
   * @param text The text to be animated.
   * @throws Exception if an interruption occurs during the animation.
   */
  private void animateText(String text) throws Exception {
    for (int i = 0; i < text.length(); i++) {
      System.out.print(text.charAt(i));
      Thread.sleep(100);
    }
    System.out.println();
  }
}
