package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;

import java.io.IOException;

public class ExitCommand implements CommandHandler {
  @Override
  public void execute(String[] command) throws IOException {
    System.out.println("Exiting the program...");
    System.exit(0);
  }
}
