package bg.tu_varna.f22621629.processor;

import bg.tu_varna.f22621629.handlers.XMLFileHandler;
import bg.tu_varna.f22621629.models.Command;

import java.util.Scanner;

/**
 * The {@code ApplicationProcessor} class is responsible for running the main loop of the raster graphics application.
 * It interacts with the user through the command line, processes commands, and handles exceptions.
 */

public class App {
  /**
   * Starts the main loop of the application.
   * It prints a welcome message, initializes the necessary handlers, and processes user commands.
   */
  public static void run() {
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
      } catch (Exception e) {
        System.out.println("An error has occurred! " + e.getMessage());
      }
    }
  }
}
