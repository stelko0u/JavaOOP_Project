package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.FileExceptionHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;
import bg.tu_varna.f22621629.models.Command;
import bg.tu_varna.f22621629.models.Image;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

  /**
   * The LoadCommand class implements the CommandHandler interface to handle the load command.
   * It loads an image file specified by the session name.
   */
public class LoadCommand implements CommandHandler {
  private XMLFileHandler fileHandler;
  private StringBuilder loadedImageBuffer;
  private Image image;

  /**
   * Constructs a LoadCommand object and initializes the XMLFileHandler instance.
   */
  public LoadCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
    this.loadedImageBuffer = new StringBuilder();
    this.image = new Image("");
  }

    /**
     * Executes the load command, loading an image file specified by the session name.
     *
     * @param command the Command object containing the session name
     * @throws IOException            if an I/O error occurs while loading the image
     * @throws FileExceptionHandler   if a file-related exception occurs
     */
  @Override
  public void execute(Command command) throws IOException, FileExceptionHandler {
    if (command.getArguments().length != 1) {
      System.out.println("Usage: load <session_name>");
      return;
    }
    if (!command.getArguments()[0].endsWith(".pbm") && !command.getArguments()[0].endsWith(".ppm") && !command.getArguments()[0].endsWith(".pgm")) {
      System.out.println("Opening only .ppm / .pbm / .pgm files!");
      return;
    }

    Image image = new Image("");
    image.setName(command.getArguments()[0]);
    loadSessionImage(image);
  }

    /**
     * Loads an image file specified by the session name.
     *
     * @param image the Image object representing the image to load
     */
  private void loadSessionImage(Image image) {
    String imagePath = "images/" + image.getName();

    loadedImageBuffer.setLength(0);
    if (fileHandler.getCurrentSession() == null) {
      System.out.println("First you need to switch to some session, you can see the sessions from > session info");
      return;
    }

    if (!fileHandler.isFileInCurrentSession(imagePath)) {
      System.out.println("This file is not current session!");
      return;
    }
    if (isValidImageFile(image)) {
        try (BufferedReader reader = new BufferedReader(new FileReader(imagePath))) {
          fileHandler.setFileNameLoadedImage(image.getName());

          String line;
          while ((line = reader.readLine()) != null) {
            loadedImageBuffer.append(line).append("\n");
          }
          image.setContent(loadedImageBuffer.toString());
          fileHandler.setLoadedImage(image);
          System.out.println("Image loaded successfully:");
          System.out.println(loadedImageBuffer.toString());
          fileHandler.setLoadedImage(image);
        } catch (IOException e) {
          System.out.println("Error loading image: " + e.getMessage());
        }
      }
  }

    /**
     * Checks if the file extension is a valid image format.
     *
     * @param image the Image object
     * @return true if the file extension is valid for image format, otherwise false
     */
  private boolean isValidImageFile(Image image) {
    String extension = getFileExtension(image);
    return extension != null && (extension.equals("ppm") || extension.equals("pgm") || extension.equals("pbm"));
  }

    /**
     * Gets the extension of a file from its path.
     *
     * @param image the Image object
     * @return the extension of the file
     */
  private String getFileExtension(Image image) {
    int dotIndex = image.getName().lastIndexOf(".");
    if (dotIndex == -1 || dotIndex == image.getName().length() - 1) {
      return null;
    }
    return image.getName().substring(dotIndex + 1).toLowerCase();
  }
}
