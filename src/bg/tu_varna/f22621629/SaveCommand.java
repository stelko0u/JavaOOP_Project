package bg.tu_varna.f22621629;

import java.io.IOException;

public class SaveCommand implements CommandHandler{
  @Override
  public void execute(String[] command) throws IOException {
    System.out.println("Successfully saved the file!");
  }
}
