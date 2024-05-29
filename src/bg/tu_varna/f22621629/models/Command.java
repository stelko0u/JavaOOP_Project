package bg.tu_varna.f22621629.models;

public class Command {
  private String name;
  private String[] arguments;

  public Command(String name, String[] arguments) {
    this.name = name;
    this.arguments = arguments;
  }

  public String getName() {
    return name;
  }

  public String[] getArguments() {
    return arguments;
  }
}
