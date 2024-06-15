package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;
import bg.tu_varna.f22621629.models.Command;
import bg.tu_varna.f22621629.models.Image;
import bg.tu_varna.f22621629.models.Session;
import bg.tu_varna.f22621629.processor.ImageProcessor;
import bg.tu_varna.f22621629.utils.FileUtils;
import bg.tu_varna.f22621629.utils.ImageUtils;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * The GrayScaleCommand class represents a command for applying a grayscale effect to images
 * in the currently active session.
 */
public class GrayScaleCommand implements CommandHandler {
  private XMLFileHandler fileHandler;
  private ImageProcessor imageProcessor;
  /**
   * Constructs a GrayScaleCommand instance and initializes the XMLFileHandler and ImageProcessor.
   */
  public GrayScaleCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
    this.imageProcessor = new ImageProcessor();
  }
  /**
   * Executes the command to apply a grayscale effect to all images in the current session.
   *
   * @param command The command containing the arguments (not used in this implementation).
   * @throws IOException if an I/O error occurs during execution.
   */
  @Override
  public void execute(Command command) throws IOException {
    if (!fileHandler.isFileOpened()) {
      System.out.println("No file is currently open. Please open a file first.");
      return;
    }

    Session currentSession = fileHandler.getCurrentSession();
    if (currentSession == null) {
      System.out.println("No session is currently active. Please start a session first.");
      return;
    }

    List<String> fileNames = currentSession.getFileNames();
    List<String> individualFileNames = new ArrayList<>();
    for (String files : fileNames) {
      individualFileNames.addAll(Arrays.asList(files.split(", ")));
    }

    for (String fileName : individualFileNames) {
      String filePath = "images/" + fileName;

      if (!FileUtils.fileExists(filePath)) {
        System.out.println("File '" + fileName + "' not found in the current session. Skipping.");
        continue;
      }

      Image loadedImageAsObject = fileHandler.getLoadedImage();
      if (ImageUtils.isColorImage(loadedImageAsObject)) {
        imageProcessor.applyGrayScaleEffect(loadedImageAsObject);
      } else {
        System.out.println("This format is not supported! - " + fileName);
      }
    }
  }
}