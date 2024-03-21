package bg.tu_varna.f22621629;

import bg.tu_varna.f22621629.Handlers.XMLFileHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Application {
  public static void main(String[] args) {
    XMLFileHandler fileHandler = new XMLFileHandler("");
    CommandProcessor commandProcessor = new CommandProcessor(fileHandler);
    Scanner scanner = new Scanner(System.in);

    while(true) {
      System.out.print("> ");
      String input = scanner.nextLine().trim();

      if (input.isEmpty()) {
        continue;
      }

      commandProcessor.proccessingCommands(input);
    }
  }
}
