package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.creators.CollageCreator;
import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.FileExceptionHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;
import bg.tu_varna.f22621629.models.Command;
import bg.tu_varna.f22621629.models.Image;
import bg.tu_varna.f22621629.models.Session;
import bg.tu_varna.f22621629.utils.ImageUtils;

import java.io.*;
import java.util.Set;

/**
 * The CollageCommand class represents a command for creating a collage from two images.
 */
public class CollageCommand implements CommandHandler {
  /** The XML file handler instance for handling session and file operations. */
  private final XMLFileHandler fileHandler;
  /** The utility class for reading image data. */
  private final ImageUtils imageReader;
  /** The creator for generating collages from images. */
  private final CollageCreator collageCreator;
  /**
   * Constructs a CollageCommand.
   */
  public CollageCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
    this.imageReader = new ImageUtils();
    this.collageCreator = new CollageCreator();
  }
  /**
   * Executes the collage command by creating a collage from two images.
   *
   * @param command the command object representing the collage command
   * @throws IOException if an I/O error occurs while reading or writing image files
   * @throws FileExceptionHandler if any file-related exception occurs
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
    String outimage = command.getArguments()[3];

    Image firstImageContent = new Image(imageReader.readImage(firstImageFileName).toString());
    Image secondImageContent = new Image(imageReader.readImage(secondImageFileName).toString());

    collageCreator.createCollage(firstImageContent, secondImageContent, direction, outimage);
  }
}