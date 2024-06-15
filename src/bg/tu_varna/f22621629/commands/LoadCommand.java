package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.FileExceptionHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;
import bg.tu_varna.f22621629.models.*;
import bg.tu_varna.f22621629.utils.ImageUtils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The LoadCommand class is responsible for loading image files into the current session
 * using the XMLFileHandler. It supports loading .ppm, .pgm, and .pbm file formats.
 */
public class LoadCommand implements CommandHandler {
  private XMLFileHandler fileHandler;
  private StringBuilder loadedImageBuffer;

  /**
   * Constructs a LoadCommand instance and initializes the XMLFileHandler and the StringBuilder
   * for buffering loaded image content.
   */
  public LoadCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
    this.loadedImageBuffer = new StringBuilder();
  }

  /**
   * Executes the command to load an image file into the current session.
   *
   * @param command The command containing the arguments.
   * @throws IOException if an I/O error occurs during execution.
   * @throws FileExceptionHandler if an error occurs related to file handling.
   */
  @Override
  public void execute(Command command) throws IOException, FileExceptionHandler {
    if (command.getArguments().length != 1) {
      System.out.println("Usage: load <session_name>");
      return;
    }
    String fileName = command.getArguments()[0];
    if (!fileName.endsWith(".pbm") && !fileName.endsWith(".ppm") && !fileName.endsWith(".pgm")) {
      System.out.println("Opening only .ppm / .pbm / .pgm files!");
      return;
    }

    String filePath = "images/" + fileName;
    if (fileHandler.getCurrentSession() == null) {
      System.out.println("First you need to switch to some session, you can see the sessions from > session info");
      return;
    }

    if (!fileHandler.isFileInCurrentSession(filePath)) {
      System.out.println("This file is not in the current session!");
      return;
    }

    if (isValidImageFile(fileName)) {
      loadSessionImage(filePath);
    } else {
      System.out.println("Invalid image file format.");
    }
  }

  /**
   * Loads the image from the specified file path and sets it in the file handler.
   *
   * @param filePath The path to the image file.
   */
  private void loadSessionImage(String filePath) {
    loadedImageBuffer.setLength(0);
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        loadedImageBuffer.append(line).append("\n");
      }

      String content = loadedImageBuffer.toString();
      String[] lines = content.split("\n");

      if (lines.length < 3) {
        System.out.println("Invalid P3 image format.");
        return;
      }

      if (lines[0].trim().equals("P3")) {
        String[] dimensions = lines[1].trim().split("\\s+");
        int width = Integer.parseInt(dimensions[0]);
        int height = Integer.parseInt(dimensions[1]);
        int maxLightValue = Integer.parseInt(dimensions[2]);

        Pixel[][] pixels = new Pixel[height][width];
        int pixelIndex = 0;

        for (int i = 2; i < lines.length; i++) {
          String[] rgbValues = lines[i].trim().split("\\s+");
          for (int j = 0; j < rgbValues.length; j += 3) {
            int red = Integer.parseInt(rgbValues[0]);
            int green = Integer.parseInt(rgbValues[1]);
            int blue = Integer.parseInt(rgbValues[2]);

            pixels[pixelIndex / width][pixelIndex % width] = new Pixel(red, green, blue);
            pixelIndex++;
          }
        }

        Image image = new Image(pixels, new int[]{width, height}, filePath, "P3");
        image.setPixels(pixels);
        image.setContent(content);

        fileHandler.setLoadedImage(image);
        System.out.println("P3 Image loaded successfully:");
        System.out.println(content);
      } else {
        Image image = ImageUtils.readImageFromFile(filePath);
        image.setContent(content);
        fileHandler.setLoadedImage(image);
        System.out.println("Image loaded successfully:");
        System.out.println(content);
      }
    } catch (IOException | NumberFormatException e) {
      System.out.println("Error loading image: " + e.getMessage());
    }
  }
  /**
   * Checks if the given file name has a valid image file extension.
   *
   * @param fileName The name of the file to check.
   * @return true if the file has a valid extension, false otherwise.
   */
  private boolean isValidImageFile(String fileName) {
    String extension = getFileExtension(fileName);
    return extension != null && (extension.equals("ppm") || extension.equals("pgm") || extension.equals("pbm"));
  }
  /**
   * Gets the file extension of the given file name.
   *
   * @param fileName The name of the file.
   * @return The file extension, or null if there is no extension.
   */
  private String getFileExtension(String fileName) {
    int dotIndex = fileName.lastIndexOf(".");
    if (dotIndex == -1 || dotIndex == fileName.length() - 1) {
      return null;
    }
    return fileName.substring(dotIndex + 1).toLowerCase();
  }
}
