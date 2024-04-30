package bg.tu_varna.f22621629.Commands;

import bg.tu_varna.f22621629.Handlers.CommandHandler;
import bg.tu_varna.f22621629.Handlers.FileExceptionHandler;
import bg.tu_varna.f22621629.Handlers.XMLFileHandler;
import bg.tu_varna.f22621629.Models.Session;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class LoadCommand implements CommandHandler {
  private XMLFileHandler fileHandler;
  private boolean isFound = false;

  public LoadCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
  }

  @Override
  public void execute(String[] args) throws IOException, FileExceptionHandler {
    if (args.length < 2) {
      System.out.println("Usage: load <session_name>");
      return;
    }
    if (!args[1].endsWith(".pbm") && !args[1].endsWith(".ppm")) {
      System.out.println("Openning only .ppm / .pbm / .pgm files!");
      return;
    }

    String sessionName = args[1];
    loadSessionImage(sessionName);
  }
  private void loadSessionImage(String sessionName) {
    String imagePath = "images/" + sessionName;
    Set<Session> sessions = fileHandler.getSessions();

    for (Session session : sessions) {
      if (imagePath.equals(session.getFileName())) {
        isFound = true;
        break;
      }
    }

    if (isValidImageFile(imagePath) || isFound) {
      try (BufferedReader reader = new BufferedReader(new FileReader(imagePath))) {
        StringBuilder imageContent = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
          imageContent.append(line).append("\n");
        }

        System.out.println("Image loaded successfully:");
        System.out.println(imageContent.toString());
      } catch (IOException e) {
        System.out.println("Error loading image: " + e.getMessage());
      }
    } else {
      System.out.println("Invalid image file: " + imagePath);
    }
  }

  private boolean isValidImageFile(String filePath) {
    String extension = getFileExtension(filePath);
    return extension != null && (extension.equals("ppm") || extension.equals("pgm") || extension.equals("pbm"));
  }

  private String getFileExtension(String filePath) {
    int dotIndex = filePath.lastIndexOf(".");
    if (dotIndex == -1 || dotIndex == filePath.length() - 1) {
      return null;
    }
    return filePath.substring(dotIndex + 1).toLowerCase();
  }
}