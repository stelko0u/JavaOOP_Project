package bg.tu_varna.f22621629.utils;
import bg.tu_varna.f22621629.models.Image;
import java.io.*;

/**
 * Utility class for file operations such as writing, checking existence, and saving collages.
 * This class follows the singleton pattern to ensure only one instance is created.
 */
public class FileUtils {

  private static FileUtils instance;

  /**
   * Returns the singleton instance of the FileUtils class.
   * Uses double-checked locking for thread safety.
   *
   * @return the singleton instance of FileUtils
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
   * Writes the content of an Image object to a file.
   *
   * @param fileName the name of the file to write to
   * @param image the Image object containing the content to write
   * @throws IOException if an I/O error occurs
   */
  public static void writeFile(String fileName, Image image) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
      writer.write(image.getContent());
    }
  }

  /**
   * Checks if a file exists at the specified path.
   *
   * @param filePath the path of the file to check
   * @return true if the file exists, false otherwise
   */
  public static boolean fileExists(String filePath) {
    return new File(filePath).exists();
  }

  /**
   * Saves collage data to a specified file.
   *
   * @param collageData the data of the collage to save
   * @param outImage the name of the output file
   * @throws IOException if an I/O error occurs
   */
  public void saveCollageToFile(String collageData, String outImage) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("images/" + outImage))) {
      writer.write(collageData);
    }
    System.out.println("Collage created successfully and added to the current session: " + outImage);
  }

  /**
   * Generates a file name for a rotated image based on the original file name and rotation direction.
   *
   * @param originalFileName the original file name
   * @param direction the direction of rotation (e.g., "left", "right")
   * @return the new file name with rotation information
   */
  public static String getRotatedFileName(String originalFileName, String direction) {
    int dotIndex = originalFileName.lastIndexOf(".");
    String fileNameWithoutExtension = originalFileName.substring(0, dotIndex);
    return fileNameWithoutExtension + "_rotated_" + direction + originalFileName.substring(dotIndex);
  }
}
