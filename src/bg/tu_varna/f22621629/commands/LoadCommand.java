package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.FileExceptionHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoadCommand implements CommandHandler {
  private XMLFileHandler fileHandler;
  private boolean isFound = false;
  private StringBuilder loadedImageBuffer;

  public StringBuilder getLoadedImageBuffer() {
    return loadedImageBuffer;
  }

  public LoadCommand() {

    this.fileHandler = XMLFileHandler.getInstance();
    this.loadedImageBuffer = new StringBuilder();
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

    loadedImageBuffer.setLength(0);
    if(fileHandler.getCurrentSession() == null) {
      System.out.println("First you need to switch to some session, you can see the sessions from > session info");
      return;
    }
      if (fileHandler.isFileInCurrentSession(imagePath)) {
        isFound = true;
        try (BufferedReader reader = new BufferedReader(new FileReader(imagePath))) {
          fileHandler.setFileNameLoadedImage(sessionName);

          String line;
          while ((line = reader.readLine()) != null) {
            loadedImageBuffer.append(line).append("\n");
          }
          fileHandler.setLoadedImage(line);
          System.out.println("Image loaded successfully:");
          System.out.println(loadedImageBuffer.toString());
          fileHandler.setLoadedImage(loadedImageBuffer.toString());
        } catch (IOException e) {
          System.out.println("Error loading image: " + e.getMessage());
        }
        return;
      }

    if (isValidImageFile(imagePath) && isFound) {
      try (BufferedReader reader = new BufferedReader(new FileReader(imagePath))) {
        StringBuilder imageContent = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
          imageContent.append(line).append("\n");
        }
        fileHandler.setLoadedImage(imageContent.toString());
        System.out.println("Image loaded successfully:");
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