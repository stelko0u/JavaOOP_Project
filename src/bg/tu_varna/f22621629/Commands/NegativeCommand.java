package bg.tu_varna.f22621629.Commands;

import bg.tu_varna.f22621629.Handlers.CommandHandler;
import bg.tu_varna.f22621629.Handlers.FileExceptionHandler;
import bg.tu_varna.f22621629.Handlers.XMLFileHandler;
import bg.tu_varna.f22621629.Models.Session;

import java.io.*;
import java.util.Set;


public class NegativeCommand implements CommandHandler {
  private XMLFileHandler fileHandler;
  private static final String IMAGES_FOLDER = "images\\";

  public NegativeCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
  }

  @Override
  public void execute(String[] args) throws IOException {
    XMLFileHandler fileHandler = XMLFileHandler.getInstance();
    if (!fileHandler.isFileOpened()) {
      System.out.println("No file is currently open. Please open a file first.");
      return;
    }

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

          String modifiedImageData = applyNegativeEffect(imageData.toString());

          String negativeImagePath = IMAGES_FOLDER + getNegativeFileName(session.getFileName());
          try (BufferedWriter writer = new BufferedWriter(new FileWriter(negativeImagePath))) {
            writer.write(modifiedImageData);
          } catch (IOException e) {
            e.printStackTrace();
            return;
          }

          System.out.println("Negative effect applied successfully to: " + session.getFileName());
        } else {
          System.out.println("Unsupported image format for negative effect: " + session.getFileName());
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

  private String applyNegativeEffect(String imageData) {
    StringBuilder modifiedImageData = new StringBuilder();

    String[] lines = imageData.split("\n");

    modifiedImageData.append(lines[0]).append("\n");

    if (lines[0].equals("P1")) {
      modifiedImageData.append(lines[1]).append("\n");

      for (int i = 2; i < lines.length; i++) {
        String line = lines[i];

        for (int j = 0; j < line.length(); j++) {
          char pixelChar = line.charAt(j);

          if (pixelChar == '0') {
            modifiedImageData.append('1');
          } else if (pixelChar == '1') {
            modifiedImageData.append('0');
          } else {
            modifiedImageData.append(pixelChar);
          }

          if (j < line.length() - 1) {
            modifiedImageData.append(" ");
          }
        }

        if (i < lines.length - 1) {
          modifiedImageData.append("\n");
        }
      }
    } else if (lines[0].startsWith("P2")) {
      for (int i = 1; i < 4; i++) {
        modifiedImageData.append(lines[i]).append("\n");
      }

      for (int i = 4; i < lines.length; i++) {
        String line = lines[i];

        String[] values = line.trim().split("\\s+");

        for (int j = 0; j < values.length; j++) {
          int value = Integer.parseInt(values[j]);

          int invertedValue = invert(value);

          modifiedImageData.append(invertedValue);

          if (j < values.length - 1) {
            modifiedImageData.append(" ");
          }
        }

        if (i < lines.length - 1) {
          modifiedImageData.append("\n");
        }
      }
    } else if (lines[0].startsWith("P3")) {
      modifiedImageData.append(lines[1]).append("\n");

      for (int i = 2; i < lines.length; i++) {
        String line = lines[i];

        String[] values = line.trim().split("\\s+");

        for (int j = 0; j < values.length; j++) {
          int value = Integer.parseInt(values[j]);

          int invertedValue = invert(value);

          modifiedImageData.append(invertedValue);

          if (j < values.length - 1) {
            modifiedImageData.append(" ");
          }
        }

        if (i < lines.length - 1) {
          modifiedImageData.append("\n");
        }
      }
    }

    return modifiedImageData.toString();
  }

  private int invert(int value) {
    return 255 - value;
  }

  private String getNegativeFileName(String originalFileName) {
    int dotIndex = originalFileName.lastIndexOf(".");
    String fileNameWithoutExtension = originalFileName.substring(0, dotIndex);
    return fileNameWithoutExtension + "_negative." + originalFileName.substring(dotIndex + 1);
  }
}