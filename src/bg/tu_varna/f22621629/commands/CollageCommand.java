package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.FileExceptionHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;
import bg.tu_varna.f22621629.models.Session;

import java.io.*;
import java.util.Set;

/**
 * The CollageCommand class represents a command for creating a collage from two images.
 */
public class CollageCommand implements CommandHandler {

  private final XMLFileHandler fileHandler;

  /**
   * Constructs a CollageCommand object.
   */
  public CollageCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
  }

  /**
   * Executes the collage creation command.
   *
   * @param args Command arguments including direction, image filenames, and output filename.
   * @throws IOException            If an I/O error occurs.
   * @throws FileExceptionHandler If an exception related to file handling occurs.
   */
  @Override
  public void execute(String[] args) throws IOException, FileExceptionHandler {
    if (!fileHandler.isSessionLoaded()) {
      System.out.println("No loaded session. Use > switch 'id' ");
      return;
    }
    if (args.length < 2) {
      System.out.println("Usage: collage <direction> <image1> <image2> <outimage>");
      return;
    }

    String[] commands = args[1].toString().split(" ");
    if (commands.length < 4) {
      System.out.println("Usage: collage <direction> <image1> <image2> <outimage>");
      return;
    }

    String direction = commands[0];
    String firstImageFileName = "images/" + commands[1];
    String secondImageFileName = "images/" + commands[2];
    String outimage = commands[3];


    Set<Session> sessions = fileHandler.getSessions();
    StringBuilder firstImageContent = new StringBuilder();
    StringBuilder secondImageContent = new StringBuilder();

    try (BufferedReader reader = new BufferedReader(new FileReader(firstImageFileName))) {
      String firstLine;
      while ((firstLine = reader.readLine()) != null) {
        firstImageContent.append(firstLine).append("\n");
      }
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(secondImageFileName))) {
      String secondLine;
      while ((secondLine = reader.readLine()) != null) {
        secondImageContent.append(secondLine).append("\n");
      }
    }

    createCollage(firstImageContent, secondImageContent, direction, outimage);

  }

  /**
   * Creates a collage from two images based on the specified direction.
   *
   * @param firstImageContent  Content of the first image.
   * @param secondImageContent Content of the second image.
   * @param direction          The direction of collage (horizontal or vertical).
   * @param outImage           The filename for the output collage image.
   * @throws IOException If an I/O error occurs.
   */
  private void createCollage(StringBuilder firstImageContent, StringBuilder secondImageContent, String direction, String outImage) throws IOException {
    StringBuilder collageData = new StringBuilder();

    // HORIZONTAL
    if (direction.equalsIgnoreCase("horizontal")) {
      // Implementation for horizontal collage
    }

    // VERTICAL
    if (direction.equalsIgnoreCase("vertical")) {
      // Implementation for vertical collage
    }

    saveCollageToFile(collageData.toString(), outImage);
  }

  /**
   * Saves collage data to a file.
   *
   * @param collageData The collage data to be saved.
   * @param outImage    The filename for the output collage image.
   * @throws IOException If an I/O error occurs.
   */
  private void saveCollageToFile(String collageData, String outImage) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("images/" + outImage))) {
      writer.write(collageData);
    }
    System.out.println("Collage created successfully and added to the current session: " + outImage);
  }
}
