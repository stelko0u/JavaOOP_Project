package bg.tu_varna.f22621629.models;

public class Pixel {
  private int red;
  private int green;
  private int blue;
  private int value;


  public Pixel(int red, int green, int blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
  }


  public Pixel(int value) {
    this.value = value;
  }

  public int getRed() {
    return red;
  }

  public int getGreen() {
    return green;
  }

  public int getBlue() {
    return blue;
  }

  public int getValue() {
    return value;
  }

  public void setRed(int red) {
    this.red = red;
  }

  public void setGreen(int green) {
    this.green = green;
  }

  public void setBlue(int blue) {
    this.blue = blue;
  }

  public void setValue(int value) {
    this.value = value;
  }
}
