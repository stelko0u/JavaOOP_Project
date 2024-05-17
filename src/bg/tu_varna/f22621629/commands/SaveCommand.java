package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.FileExceptionHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;

import java.io.*;
/**
 * The SaveCommand class implements the CommandHandler interface to handle the save command.
 * It saves the current content to the opened file.
 */
public class SaveCommand implements CommandHandler {

  private XMLFileHandler fileHandler;

  /**
   * Constructs a SaveCommand object and initializes the XMLFileHandler instance.
   */
  public SaveCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
  }

  /**
   * Executes the command to save the current content to the opened file.
   * @param command The command arguments (not used in this command).
   * @throws IOException if an I/O error occurs.
   * @throws FileExceptionHandler if an error related to file handling occurs.
   */
  @Override
  public void execute(String[] command) throws IOException, FileExceptionHandler {
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
        if (line.trim().equals("</session>")) {
          content.append(line).append("\n");
        } else {
          content.append(line).append("\n");
          if (line.contains("<session id=\"" + fileHandler.getCurrentSessionNumber() + "\">")) {
            content.append(fileHandler.getCurrentSession().getTransformations());
          }
        }
      }

      try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile))) {
        writer.write(content.toString());
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println("Changes successfully saved to the file.");
    fileHandler.open(fileHandler.getFileName());
  }
}
