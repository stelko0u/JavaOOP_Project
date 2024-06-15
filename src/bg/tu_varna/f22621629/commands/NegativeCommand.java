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
 * The NegativeCommand class represents a command for applying a negative effect to the currently loaded image.
 */
public class NegativeCommand implements CommandHandler {
  private static final String IMAGES_FOLDER = "images/";
  private FileUtils fileUtils;

  /**
   * Constructs a NegativeCommand instance and initializes the FileUtils instance.
   */
  public NegativeCommand() {
    this.fileUtils = fileUtils.getInstance();
  }

  /**
   * Executes the command to apply a negative effect to the currently loaded image.
   *
   * @param command The command containing the arguments (not used in this implementation).
   * @throws IOException if an I/O error occurs during execution.
   */
  @Override
  public void execute(Command command) throws IOException {
    XMLFileHandler fileHandler = XMLFileHandler.getInstance();
    if (fileHandler.getLoadedImage() == null) {
      System.out.println("No loaded image! Please load an image first!");
      return;
    }

    Image loadedImage = fileHandler.getLoadedImage();

    Image modifiedImageData = ImageUtils.applyNegativeEffect(loadedImage);

    String negativeFileName = "negative_" + modifiedImageData.getImageNameWithoutPath();
    String negativeImagePath = IMAGES_FOLDER + negativeFileName;

    fileUtils.writeFile(negativeImagePath, modifiedImageData);

    System.out.println("Negative effect applied successfully to: " + negativeFileName);

    int nextId = fileHandler.getSessions().size() + 1;
    String newImageElement = "<session id=\"" + nextId + "\">\n" +
            "    <image name=\"" + negativeFileName + "\">\n" +
            "    </image>\n" +
            "</session>\n";
    fileHandler.setNextLocalImage(newImageElement);
    System.out.println("Image added to session successfully.");
  }
}