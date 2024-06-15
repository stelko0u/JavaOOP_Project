package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.ImageHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;
import bg.tu_varna.f22621629.imageRotator.ImageRotator;
import bg.tu_varna.f22621629.models.*;
import bg.tu_varna.f22621629.utils.FileUtils;
import java.io.*;
/**
 * The RotateImage class represents a command for rotating the loaded image.
 */

public class RotateImage implements CommandHandler {

  /** The XML file handler instance. */
  private final XMLFileHandler fileHandler;

  /** The folder path where images are stored. */
  private static final String IMAGES_FOLDER = "images/";

  /**
   * Constructs a RotateImage object.
   */
  public RotateImage() {
    this.fileHandler = XMLFileHandler.getInstance();
  }

  /**
   * Executes the rotation command by rotating the loaded image to the specified direction.
   *
   * @param command the command object representing the rotation command
   * @throws IOException if an I/O error occurs while reading or writing the image data
   */
  @Override
  public void execute(Command command) throws IOException {
    if (!fileHandler.isFileOpened()) {
      System.out.println("No file is currently open. Please open a file first.");
      return;
    }
    if (fileHandler.getLoadedImage() == null) {
      System.out.println("No loaded image! Please load an image first!");
      return;
    }

    if (command.getArguments().length != 1) {
      System.out.println("Usage: rotate <left/right>");
      return;
    }

    String direction = command.getArguments()[0];
    if (!direction.equalsIgnoreCase("left") && !direction.equalsIgnoreCase("right")) {
      System.out.println("Usage only 'left' and 'right' directions!");
      return;
    }

    Image loadedImage = fileHandler.getLoadedImage();
    Image rotatedImage = rotateImage(loadedImage, direction);

    if (rotatedImage != null) {
      String rotatedImagePath = IMAGES_FOLDER + FileUtils.getRotatedFileName(loadedImage, direction);
      File rotatedImageFile = new File(rotatedImagePath);
      rotatedImage.setImageName(rotatedImagePath);
      ImageHandler imageHandler = new ImageHandler(rotatedImageFile.getAbsolutePath());
      imageHandler.writeImageData(rotatedImage);
      System.out.println("Image rotated successfully: " + rotatedImagePath);
    } else {
      System.out.println("Failed to rotate the image.");
    }
  }

  /**
   * Rotates the given image to the specified direction.
   *
   * @param image the image to rotate
   * @param direction the direction to rotate ("left" or "right")
   * @return the rotated image object
   */
  private Image rotateImage(Image image, String direction) {
    Image rotatedImage = null;

    if (image instanceof ImagePBM || image.getFormat().equals("P1")) {
      rotatedImage = ImageRotator.rotateImage(image, direction);
    } else if (image instanceof ImagePGM || image.getFormat().equals("P2")) {
      rotatedImage = ImageRotator.rotateImage(image, direction);
    } else if (image instanceof ImagePPM || image.getFormat().equals("P3")) {
      rotatedImage = ImageRotator.rotateImage(image, direction);
    }

    return rotatedImage;
  }
}