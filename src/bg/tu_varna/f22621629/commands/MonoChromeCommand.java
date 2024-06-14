package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;
import bg.tu_varna.f22621629.models.Command;
import bg.tu_varna.f22621629.models.Image;
import bg.tu_varna.f22621629.models.Session;
import bg.tu_varna.f22621629.utils.FileUtils;
import bg.tu_varna.f22621629.utils.ImageUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * The MonoChromeCommand class represents a command for converting color images to monochrome.
 */
public class MonoChromeCommand implements CommandHandler {
  private XMLFileHandler fileHandler;


  public MonoChromeCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
  }


  @Override
  public void execute(Command command) throws IOException {
    if (!fileHandler.isFileOpened()) {
      System.out.println("No file is currently open. Please open a file first.");
      return;
    }

    Session currentSession = fileHandler.getCurrentSession();
    if (currentSession == null) {
      System.out.println("No session is currently active. Please start a session first.");
      return;
    }

    List<String> fileNames = currentSession.getFileNames();
    List<String> individualFileNames = new ArrayList<>();

    for (String files : fileNames) {
      individualFileNames.addAll(Arrays.asList(files.split(", ")));
    }
    for (String fileName : individualFileNames) {
      String filePath = "images/" + fileName;
      Image image = ImageUtils.readImageFromFile(filePath);
      if (image == null) {
        System.out.println("File '" + fileName + "' not found in the current session. Skipping.");
        continue;
      }
      if (ImageUtils.isColorImage(image)) {
        ImageUtils.applyMonochromeEffect(image);
      } else {
        System.out.println("The image cannot be made monochrome!");
      }
    }
  }
}