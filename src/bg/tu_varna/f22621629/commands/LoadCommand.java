package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.FileExceptionHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

  /**
   * The LoadCommand class implements the CommandHandler interface to handle the load command.
   * It loads an image file specified by the session name.
   */
public class LoadCommand implements CommandHandler {
  private XMLFileHandler fileHandler;
  private boolean isFound = false;
  private StringBuilder loadedImageBuffer;

  /**
   * Constructs a LoadCommand object and initializes the XMLFileHandler instance.
   */
  public LoadCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
    this.loadedImageBuffer = new StringBuilder();
  }

  /**
   * Executes the command to load an image file specified by the session name.
   * @param args The command arguments containing the session name.
   * @throws IOException if an I/O error occurs.
   * @throws FileExceptionHandler if an error related to file handling occurs.
   */
  @Override
  public void execute(String[] args) throws IOException, FileExceptionHandler {
    if (args.length < 2) {
      System.out.println("Usage: load <session_name>");
      return;
    }
    if (!args[1].endsWith(".pbm") && !args[1].endsWith(".ppm") && !args[1].endsWith(".pgm")) {
      System.out.println("Opening only .ppm / .pbm / .pgm files!");
      return;
    }

    String sessionName = args[1];
    loadSessionImage(sessionName);
  }

  /**
   * Loads an image file specified by the session name.
   * @param sessionName The name of the session containing the image.
   */
  private void loadSessionImage(String sessionName) {
    String imagePath = "images/" + sessionName;

    loadedImageBuffer.setLength(0);
    if (fileHandler.getCurrentSession() == null) {
      System.out.println("First you need to switch to some session, you can see the sessions from > session info");
      return;
    }

    if (!fileHandler.isFileInCurrentSession(imagePath)) {
      System.out.println("This file is not current session!");
      return;
    }

    if (isValidImageFile(imagePath)) {
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
      }
  }

  /**
   * Checks if the file extension is a valid image format.
   * @param filePath The path of the file.
   * @return true if the file extension is valid for image format, otherwise false.
   */
  private boolean isValidImageFile(String filePath) {
    String extension = getFileExtension(filePath);
    return extension != null && (extension.equals("ppm") || extension.equals("pgm") || extension.equals("pbm"));
  }

  /**
   * Gets the extension of a file from its path.
   * @param filePath The path of the file.
   * @return The extension of the file.
   */
  private String getFileExtension(String filePath) {
    int dotIndex = filePath.lastIndexOf(".");
    if (dotIndex == -1 || dotIndex == filePath.length() - 1) {
      return null;
    }
    return filePath.substring(dotIndex + 1).toLowerCase();
  }
}
