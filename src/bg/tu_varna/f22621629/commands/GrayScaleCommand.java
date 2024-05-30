package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;
import bg.tu_varna.f22621629.models.Command;
import bg.tu_varna.f22621629.models.Session;
import bg.tu_varna.f22621629.processor.ImageProcessor;
import bg.tu_varna.f22621629.utils.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * The GrayScaleCommand class represents a command for applying grayscale effect to images.
 */
public class GrayScaleCommand implements CommandHandler {
  /** The XML file handler instance for handling file operations. */
  private XMLFileHandler fileHandler;
  /** The image processor for applying grayscale effect to images. */
  private ImageProcessor imageProcessor;

  /**
   * Constructs a GrayScaleCommand.
   */
  public GrayScaleCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
    this.imageProcessor = new ImageProcessor();
  }

  /**
   * Executes the grayscale command by applying the effect to each color image in the current session.
   *
   * @param command the command object representing the grayscale command
   * @throws IOException if an I/O error occurs while processing image files
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
        if (imageProcessor.isColorImage(filePath)) {
          imageProcessor.applyGrayScaleEffect(filePath);
        } else {
          System.out.println("This format is not supported! - " + fileName);
        }
    }
  }
}
