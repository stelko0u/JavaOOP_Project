package bg.tu_varna.f22621629.models;


/**
 * Represents a PBM format image, a subclass of the Image class.
 * It provides specific implementation for PBM image format.
 */
public class ImagePBM extends Image{
  /**
   * Constructs an ImagePBM object with specified pixel data, size, image name, and format ("P1").
   *
   * @param pixels    The 2D array of Pixel objects representing the PBM image.
   * @param sizes     The array containing width and height of the PBM image.
   * @param imageName The name of the PBM image file.
   * @param format    The format of the PBM image ("P1").
   */
  public ImagePBM(Pixel[][] pixels, int[] sizes, String imageName, String format) {
    super(pixels, sizes, imageName, format);
  }

  /**
   * Returns the format of the PBM image, which is always "P1".
   *
   * @return The format of the PBM image ("P1").
   */
  @Override
  public String getFormat() {
    return "P1";
  }
}
