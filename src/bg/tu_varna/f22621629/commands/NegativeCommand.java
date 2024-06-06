package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;
import bg.tu_varna.f22621629.models.Command;
import bg.tu_varna.f22621629.models.Image;
import bg.tu_varna.f22621629.processor.ImageProcessor;
import bg.tu_varna.f22621629.utils.FileUtils;
import bg.tu_varna.f22621629.utils.ImageUtils;

import java.io.*;
import java.util.logging.FileHandler;
/**
 * The NegativeCommand class represents a command for applying a negative effect to the loaded image.
 */
public class NegativeCommand implements CommandHandler {
  /** The folder path where images are stored. */
  private static final String IMAGES_FOLDER = "images\\";
  /** The utility class for image operations. */
  private ImageUtils imageUtils;
  /** The utility class for file operations. */
  private FileUtils fileUtils;

  /**
   * Constructs a NegativeCommand object.
   */
  public NegativeCommand() {
    this.imageUtils = new ImageUtils();
    this.fileUtils = fileUtils.getInstance();
  }
  /**
   * Executes the negative effect command by applying the negative effect to the loaded image and saving it.
   *
   * @param command the command object representing the negative effect command
   * @throws IOException if an I/O error occurs while processing the image or writing to a file
   */
  @Override
  public void execute(Command command) throws IOException {
    XMLFileHandler fileHandler = XMLFileHandler.getInstance();
    if (fileHandler.getLoadedImage() == null) {
      System.out.println("No loaded image! Please load an image first!");
      return;
    }

    Image loadedImageAsString = fileHandler.getLoadedImage();
    if (loadedImageAsString == null) {
      System.out.println("No image is currently loaded. Please load an image first.");
      return;
    }

    Image modifiedImageData = new Image(imageUtils.applyNegativeEffect(loadedImageAsString.getContent()));

    String negativeFileName = "negative_" + fileHandler.getFileNameLoadedImage();
    String negativeImagePath = IMAGES_FOLDER + negativeFileName;

    fileUtils.writeFile(negativeImagePath, modifiedImageData);

    System.out.println("Negative effect applied successfully to: " + fileHandler.getFileNameLoadedImage());

    int nextId = fileHandler.getSessions().size() + 1;
    String newImageElement = "<session id=\"" + nextId + "\">\n" +
            "    <image name=\"" + negativeFileName + "\">\n" +
            "    </image>\n" +
            "</session>\n";
    fileHandler.setNextLocalImage(newImageElement);
    System.out.println("Image added to session successfully.");
  }
}
