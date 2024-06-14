package bg.tu_varna.f22621629.models;


public class Image {
  private Pixel[][] pixels;
  private int[] sizes;
  private String imageName;
  private String format;
  private String content;

  public Image(Pixel[][] pixels, int[] sizes, String imageName, String format) {
    this.pixels = pixels;
    this.sizes = sizes;
    this.imageName = imageName;
    this.format = format;
  }

  public String getFormat() {
    return format;
  }

  public Pixel[][] getPixels() {
    return pixels;
  }

  public void setPixels(Pixel[][] pixels) {
    this.pixels = pixels;
  }

  public String getImageName() {
    return imageName;
  }
  public String getImageNameWithoutPath() {
    String[] pathArr = this.imageName.split("/");
    return pathArr[1];
  }

  public void setImageName(String imageName) {
    this.imageName = imageName;
  }

  public String getContent() {
    StringBuilder contentBuilder = new StringBuilder();
    contentBuilder.append(format).append("\n");
    if (format.equals("P1") || format.equals("P2")) {
      contentBuilder.append(pixels[0].length).append(" ").append(pixels.length).append(" ");
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

  public void setContent(String content) {
    this.content = content;
  }
  public String toXMLString() {
    return "        <image name=\"" + this.imageName + "\">\n" +
            "        </image>\n";
  }

  public int[] getSizes() {
    return sizes;
  }

  public void setSizes(int[] ints) {
    this.sizes = sizes;
  }

  public void setFormat(String format) {
    this.format = format;
  }
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