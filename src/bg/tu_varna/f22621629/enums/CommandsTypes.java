package bg.tu_varna.f22621629.enums;

import bg.tu_varna.f22621629.handlers.CommandsException;

public enum CommandsTypes {
  OPEN("open"),
  CLOSE("close"),
  SAVE("save"),
  SAVEAS("saveas"),
  HELP("help"),
  EXIT("exit"),
  LOAD("load"),
  GRAYSCALE("grayscale"),
  MONOCHROME("monochrome"),
  NEGATIVE("negative"),
  ROTATE("rotate"),
  UNDO("undo"),
  ADD("add"),
  SESSIONINFO("session info"),
  SWITCH("switch"),
  COLLAGE("collage"),
  GRAPHICSHELP("graphics help");



  private final String command;

  CommandsTypes(String command) {
    this.command = command;
  }

  public String getCommand() {
    return this.command;
  }

  public static CommandsTypes getByValue(String value) throws CommandsException {
    for (CommandsTypes command : CommandsTypes.values()) {
      if (command.getCommand().equals(value)) {
        return command;
      }
    }
    throw new CommandsException();
  }
}