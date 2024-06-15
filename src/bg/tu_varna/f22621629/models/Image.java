package bg.tu_varna.f22621629.models;

/**
 * Represents an image with its pixel data, size, name, format, and content.
 */
public class Image {
  private Pixel[][] pixels;
  private int[] sizes;
  private String imageName;
  private String format;
  private String content;

  /**
   * Constructs an Image object with specified pixel data, size, image name, and format.
   *
   * @param pixels    The 2D array of Pixel objects representing the image.
   * @param sizes     The array containing width and height of the image.
   * @param imageName The name of the image file.
   * @param format    The format of the image ("P1", "P2", or "P3").
   */
  public Image(Pixel[][] pixels, int[] sizes, String imageName, String format) {
    this.pixels = pixels;
    this.sizes = sizes;
    this.imageName = imageName;
    this.format = format;
  }

  /**
   * Returns the format of the image.
   *
   * @return The format of the image ("P1", "P2", or "P3").
   */
  public String getFormat() {
    return format;
  }

  /**
   * Returns the 2D array of Pixel objects representing the image.
   *
   * @return The 2D array of Pixel objects.
   */
  public Pixel[][] getPixels() {
    return pixels;
  }

  /**
   * Sets the pixel data of the image.
   *
   * @param pixels The 2D array of Pixel objects to set.
   */
  public void setPixels(Pixel[][] pixels) {
    this.pixels = pixels;
  }

  /**
   * Returns the name of the image.
   *
   * @return The name of the image.
   */
  public String getImageName() {
    return imageName;
  }

  /**
   * Returns the image name without the path.
   *
   * @return The image name without the path.
   */
  public String getImageNameWithoutPath() {
    String[] pathArr = this.imageName.split("/");
    return pathArr[1];
  }

  /**
   * Sets the name of the image.
   *
   * @param imageName The name of the image to set.
   */
  public void setImageName(String imageName) {
    this.imageName = imageName;
  }

  /**
   * Generates the content of the image in the specified format.
   *
   * @return The content of the image as a formatted string.
   */
  public String getContent() {
    StringBuilder contentBuilder = new StringBuilder();
    contentBuilder.append(format).append("\n");
    if (format.equals("P1") || format.equals("P2")) {
      contentBuilder.append(pixels[0].length).append(" ").append(pixels.length).append(" ").append("\n");
    }
    if (format.equals("P2")) {
      contentBuilder.append(15).append("\n");
    } else if (format.equals("P2")){
      contentBuilder.append("\n");

    }
    if (format.equals("P3")) {
      contentBuilder.append(pixels[0].length).append(" ").append(pixels.length).append(" ").append(255).append("\n");
    }
    for (Pixel[] row : pixels) {
      for (Pixel pixel : row) {
        if (format.equals("P1") || format.equals("P2")) {
          contentBuilder.append(pixel.getValue()).append(" ");
        } else {
          contentBuilder.append(pixel.getRed()).append(" ");
          contentBuilder.append(pixel.getGreen()).append(" ");
          contentBuilder.append(pixel.getBlue()).append(" ");
        }
      }
      contentBuilder.append("\n");
    }

    return contentBuilder.toString();
  }

  /**
   * Sets the content of the image.
   *
   * @param content The content of the image to set.
   */
  public void setContent(String content) {
    this.content = content;
  }

  /**
   * Converts the image data to an XML string representation.
   *
   * @return The XML string representation of the image.
   */
  public String toXMLString() {
    return "        <image name=\"" + this.imageName + "\">\n" +
            "        </image>\n";
  }

  /**
   * Returns the sizes of the image (width and height).
   *
   * @return The array containing width and height of the image.
   */
  public int[] getSizes() {
    return sizes;
  }

  /**
   * Sets the sizes of the image (width and height).
   *
   * @param sizes The array containing width and height of the image to set.
   */
  public void setSizes(int[] sizes) {
    this.sizes = sizes;
  }

  /**
   * Sets the format of the image.
   *
   * @param format The format of the image ("P1", "P2", or "P3") to set.
   */
  public void setFormat(String format) {
    this.format = format;
  }

  /**
   * Generates the content of the image from its pixel data.
   * This method is used to update the internal content based on the current pixel data.
   */
  public void setContentFromPixels() {
    StringBuilder contentBuilder = new StringBuilder();
    contentBuilder.append(format).append("\n");
    contentBuilder.append(sizes[0]).append(" ").append(sizes[1]).append("\n");

    if (format.equals("P3")) {
      contentBuilder.append(255).append("\n");
    }

    for (Pixel[] row : pixels) {
      for (Pixel pixel : row) {
        if (format.equals("P1") || format.equals("P2")) {
          contentBuilder.append(pixel.getValue()).append(" ");
        } else {
          contentBuilder.append(pixel.getRed()).append(" ");
          contentBuilder.append(pixel.getGreen()).append(" ");
          contentBuilder.append(pixel.getBlue()).append(" ");
        }
      }
      contentBuilder.append("\n");
    }

    this.content = contentBuilder.toString();
  }
}