package bg.tu_varna.f22621629.models;


public class ImagePBM extends Image{
  public ImagePBM(Pixel[][] pixels, int[] sizes, String imageName, String format) {
    super(pixels, sizes, imageName, format);
  }

  @Override
  public String getFormat() {
    return "P1";
  }
}
