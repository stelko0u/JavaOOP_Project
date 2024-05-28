package bg.tu_varna.f22621629;

import bg.tu_varna.f22621629.handlers.CommandsException;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;
import bg.tu_varna.f22621629.processor.ApplicationProcessor;
import bg.tu_varna.f22621629.processor.CommandProcessor;

import java.util.Scanner;
/**
 * The {@code Application} class is the entry point of the raster graphics application.
 * It initializes and starts the application by invoking the {@code run} method of {@code ApplicationProcessor}.
 */

public class Application {
  /**
   * The main method that serves as the entry point of the application.
   * It creates an instance of {@code ApplicationProcessor} and starts the application.
   *
   * @param args command-line arguments (not used).
   * @throws CommandsException if an error occurs while processing commands.
   */

  public static void main(String[] args) throws CommandsException {
    ApplicationProcessor appProcessor = new ApplicationProcessor();
    appProcessor.run();
  }
}
