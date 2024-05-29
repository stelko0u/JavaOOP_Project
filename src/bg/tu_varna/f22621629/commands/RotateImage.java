package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;
import bg.tu_varna.f22621629.models.Command;

import java.io.*;

/**
 * The RotateImage class represents a command to rotate an image.
 */
public class RotateImage implements CommandHandler {
  private final XMLFileHandler fileHandler;
  private static final String IMAGES_FOLDER = "images\\";

  /**
   * Constructs a RotateImage object.
   */
  public RotateImage() {
    this.fileHandler = XMLFileHandler.getInstance();
  }

  /**
   * Executes the rotation command on an image.
   *
//   * @param args Command arguments.
   * @throws IOException If an I/O error occurs.
   */
  @Override
  public void execute(Command commands) throws IOException {
    XMLFileHandler fileHandler = XMLFileHandler.getInstance();
    if (!fileHandler.isFileOpened()) {
      System.out.println("No file is currently open. Please open a file first.");
      return;
    }
    if (fileHandler.getFileNameLoadedImage() == null) {
      System.out.println("No loaded image! Please load an image first!");
      return;
    }

    if (commands.getArguments().length != 1) {
      System.out.println("Usage: rotate <left/right>");
      return;
    }

    if (!commands.getArguments()[0].equalsIgnoreCase("left") && !commands.getArguments()[0].equalsIgnoreCase("right")) {
      System.out.println("Usage only 'left' and 'right' directions!");
      return;
    }
    String direction = commands.getArguments().length == 1 ? commands.getArguments()[0] : "left";

    String loadedImage = String.valueOf(fileHandler.getLoadedImage());
    String loadedImageFilePath = fileHandler.getFileNameLoadedImage();
    if (fileHandler.getLoadedImage() == null) {
      return;
    }


    String imagePath = IMAGES_FOLDER + loadedImageFilePath;
    File imageFile = new File(imagePath);
    if (imageFile.exists()) {
      if (isSupportedImageFormat(loadedImageFilePath)) {
        StringBuilder imageData = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(imageFile))) {
          String line;
          while ((line = reader.readLine()) != null) {
            imageData.append(line).append("\n");
          }
        } catch (IOException e) {
          e.printStackTrace();
          return;
        }

        String rotatedImageData = rotateImage(loadedImageFilePath, imageData.toString(), direction);
        System.out.println();
        String rotatedImagePath = IMAGES_FOLDER + getRotatedFileName(loadedImageFilePath, direction);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rotatedImagePath))) {
          writer.write(rotatedImageData);
        } catch (IOException e) {
          e.printStackTrace();
          return;
        }

        System.out.println("Image rotated successfully: " + rotatedImagePath);
      } else {
        System.out.println("Unsupported image format for rotation: " + loadedImage);
      }
    } else {
      System.out.println("Image file not found: " + loadedImage);
    }

  }

  /**
   * Checks if the image format is supported for rotation.
   *
   * @param fileName The name of the image file.
   * @return True if the image format is supported, false otherwise.
   */
  private boolean isSupportedImageFormat(String fileName) {
    String[] supportedFormats = {"ppm", "pgm", "pbm"};
    for (String format : supportedFormats) {
      if (fileName.toLowerCase().endsWith(format)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Rotates the image data based on the specified direction.
   *
   * @param fileName  The name of the image file.
   * @param imageData The image data.
   * @param direction The direction of rotation (left/right).
   * @return The rotated image data.
   */
  private String rotateImage(String fileName, String imageData, String direction) {
    StringBuilder rotatedImageData = new StringBuilder();
    String[] lines = imageData.split("\n");
    int[][] matrix = null;
    String tempLine = "";


    if (lines[0].startsWith("P1")) {

      // P1 RIGHT
      if (direction.equalsIgnoreCase("right")) {
        String[] data = imageData.split("\n");
        matrix = new int[data.length - 2][data.length - 2];

        for (int i = 0; i < data.length - 2; i++) {
          String[] values = data[i + 2].split(" ");
          matrix[i] = new int[values.length];
          for (int j = 0; j < values.length; j++) {
            matrix[i][j] = Integer.parseInt(values[j]);
          }
        }
        int row = matrix.length;
        int cols = row;
        for (int i = 0; i < row; i++) {
          for (int j = i; j < cols; j++) {
            int temp = matrix[i][j];
            matrix[i][j] = matrix[j][i];
            matrix[j][i] = temp;
          }
        }

        for (int i = 0; i < row; i++) {
          for (int j = 0; j < cols / 2; j++) {
            int temp = matrix[i][j];
            matrix[i][j] = matrix[i][cols - 1 - j];
            matrix[i][cols - 1 - j] = temp;
          }
        }

        System.out.println("\nSuccessfully rotated on 90 degrees clockwise");
        rotatedImageData.append("P1").append("\n");
        rotatedImageData.append(matrix.length).append(" ").append(matrix.length).append("\n");
        for (int[] rows : matrix) {
          for (int i = 0; i < rows.length; i++) {
            System.out.print(rows[i]);
            rotatedImageData.append(rows[i]);
            if (i < rows.length - 1) {
              System.out.print(" ");
              rotatedImageData.append(" ");
            }
          }
          rotatedImageData.append("\n");
          System.out.println();
        }

      }

      // P1 LEFT
      if (direction.equalsIgnoreCase("left")) {
        String[] data = imageData.split("\n");
        matrix = new int[data.length - 2][data.length - 2];

        for (int i = 0; i < data.length - 2; i++) {
          String[] values = data[i + 2].split(" ");
          matrix[i] = new int[values.length];
          for (int j = 0; j < values.length; j++) {
            matrix[i][j] = Integer.parseInt(values[j]);
          }
        }

        int rows = matrix.length;
        int cols = rows;

        int[][] rotatedMatrix = new int[cols][rows];
        for (int i = 0; i < rows; i++) {
          for (int j = 0; j < cols; j++) {
            rotatedMatrix[cols - 1 - j][i] = matrix[i][j];
          }
        }


        System.out.println("\nSuccessfully rotated 90 degrees counterclockwise:");
        rotatedImageData.append("P1").append("\n");
        rotatedImageData.append(matrix.length).append(" ").append(matrix.length).append("\n");
        for (int[] row : rotatedMatrix) {
          for (int i = 0; i < row.length; i++) {
            System.out.print(row[i]);
            rotatedImageData.append(row[i]);
            if (i < row.length - 1) {
              System.out.print(" ");
              rotatedImageData.append(" ");
            }
          }
          rotatedImageData.append("\n");
          System.out.println();
        }
      }
    }


    if (lines[0].startsWith("P2")) {
      // P2 RIGHT
      if (direction.equalsIgnoreCase("right")) {
        String[] data = imageData.split("\n");
        matrix = new int[data.length - 4][data.length - 4];

        for (int i = 0; i < data.length - 4; i++) {
          String[] values = data[i + 4].split("\\s+");
          matrix[i] = new int[values.length];
          for (int j = 0; j < values.length; j++) {
            matrix[i][j] = Integer.parseInt(values[j]);
          }
        }
        int row = matrix.length;
        int cols = row;
        for (int i = 0; i < row; i++) {
          for (int j = i; j < cols; j++) {
            int temp = matrix[i][j];
            matrix[i][j] = matrix[j][i];
            matrix[j][i] = temp;
          }
        }

        for (int i = 0; i < row; i++) {
          for (int j = 0; j < cols / 2; j++) {
            int temp = matrix[i][j];
            matrix[i][j] = matrix[i][cols - 1 - j];
            matrix[i][cols - 1 - j] = temp;
          }
        }

        System.out.println("\nSuccessfully rotated on 90 degrees clockwise");
        rotatedImageData.append("P2").append("\n");
        rotatedImageData.append("# ").append(getRotatedFileName(fileName, direction)).append("\n");
        rotatedImageData.append(matrix.length).append(" ").append(matrix.length).append("\n");
        rotatedImageData.append(15).append("\n");
        for (int[] rows : matrix) {
          for (int i = 0; i < rows.length; i++) {
            System.out.print(rows[i]);
            rotatedImageData.append(rows[i]);
            if (i < rows.length - 1) {
              if (rows[i] > 9) {
                System.out.print(" ");
                rotatedImageData.append(" ");
              } else {
                System.out.print("  ");
                rotatedImageData.append("  ");
              }
            }
          }
          System.out.println();
          rotatedImageData.append("\n");
        }
      }

      // P2 LEFT
      if (direction.equalsIgnoreCase("left")) {
        String[] data = imageData.split("\n");
        matrix = new int[data.length - 4][data.length - 4];

        for (int i = 0; i < data.length - 4; i++) {
          String[] values = data[i + 4].split("\\s+");
          matrix[i] = new int[values.length];
          for (int j = 0; j < values.length; j++) {
            matrix[i][j] = Integer.parseInt(values[j]);
          }
        }

        int rows = matrix.length;
        int cols = rows;

        int[][] rotatedMatrix = new int[cols][rows];
        for (int i = 0; i < rows; i++) {
          for (int j = 0; j < cols; j++) {
            rotatedMatrix[cols - 1 - j][i] = matrix[i][j];
          }
        }

        System.out.println("\nSuccessfully rotated 90 degrees counterclockwise:");
        rotatedImageData.append("P2").append("\n");
        rotatedImageData.append("# ").append(getRotatedFileName(fileName, direction)).append("\n");
        rotatedImageData.append(matrix.length).append(" ").append(matrix.length).append("\n");
        rotatedImageData.append(15).append("\n");
        for (int[] row : rotatedMatrix) {
          for (int i = 0; i < row.length; i++) {
            System.out.print(row[i]);
            rotatedImageData.append(row[i]);
            if (i < row.length - 1) {
              if (row[i] > 9) {
                System.out.print(" ");
                rotatedImageData.append(" ");
              } else {
                System.out.print("  ");
                rotatedImageData.append("  ");
              }
            }
          }
          System.out.println();
          rotatedImageData.append("\n");
        }
      }
    }


    if (lines[0].startsWith("P3")) {

      int[][][] pixelMatrix;
      // P3 RIGHT
      if (direction.equalsIgnoreCase("right")) {
        String[] data = imageData.split("\n");

        int width = Integer.parseInt(data[1].split(" ")[0]);
        int height = Integer.parseInt(data[1].split(" ")[1]);
        int maxValue = Integer.parseInt(data[1].split(" ")[2]);
        matrix = new int[data.length - 2][data.length - 2];
        pixelMatrix = new int[width][height][maxValue];

        for (int i = 0; i < data.length - 2; i++) {
          String[] values = data[i + 2].split("\\s+");
          matrix[i] = new int[values.length];
          for (int j = 0; j < values.length; j++) {
            matrix[i][j] = Integer.parseInt(values[j]);
          }
        }

        for(int i = 0; i < height; i++) {
          for (int j = 0; j < width; j++) {
            pixelMatrix[i][j] = matrix[i * width + j];
          }
        }

        int[][][] rotatedMatrix = rotatedCounterClockwise(pixelMatrix, width, height);
        System.out.println("\nSuccessfully rotated on 90 degrees clockwise");

        System.out.println("P3");
        rotatedImageData.append("P3").append("\n");
        rotatedImageData.append(width).append(" ")
                .append(height).append(" ").append(maxValue).append("\n");
        System.out.println(width + " " + height + " " + maxValue);
        for (int i = 0; i < width; i++) {
          for (int j = 0; j < height; j++) {
            System.out.println(rotatedMatrix[i][j][0] + " " + rotatedMatrix[i][j][1] + " " + rotatedMatrix[i][j][2]);

            rotatedImageData
                    .append(rotatedMatrix[i][j][0]).append(" ")
                    .append(rotatedMatrix[i][j][1]).append(" ")
                    .append(rotatedMatrix[i][j][2]);
            if (j < height) {
              rotatedImageData.append("\n");
            }

          }
        }
      }

      // P3 LEFT
      if (direction.equalsIgnoreCase("left")) {
        String[] data = imageData.split("\n");

        int width = Integer.parseInt(data[1].split(" ")[0]);
        int height = Integer.parseInt(data[1].split(" ")[1]);
        int maxValue = Integer.parseInt(data[1].split(" ")[2]);
        matrix = new int[data.length - 2][data.length - 2];
        pixelMatrix = new int[width][height][maxValue];

        for (int i = 0; i < data.length - 2; i++) {
          String[] values = data[i + 2].split("\\s+");
          matrix[i] = new int[values.length];
          for (int j = 0; j < values.length; j++) {
            matrix[i][j] = Integer.parseInt(values[j]);
          }
        }

        for(int i = 0; i < height; i++) {
          for (int j = 0; j < width; j++) {
            pixelMatrix[i][j] = matrix[i * width + j];
          }
        }

        int[][][] rotatedMatrix = rotatedClockwise(pixelMatrix, width, height);
        System.out.println("\nSuccessfully rotated on 90 degrees clockwise");

        System.out.println("P3");
        rotatedImageData.append("P3").append("\n");
        rotatedImageData.append(width).append(" ")
                .append(height).append(" ").append(maxValue).append("\n");
        System.out.println(width + " " + height + " " + maxValue);
        for (int i = 0; i < width; i++) {
          for (int j = 0; j < height; j++) {
            System.out.println(rotatedMatrix[i][j][0] + " " + rotatedMatrix[i][j][1] + " " + rotatedMatrix[i][j][2]);

            rotatedImageData
                    .append(rotatedMatrix[i][j][0]).append(" ")
                    .append(rotatedMatrix[i][j][1]).append(" ")
                    .append(rotatedMatrix[i][j][2]);
            if (j < height) {
              rotatedImageData.append("\n");
            }
          }
        }
      }
    }

    return rotatedImageData.toString();
  }


  /**
   * Generates the file name for the rotated image.
   *
   * @param originalFileName The original file name.
   * @param direction        The direction of rotation (left/right).
   * @return The file name for the rotated image.
   */
  private String getRotatedFileName(String originalFileName, String direction)
  {
    int dotIndex = originalFileName.lastIndexOf(".");
    String fileNameWithoutExtension = originalFileName.substring(0, dotIndex);
    return fileNameWithoutExtension + "_rotated_" + direction + "." + originalFileName.substring(dotIndex + 1);
  }
  private int[][][] rotatedCounterClockwise(int[][][] matrix, int width, int height) {
    int[][][] rotatedMatrix = new int[width][height][3];
    for(int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        rotatedMatrix[width - 1 - j][i] = matrix[i][j];
      }
    }
    return rotatedMatrix;
  }
  private int[][][] rotatedClockwise(int[][][] matrix, int width, int height) {
    int[][][] rotatedMatrix = new int[width][height][3];
    for(int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        rotatedMatrix[j][height - 1 - i] = matrix[i][j];
      }
    }
    return rotatedMatrix;
  }
}
