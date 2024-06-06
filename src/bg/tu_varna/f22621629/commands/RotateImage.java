package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.ImageHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;
import bg.tu_varna.f22621629.imageRotator.ImageRotator;
import bg.tu_varna.f22621629.models.Command;
import bg.tu_varna.f22621629.models.Image;
import bg.tu_varna.f22621629.utils.FileUtils;
import bg.tu_varna.f22621629.utils.ImageUtils;

import java.io.*;
/**
 * The RotateImage class represents a command for rotating the loaded image.
 */

public class RotateImage implements CommandHandler {
  /** The XML file handler instance. */
  private final XMLFileHandler fileHandler;
  /** The folder path where images are stored. */
  private static final String IMAGES_FOLDER = "images\\";

  /**
   * Constructs a RotateImage object.
   */

  public RotateImage() {
    this.fileHandler = XMLFileHandler.getInstance();
  }
  /**
   * Executes the rotation command by rotating the loaded image to the specified direction.
   *
   * @param commands the command object representing the rotation command
   * @throws IOException if an I/O error occurs while reading or writing the image data
   */
  @Override
  public void execute(Command commands) throws IOException {
    if (!fileHandler.isFileOpened()) {
      System.out.println("No file is currently open. Please open a file first.");
      return;
    }
    if (fileHandler.getLoadedImage() == null) {
      System.out.println("No loaded image! Please load an image first!");
      return;
    }

    if (commands.getArguments().length != 1) {
      System.out.println("Usage: rotate <left/right>");
      return;
    }

    String direction = commands.getArguments()[0];
    if (!direction.equalsIgnoreCase("left") && !direction.equalsIgnoreCase("right")) {
      System.out.println("Usage only 'left' and 'right' directions!");
      return;
    }

    String loadedImageFilePath = fileHandler.getFileNameLoadedImage();
    String imagePath = IMAGES_FOLDER + loadedImageFilePath;
    File imageFile = new File(imagePath);

    if (imageFile.exists() && ImageUtils.isSupportedImageFormat(loadedImageFilePath)) {
      ImageHandler imageHandler = new ImageHandler(imagePath);
      Image imageData = new Image(imageHandler.readImageData());
      Image rotatedImageData = new Image(ImageRotator.rotateImage(loadedImageFilePath, imageData, direction));
      String rotatedImagePath = IMAGES_FOLDER + FileUtils.getRotatedFileName(loadedImageFilePath, direction);
      imageHandler.writeImageData(rotatedImagePath, rotatedImageData);
      System.out.println("Image rotated successfully: " + rotatedImagePath);
    } else {
      System.out.println("Unsupported image format or file not found: " + loadedImageFilePath);
    }
  }
}