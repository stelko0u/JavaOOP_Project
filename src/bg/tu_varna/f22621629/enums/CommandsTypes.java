/**
 * The CommandsTypes enum defines different types of commands supported by the application.
 */
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

  /**
   * Constructs a CommandsTypes enum with the specified command string.
   * @param command The command string.
   */
  CommandsTypes(String command) {
    this.command = command;
  }

  /**
   * Gets the command string associated with the enum value.
   * @return The command string.
   */
  public String getCommand() {
    return this.command;
  }

  /**
   * Gets the CommandsTypes enum value based on the provided command string.
   * @param value The command string.
   * @return The CommandsTypes enum value.
   * @throws CommandsException if the command string is not recognized.
   */
  public static CommandsTypes getByValue(String value) throws CommandsException {
    for (CommandsTypes command : CommandsTypes.values()) {
      if (command.getCommand().equals(value)) {
        return command;
      }
    }
    throw new CommandsException("Unknown command!");
  }
}
