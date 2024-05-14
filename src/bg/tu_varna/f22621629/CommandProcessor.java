package bg.tu_varna.f22621629;

import bg.tu_varna.f22621629.Commands.*;
import bg.tu_varna.f22621629.Handlers.CommandHandler;
import bg.tu_varna.f22621629.Handlers.FileExceptionHandler;
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
    commands.put("saveas", new SaveAsCommand());
    commands.put("help", new HelpCommand());
    commands.put("exit", new ExitCommand());
    commands.put("load", new LoadCommand());
    commands.put("graphics", new GraphicOperations());
    commands.put("session info", new DisplaySessionInfoCommand());
    commands.put("negative", new NegativeCommand());
    commands.put("rotate", new RotateImage());
    commands.put("add", new AddImageCommand());
    commands.put("collage", new CollageCommand());
    commands.put("switch", new SwitchCommand());

  }

public void proccessingCommands(String input) throws IOException, FileExceptionHandler {
  String[] commandAsParts = input.split("\\s+", 2);
  String commandKey = commandAsParts[0].toLowerCase().trim();

  if (commands.containsKey(commandKey) || commands.containsKey(input)) {


    if (fileHandler.isFileOpened() && input.equals("session info")) {
      CommandHandler command = commands.get(input);
      command.execute(new String[]{input});
      return;
    }


    if (fileHandler.isFileOpened() || commandKey.equals("open") || commandKey.equals("help")) {
      CommandHandler command = commands.get(commandKey);
      command.execute(commandAsParts);
    } else {
      System.out.println("No file opened. Please open a file first.");
    }


  } else {
    System.out.println("Unknown command!");
  }
}
}
