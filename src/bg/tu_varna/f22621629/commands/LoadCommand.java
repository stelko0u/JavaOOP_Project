package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.FileExceptionHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;
import bg.tu_varna.f22621629.models.*;
import bg.tu_varna.f22621629.utils.ImageUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LoadCommand implements CommandHandler {
  private XMLFileHandler fileHandler;
  private StringBuilder loadedImageBuffer;

  public LoadCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
    this.loadedImageBuffer = new StringBuilder();
  }

  @Override
  public void execute(Command command) throws IOException, FileExceptionHandler {
    if (command.getArguments().length != 1) {
      System.out.println("Usage: load <session_name>");
      return;
    }
    String fileName = command.getArguments()[0];
    if (!fileName.endsWith(".pbm") && !fileName.endsWith(".ppm") && !fileName.endsWith(".pgm")) {
      System.out.println("Opening only .ppm / .pbm / .pgm files!");
      return;
    }

    String filePath = "images/" + fileName;
    if (fileHandler.getCurrentSession() == null) {
      System.out.println("First you need to switch to some session, you can see the sessions from > session info");
      return;
    }

    if (!fileHandler.isFileInCurrentSession(filePath)) {
      System.out.println("This file is not in the current session!");
      return;
    }

    if (isValidImageFile(fileName)) {
      loadSessionImage(filePath);
    } else {
      System.out.println("Invalid image file format.");
    }
  }

  private void loadSessionImage(String filePath) {
    loadedImageBuffer.setLength(0);
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        loadedImageBuffer.append(line).append("\n");
      }

      String content = loadedImageBuffer.toString();
      String[] lines = content.split("\n");

      if (lines.length < 3) {
        System.out.println("Invalid P3 image format.");
        return;
      }

      // Check if it's a P3 image
      if (lines[0].trim().equals("P3")) {
        // Read dimensions and max color value
        String[] dimensions = lines[1].trim().split("\\s+");
        int width = Integer.parseInt(dimensions[0]);
        int height = Integer.parseInt(dimensions[1]);
        int maxLightValue = Integer.parseInt(dimensions[2]);

        Pixel[][] pixels = new Pixel[height][width];
        int pixelIndex = 0;

        // Read RGB data
        for (int i = 2; i < lines.length; i++) {
          String[] rgbValues = lines[i].trim().split("\\s+");
          for (int j = 0; j < rgbValues.length; j += 3) {
            int red = Integer.parseInt(rgbValues[0]);
            int green = Integer.parseInt(rgbValues[1]);
            int blue = Integer.parseInt(rgbValues[2]);

            pixels[pixelIndex / width][pixelIndex % width] = new Pixel(red, green, blue);
            pixelIndex++;
          }
        }

        // Create and set Image object
        Image image = new Image(pixels, new int[]{width, height}, filePath, "P3");
        image.setPixels(pixels);
        image.setContent(content);

        fileHandler.setLoadedImage(image);
        System.out.println("P3 Image loaded successfully:");
        System.out.println(content);
      } else {
        Image image = ImageUtils.readImageFromFile(filePath);
        image.setContent(content);
        fileHandler.setLoadedImage(image);
        System.out.println("Image loaded successfully:");
        System.out.println(content);
      }
    } catch (IOException | NumberFormatException e) {
      System.out.println("Error loading image: " + e.getMessage());
    }
  }

  private boolean isValidImageFile(String fileName) {
    String extension = getFileExtension(fileName);
    return extension != null && (extension.equals("ppm") || extension.equals("pgm") || extension.equals("pbm"));
  }

  private String getFileExtension(String fileName) {
    int dotIndex = fileName.lastIndexOf(".");
    if (dotIndex == -1 || dotIndex == fileName.length() - 1) {
      return null;
    }
    return fileName.substring(dotIndex + 1).toLowerCase();
  }
}
