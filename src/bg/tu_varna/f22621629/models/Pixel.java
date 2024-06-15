package bg.tu_varna.f22621629.models;

/**
 * Represents a pixel in an image, characterized by its RGB color components or a single value.
 */
public class Pixel {
  private int red;
  private int green;
  private int blue;
  private int value;

  /**
   * Constructs a Pixel object with specified RGB color components.
   *
   * @param red   The red component of the pixel color.
   * @param green The green component of the pixel color.
   * @param blue  The blue component of the pixel color.
   */
  public Pixel(int red, int green, int blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  /**
   * Constructs a Pixel object with a single value (typically for grayscale images).
   *
   * @param value The value of the pixel (for grayscale).
   */
  public Pixel(int value) {
    this.value = value;
  }

  /**
   * Returns the red component of the pixel color.
   *
   * @return The red component of the pixel color.
   */
  public int getRed() {
    return red;
  }

  /**
   * Returns the green component of the pixel color.
   *
   * @return The green component of the pixel color.
   */
  public int getGreen() {
    return green;
  }

  /**
   * Returns the blue component of the pixel color.
   *
   * @return The blue component of the pixel color.
   */
  public int getBlue() {
    return blue;
  }

  /**
   * Returns the value of the pixel (for grayscale).
   *
   * @return The value of the pixel (for grayscale).
   */
  public int getValue() {
    return value;
  }

  /**
   * Sets the value of the pixel (for grayscale).
   *
   * @param value The value to set.
   */
  public void setValue(int value) {
    this.value = value;
  }
}
