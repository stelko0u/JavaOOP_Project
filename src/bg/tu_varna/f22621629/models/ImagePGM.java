package bg.tu_varna.f22621629.models;

/**
 * Represents a PGM format image, a subclass of the Image class.
 * Provides specific implementation for PGM image format.
 */
public class ImagePGM extends Image{
  private int maxGrayValue;

  /**
   * Constructs an ImagePGM object with specified pixel data, size, image name, format ("P2"),
   * and maximum gray value.
   *
   * @param pixels       The 2D array of Pixel objects representing the PGM image.
   * @param sizes        The array containing width and height of the PGM image.
   * @param imageName    The name of the PGM image file.
   * @param format       The format of the PGM image ("P2").
   * @param maxGrayValue The maximum gray value (typically 15) allowed in the PGM image.
   */
  public ImagePGM(Pixel[][] pixels, int[] sizes, String imageName, String format, int maxGrayValue) {
    super(pixels, sizes, imageName, format);
    this.maxGrayValue = maxGrayValue;
  }

  /**
   * Returns the format of the PGM image, which is always "P2".
   *
   * @return The format of the PGM image ("P2").
   */
  @Override
  public String getFormat() {
    return "P2";
  }

  /**
   * Returns the maximum gray value allowed in the PGM image.
   *
   * @return The maximum gray value (typically 15) allowed in the PGM image.
   */
  public int getMaxGrayValue() {
    return 15;
  }
}
