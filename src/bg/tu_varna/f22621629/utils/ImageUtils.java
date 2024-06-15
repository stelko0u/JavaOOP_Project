package bg.tu_varna.f22621629.utils;

import bg.tu_varna.f22621629.models.*;
import java.io.*;
/**
 * Utility class for various image processing operations.
 */
public class ImageUtils {
  /**
   * Checks if the given image is a color image (P3 format).
   *
   * @param image The image to check.
   * @return true if the image is in P3 format (color image), false otherwise.
   */
  public static boolean isColorImage(Image image) {
    try (BufferedReader reader = new BufferedReader(new FileReader(image.getImageName()))) {
      String line;
      if ((line = reader.readLine()) != null) {
        return line.startsWith("P3");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Applies a monochrome effect to the given image and saves the result as a PBM format file.
   *
   * @param image The image to apply the effect to.
   */
  public static void applyMonochromeEffect(Image image) {
    try (BufferedReader reader = new BufferedReader(new FileReader(image.getImageName()))) {
      StringBuilder imageAsString = new StringBuilder();
      String line;

      while ((line = reader.readLine()) != null) {
        imageAsString.append(line).append("\n");
      }

      if (imageAsString.length() >= 4 && imageAsString.toString().startsWith("P3")) {
        String[] imageAsStringArray = imageAsString.toString().split("\n");
        String[] dimensions = imageAsStringArray[1].split(" ");
        int width = Integer.parseInt(dimensions[0]);
        int height = Integer.parseInt(dimensions[1]);
        String newFile = image.getImageName().replace(".ppm", "_monochrome.pbm");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(newFile))) {
          writer.write("P1\n");
          writer.write(width + " " + height + "\n");

          int pixelIndex = 0;
          for (int i = 3; i < imageAsStringArray.length; i++) {
            line = imageAsStringArray[i];
            String[] pixelValues = line.trim().split("\\s+");

            for (int j = 0; j < pixelValues.length; j += 3) {
              int red = Integer.parseInt(pixelValues[j]);
              int green = Integer.parseInt(pixelValues[j + 1]);
              int blue = Integer.parseInt(pixelValues[j + 2]);

              int grayValue = (int) (0.299 * red + 0.587 * green + 0.114 * blue);
              int monochromeValue = grayValue < 128 ? 0 : 1;

              if (pixelIndex != 0 && pixelIndex % width == 0) {
                writer.write("\n");
              }
              writer.write(monochromeValue + " ");
              pixelIndex++;
            }
          }
          System.out.println("Successfully converted image to monochrome: " + newFile);
        } catch (IOException e) {
          e.printStackTrace();
        }
      } else {
        System.out.println("The image format is not supported for conversion to monochrome.");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Reads the content of an image file into a StringBuilder.
   * @param image The image to read.
   * @return A StringBuilder containing the content of the image file.
   * @throws IOException if an I/O error occurs while reading the image file.
   */
  public static StringBuilder readImage(Image image) throws IOException {
    StringBuilder imageContent = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new FileReader(image.getImageName()))) {
      String line;
      while ((line = reader.readLine()) != null) {
        imageContent.append(line).append("\n");
      }
    }
    return imageContent;
  }

  /**
   * Applies a negative effect to the given image and returns a new Image object with the effect applied.
   *
   * @param image The image to apply the effect to.
   * @return A new Image object with the negative effect applied.
   */
  public static Image applyNegativeEffect(Image image) {
    try (BufferedReader reader = new BufferedReader(new FileReader(image.getImageName()))) {
      String[] lines = readImage(image).toString().split("\n");

      if (lines[0].equals("P1")) {
        String[] dimensions = lines[1].split("\\s+");
        int width = Integer.parseInt(dimensions[0]);
        int height = Integer.parseInt(dimensions[1]);

        Pixel[][] pixels = new Pixel[height][width];
        for (int i = 2; i < lines.length; i++) {
          String line = lines[i].trim();
          if (line.isEmpty() || line.startsWith("#")) {
            continue;
          }
          String[] pixelValues = line.split("\\s+");
          for (int j = 0; j < pixelValues.length; j++) {
            char pixelChar = pixelValues[j].charAt(0);
            if (pixelChar == '0') {
              pixels[i - 2][j] = new Pixel(1);
            } else if (pixelChar == '1') {
              pixels[i - 2][j] = new Pixel(0);
            }
          }
        }
        return new ImagePBM(pixels, new int[]{width, height}, image.getImageName(), "P1");
      } else if (lines[0].startsWith("P2")) {
        int width = Integer.parseInt(lines[1].split(" ")[0]);
        int height = Integer.parseInt(lines[1].split(" ")[1]);
        int maxGrayValue = Integer.parseInt(lines[2]);

        Pixel[][] pixels = new Pixel[height][width];
        for (int i = 3; i < lines.length; i++) {
          String line = lines[i];
          String[] pixelValues = line.split("\\s+");
          for (int j = 0; j < pixelValues.length; j++) {
            int grayValue = Integer.parseInt(pixelValues[j]);
            int invertedValue = invert(grayValue, maxGrayValue);
            pixels[i - 3][j] = new Pixel(invertedValue);
          }
        }
        return new ImagePGM(pixels, new int[]{width, height}, image.getImageName(), "P2", maxGrayValue);
      } else if (lines[0].startsWith("P3")) {
        int width = Integer.parseInt(lines[1].split(" ")[0]);
        int height = Integer.parseInt(lines[1].split(" ")[1]);
        int maxColorValue = Integer.parseInt(lines[1].split(" ")[2]);

        Pixel[][] pixels = new Pixel[height][width];
        int pixelRow = 0;
        int pixelCol = 0;

        for (int i = 2; i < lines.length; i++) {
          String line = lines[i];
          String[] pixelValues = line.split("\\s+");
          for (int j = 0; j < pixelValues.length; j += 3) {
            int red = Integer.parseInt(pixelValues[j]);
            int green = Integer.parseInt(pixelValues[j + 1]);
            int blue = Integer.parseInt(pixelValues[j + 2]);
            int invertedRed = invert(red, maxColorValue);
            int invertedGreen = invert(green, maxColorValue);
            int invertedBlue = invert(blue, maxColorValue);
            pixels[pixelRow][pixelCol] = new Pixel(invertedRed, invertedGreen, invertedBlue);
            pixelCol++;
            if (pixelCol == width) {
              pixelCol = 0;
              pixelRow++;
            }
          }
        }

        return new ImagePPM(pixels, new int[]{width, height}, image.getImageName(), "P3", maxColorValue);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  private static int invert(int value, int maxValue) {
    return maxValue - value;
  }

  /**
   * Reads the content of an image file into a StringBuilder.
   * @param fileName The image to read.
   * @return A StringBuilder containing the content of the image file.
   * @throws IOException if an I/O error occurs while reading the image file.
   */
  public static Image readImageFromFile(String fileName) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
      String format = reader.readLine().trim();
      String[] sizes = reader.readLine().split(" ");
      int width = Integer.parseInt(sizes[0]);
      int height = Integer.parseInt(sizes[1]);
      Pixel[][] pixels = new Pixel[height][width];

      if (format.equals("P1")) {
        for (int i = 0; i < height; i++) {
          String[] pixelValues = reader.readLine().split(" ");
          for (int j = 0; j < width; j++) {
            pixels[i][j] = new Pixel(Integer.parseInt(pixelValues[j]));
          }
        }
      } else if (format.equals("P2")) {
        String maxVal = reader.readLine();
        for (int i = 0; i < height; i++) {
          String[] pixelValues = reader.readLine().split("\\s+");
          for (int j = 0; j < width; j++) {
            int value = Integer.parseInt(pixelValues[j]);
            pixels[i][j] = new Pixel(value);
          }
        }
      } else if (format.equals("P3")) {
        String[] pixelValues;
        for (int i = 0; i < height; i++) {
          for (int j = 0; j < width; j++) {
            pixelValues = reader.readLine().split("\\s+");
            if (pixelValues == null) {
              break;
            }
            int red = Integer.parseInt(pixelValues[0]);
            int green = Integer.parseInt(pixelValues[1]);
            int blue = Integer.parseInt(pixelValues[2]);
            pixels[i][j] = new Pixel(red, green, blue);
          }
        }
      }
      return new Image(pixels, new int[]{width, height}, fileName, format);
    }
  }
}