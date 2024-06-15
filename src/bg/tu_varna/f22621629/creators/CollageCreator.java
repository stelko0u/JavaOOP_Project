package bg.tu_varna.f22621629.creators;

import bg.tu_varna.f22621629.models.Image;
import bg.tu_varna.f22621629.models.Pixel;
import bg.tu_varna.f22621629.utils.FileUtils;

import java.io.IOException;
/**
 * Creates a collage by combining two images either horizontally or vertically.
 */
public class CollageCreator {

  /** The utility class for file operations. */
  private FileUtils utils;

  /**
   * Constructs a CollageCreator.
   */
  public CollageCreator() {
    this.utils = FileUtils.getInstance();
  }

  /**
   * Creates a collage by combining two images in the specified direction.
   *
   * @param firstImage the first image object
   * @param secondImage the second image object
   * @param direction the direction to combine the images, either "horizontal" or "vertical"
   * @param outImage the resulting collage image object
   * @throws IOException if an I/O error occurs while creating the collage
   */
  public void createCollage(Image firstImage, Image secondImage, String direction, Image outImage) throws IOException {
    Pixel[][] collagePixels;
    int width, height;

    if (direction.equalsIgnoreCase("horizontal")) {
      collagePixels = createHorizontalCollage(firstImage, secondImage);
      width = firstImage.getSizes()[0] + secondImage.getSizes()[0];
      height = Math.max(firstImage.getSizes()[1], secondImage.getSizes()[1]);
    } else if (direction.equalsIgnoreCase("vertical")) {
      collagePixels = createVerticalCollage(firstImage, secondImage);
      width = Math.max(firstImage.getSizes()[0], secondImage.getSizes()[0]);
      height = firstImage.getSizes()[1] + secondImage.getSizes()[1];
    } else {
      throw new IllegalArgumentException("Invalid direction. Expected 'horizontal' or 'vertical'.");
    }

    outImage.setPixels(collagePixels);
    outImage.setSizes(new int[]{width, height});
    outImage.setFormat(firstImage.getFormat());

    outImage.setContentFromPixels();

    utils.saveCollageToFile(outImage);
  }

  /**
   * Creates a horizontal collage by combining two images side by side.
   *
   * @param firstImage the first image object
   * @param secondImage the second image object
   * @return a 2D array of pixels representing the horizontal collage
   */
  private Pixel[][] createHorizontalCollage(Image firstImage, Image secondImage) {
    Pixel[][] firstPixels = firstImage.getPixels();
    Pixel[][] secondPixels = secondImage.getPixels();

    int height = Math.max(firstPixels.length, secondPixels.length);
    int width = firstPixels[0].length + secondPixels[0].length;

    Pixel[][] collagePixels = new Pixel[height][width];

    for (int y = 0; y < firstPixels.length; y++) {
      for (int x = 0; x < firstPixels[0].length; x++) {
        collagePixels[y][x] = firstPixels[y][x];
      }
    }


    for (int y = 0; y < secondPixels.length; y++) {
      for (int x = 0; x < secondPixels[0].length; x++) {
        collagePixels[y][x + firstPixels[0].length] = secondPixels[y][x];
      }
    }

    return collagePixels;
  }

  /**
   * Creates a vertical collage by combining two images, one above the other.
   *
   * @param firstImage  The first image to be placed at the top of the collage.
   * @param secondImage The second image to be placed at the bottom of the collage.
   * @return A 2D array of Pixels representing the combined vertical collage of the two images.
   */
private Pixel[][] createVerticalCollage(Image firstImage, Image secondImage) {
  Pixel[][] firstPixels = firstImage.getPixels();
  Pixel[][] secondPixels = secondImage.getPixels();

  int height = firstImage.getSizes()[1] + secondImage.getSizes()[1];
  int width = Math.max(firstImage.getSizes()[0], secondImage.getSizes()[0]);

  Pixel[][] collagePixels = new Pixel[height][width];

  for (int y = 0; y < height; y++) {
    for (int x = 0; x < width; x++) {
      if (y < firstPixels.length && x < firstPixels[0].length) {
        collagePixels[y][x] = firstPixels[y][x];
      } else if (y - firstPixels.length < secondPixels.length && x < secondPixels[0].length) {
        collagePixels[y][x] = secondPixels[y - firstPixels.length][x];
      } else {
        collagePixels[y][x] = new Pixel(0, 0, 0);
      }
    }
  }
  return collagePixels;
  }
}