package bg.tu_varna.f22621629;

import java.io.IOException;

public class OpenCommand implements CommandHandler{
  private String filePath;

  public OpenCommand(String filePath) {
    this.filePath = filePath;
  }

  @Override
  public void execute(String[] command) throws IOException {
    //
    System.out.println("Successfully opened " + this.filePath);
  }
}
