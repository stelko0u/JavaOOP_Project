  package bg.tu_varna.f22621629.processor;

  import bg.tu_varna.f22621629.commands.*;
  import bg.tu_varna.f22621629.enums.CommandsTypes;
  import bg.tu_varna.f22621629.handlers.CommandHandler;
  import bg.tu_varna.f22621629.handlers.XMLFileHandler;
  import bg.tu_varna.f22621629.models.Command;

  import java.util.HashMap;
  /**
   * Processes commands for the Raster graphics application.
   * Initializes and manages the available commands and their handlers.
   */
  public class CommandProcessor {

    private HashMap<CommandsTypes, CommandHandler> commands = new HashMap<>();
    private XMLFileHandler fileHandler;

    /**
     * Initializes the CommandProcessor with the given XMLFileHandler.
     * Sets up the available commands and their respective handlers.
     *
     * @param fileHandler the XMLFileHandler instance to be used by the command processor
     */
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

    /**
     * Processes the given command by determining its type and executing the corresponding handler.
     *
     * @param command the Command object containing the command name and arguments
     * @throws Exception if an error occurs during command processing
     */

    public void processingCommands(Command command) throws Exception {
      try {
        CommandsTypes commandType = CommandsTypes.getByValue(command.getName());
        CommandHandler handler = commands.get(commandType);
        if (handler != null) {
          handler.execute(command);
        } else {
          System.out.println("Unknown command: " + command.getName());
        }
    } catch (Exception e) {
//        System.out.println(e.getMessage());
        e.printStackTrace();
      }
  }
}