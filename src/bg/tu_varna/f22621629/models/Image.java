package bg.tu_varna.f22621629.models;

/**
 * The Image class represents an image with content and a name.
 */
public class Image {
  private String content;
  private String name;

  /**
   * Constructs an Image object with the specified content.
   *
   * @param content the content of the image
   */
  public Image(String content) {
    this.content = content;
  }

  /**
   * Sets the content of the image.
   *
   * @param content the new content of the image
   */
  public void setContent(String content) {
    this.content = content;
  }

  /**
   * Returns the content of the image.
   *
   * @return the content of the image
   */
  public String getContent() {
    return content;
  }

  /**
   * Sets the name of the image.
   *
   * @param name the new name of the image
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Returns the name of the image.
   *
   * @return the name of the image
   */
  public String getName() {
    return name;
  }

  /**
   * Converts the image object to an XML string representation.
   *
   * @return the XML string representation of the image
   */
  public String toXMLString() {
    return "        <image name=\"" + this.name + "\">\n" +
            "        </image>\n";
  }
}
