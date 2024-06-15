package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.creators.CollageCreator;
import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.FileExceptionHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;
import bg.tu_varna.f22621629.models.Command;
import bg.tu_varna.f22621629.models.Image;
import bg.tu_varna.f22621629.utils.FileUtils;
import bg.tu_varna.f22621629.utils.ImageUtils;

import java.io.*;

/**
 * The CollageCommand class is a concrete implementation of the CommandHandler interface.
 * It handles the creation of a collage from two images and saves the result.
 */
public class CollageCommand implements CommandHandler {

  private final XMLFileHandler fileHandler;
  private final CollageCreator collageCreator;
  /**
   * Constructs a CollageCommand instance and initializes the XMLFileHandler and CollageCreator.
   */
  public CollageCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
    this.collageCreator = new CollageCreator();
  }
  /**
   * Executes the command to create a collage from two images.
   *
   * @param command The command containing the arguments.
   * @throws IOException if an I/O error occurs during execution.
   * @throws FileExceptionHandler if an error occurs related to file handling.
   */
  @Override
  public void execute(Command command) throws IOException, FileExceptionHandler {
    if (!fileHandler.isSessionLoaded()) {
      System.out.println("No loaded session. Use > switch 'id' ");
      return;
    }
    if (command.getArguments().length != 4) {
      System.out.println("Usage: collage <direction> <image1> <image2> <outimage>");
      return;
    }

    String direction = command.getArguments()[0];
    String firstImageFileName = "images/" + command.getArguments()[1];
    String secondImageFileName = "images/" + command.getArguments()[2];
    String outImageFileName = "images/" + command.getArguments()[3];

    Image firstImage = ImageUtils.readImageFromFile(firstImageFileName);
    Image secondImage = ImageUtils.readImageFromFile(secondImageFileName);

    Image outImage = new Image(null, new int[]{0, 0}, outImageFileName, firstImage.getFormat());
    collageCreator.createCollage(firstImage, secondImage, direction, outImage);
  }
}