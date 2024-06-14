package bg.tu_varna.f22621629.models;

public class ImagePPM extends Image{
  private int maxColorValue;

  public ImagePPM(Pixel[][] pixels, int[] sizes, String imageName, String format, int maxColorValue) {
    super(pixels, sizes, imageName, format);
//    super(pixels);
    this.maxColorValue = maxColorValue;
  }

  public int getMaxColorValue() {
    return maxColorValue;
  }

  @Override
  public String getFormat() {
    return "P3";
  }

  public int getMaxGrayValue() {
    return 255;
  }
}
