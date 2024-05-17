package bg.tu_varna.f22621629.enums;

import bg.tu_varna.f22621629.handlers.CommandsException;
/**
 * The CommandsTypes enum defines different types of commands supported by the application.
 */
public enum CommandsTypes {
  /** Open command. */
  OPEN("open"),

  /** Close command. */
  CLOSE("close"),

  /** Save command. */
  SAVE("save"),

  /** SaveAs command. */
  SAVEAS("saveas"),

  /** Help command. */
  HELP("help"),

  /** Exit command. */
  EXIT("exit"),

  /** Load command. */
  LOAD("load"),

  /** Grayscale command. */
  GRAYSCALE("grayscale"),

  /** Monochrome command. */
  MONOCHROME("monochrome"),

  /** Negative command. */
  NEGATIVE("negative"),

  /** Rotate command. */
  ROTATE("rotate"),

  /** Undo command. */
  UNDO("undo"),

  /** Add command. */
  ADD("add"),

  /** Session Info command. */
  SESSIONINFO("session info"),

  /** Switch command. */
  SWITCH("switch"),

  /** Collage command. */
  COLLAGE("collage"),

  /** Graphics Help command. */
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
