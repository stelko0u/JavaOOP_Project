package bg.tu_varna.f22621629.utils;
import bg.tu_varna.f22621629.models.Image;
import bg.tu_varna.f22621629.processor.ImageProcessor;

import java.io.*;


public class FileUtils {

  private static FileUtils instance;

  public static FileUtils getInstance() {
    if (instance == null) {
      synchronized (FileUtils.class) {
        if (instance == null) {
          instance = new FileUtils();
        }
      }
    }
    return instance;
  }

  public void writeFile(String fileName, Image image) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
      writer.write(image.getContent());
    }
  }

  public static boolean fileExists(String imageName) {
    return new File(imageName).exists();
  }

  public void saveCollageToFile(Image outImage) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(outImage.getImageName()))) {
      writer.write(outImage.getContent());
    }
    System.out.println("Collage created successfully and added to the current session: " + outImage.getImageName());
  }
  public static String getRotatedFileName(Image originalImage, String direction) {
    int dotIndex = originalImage.getImageNameWithoutPath().lastIndexOf(".");
    String fileNameWithoutExtension = originalImage.getImageNameWithoutPath().substring(0, dotIndex);
    return fileNameWithoutExtension + "_rotated_" + direction + originalImage.getImageNameWithoutPath().substring(dotIndex);
  }
}
