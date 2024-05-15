package bg.tu_varna.f22621629;

import bg.tu_varna.f22621629.commands.*;
import bg.tu_varna.f22621629.enums.CommandsTypes;
import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.CommandsException;
import bg.tu_varna.f22621629.handlers.FileExceptionHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;

import java.io.IOException;
import java.util.HashMap;



public class CommandProcessor {
  private HashMap<CommandsTypes, CommandHandler> commands = new HashMap<>();
  private XMLFileHandler fileHandler;

  public CommandProcessor(XMLFileHandler fileHandler) {
    this.fileHandler = XMLFileHandler.getInstance();
    commands.put(CommandsTypes.OPEN, new OpenCommand());
    commands.put(CommandsTypes.CLOSE, new CloseCommand());
    commands.put(CommandsTypes.SAVE, new SaveCommand());
    commands.put(CommandsTypes.SAVEAS, new SaveAsCommand());
    commands.put(CommandsTypes.HELP, new HelpCommand());
    commands.put(CommandsTypes.EXIT, new ExitCommand());
    commands.put(CommandsTypes.LOAD, new LoadCommand());
    commands.put(CommandsTypes.GRAPHICSHELP, new GraphicOperations());
    commands.put(CommandsTypes.SESSIONINFO, new DisplaySessionInfoCommand());
    commands.put(CommandsTypes.NEGATIVE, new NegativeCommand());
    commands.put(CommandsTypes.ROTATE, new RotateImage());
    commands.put(CommandsTypes.ADD, new AddImageCommand());
    commands.put(CommandsTypes.COLLAGE, new CollageCommand());
    commands.put(CommandsTypes.SWITCH, new SwitchCommand());
    commands.put(CommandsTypes.GRAYSCALE, new GrayScaleCommand());
    commands.put(CommandsTypes.MONOCHROME, new MonoChromeCommand());
    commands.put(CommandsTypes.UNDO, new UndoCommand());


  }

  public void proccessingCommands(String input) throws IOException, FileExceptionHandler, CommandsException {
    String[] commandAsParts = input.split("\\s+", 2);
    CommandsTypes commandKey;

    if (input.equalsIgnoreCase("session info") || input.equalsIgnoreCase("graphics help")) {
      commandKey = CommandsTypes.getByValue(input);
    } else {
      commandKey = CommandsTypes.getByValue(commandAsParts[0].toLowerCase());
    }

    if (commands.containsKey(commandKey) || commands.containsKey(input)) {
      if (fileHandler.isFileOpened() && commandKey == CommandsTypes.SESSIONINFO) {
        CommandHandler command = commands.get(commandKey);
        command.execute(commandAsParts);
        return;
      }

      if (fileHandler.isFileOpened() || commandKey == CommandsTypes.OPEN || commandKey == CommandsTypes.HELP || commandKey == CommandsTypes.EXIT) {
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
