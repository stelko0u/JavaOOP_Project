package bg.tu_varna.f22621629.Commands;

import bg.tu_varna.f22621629.Handlers.CommandHandler;
import bg.tu_varna.f22621629.Handlers.XMLFileHandler;
import bg.tu_varna.f22621629.Models.Session;

import java.io.*;
import java.util.Set;

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

    String direction = args.length > 1 ? args[1] : "left";

    Set<Session> sessions = fileHandler.getSessions();
    for (Session session : sessions) {
      String imagePath = IMAGES_FOLDER + session.getFileName();
      File imageFile = new File(imagePath);
      if (imageFile.exists()) {
        if (isSupportedImageFormat(session.getFileName())) {
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

          String rotatedImageData = rotateImage(imageData.toString(), direction);

          String rotatedImagePath = IMAGES_FOLDER + getRotatedFileName(session.getFileName(), direction);
          try (BufferedWriter writer = new BufferedWriter(new FileWriter(rotatedImagePath))) {
            writer.write(rotatedImageData);
          } catch (IOException e) {
            e.printStackTrace();
            return;
          }

          System.out.println("Image rotated successfully: " + session.getFileName());
        } else {
          System.out.println("Unsupported image format for rotation: " + session.getFileName());
        }
      } else {
        System.out.println("Image file not found: " + session.getFileName());
      }
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

  private String rotateImage(String imageData, String direction) {
    StringBuilder rotatedImageData = new StringBuilder();

    String[] lines = imageData.split("\n");

    if (lines[0].startsWith("P1")) {
      return imageData;
    } else if (lines[0].startsWith("P2")) {
      int height = Integer.parseInt(lines[2].split("\\s+")[1]);
      int width = lines[2].split("\\s+").length;

      char[][] pixels = new char[height][width];

      for (int i = 0; i < height; i++) {
        String[] row = lines[i + 3].split("\\s+");
        for (int j = 0; j < width; j++) {
          pixels[i][j] = row[j].charAt(0);
        }
      }

      if (direction.equals("right")) {
        for (int j = 0; j < width; j++) {
          for (int i = height - 1; i >= 0; i--) {
            rotatedImageData.append(pixels[i][j]);
            if (i > 0) {
              rotatedImageData.append(" ");
            }
          }
          if (j < width - 1) {
            rotatedImageData.append("\n");
          }
        }
      } else if (direction.equals("left")) {
        for (int j = width - 1; j >= 0; j--) {
          for (int i = 0; i < height; i++) {
            rotatedImageData.append(pixels[i][j]);
            if (i < height - 1) {
              rotatedImageData.append(" ");
            }
          }
          if (j > 0) {
            rotatedImageData.append("\n");
          }
        }
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
