package bg.tu_varna.f22621629.utils;
import bg.tu_varna.f22621629.models.Image;
import bg.tu_varna.f22621629.processor.ImageProcessor;

import java.io.*;


/**
 * Utility class for handling file operations related to images.
 */
public class FileUtils {

  private static FileUtils instance;

  /**
   * Returns the singleton instance of FileUtils.
   *
   * @return The singleton instance of FileUtils.
   */
  public static FileUtils getInstance() {
    if (instance == null) {
      synchronized (FileUtils.class) {
        if (instance == null) {
          instance = new FileUtils();
        }
      }
    }
    return instance;
  }

  /**
   * Writes image content to a file specified by fileName.
   *
   * @param fileName The name of the file to write to.
   * @param image    The image object containing the content to write.
   * @throws IOException if an I/O error occurs while writing to the file.
   */
  public void writeFile(String fileName, Image image) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
      writer.write(image.getContent());
    }
  }

  /**
   * Checks if a file with the specified name exists.
   *
   * @param imageName The name of the file to check.
   * @return true if the file exists, false otherwise.
   */
  public static boolean fileExists(String imageName) {
    return new File(imageName).exists();
  }

  /**
   * Saves a collage image to a file using its original name.
   *
   * @param outImage The collage image to save.
   * @throws IOException if an I/O error occurs while writing to the file.
   */
  public void saveCollageToFile(Image outImage) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(outImage.getImageName()))) {
      writer.write(outImage.getContent());
    }
    System.out.println("Collage created successfully and added to the current session: " + outImage.getImageName());
  }
  /**
   * Generates a new file name for a rotated image based on the original image's name and rotation direction.
   *
   * @param originalImage The original image object.
   * @param direction     The direction of rotation ("left" or "right").
   * @return The new file name for the rotated image.
   */
  public static String getRotatedFileName(Image originalImage, String direction) {
    int dotIndex = originalImage.getImageNameWithoutPath().lastIndexOf(".");
    String fileNameWithoutExtension = originalImage.getImageNameWithoutPath().substring(0, dotIndex);
    return fileNameWithoutExtension + "_rotated_" + direction + originalImage.getImageNameWithoutPath().substring(dotIndex);
  }
}
