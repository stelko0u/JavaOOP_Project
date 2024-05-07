package bg.tu_varna.f22621629.Commands;

import bg.tu_varna.f22621629.Handlers.CommandHandler;
import bg.tu_varna.f22621629.Handlers.XMLFileHandler;

import java.io.*;



public class AddImageCommand implements CommandHandler {

  private XMLFileHandler fileHandler;

  public AddImageCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
  }

  @Override
  public void execute(String[] args) throws IOException {
    if (args.length < 2) {
      System.out.println("Usage: add <image>");
      return;
    }

    String currentFile = fileHandler.getFileName();
    if (currentFile == null) {
      System.out.println("Error: No file is currently open.");
      return;
    }

    int nextId = fileHandler.getSessions().size() + 1;

    String newImageElement = "<session id=\"" + nextId + "\">\n" +
            "    <image name=\"" + args[1] + "\">\n" +
            "    </image>\n" +
            "    <transformations>\n" +
            "        <grayscale/>\n" +
            "        <monochrome/>\n" +
            "    </transformations>\n" +
            "</session>\n";
  fileHandler.setNextLocalImage(newImageElement);
    System.out.println("Image added to session successfully.");
//    try (BufferedReader reader = new BufferedReader(new FileReader(currentFile))) {
//      StringBuilder content = new StringBuilder();
//      String line;
//      boolean sessionFound = false;
//
//      while ((line = reader.readLine()) != null) {
//        if (line.trim().equals("</sessions>")) {
//          if (!sessionFound) {
//            content.append(newImageElement);
//          }
//          content.append(line).append("\n");
//          sessionFound = true;
//        } else {
//          content.append(line).append("\n");
//        }
//      }
//
//      if (!sessionFound) {
//        content.append(newImageElement);
//      }
//
//      try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile))) {
//        writer.write(content.toString());
//      }
//
//      System.out.println("Image added to session successfully.");

//    } catch (IOException e) {
//      e.printStackTrace();
//    }
  }
}

