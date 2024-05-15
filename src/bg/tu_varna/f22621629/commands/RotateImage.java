package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;

import java.io.*;

public class RotateImage implements CommandHandler {
  private XMLFileHandler fileHandler;
  private static final String IMAGES_FOLDER = "images\\";

  public RotateImage() {
    this.fileHandler = XMLFileHandler.getInstance();
  }

  @Override
  public void execute(String[] args) throws IOException {
    XMLFileHandler fileHandler = XMLFileHandler.getInstance();
    if (!fileHandler.isFileOpened()) {
      System.out.println("No file is currently open. Please open a file first.");
      return;
    }
    if (fileHandler.getFileNameLoadedImage() == null) {
      System.out.println("No loaded image! Please load a image first!");
      return;
    }

    if (args.length != 2) {
      System.out.println("Usage: rotate <left/right>");
      return;
    }

    if (!args[1].equalsIgnoreCase("left") || !args[1].equalsIgnoreCase("right")) {
      System.out.println("Usage only 'left' and 'right' directions!");
        return;
    }
    String direction = args.length > 1 ? args[1] : "left";

    String loadedImage = fileHandler.getLoadedImage();
    String loadedImageFilePath = fileHandler.getFileNameLoadedImage();
    if(fileHandler.getLoadedImage() == null) {
      return;
    }





      String imagePath = IMAGES_FOLDER  + loadedImageFilePath;
      File imageFile = new File(imagePath);
      if (imageFile.exists()) {
        if (isSupportedImageFormat(loadedImageFilePath)) {
          StringBuilder imageData = new StringBuilder();
          try (BufferedReader reader = new BufferedReader(new FileReader(imageFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
              imageData.append(line).append("\n");
            }
          } catch (IOException e) {
            e.printStackTrace();
            return;
          }

          String rotatedImageData = rotateImage(loadedImageFilePath ,imageData.toString(), direction);

          String rotatedImagePath = IMAGES_FOLDER + getRotatedFileName(loadedImageFilePath, direction);
          try (BufferedWriter writer = new BufferedWriter(new FileWriter(rotatedImagePath))) {
            writer.write(rotatedImageData);
          } catch (IOException e) {
            e.printStackTrace();
            return;
          }

          System.out.println("Image rotated successfully: " + imagePath);
        } else {
          System.out.println("Unsupported image format for rotation: " + loadedImage);
        }
      } else {
        System.out.println("Image file not found: " + loadedImage);
      }

  }

  private boolean isSupportedImageFormat(String fileName) {
    String[] supportedFormats = {"ppm", "pgm", "pbm"};
    for (String format : supportedFormats) {
      if (fileName.toLowerCase().endsWith(format)) {
        return true;
      }
    }
    return false;
  }

  private String rotateImage(String fileName, String imageData, String direction) {
    StringBuilder rotatedImageData = new StringBuilder();
    String[] lines = imageData.split("\n");


    if (lines[0].startsWith("P1")) {
      String[][] rotatedImage = null;
      String[][] pixels;
      String[] sizes;
      int width = 0;
      int height = 0;

      // P1 RIGHT
      if (direction.equalsIgnoreCase("right")) {
          sizes = lines[1].split(" ");
          width = Integer.parseInt(sizes[0]);
          height = Integer.parseInt(sizes[1]);

          pixels = new String[height][];
          rotatedImage = new String[width][height];
          for (int i = 2; i < lines.length; i++) {
            String[] row = lines[i].split("\\s+");
            pixels[i - 2] = row;
          }

          for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
              rotatedImage[j][i] = pixels[i][width - 1 - j];
            }
          }
        }
        // P1 LEFT
        if (direction.equalsIgnoreCase("left")) {
        sizes = lines[1].split(" ");
        width = Integer.parseInt(sizes[0]);
        height = Integer.parseInt(sizes[1]);

        pixels = new String[height][];
        rotatedImage = new String[width][height];

        for (int i = 2; i < lines.length; i++) {
          String[] row = lines[i].split("\\s+");
          pixels[i - 2] = row;
        }

          for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
              rotatedImage[width - 1 - j][i] = pixels[i][j];
            }
          }
      }


        rotatedImageData.append("P1").append("\n");
        rotatedImageData.append(width).append(" ").append(height).append("\n");
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          rotatedImageData.append(rotatedImage[j][i]).append(" ");
        }
        rotatedImageData.append("\n");
      }
    }


    if (lines[0].startsWith("P2")) {
      String[] sizes = lines[2].split(" ");
      int width = Integer.parseInt(sizes[0]);
      int height = Integer.parseInt(sizes[1]);

      int[][] pixels = new int[height][width];

      int lineIndex = 4;
      for (int i = 0; i < height; i++) {
        String[] pixelValues = lines[lineIndex++].trim().split("\\s+");
        for (int j = 0; j < width; j++) {
          pixels[i][j] = Integer.parseInt(pixelValues[j]);
        }
      }

      int[][] rotatedImage = new int[width][height];
      for (int i = 0; i < width; i++) {
        for (int j = 0; j < height; j++) {
          rotatedImage[i][j] = pixels[j][width - 1 - i];
        }
      }

      System.out.println("P2");
      System.out.println("# stelko_rotated.pgm");
      System.out.println(height + " " + width);
      System.out.println("15");
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          System.out.print(rotatedImage[i][j] + " ");
        }
        System.out.println();
      }




    } else if (lines[0].startsWith("P3")) {
      int width = Integer.parseInt(lines[1].split("\\s+")[0]);
      int height = Integer.parseInt(lines[1].split("\\s+")[1]);

      if (direction.equals("right")) {
        rotatedImageData.append(lines[0]).append("\n");
        rotatedImageData.append(lines[1]).append("\n");

        for (int j = 0; j < width; j++) {
          for (int i = height - 1; i >= 0; i--) {
            String[] rgb = lines[i + 2].split("\\s+");
            rotatedImageData.append(rgb[j * 3]).append(" ");
            rotatedImageData.append(rgb[j * 3 + 1]).append(" ");
            rotatedImageData.append(rgb[j * 3 + 2]);

            if (i > 0) {
              rotatedImageData.append(" ");
            }
          }
          if (j < width - 1) {
            rotatedImageData.append("\n");
          }
        }
      } else if (direction.equals("left")) {
        rotatedImageData.append(lines[0]).append("\n");
        rotatedImageData.append(lines[1]).append("\n");

        for (int j = width - 1; j >= 0; j--) {
          for (int i = 0; i < height; i++) {
            String[] rgb = lines[i + 2].split("\\s+");
            rotatedImageData.append(rgb[j * 3]).append(" ");
            rotatedImageData.append(rgb[j * 3 + 1]).append(" ");
            rotatedImageData.append(rgb[j * 3 + 2]);

            if (i < height - 1) {
              rotatedImageData.append(" ");
            }
          }
          if (j > 0) {
            rotatedImageData.append("\n");
          }
        }
      }
    }
    return rotatedImageData.toString();
  }


  private String getRotatedFileName(String originalFileName, String direction) {
    int dotIndex = originalFileName.lastIndexOf(".");
    String fileNameWithoutExtension = originalFileName.substring(0, dotIndex);
    return fileNameWithoutExtension + "_rotated_" + direction + "." + originalFileName.substring(dotIndex + 1);
  }
}
