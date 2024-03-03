package bg.tu_varna.f22621629;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommandProcessor {
  private Map<String, CommandHandler> commands = new HashMap<>();

  public CommandProcessor() {
    commands.put("open", new OpenCommand(""));
    commands.put("close", new CloseCommand());
    commands.put("save", new SaveCommand());
    commands.put("saveas", new SaveAsCommand(""));
    commands.put("help", new HelpCommand());
    commands.put("exit", new ExitCommand());
  }

  public void proccessingCommands(String input) {
    String[] commandAsParts = input.split("\\s+", 2);
    String commandKey = commandAsParts[0].toLowerCase().trim();

    if (commands.containsKey(commandKey)) {
      CommandHandler command = commands.get(commandKey);

      if (command instanceof OpenCommand || command instanceof SaveAsCommand) {
        if (commandAsParts.length > 1) {
          try {
            command.execute(commandAsParts);
          } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
          }
        } else {
          System.out.println("Invalid command format!");
        }
      } else {
        try {
          command.execute(commandAsParts);

        } catch (IOException e) {
          System.err.println("Error: "+ e.getMessage());
        }
      }
    } else {
      System.out.println("Unknown command: " + commandKey);
    }
  }
}
