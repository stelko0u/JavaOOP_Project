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
   * Writes the image data to the file specified by the rotatedImage.
   *
   * @param rotatedImage the image object containing the file name to write to
   * @throws IOException if an I/O error occurs while writing the image file
   */
  public void writeImageData(Image rotatedImage) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(rotatedImage.getImageName()))) {
      writer.write(rotatedImage.getContent());
    }
  }
}