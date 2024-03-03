package bg.tu_varna.f22621629;

import java.io.IOException;

public class SaveAsCommand implements CommandHandler{
  private String filePath;

  public SaveAsCommand(String filePath) {
    this.filePath = filePath;
  }

  @Override
  public void execute(String[] command) throws IOException {
    System.out.println("Successfully saved the file!");
  }
}
