package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SaveAsCommand implements CommandHandler {
  private XMLFileHandler fileHandler;

  public SaveAsCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
  }

  @Override
  public void execute(String[] command) throws IOException {
    if (command.length < 2) {
      System.out.println("Usage: saveas <file_path>");
      return;
    }
    String filePath = command[1];
    File file = new File(filePath);

    if (file.exists()) {
      System.out.println("File already exists. Do you want to overwrite it? (yes/no)");
      Scanner scanner = new Scanner(System.in);
      String answer = scanner.nextLine();
      if (!answer.equalsIgnoreCase("yes")) {
        System.out.println("Save as cancelled!");
        return;
      }
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
      writer.write(fileHandler.getContent());
    } catch (IOException e) {
      System.out.println("Error occurred while saving the file: " + e.getMessage());
    }
    System.out.println("File saved successfully as " + filePath);
  }
}
