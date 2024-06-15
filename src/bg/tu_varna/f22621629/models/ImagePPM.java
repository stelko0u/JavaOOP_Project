package bg.tu_varna.f22621629.models;

/**
 * Represents a PPM format image, a subclass of the Image class.
 * Provides specific implementation for PPM image format.
 */
public class ImagePPM extends Image{
  private int maxColorValue;

  /**
   * Constructs an ImagePPM object with specified pixel data, size, image name, format ("P3"),
   * and maximum color value.
   *
   * @param pixels        The 2D array of Pixel objects representing the PPM image.
   * @param sizes         The array containing width and height of the PPM image.
   * @param imageName     The name of the PPM image file.
   * @param format        The format of the PPM image ("P3").
   * @param maxColorValue The maximum color value (typically 255) allowed in the PPM image.
   */
  public ImagePPM(Pixel[][] pixels, int[] sizes, String imageName, String format, int maxColorValue) {
    super(pixels, sizes, imageName, format);
    this.maxColorValue = maxColorValue;
  }

  /**
   * Returns the maximum color value allowed in the PPM image.
   *
   * @return The maximum color value (typically 255) allowed in the PPM image.
   */
  public int getMaxColorValue() {
    return maxColorValue;
  }

  /**
   * Returns the format of the PPM image, which is always "P3".
   *
   * @return The format of the PPM image ("P3").
   */
  @Override
  public String getFormat() {
    return "P3";
  }

  /**
   * Returns the maximum gray value allowed in the PPM image, which is 255.
   * This method is specific to PPM images.
   *
   * @return The maximum gray value (255) allowed in the PPM image.
   */
  public int getMaxGrayValue() {
    return 255;
  }
}
