package bg.tu_varna.f22621629.utils;

import java.io.*;
/**
 * Utility class for file operations.
 * This class provides methods for writing to files, checking file existence,
 * saving collages to files, and generating rotated file names.
 * It follows the Singleton pattern to ensure a single instance.
 */
public class FileUtils {

  private static FileUtils instance;
  /**
   * Returns the singleton instance of the FileUtils class.
   * Ensures that only one instance of the class is created.
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
   * Writes the given content to a file with the specified name.
   * If the file already exists, it will be overwritten.
   *
   * @param fileName the name of the file to write to
   * @param content the content to write to the file
   * @throws IOException if an I/O error occurs
   */
  public static void writeFile(String fileName, String content) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
      writer.write(content);
    }
  }
  /**
   * Checks if a file exists at the specified file path.
   *
   * @param filePath the path of the file to check
   * @return true if the file exists, false otherwise
   */
  public static boolean fileExists(String filePath) {
    return new File(filePath).exists();
  }
  /**
   * Saves collage data to a file in the images directory.
   * The collage data is written to a file with the specified output image name.
   *
   * @param collageData the data of the collage to save
   * @param outImage the name of the output image file
   * @throws IOException if an I/O error occurs
   */
  public void saveCollageToFile(String collageData, String outImage) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("images/" + outImage))) {
      writer.write(collageData);
    }
    System.out.println("Collage created successfully and added to the current session: " + outImage);
  }
  /**
   * Generates a rotated file name based on the original file name and rotation direction.
   * The new file name includes the rotation direction before the file extension.
   *
   * @param originalFileName the original file name
   * @param direction the direction of rotation (e.g., "left" or "right")
   * @return the generated file name with rotation direction included
   */
  public static String getRotatedFileName(String originalFileName, String direction) {
    int dotIndex = originalFileName.lastIndexOf(".");
    String fileNameWithoutExtension = originalFileName.substring(0, dotIndex);
    return fileNameWithoutExtension + "_rotated_" + direction + originalFileName.substring(dotIndex);
  }
}
