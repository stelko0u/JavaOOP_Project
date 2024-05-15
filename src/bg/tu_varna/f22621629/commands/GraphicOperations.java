package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;

import java.io.IOException;

public class GraphicOperations implements CommandHandler {
  @Override
  public void execute(String[] command) throws IOException {
    StringBuilder menu = new StringBuilder();
    menu.append("The following commands are supported:\n");
    menu.append(" - grayscale\n");
    menu.append(" - monochrome\n");
    menu.append(" - negative\n");
    menu.append(" - rotate <direction>\n");
    menu.append(" - undo\n");
    menu.append(" - add <image>\n");
    menu.append(" - session info\n");
    menu.append(" - switch <session>\n");
    menu.append(" - collage <direction> <image1> <image2> <outimage>");
    System.out.println(menu.toString());
  }
}
