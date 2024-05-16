package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;
import bg.tu_varna.f22621629.models.Session;

import java.io.*;
import java.util.List;

/**
 * The MonoChromeCommand class represents a command for converting color images to monochrome.
 */
public class MonoChromeCommand implements CommandHandler {

  private XMLFileHandler fileHandler;

  /**
   * Constructs a MonoChromeCommand object.
   */
  public MonoChromeCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
  }

  /**
   * Executes the monochrome conversion command.
   * @param args Command arguments (not used).
   * @throws IOException If an I/O error occurs.
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
        applyMonochromeEffect(filePath);
      }
    }
  }

  /**
   * Checks if the image file is in color format.
   * @param fileName The name of the image file.
   * @return True if the image is in color format, false otherwise.
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
   * Applies the monochrome effect to the color image.
   * @param fileName The name of the image file.
   */
  private void applyMonochromeEffect(String fileName) {
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
      StringBuilder imageAsString = new StringBuilder();
      String line;

      while ((line = reader.readLine()) != null) {
        imageAsString.append(line).append("\n");
      }

      if (imageAsString.length() >= 4 && imageAsString.toString().startsWith("P3")) {
        String[] imageAsStringArray = imageAsString.toString().split("\n");
        String[] dimensions = imageAsStringArray[1].split(" ");
        String[] fullFilePath = fileName.split("/");
        String[] fileAndExtension = fullFilePath[1].split("\\.");
        String newFile = fullFilePath[0] + "/" + fileAndExtension[0] + "_monochrome." + fileAndExtension[1];

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(newFile))) {
          writer.write("P1\n");
          writer.write(String.join(" ", dimensions) + "\n");

          for (int i = 3; i < imageAsStringArray.length; i++) {
            line = imageAsStringArray[i];
            String[] pixelValues = line.trim().split("\\s+");

            for (int j = 0; j < pixelValues.length; j += 3) {
              int red = Integer.parseInt(pixelValues[j]);
              int green = Integer.parseInt(pixelValues[j + 1]);
              int blue = Integer.parseInt(pixelValues[j + 2]);

              int grayValue = (int) (0.299 * red + 0.587 * green + 0.114 * blue);
              int monochromeValue = grayValue < 128 ? 0 : 1;
              writer.write(monochromeValue + " ");
            }
            writer.write("\n");
          }
          System.out.println("Successfully converted image to monochrome: " + newFile);
        } catch (IOException e) {
          e.printStackTrace();
        }
      } else {
        System.out.println("The image format is not supported for conversion to monochrome.");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
