package bg.tu_varna.f22621629.handlers;

import bg.tu_varna.f22621629.models.Image;

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
   * Reads the image data from the file specified by the imagePath.
   *
   * @return the content of the image as a string
   * @throws IOException if an I/O error occurs while reading the image file
   */
  public String readImageData() throws IOException {
    Image imageData = new Image("");
    StringBuilder sb = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new FileReader(imagePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        sb.append(line).append("\n");
      }
      imageData.setContent(sb.toString());
    }
    return imageData.getContent();
  }

  /**
   * Writes the image data to the file specified by the rotatedImage.
   *
   * @param rotatedImage the image object containing the file name to write to
   * @param rotatedImageData the image object containing the content to write
   * @throws IOException if an I/O error occurs while writing the image file
   */
  public void writeImageData(Image rotatedImage, Image rotatedImageData) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(rotatedImage.getName()))) {
      writer.write(rotatedImageData.getContent());
    }
  }
}
