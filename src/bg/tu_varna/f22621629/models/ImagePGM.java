package bg.tu_varna.f22621629.models;

public class ImagePGM extends Image{
  private int maxGrayValue;


  public ImagePGM(Pixel[][] pixels, int[] sizes, String imageName, String format, int maxGrayValue) {
    super(pixels, sizes, imageName, format);

    this.maxGrayValue = maxGrayValue;
  }


  @Override
  public String getFormat() {
    return "P2";
  }

  public int getMaxGrayValue() {
    return 15;
  }
}
