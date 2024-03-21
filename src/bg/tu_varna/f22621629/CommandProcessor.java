package bg.tu_varna.f22621629;

import bg.tu_varna.f22621629.Commands.*;
import bg.tu_varna.f22621629.Handlers.CommandHandler;
import bg.tu_varna.f22621629.Handlers.XMLFileHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



public class CommandProcessor {
  private Map<String, CommandHandler> commands = new HashMap<>();
  private XMLFileHandler fileHandler;
  public CommandProcessor(XMLFileHandler fileHandler) {
    this.fileHandler = XMLFileHandler.getInstance();
    commands.put("open", new OpenCommand());
    commands.put("close", new CloseCommand());
    commands.put("save", new SaveCommand());
    commands.put("saveas", new SaveAsCommand(""));
    commands.put("help", new HelpCommand());
    commands.put("exit", new ExitCommand());
    commands.put("load", new LoadCommand());
  }


  public void proccessingCommands(String input) {
    String[] commandAsParts = input.split("\\s+", 2);
    String commandKey = commandAsParts[0].toLowerCase().trim();
    System.out.println(fileHandler.isFileOpened());

    if (commands.containsKey(commandKey)) {
      CommandHandler command = commands.get(commandKey);
      try {
        if (commandKey.equals("open") || fileHandler.isFileOpened()) {
          command.execute(commandAsParts);
        } else if (fileHandler == null) {
          System.out.println("File handler is not initialized.");
        } else if (!fileHandler.isFileOpened()) {
          System.out.println("No file opened. Please open a file first.");
        } else {
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
              System.err.println("Error: " + e.getMessage());
            }
          }
        }
      } catch (NullPointerException e) {
        System.out.println("NullPointerException caught: " + e.getMessage());
      } catch (IOException e) {
        System.out.println("Error: " + e.getMessage());
      }
    } else {
      System.out.println("Unknown command \""+commandKey+"\" ");
    }
  }
}
