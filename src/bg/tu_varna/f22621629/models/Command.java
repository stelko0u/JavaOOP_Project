package bg.tu_varna.f22621629.models;
/**
 * The Command class represents a command to be executed by the application.
 */
public class Command {
  private String name;
  private String[] arguments;
  /**
   * Constructs a new Command object with the given name and arguments.
   *
   * @param name      the name of the command
   * @param arguments the arguments associated with the command
   */
  public Command(String name, String[] arguments) {
    this.name = name;
    this.arguments = arguments;
  }
  /**
   * Retrieves the name of the command.
   *
   * @return the name of the command
   */
  public String getName() {
    return name;
  }
  /**
   * Retrieves the arguments associated with the command.
   *
   * @return the arguments of the command
   */
  public String[] getArguments() {
    return arguments;
  }
}
