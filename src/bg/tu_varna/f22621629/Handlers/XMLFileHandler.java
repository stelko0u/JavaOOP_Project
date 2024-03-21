package bg.tu_varna.f22621629.Handlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class XMLFileHandler implements FileHandler{
  private static XMLFileHandler instance;
  private String content;
  private boolean isFileOpened = false;
  private String filePath;

  private XMLFileHandler() {
    isFileOpened = false;
    content = "";
  }

  public void setContent(String content) {
    this.content = content;
  }

  public static XMLFileHandler getInstance() {
    if(instance == null) {
      instance = new XMLFileHandler();
;    }
    return instance;
  }

  public void setFileOpened(boolean fileOpened) {
    isFileOpened = fileOpened;
  }

  public XMLFileHandler(String filePath ) {
    this.filePath  = filePath;
  }

  public void open(String filePath) throws FileExceptionHandler {
    File file = new File(filePath);
    if (!file.exists()) {
      try {
          if(!file.createNewFile()) {
            throw new FileExceptionHandler("Failed to create a new file!");
          }
      } catch (IOException e) {
        throw new FileExceptionHandler("Error creating a new file.", e);
      }
    }
    try {
      BufferedReader reader = new BufferedReader(new FileReader(filePath));
      StringBuilder sb = new StringBuilder();
      String line;

      while ((line = reader.readLine()) != null) {
        sb.append(line).append("\n");
      }
      setContent(sb.toString());
      System.out.println(content);
      this.isFileOpened = true;
      reader.close();
    } catch(IOException e) {
      throw new FileExceptionHandler("Error reading the file!");
    }
  }

  public String getContent() {
    return content;
  }

  public boolean isFileOpened() {
    return isFileOpened;
  }
}
