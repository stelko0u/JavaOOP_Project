package bg.tu_varna.f22621629.commands;

import bg.tu_varna.f22621629.handlers.CommandHandler;
import bg.tu_varna.f22621629.handlers.XMLFileHandler;

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

      int currentSessionNumber = fileHandler.getCurrentSessionNumber();

      String newImageElement = "        <image name=\"" + args[1] + "\">\n" +
              "        </image>\n";
      fileHandler.setNextLocalImageForSession(currentSessionNumber, newImageElement);
      System.out.println("Image added to session " + currentSessionNumber + " successfully.");
    }
  }

