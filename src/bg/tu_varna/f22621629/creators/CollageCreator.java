package bg.tu_varna.f22621629.creators;

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
    this.utils = utils.getInstance();
  }
  /**
   * Creates a collage by combining two images.
   *
   * @param firstImageContent the content of the first image
   * @param secondImageContent the content of the second image
   * @param direction the direction of collage creation (horizontal or vertical)
   * @param outImage the path to save the collage image
   * @throws IOException if an I/O error occurs while creating the collage
   */
  public void createCollage(StringBuilder firstImageContent, StringBuilder secondImageContent, String direction, String outImage) throws IOException {
    StringBuilder collageData = new StringBuilder();
    if (direction.equalsIgnoreCase("horizontal")) {
      createHorizontalCollage(firstImageContent, secondImageContent, collageData, outImage);
    } else if (direction.equalsIgnoreCase("vertical")) {
      createVerticalCollage(firstImageContent, secondImageContent, collageData, outImage);
    }
    utils.saveCollageToFile(collageData.toString(), outImage);
  }
  /**
   * Creates a horizontal collage by combining two images.
   *
   * @param firstImageContent the content of the first image
   * @param secondImageContent the content of the second image
   * @param collageData the StringBuilder to store collage data
   * @param outImage the path to save the collage image
   */
  private void createHorizontalCollage(StringBuilder firstImageContent, StringBuilder secondImageContent, StringBuilder collageData, String outImage) {
    String[] contentFirstImage = firstImageContent.toString().split("\n");
    String[] contentSecondImage = secondImageContent.toString().split("\n");
    String[] sizes = contentFirstImage[1].split(" ");
    int width = 0;
    int height = 0;
    int start = 0;
    if (contentFirstImage[0].startsWith("P1")) {
      width = Integer.parseInt(sizes[0]);
      height = Integer.parseInt(sizes[1]);
      start = 2;
      collageData.append("P1").append('\n');
      collageData.append(width + width).append(" ").append(height).append("\n");
      for (int i = start; i < contentFirstImage.length; i++) {
        collageData.append(contentFirstImage[i]).append(" ");
        collageData.append(contentSecondImage[i]).append(" ").append("\n");
      }
    } else if (contentFirstImage[0].startsWith("P2")) {
      sizes = contentFirstImage[2].split(" ");
      width = Integer.parseInt(sizes[0]);
      height = Integer.parseInt(sizes[1]);
      start = 4;
      collageData.append("P2").append("\n");
      collageData.append("# ").append(outImage).append("\n");
      collageData.append(width + width).append(" ").append(height).append("\n");
      for (int i = start; i < contentFirstImage.length; i++) {
        collageData.append(contentFirstImage[i]).append(" ");
        collageData.append(contentSecondImage[i]).append(" ").append("\n");
      }
    } else if (contentFirstImage[0].startsWith("P3")) {
      sizes = contentFirstImage[1].split(" ");
      width = Integer.parseInt(sizes[0]);
      height = Integer.parseInt(sizes[1]);
      start = 2;
      collageData.append("P3").append(" ").append(width + width).append(" ")
              .append(height).append(" ").append(sizes[2]).append("\n");
      for (int i = start; i < contentFirstImage.length; i++) {
        collageData.append(contentFirstImage[i]).append(" ");
        collageData.append(contentSecondImage[i]).append(" ").append("\n");
      }
    }
  }
  /**
   * Creates a vertical collage by combining two images.
   *
   * @param firstImageContent the content of the first image
   * @param secondImageContent the content of the second image
   * @param collageData the StringBuilder to store collage data
   * @param outImage the path to save the collage image
   */
  private void createVerticalCollage(StringBuilder firstImageContent, StringBuilder secondImageContent, StringBuilder collageData, String outImage) {
    String[] contentFirstImage = firstImageContent.toString().split("\n");
    String[] contentSecondImage = secondImageContent.toString().split("\n");
    String[] sizes;

    int width = 0;
    int height = 0;
    int start = 0;
    if (contentSecondImage[0].equalsIgnoreCase("P1")) {
      sizes = contentFirstImage[1].split(" ");
      width = Integer.parseInt(sizes[0]);
      height = Integer.parseInt(sizes[1]);
      start = 2;
      collageData.append("P1").append('\n');
      collageData.append(width).append(" ").append(height + height).append("\n");
      for (int i = start; i < contentFirstImage.length; i++) {
        collageData.append(contentFirstImage[i]).append("\n");
      }
      for (int i = start; i < contentSecondImage.length; i++) {
        collageData.append(contentSecondImage[i]).append("\n");
      }
    } else if (contentFirstImage[0].equalsIgnoreCase("P2")) {
      sizes = contentFirstImage[2].split(" ");
      width = Integer.parseInt(sizes[0]);
      height = Integer.parseInt(sizes[1]);
      start = 4;
      collageData.append("P2").append("\n");
      collageData.append("# ").append(outImage).append("\n");
      collageData.append(width).append(" ").append(height + height).append("\n");
      for (int i = start; i < contentFirstImage.length; i++) {
        collageData.append(contentFirstImage[i]).append("\n");
      }
      for (int i = start; i < contentSecondImage.length; i++) {
        collageData.append(contentSecondImage[i]).append("\n");
      }
    } else if (contentFirstImage[0].startsWith("P3")) {
      sizes = contentFirstImage[1].split(" ");
      width = Integer.parseInt(sizes[0]);
      height = Integer.parseInt(sizes[1]);
      start = 2;
      collageData.append("P3").append(" ").append(width).append(" ")
              .append(height + height).append(" ").append(sizes[2]).append("\n");
      for (int i = start; i < contentFirstImage.length; i++) {
        collageData.append(contentFirstImage[i]).append("\n");
      }
      for (int i = start; i < contentSecondImage.length; i++) {
        collageData.append(contentSecondImage[i]).append("\n");
      }
    }
  }
}
