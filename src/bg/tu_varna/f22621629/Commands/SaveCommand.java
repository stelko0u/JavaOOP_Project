package bg.tu_varna.f22621629.Commands;

import bg.tu_varna.f22621629.Handlers.CommandHandler;
import bg.tu_varna.f22621629.Handlers.XMLFileHandler;

import java.io.*;

public class SaveCommand implements CommandHandler {
  private XMLFileHandler fileHandler;

  public SaveCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
  }

  @Override
  public void execute(String[] command) throws IOException {
    String currentFile = fileHandler.getFileName();
    if (currentFile == null) {
      System.out.println("Error: No file is currently open.");
      return;
    }

    if (fileHandler.getContent() == null) {
      System.out.println("Error: No content to save.");
      return;
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(currentFile))) {
      StringBuilder content = new StringBuilder();
      String line;
      boolean sessionFound = false;

      while ((line = reader.readLine()) != null) {
        if (line.trim().equals("</sessions>")) {
          if (!sessionFound) {
            content.append(fileHandler.getNextLocalImage());
          }
          content.append(line).append("\n");
          sessionFound = true;
        } else {
          content.append(line).append("\n");
        }
      }

      if (!sessionFound) {
        content.append(fileHandler.getNextLocalImage());
      }

      try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile))) {
        writer.write(content.toString());
      }



    } catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println("Changes successfully saved to the file.");
  }
}
