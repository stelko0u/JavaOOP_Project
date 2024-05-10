package bg.tu_varna.f22621629.Commands;

import bg.tu_varna.f22621629.Handlers.CommandHandler;
import bg.tu_varna.f22621629.Handlers.FileExceptionHandler;
import bg.tu_varna.f22621629.Handlers.XMLFileHandler;
import bg.tu_varna.f22621629.Models.Session;

import java.io.*;
import java.util.Set;

public class CollageCommand implements CommandHandler {

  private final XMLFileHandler fileHandler;

  public CollageCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
  }

  @Override
  public void execute(String[] args) throws IOException, FileExceptionHandler {
    String[] commands = args[1].toString().split(" ");
    System.out.println(commands.length);
    if (commands.length < 4) {
      System.out.println("Usage: collage <direction> <image1> <image2> <outimage>");
      return;
    }

    String direction = commands[0];
    String firstImageFileName = commands[1];
    String secondImageFileName = commands[2];
    String outimage = commands[3];


    Set<Session> sessions = fileHandler.getSessions();
    StringBuilder firstImageContent = new StringBuilder();
    StringBuilder secondImageContent = new StringBuilder();


    try (BufferedReader reader = new BufferedReader(new FileReader(firstImageFileName))) {
      String firstLine;
      while ((firstLine = reader.readLine()) != null) {
        firstImageContent.append(firstLine).append("\n");
      }

      System.out.println("Image1 loaded successfully:");
      System.out.println(firstImageContent.toString());



      System.out.println("Collage created successfully and added to the current session.");
    }
    try (BufferedReader reader = new BufferedReader(new FileReader(secondImageFileName))) {


      String secondLine;
      while ((secondLine = reader.readLine()) != null) {
        firstImageContent.append(secondLine).append("\n");
      }
      createCollage(firstImageContent, secondImageContent, direction, outimage);


      System.out.println("Collage created successfully and added to the current session.");
    }
    createCollage(firstImageContent, secondImageContent, direction, outimage);

  }
  private String createCollage(StringBuilder firstImageContent, StringBuilder secondImageContent, String direction, String outImage) throws IOException {
    StringBuilder collageData = new StringBuilder();


        if (direction.equalsIgnoreCase("horizontal")) {
          String[] contentFirstImage = firstImageContent.toString().split("\n");
          String[] contentSecondImage = firstImageContent.toString().split("\n");
          String[] sizes = contentFirstImage[1].split(" ");
          int width = Integer.parseInt(sizes[0]);
          int height = Integer.parseInt(sizes[1]);
          for (int i = 2; i < height; i++) {
            for (int j = 0; i < width; i++) {
              collageData.append(firstImageContent).append(secondImageContent);
            }
          }
          saveCollageToFile(collageData.toString(), outImage);
        }

        if (direction.equalsIgnoreCase("vertical")) {

        }
    return collageData.toString();
  }

  private void saveCollageToFile(String collageData, String outImage) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(outImage))) {
      writer.write(collageData);
    }
  }
}
