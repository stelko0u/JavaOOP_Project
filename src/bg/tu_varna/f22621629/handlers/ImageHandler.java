package bg.tu_varna.f22621629.handlers;

import java.io.*;
/**
 * Handles reading and writing image data to and from files.
 */
public class ImageHandler {
  /** The path to the image file. */
  private final String imagePath;
  /**
   * Constructs an ImageHandler with the specified image file path.
   *
   * @param imagePath the path to the image file
   */
  public ImageHandler(String imagePath) {
    this.imagePath = imagePath;
  }
  /**
   * Reads the image data from the specified image file.
   *
   * @return the image data read from the file
   * @throws IOException if an I/O error occurs while reading the file
   */
  public String readImageData() throws IOException {
    StringBuilder imageData = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new FileReader(imagePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        imageData.append(line).append("\n");
      }
    }
    return imageData.toString();
  }
  /**
   * Writes the rotated image data to the specified file path.
   *
   * @param rotatedImagePath the path to write the rotated image data
   * @param rotatedImageData the rotated image data to write to the file
   * @throws IOException if an I/O error occurs while writing the file
   */
  public void writeImageData(String rotatedImagePath, String rotatedImageData) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(rotatedImagePath))) {
      writer.write(rotatedImageData);
    }
  }
}
