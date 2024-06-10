package bg.tu_varna.f22621629.utils;

import bg.tu_varna.f22621629.models.Image;

import java.io.*;
/**
 * Utility class for image processing operations.
 * This class provides methods to check image format, apply effects,
 * and read image data.
 */
public class ImageUtils {


  private static final String[] SUPPORTED_FORMATS = {"ppm", "pgm", "pbm"};
  /**
   * Checks if the image is a color image by reading its header.
   * A color image is identified by the "P3" format.
   *
   * @param image the name of the file to check
   * @return true if the image is a color image, false otherwise
   */
  public static boolean isColorImage(Image image) {
    try (BufferedReader reader = new BufferedReader(new FileReader(image.getName()))) {
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
   * Applies a monochrome effect to a P3 (color) image.
   * Converts the image to a P1 (monochrome) format.
   *
   * @param image the name of the file to convert
   */
  public static void applyMonochromeEffect(Image image) {
    try (BufferedReader reader = new BufferedReader(new FileReader(image.getName()))) {
      StringBuilder imageAsString = new StringBuilder();
      String line;

      while ((line = reader.readLine()) != null) {
        imageAsString.append(line).append("\n");
      }

      if (imageAsString.length() >= 4 && imageAsString.toString().startsWith("P3")) {
        String[] imageAsStringArray = imageAsString.toString().split("\n");
        String[] dimensions = imageAsStringArray[1].split(" ");
        String[] fullFilePath = image.getName().split("/");
        String[] fileAndExtension = fullFilePath[1].split("\\.");
        String newFile = fullFilePath[0] + "/" + fileAndExtension[0] + "_monochrome." + fileAndExtension[1];

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(newFile))) {
          writer.write("P1\n");
          writer.write(String.join(" ", dimensions) + "\n");

          for (int i = 3; i < imageAsStringArray.length; i++) {
            line = imageAsStringArray[i];
            String[] pixelValues = line.trim().split("\\s+");

            for (int j = 0; j < pixelValues.length; j += 3) {
              int red = Integer.parseInt(pixelValues[j]);
              int green = Integer.parseInt(pixelValues[j + 1]);
              int blue = Integer.parseInt(pixelValues[j + 2]);

              int grayValue = (int) (0.299 * red + 0.587 * green + 0.114 * blue);
              int monochromeValue = grayValue < 128 ? 0 : 1;
              writer.write(monochromeValue + " ");
            }
            writer.write("\n");
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
   * Reads the content of an image file.
   *
   * @param image the name of the file to read
   * @return the content of the image file as a StringBuilder
   * @throws IOException if an I/O error occurs
   */
    public StringBuilder readImage(Image image) throws IOException {
      StringBuilder imageContent = new StringBuilder();
      try (BufferedReader reader = new BufferedReader(new FileReader(image.getName()))) {
        String line;
        while ((line = reader.readLine()) != null) {
          imageContent.append(line).append("\n");
        }
      }
      return imageContent;
    }
  /**
   * Applies a negative effect to an image.
   * Inverts the pixel values of the image.
   *
   * @param image the content of the image as a string
   * @return the modified image data with the negative effect applied
   */
  public String applyNegativeEffect(Image image) {
    StringBuilder modifiedImageData = new StringBuilder();
    String[] lines = image.getContent().split("\n");
    modifiedImageData.append(lines[0]).append("\n");

    if (lines[0].equals("P1")) {
      modifiedImageData.append(lines[1]).append("\n");
      for (int i = 2; i < lines.length; i++) {
        String line = lines[i];
        for (int j = 0; j < line.length(); j++) {
          char pixelChar = line.charAt(j);
          if (pixelChar == '0') {
            modifiedImageData.append('1');
          } else if (pixelChar == '1') {
            modifiedImageData.append('0');
          } else {
            modifiedImageData.append(pixelChar);
          }
          if (j < line.length() - 1) {
            modifiedImageData.append(" ");
          }
        }
        if (i < lines.length - 1) {
          modifiedImageData.append("\n");
        }
      }
    } else if (lines[0].startsWith("P2") || lines[0].startsWith("P3")) {
      for (int i = 1; i < lines.length; i++) {
        String line = lines[i];
        if (line.startsWith("#") || i < 4) {
          modifiedImageData.append(line).append("\n");
          continue;
        }
        String[] values = line.trim().split("\\s+");
        for (int j = 0; j < values.length; j++) {
          int value = Integer.parseInt(values[j]);
          int invertedValue = invert(value);
          modifiedImageData.append(invertedValue);
          if (j < values.length - 1) {
            modifiedImageData.append(" ");
          }
        }
        if (i < lines.length - 1) {
          modifiedImageData.append("\n");
        }
      }
    }

    return modifiedImageData.toString();
  }
  /**
   * Inverts a pixel value.
   *
   * @param value the original pixel value
   * @return the inverted pixel value
   */
  private int invert(int value) {
    return 255 - value;
  }

  /**
   * Checks if the image format is supported.
   * Supported formats are "ppm", "pgm", and "pbm".
   *
   * @param image the name of the file to check
   * @return true if the format is supported, false otherwise
   */
  public static boolean isSupportedImageFormat(Image image) {
    for (String format : SUPPORTED_FORMATS) {
      if (image.getName().toLowerCase().endsWith(format)) {
        return true;
      }
    }
    return false;
  }
}
