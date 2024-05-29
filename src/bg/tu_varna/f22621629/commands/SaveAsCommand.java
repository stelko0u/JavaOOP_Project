package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.FileExceptionHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;
import bg.tu_varna.f22621629.models.Command;

import java.io.*;
import java.util.Scanner;
/**
 * The SaveAsCommand class implements the CommandHandler interface to handle the saveas command.
 * It saves the current content to the specified file path.
 */
public class SaveAsCommand implements CommandHandler {

  private XMLFileHandler fileHandler;

  /**
   * Constructs a SaveAsCommand object and initializes the XMLFileHandler instance.
   */
  public SaveAsCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
  }

  /**
   * Executes the command to save the current content to the specified file path.
   *
   * @param command The command arguments containing the file path to save as.
   * @throws IOException if an I/O error occurs.
   */
  @Override
  public void execute(Command command) throws IOException, FileExceptionHandler {
    if (command.getArguments().length != 1) {
      System.out.println("Usage: saveas <file_path>");
      return;
    }
    String filePath = command.getArguments()[0];
    if (!filePath.endsWith(".xml")) {
      System.out.println("Save only in XML format files!");
      return;
    }
    File file = new File(filePath);

    if (file.exists()) {
      System.out.println("File already exists. Do you want to overwrite it? (yes/no)");
      Scanner scanner = new Scanner(System.in);
      System.out.print("> ");
      String answer = scanner.nextLine();
      if (!answer.equalsIgnoreCase("yes")) {
        System.out.println("Save as cancelled!");
        return;
      }
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(fileHandler.getFileName()))) {
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

      try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

        System.out.println(fileHandler.getContent());
        writer.write(content.toString());
      } catch (IOException e) {
        System.out.println("Error occurred while saving the file: " + e.getMessage());
      }
      System.out.println("File saved successfully as " + filePath);
      fileHandler.open(fileHandler.getFileName());
    }
  }
}