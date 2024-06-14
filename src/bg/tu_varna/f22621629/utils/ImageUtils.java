package bg.tu_varna.f22621629.utils;

import bg.tu_varna.f22621629.models.*;

import java.io.*;

public class ImageUtils {
  private static final String[] SUPPORTED_FORMATS = {"ppm", "pgm", "pbm"};

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

  public static boolean isSupportedImageFormat(Image image) {
    for (String format : SUPPORTED_FORMATS) {
      if (image.getImageName().toLowerCase().endsWith(format)) {
        return true;
      }
    }
    return false;
  }

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


  public Image readImageFromImageName(String imageName) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(imageName))) {
      String format = reader.readLine();
      String[] sizeTokens = reader.readLine().split(" ");
      int width = Integer.parseInt(sizeTokens[0]);
      int height = Integer.parseInt(sizeTokens[1]);
      int maxVal = 255;

      if (format.equals("P2") || format.equals("P3")) {
        maxVal = Integer.parseInt(reader.readLine());
      }

      Pixel[][] pixels = new Pixel[height][width];
      for (int y = 0; y < height; y++) {
        String[] pixelTokens = reader.readLine().split(" ");
        for (int x = 0; x < width; x++) {
          int red = 0, green = 0, blue = 0;
          if (format.equals("P1")) {
            int val = Integer.parseInt(pixelTokens[x]);
            red = green = blue = val * 255;
          } else if (format.equals("P2")) {
            int gray = Integer.parseInt(pixelTokens[x]);
            red = green = blue = gray;
          } else if (format.equals("P3")) {
            red = Integer.parseInt(pixelTokens[x * 3]);
            green = Integer.parseInt(pixelTokens[x * 3 + 1]);
            blue = Integer.parseInt(pixelTokens[x * 3 + 2]);
          }
          pixels[y][x] = new Pixel(red, green, blue);
        }
      }

      return new Image(pixels, new int[]{width, height, maxVal}, imageName, format);
    }
  }

  public void writeImage(Image image, String filePath) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
      writer.write(image.getFormat() + "\n");
      writer.write(image.getSizes()[0] + " " + image.getSizes()[1] + "\n");
      if (image.getFormat().equals("P2") || image.getFormat().equals("P3")) {
        writer.write(image.getSizes()[2] + "\n");
      }

      for (Pixel[] row : image.getPixels()) {
        for (Pixel pixel : row) {
          writer.write(pixel.getValue());
        }
        writer.write("\n");
      }
    }
  }
}

