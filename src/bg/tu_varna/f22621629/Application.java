package bg.tu_varna.f22621629;

import java.util.Scanner;

public class Application {
  public static void main(String[] args) {
    CommandProcessor commandProcessor = new CommandProcessor();
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
