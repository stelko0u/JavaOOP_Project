package bg.tu_varna.f22621629;

import bg.tu_varna.f22621629.handlers.CommandsException;
import bg.tu_varna.f22621629.handlers.FileExceptionHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;

import java.io.IOException;
import java.util.Scanner;
/** Application **/
public class Application {
  public static void main(String[] args) {
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
        commandProcessor.proccessingCommands(input);
      } catch (IOException | FileExceptionHandler e) {
        throw new RuntimeException(e);
      } catch (CommandsException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
