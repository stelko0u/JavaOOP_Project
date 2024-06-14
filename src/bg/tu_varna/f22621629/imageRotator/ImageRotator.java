package bg.tu_varna.f22621629.imageRotator;


import bg.tu_varna.f22621629.models.Image;
import bg.tu_varna.f22621629.models.Pixel;

/**
 * Utility class for rotating images in PBM, PGM, and PPM formats.
 * Provides methods for rotating images to the right or left.
 */
public class ImageRotator {
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

  private static int[][][] parsePPMData(Pixel[][] pixels) {
    int height = pixels.length;
    int width = pixels[0].length;
    int[][][] matrix = new int[height][width][3];

    // Assuming pixels is already populated correctly
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

  private static Pixel[][] formatPBMData(int[][] matrix) {
    int height = matrix.length;
    int width = matrix[0].length;
    Pixel[][] pixels = new Pixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int value = matrix[i][j];
        if (value == 0) {
          pixels[i][j] = new Pixel(0); // Assuming Pixel constructor for black
        } else {
          pixels[i][j] = new Pixel(1); // Assuming Pixel constructor for white
        }
      }
    }
    return pixels;
  }

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