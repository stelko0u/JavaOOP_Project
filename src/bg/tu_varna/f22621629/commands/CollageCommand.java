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
    if (args.length < 5) {
      System.out.println("Usage: collage <direction> <image1> <image2> <outimage>");
      return;
    }



    String direction = args[1];
    String firstImageFileName = "images/" + args[2];
    String secondImageFileName = "images/" + args[3];
    String outimage = args[4];


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
      String[] contentFirstImage = firstImageContent.toString().split("\n");
      String[] contentSecondImage = secondImageContent.toString().split("\n");
      String[] sizes = contentFirstImage[1].split(" ");
      int width = 0;
      int height = 0;
      int start = 0;
      if (contentFirstImage[0].startsWith("P1")) {
        width = Integer.parseInt(sizes[0]);
        height = Integer.parseInt(sizes[1]);
        start = 2;
        collageData.append("P1").append('\n');
        collageData.append(width+width).append(" ").append(height).append("\n");
        for (int i = start; i < contentFirstImage.length; i++) {
          collageData.append(contentFirstImage[i]).append(" ");
          collageData.append(contentSecondImage[i]).append(" ").append("\n");
        }
      } else if (contentFirstImage[0].startsWith("P2")) {
        sizes = contentFirstImage[2].split(" ");
        width = Integer.parseInt(sizes[0]);
        height = Integer.parseInt(sizes[1]);
        start = 4;
        collageData.append("P2").append("\n");
        collageData.append("# ").append(outImage).append("\n");
        collageData.append(width+width).append(" ").append(height).append("\n");
        for (int i = start; i < contentFirstImage.length; i++) {
          collageData.append(contentFirstImage[i]).append(" ");
          collageData.append(contentSecondImage[i]).append(" ").append("\n");
        }
      } else if (contentFirstImage[0].startsWith("P3")) {
        sizes = contentFirstImage[1].split(" ");
        width = Integer.parseInt(sizes[0]);
        height = Integer.parseInt(sizes[1]);
        start = 2;
        collageData.append("P3").append(" ").append(width+width).append(" ")
                .append(height).append(" ").append(sizes[2]).append("\n");
        for (int i = start; i < contentFirstImage.length; i++) {
          collageData.append(contentFirstImage[i]).append(" ");
          collageData.append(contentSecondImage[i]).append(" ").append("\n");
        }
      }
      saveCollageToFile(collageData.toString(), outImage);
    }
    // VERTICAL
    if (direction.equalsIgnoreCase("vertical")) {
      String[] contentFirstImage = firstImageContent.toString().split("\n");
      String[] contentSecondImage = secondImageContent.toString().split("\n");
      String[] sizes;

      int width = 0;
      int height = 0;
      int start = 0;
      if (contentSecondImage[0].equalsIgnoreCase("P1")) {
        sizes = contentFirstImage[1].split(" ");
        width = Integer.parseInt(sizes[0]);
        height = Integer.parseInt(sizes[1]);
        start = 2;
        collageData.append("P1").append('\n');
        collageData.append(width).append(" ").append(height+height).append("\n");
        for (int i = start; i < contentFirstImage.length ; i++) {
          collageData.append(contentFirstImage[i]).append("\n");
        }
        for (int i = start; i < contentSecondImage.length; i++) {
          collageData.append(contentSecondImage[i]).append("\n");
        }
      } else if (contentFirstImage[0].equalsIgnoreCase("P2")) {
        sizes = contentFirstImage[2].split(" ");
        width = Integer.parseInt(sizes[0]);
        height = Integer.parseInt(sizes[1]);
        start = 4;
        collageData.append("P2").append("\n");
        collageData.append("# ").append(outImage).append("\n");
        collageData.append(width).append(" ").append(height+height).append("\n");
        for (int i = start; i < contentFirstImage.length; i++) {
          collageData.append(contentFirstImage[i]).append("\n");
        }
        for (int i = start; i < contentSecondImage.length; i++) {
          collageData.append(contentSecondImage[i]).append("\n");
        }
      } else if (contentFirstImage[0].startsWith("P3")) {
        sizes = contentFirstImage[1].split(" ");
        width = Integer.parseInt(sizes[0]);
        height = Integer.parseInt(sizes[1]);
        start = 2;
        collageData.append("P3").append(" ").append(width).append(" ")
                .append(height+height).append(" ").append(sizes[2]).append("\n");
        for (int i = start; i < contentFirstImage.length; i++) {
          collageData.append(contentFirstImage[i]).append("\n");
        }
        for (int i = start; i < contentSecondImage.length; i++) {
          collageData.append(contentSecondImage[i]).append("\n");
        }
      }
      saveCollageToFile(collageData.toString(), outImage);
    }
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
