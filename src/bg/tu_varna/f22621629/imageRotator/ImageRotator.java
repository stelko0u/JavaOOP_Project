package bg.tu_varna.f22621629.imageRotator;


/**
 * Provides methods to rotate images in various formats.
 */
public class ImageRotator {

  /**
   * Rotates the image data according to the specified direction.
   * Supports rotation for PBM, PGM, and PPM image formats.
   *
   * @param fileName   the name of the file containing the image data
   * @param imageData  the image data to rotate
   * @param direction  the direction of rotation ("right" or "left")
   * @return the rotated image data
   */
  public static String rotateImage(String fileName, String imageData, String direction) {
    StringBuilder rotatedImageData = new StringBuilder();
    String[] lines = imageData.split("\n");
    int[][] matrix = null;
    int[][][] pixelMatrix = null;
    int maxLightValue;

    if (lines[0].startsWith("P1")) {
      matrix = parsePBMData(lines);
      matrix = direction.equalsIgnoreCase("right") ? rotateRight(matrix) : rotateLeft(matrix);
      rotatedImageData.append(formatPBMData(matrix));
    } else if (lines[0].startsWith("P2")) {
      matrix = parsePGMData(lines);
      matrix = direction.equalsIgnoreCase("right") ? rotateRight(matrix) : rotateLeft(matrix);
      maxLightValue = Integer.parseInt(lines[3]);
      rotatedImageData.append(formatPGMData(matrix, maxLightValue));
    } else if (lines[0].startsWith("P3")) {
      String[] temp = lines[1].split(" ");
      maxLightValue = Integer.parseInt(temp[2]);
      pixelMatrix = parsePPMData(lines);
      pixelMatrix = direction.equalsIgnoreCase("right") ? rotateClockwise(pixelMatrix) : rotateCounterClockwise(pixelMatrix);
      rotatedImageData.append(formatPPMData(pixelMatrix, maxLightValue));
    }

    return rotatedImageData.toString();
  }
  /**
   * Parses the image data of a PBM (Portable Bitmap) format.
   *
   * @param lines  the lines of the image file
   * @return the parsed image data as a 2D array
   */
  private static int[][] parsePBMData(String[] lines) {
    int width = Integer.parseInt(lines[1].split(" ")[0]);
    int height = Integer.parseInt(lines[1].split(" ")[1]);
    int[][] matrix = new int[height][width];

    for (int i = 0; i < height; i++) {
      String[] values = lines[i + 2].split(" ");
      for (int j = 0; j < width; j++) {
        matrix[i][j] = Integer.parseInt(values[j]);
      }
    }

    return matrix;
  }
  /**
   * Parses the image data of a PGM (Portable Graymap) format.
   *
   * @param lines  the lines of the image file
   * @return the parsed image data as a 2D array
   */
  private static int[][] parsePGMData(String[] lines) {
    System.out.println();
    int width = Integer.parseInt(lines[2].split(" ")[0]);
    int height = Integer.parseInt(lines[2].split(" ")[1]);
    int[][] matrix = new int[height][width];

    for (int i = 0; i < height; i++) {
      String[] values = lines[i + 4].split("\\s+");
      for (int j = 0; j < width; j++) {
        matrix[i][j] = Integer.parseInt(values[j]);
      }
    }

    return matrix;
  }
  /**
   * Parses the image data of a PPM (Portable Pixmap) format.
   *
   * @param lines  the lines of the image file
   * @return the parsed image data as a 3D array
   */
  private static int[][][] parsePPMData(String[] lines) {
    int width = Integer.parseInt(lines[1].split(" ")[0]);
    int height = Integer.parseInt(lines[1].split(" ")[1]);
    int[][][] matrix = new int[height][width][3];
    int index = 2;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
      String[] values = lines[index++].split(" ");
        matrix[i][j][0] = Integer.parseInt(values[0]);
        matrix[i][j][1] = Integer.parseInt(values[1]);
        matrix[i][j][2] = Integer.parseInt(values[2]);
      }
    }
    return matrix;
  }
  /**
   * Rotates a 2D matrix to the right by 90 degrees.
   *
   * @param matrix  the matrix to rotate
   * @return the rotated matrix
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
   * Rotates a 2D matrix to the left by 90 degrees.
   *
   * @param matrix  the matrix to rotate
   * @return the rotated matrix
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
   * Rotates a 3D pixel matrix clockwise by 90 degrees.
   *
   * @param matrix  the pixel matrix to rotate
   * @return the rotated pixel matrix
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
   * Rotates a 3D pixel matrix counter-clockwise by 90 degrees.
   *
   * @param matrix  the pixel matrix to rotate
   * @return the rotated pixel matrix
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
   * Formats the image data of a PBM (Portable Bitmap) format.
   *
   * @param matrix  the image data matrix
   * @return the formatted image data
   */
  private static String formatPBMData(int[][] matrix) {
    StringBuilder result = new StringBuilder("P1\n");
    result.append(matrix[0].length).append(" ").append(matrix.length).append("\n");
    for (int[] row : matrix) {
      for (int value : row) {
        result.append(value).append(" ");
      }
      result.append("\n");
    }
    return result.toString();
  }
  /**
   * Formats the image data of a PGM (Portable Graymap) format.
   *
   * @param matrix        the image data matrix
   * @param maxLightValue the maximum light value of the image
   * @return the formatted image data
   */
  private static String formatPGMData(int[][] matrix, int maxLightValue) {
    StringBuilder result = new StringBuilder("P2\n");
    result.append(matrix[0].length).append(" ").append(matrix.length).append("\n");
    result.append(maxLightValue).append("\n");
    for (int[] row : matrix) {
      for (int value : row) {
        if (value > 9) {
          result.append(value).append(" ");
        } else {
          result.append(value).append("  ");
        }
      }
      result.append("\n");
    }
    return result.toString();
  }
  /**
   * Formats the image data of a PPM (Portable Pixmap) format.
   *
   * @param matrix        the image data matrix
   * @param maxLightValue the maximum light value of the image
   * @return the formatted image data
   */
  private static String formatPPMData(int[][][] matrix, int maxLightValue) {
    StringBuilder result = new StringBuilder("P3\n");
    result.append(matrix[0].length).append(" ").append(matrix.length).append(" ");
    result.append(maxLightValue).append("\n");
    for (int[][] row : matrix) {
      for (int[] pixel : row) {
        for (int value : pixel) {
          result.append(value).append(" ");
        }
      result.append("\n");
      }
    }
    return result.toString();
  }
}

