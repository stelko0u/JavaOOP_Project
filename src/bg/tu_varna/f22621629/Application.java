package bg.tu_varna.f22621629;

import bg.tu_varna.f22621629.handlers.CommandsException;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;
import bg.tu_varna.f22621629.processor.CommandProcessor;

import java.util.Scanner;

/**
 * The Application class represents the entry point of the Raster graphics application.
 */
public class Application {

  /**
   * The main method starts the application, initializes necessary components,
   * and processes user commands.
   * @param args Command-line arguments (not used).
   * @throws CommandsException if there is an exception related to command execution.
   */
  public static void main(String[] args) throws CommandsException {
    System.out.println("Welcome to Raster graphics application. Type \"help\" to show basic commands!");
    XMLFileHandler fileHandler = XMLFileHandler.getInstance();
    CommandProcessor commandProcessor = new CommandProcessor(fileHandler);
    Scanner scanner = new Scanner(System.in);

    while(true) {
      System.out.print("> ");
      String input = scanner.nextLine().trim();

      if (input.isEmpty()) {
        continue;
      }

      try {
        commandProcessor.processingCommands(input);
      } catch (Exception e) {
        System.out.println("An error has occurred!" + e.getMessage());
      }
    }
  }
}
