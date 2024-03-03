package bg.tu_varna.f22621629;

import java.io.IOException;

public class HelpCommand implements CommandHandler{
  @Override
  public void execute(String[] command) throws IOException {
    StringBuilder menu = new StringBuilder();
    menu.append("The following commands are supported:\n");
    menu.append(" - open <file> opens <file>\n");
    menu.append(" - close\n");
    menu.append(" - save\n");
    menu.append(" - saveas <file>\n");
    menu.append(" - help\n");
    menu.append(" - exit\n");
    System.out.println(menu.toString());
  }
}
