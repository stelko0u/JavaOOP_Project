package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;

import java.io.*;
/**
 * The NegativeCommand class implements the CommandHandler interface to handle the application of negative effect to an image.
 * It interacts with an XMLFileHandler instance to manage file operations.
 */
public class NegativeCommand implements CommandHandler {

  private XMLFileHandler fileHandler;
  private static final String IMAGES_FOLDER = "images\\";

  /**
   * Constructs a NegativeCommand object and initializes the XMLFileHandler instance.
   */
  public NegativeCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
  }

  /**
   * Executes the command to apply negative effect to the loaded image.
   * @param args The command arguments (not used in this command).
   * @throws IOException if an I/O error occurs.
   */
  @Override
  public void execute(String[] args) throws IOException {
    XMLFileHandler fileHandler = XMLFileHandler.getInstance();
    if (!fileHandler.isFileOpened()) {
      System.out.println("No file is currently open. Please open a file first.");
      return;
    }
    String loadedImageAsString = fileHandler.getLoadedImage();
    if (loadedImageAsString == null || loadedImageAsString.length() == 0) {
      System.out.println("No image is currently loaded. Please load an image first.");
      return;
    }
    StringBuilder loadedImageBuffer = new StringBuilder(loadedImageAsString);
    if (loadedImageBuffer == null || loadedImageBuffer.length() == 0) {
      System.out.println("No image is currently loaded. Please load an image first.");
      return;
    }

    String modifiedImageData = applyNegativeEffect(loadedImageBuffer.toString());

    String negativeFileName = "negative_" + fileHandler.getFileNameLoadedImage();
    String negativeImagePath = IMAGES_FOLDER + negativeFileName;
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(negativeImagePath))) {
      writer.write(modifiedImageData);
      System.out.println("Negative effect applied successfully to: " + fileHandler.getFileNameLoadedImage());

      String currentFile = fileHandler.getFileName();
      if (currentFile == null) {
        System.out.println("Error: No file is currently open.");
        return;
      }

      int nextId = fileHandler.getSessions().size() + 1;

      String newImageElement = "<session id=\"" + nextId + "\">\n" +
              "    <image name=\"" + negativeFileName + "\">\n" +
              "    </image>\n" +
              "</session>\n";
      fileHandler.setNextLocalImage(newImageElement);
      System.out.println("Image added to session successfully.");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Applies the negative effect to the image data.
   * @param imageData The image data as a string.
   * @return The modified image data after applying the negative effect.
   */
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

  /**
   * Inverts the value to apply the negative effect.
   * @param value The original value.
   * @return The inverted value.
   */
  private int invert(int value) {
    return 255 - value;
  }

  /**
   * Generates the negative file name for the original file.
   * @param originalFileName The original file name.
   * @return The negative file name.
   */
  private String getNegativeFileName(String originalFileName) {
    int dotIndex = originalFileName.lastIndexOf(".");
    String fileNameWithoutExtension = originalFileName.substring(0, dotIndex);
    return fileNameWithoutExtension + "_negative." + originalFileName.substring(dotIndex + 1);
  }
}
