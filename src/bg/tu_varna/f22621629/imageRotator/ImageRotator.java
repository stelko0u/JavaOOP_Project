package bg.tu_varna.f22621629.imageRotator;


import bg.tu_varna.f22621629.models.Image;
import bg.tu_varna.f22621629.models.Pixel;

/**
 * Utility class for rotating images in PBM, PGM, and PPM formats.
 * Provides methods for rotating images to the right or left.
 */
public class ImageRotator {
  /**
   * Rotates the given image according to the specified direction.
   *
   * @param image     The image object to rotate.
   * @param direction The direction to rotate ("left" or "right").
   * @return The rotated image object.
   */
  public static Image rotateImage(Image image, String direction) {
    Pixel[][] pixels = image.getPixels();
    int[][] matrix = null;
    int[][][] pixelMatrix = null;
    int maxLightValue;

    if (image.getFormat().equals("P1")) {
      matrix = parsePBMData(pixels);
      matrix = direction.equalsIgnoreCase("right") ? rotateRight(matrix) : rotateLeft(matrix);
      image.setPixels(formatPBMData(matrix));
    } else if (image.getFormat().equals("P2")) {
      matrix = parsePGMData(pixels);
      matrix = direction.equalsIgnoreCase("right") ? rotateRight(matrix) : rotateLeft(matrix);
      maxLightValue = 15;
      image.setPixels(formatPGMData(matrix, maxLightValue));
    } else if (image.getFormat().equals("P3")) {

      pixelMatrix = parsePPMData(pixels);
      pixelMatrix = direction.equalsIgnoreCase("right") ? rotateClockwise(pixelMatrix) : rotateCounterClockwise(pixelMatrix);
      maxLightValue = 255;
      image.setPixels(formatPPMData(pixelMatrix, maxLightValue));
    }

    return image;
  }
  /**
   * Parses the pixel data from a PBM image into a matrix of integers.
   *
   * @param pixels The 2D array of pixels representing the image.
   * @return The parsed matrix of pixel values.
   */
  private static int[][] parsePBMData(Pixel[][] pixels) {
    int height = pixels.length;
    int width = pixels[0].length;
    int[][] matrix = new int[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        matrix[i][j] = pixels[i][j].getValue();
      }
    }
    return matrix;
  }
  /**
   * Parses the pixel data from a PGM image into a matrix of integers.
   *
   * @param pixels The 2D array of pixels representing the image.
   * @return The parsed matrix of pixel values.
   */
  private static int[][] parsePGMData(Pixel[][] pixels) {
    int height = pixels.length;
    int width = pixels[0].length;
    int[][] matrix = new int[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        matrix[i][j] = pixels[i][j].getValue();
      }
    }

    return matrix;
  }
  /**
   * Parses the pixel data from a PPM image into a 3D matrix of integers.
   *
   * @param pixels The 2D array of pixels representing the image.
   * @return The parsed 3D matrix of pixel values.
   */
  private static int[][][] parsePPMData(Pixel[][] pixels) {
    int height = pixels.length;
    int width = pixels[0].length;
    int[][][] matrix = new int[height][width][3];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int red = pixels[i][j].getRed();
        int green = pixels[i][j].getGreen();
        int blue = pixels[i][j].getBlue();
        matrix[i][j][0] = red;
        matrix[i][j][1] = green;
        matrix[i][j][2] = blue;
      }
    }

    return matrix;
  }
  /**
   * Rotates a matrix of pixel values 90 degrees clockwise.
   *
   * @param matrix The matrix of pixel values to rotate.
   * @return The rotated matrix of pixel values.
   */
  private static int[][] rotateRight(int[][] matrix) {
    int height = matrix.length;
    int width = matrix[0].length;
    int[][] rotatedMatrix = new int[width][height];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        rotatedMatrix[j][height - 1 - i] = matrix[i][j];
      }
    }
    return rotatedMatrix;
  }

  /**
   * Rotates a matrix of pixel values 90 degrees counterclockwise.
   *
   * @param matrix The matrix of pixel values to rotate.
   * @return The rotated matrix of pixel values.
   */
  private static int[][] rotateLeft(int[][] matrix) {
    int height = matrix.length;
    int width = matrix[0].length;
    int[][] rotatedMatrix = new int[width][height];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        rotatedMatrix[width - 1 - j][i] = matrix[i][j];
      }
    }

    return rotatedMatrix;
  }

  /**
   * Rotates a 3D matrix of pixel values 90 degrees clockwise.
   *
   * @param matrix The 3D matrix of pixel values to rotate.
   * @return The rotated 3D matrix of pixel values.
   */
  private static int[][][] rotateClockwise(int[][][] matrix) {
    int height = matrix.length;
    int width = matrix[0].length;
    int[][][] rotatedMatrix = new int[width][height][3];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        rotatedMatrix[j][height - 1 - i] = matrix[i][j];
      }
    }
    return rotatedMatrix;
  }

  /**
   * Rotates a 3D matrix of pixel values 90 degrees counterclockwise.
   *
   * @param matrix The 3D matrix of pixel values to rotate.
   * @return The rotated 3D matrix of pixel values.
   */
  private static int[][][] rotateCounterClockwise(int[][][] matrix) {
    int height = matrix.length;
    int width = matrix[0].length;
    int[][][] rotatedMatrix = new int[width][height][3];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        rotatedMatrix[width - 1 - j][i] = matrix[i][j];
      }
    }
    return rotatedMatrix;
  }

  /**
   * Formats a matrix of pixel values into a 2D array of Pixel objects for PBM images.
   *
   * @param matrix The matrix of pixel values to format.
   * @return The formatted 2D array of Pixel objects.
   */
  private static Pixel[][] formatPBMData(int[][] matrix) {
    int height = matrix.length;
    int width = matrix[0].length;
    Pixel[][] pixels = new Pixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int value = matrix[i][j];
        if (value == 0) {
          pixels[i][j] = new Pixel(0);
        } else {
          pixels[i][j] = new Pixel(1);
        }
      }
    }
    return pixels;
  }

  /**
   * Formats a matrix of pixel values into a 2D array of Pixel objects for PGM images.
   *
   * @param matrix         The matrix of pixel values to format.
   * @param maxLightValue The maximum light value (typically 15 for PGM images).
   * @return The formatted 2D array of Pixel objects.
   */
  private static Pixel[][] formatPGMData(int[][] matrix, int maxLightValue) {
    int height = matrix.length;
    int width = matrix[0].length;
    Pixel[][] pixels = new Pixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int grayValue = matrix[i][j];
        pixels[i][j] = new Pixel(grayValue);
      }
    }

    return pixels;
  }

  /**
   * Formats a 3D matrix of pixel values into a 2D array of Pixel objects for PPM images.
   *
   * @param matrix         The 3D matrix of pixel values to format.
   * @param maxLightValue The maximum light value (typically 255 for PPM images).
   * @return The formatted 2D array of Pixel objects.
   */
  private static Pixel[][] formatPPMData(int[][][] matrix, int maxLightValue) {
    int height = matrix.length;
    int width = matrix[0].length;
    Pixel[][] pixels = new Pixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int red = matrix[i][j][0];
        int green = matrix[i][j][1];
        int blue = matrix[i][j][2];
        pixels[i][j] = new Pixel(red, green, blue);
      }
    }
    return pixels;
  }
}