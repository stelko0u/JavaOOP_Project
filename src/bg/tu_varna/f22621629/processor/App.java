package bg.tu_varna.f22621629.processor;

import bg.tu_varna.f22621629.handlers.CommandsException;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;
import bg.tu_varna.f22621629.models.Command;

import java.util.Scanner;

/**
 * Main application class for the Raster graphics application.
 * This class contains the main loop that processes user commands.
 */

public class App {
  /**
   * Runs the main application loop.
   * Initializes necessary components and handles user input commands.
   *
   * @throws Exception if an unexpected error occurs during execution
   */
  public static void run() throws Exception {
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
        Command command;
      if (input.equalsIgnoreCase("session info") || input.equalsIgnoreCase("graphics help")) {
          command = new Command(input, null);
      } else {
        String[] commandParts = input.split("\\s+", 2);
        String commandName = commandParts[0];
        String[] commandArgs = commandParts.length > 1 ? commandParts[1].split("\\s+") : new String[0];
        command = new Command(commandName, commandArgs);
      }

      if (!fileHandler.isFileOpened() && !(command.getName().equalsIgnoreCase("open") || command.getName().equalsIgnoreCase("exit") || command.getName().equalsIgnoreCase("help"))) {
        System.out.println("No file opened. Please open a file first.");
        continue;
      }

      try {
        commandProcessor.processingCommands(command);
      } catch (CommandsException e) {
        System.out.println("An error has occurred! " + e.getMessage());
      }
    }
  }
}
