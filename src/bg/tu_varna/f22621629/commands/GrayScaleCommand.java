package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;
import bg.tu_varna.f22621629.models.Session;

import java.io.*;
import java.util.List;
/**
 * The GrayScaleCommand class implements the CommandHandler interface to handle the grayscale conversion command.
 * It converts color images to grayscale images in the current session.
 */
public class GrayScaleCommand implements CommandHandler {

  private XMLFileHandler fileHandler;

  /**
   * Constructs a GrayScaleCommand object and initializes the XMLFileHandler instance.
   */
  public GrayScaleCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
  }

  /**
   * Executes the command to convert color images to grayscale images in the current session.
   * @param args The command arguments (not used in this command).
   * @throws IOException if an I/O error occurs.
   */
  @Override
  public void execute(String[] args) throws IOException {
    if (!fileHandler.isFileOpened()) {
      System.out.println("No file is currently open. Please open a file first.");
      return;
    }

    Session currentSession = fileHandler.getCurrentSession();
    if (currentSession == null) {
      System.out.println("No session is currently active. Please start a session first.");
      return;
    }

    List<String> fileNames = currentSession.getFileNames();
    for (String fileName : fileNames) {
      String filePath = "images/" + fileName;
      if (!new File(filePath).exists()) {
        System.out.println("File '" + fileName + "' not found in the current session. Skipping.");
        continue;
      }

      if (isColorImage(filePath)) {
        applyGrayScaleEffect(filePath);
      }
    }
  }

  /**
   * Checks if the image file is in color format.
   * @param fileName The file name of the image.
   * @return true if the image is in color format, false otherwise.
   */
  private boolean isColorImage(String fileName) {
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
      String line;
      if ((line = reader.readLine()) != null) {
        return line.startsWith("P3");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Applies the grayscale effect to the color image and saves the result.
   * @param fileName The file name of the image to be converted.
   */
  private void applyGrayScaleEffect(String fileName) {
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
      StringBuilder imageAsString = new StringBuilder();
      String line;

      while((line = reader.readLine()) != null) {
        imageAsString.append(line).append("\n");
      }

      if (imageAsString.length() >= 4 && imageAsString.toString().startsWith("P3")) {
        String[] imageAsStringArray = imageAsString.toString().split("\n");
        String[] dimensions = imageAsStringArray[1].split(" ");
        String[] fullFilePath = fileName.split("/");
        int maxValue = Integer.parseInt(dimensions[2]);
        String[] fileAndExtension = fullFilePath[1].split("\\.");
        String newFile = fullFilePath[0] + "/" + fileAndExtension[0] + "_grayscale." +  fileAndExtension[1];

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(newFile))) {
          writer.write("P3\n");
          writer.write(String.join(" ", dimensions) + "\n");

          for (int i = 2; i < imageAsStringArray.length; i++) {
            line = imageAsStringArray[i];
            String[] pixelValues = line.trim().split("\\s+");
            for (String pixelValue : pixelValues) {
              int grayValue = (int) (0.299 * Integer.parseInt(pixelValues[0]) +
                      0.587 * Integer.parseInt(pixelValues[1]) +
                      0.114 * Integer.parseInt(pixelValues[2]));
              writer.write(grayValue + " ");
            }
            writer.write("\n");
          }
          System.out.println("Successfully grayscaled image: " + newFile);
        } catch (IOException e) {
          e.printStackTrace();
        }
      } else {
        System.out.println("The image format is not supported for conversion to grayscale.");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
