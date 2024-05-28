package bg.tu_varna.f22621629.processor;

import bg.tu_varna.f22621629.handlers.XMLFileHandler;

import java.util.Scanner;

/**
 * The {@code ApplicationProcessor} class is responsible for running the main loop of the raster graphics application.
 * It interacts with the user through the command line, processes commands, and handles exceptions.
 */

public class ApplicationProcessor {
  /**
   * Starts the main loop of the application.
   * It prints a welcome message, initializes the necessary handlers, and processes user commands.
   */
  public void run() {
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
        System.out.println("An error has occurred! " + e.getMessage());
      }
    }
  }
}
