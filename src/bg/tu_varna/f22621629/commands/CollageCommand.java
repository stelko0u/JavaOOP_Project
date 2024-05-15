package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.FileExceptionHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;
import bg.tu_varna.f22621629.models.Session;

import java.io.*;
import java.util.Set;

public class CollageCommand implements CommandHandler {

  private final XMLFileHandler fileHandler;

  public CollageCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
  }

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
  private void createCollage(StringBuilder firstImageContent, StringBuilder secondImageContent, String direction, String outImage) throws IOException {
    StringBuilder collageData = new StringBuilder();

    // HORIZONTAL
        if (direction.equalsIgnoreCase("horizontal")) {
          String[] contentFirstImage = firstImageContent.toString().split("\n");
          String[] contentSecondImage = secondImageContent.toString().split("\n");


          String[] sizes = null;
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
            sizes = contentFirstImage[0].split(" ");
            width = Integer.parseInt(sizes[1]);
            height = Integer.parseInt(sizes[2]);
            start = 2;
            collageData.append("P3").append(" ").append(width+width).append(" ")
                    .append(height).append(" ").append(sizes[3]).append("\n");
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

          String[] sizes = null;
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

            sizes = contentFirstImage[0].split(" ");
            width = Integer.parseInt(sizes[1]);
            height = Integer.parseInt(sizes[2]);
            start = 2;
            collageData.append("P3").append(" ").append(width).append(" ")
                    .append(height+height).append(" ").append(sizes[3]).append("\n");

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

  private void saveCollageToFile(String collageData, String outImage) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("images/" + outImage))) {
      writer.write(collageData);
    }
      System.out.println("Collage created successfully and added to the current session: " + outImage);
  }
}
