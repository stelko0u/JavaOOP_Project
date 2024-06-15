package bg.tu_varna.f22621629.processor;

import bg.tu_varna.f22621629.models.Image;

import java.io.*;
/**
 * Handles image processing tasks such as checking if an image is a color image
 * and applying grayscale effects to images.
 */
public class ImageProcessor {

  /**
   * Applies a grayscale effect to the specified color image.
   * The grayscale image is saved as a new file with "_grayscale" appended to the original filename.
   *
   * @param image the image to process
   */
  public void applyGrayScaleEffect(Image image) {
    try (BufferedReader reader = new BufferedReader(new FileReader(image.getImageName()))) {
      StringBuilder imageAsString = new StringBuilder();
      String line;

      while((line = reader.readLine()) != null) {
        imageAsString.append(line).append("\n");
      }

      if (imageAsString.length() >= 4 && imageAsString.toString().startsWith("P3")) {
        String[] imageAsStringArray = imageAsString.toString().split("\n");
        String[] dimensions = imageAsStringArray[1].split(" ");
        String[] fullFilePath = image.getImageName().split("/");
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
