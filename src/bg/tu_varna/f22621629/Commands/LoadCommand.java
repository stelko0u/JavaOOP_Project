package bg.tu_varna.f22621629.Commands;

import bg.tu_varna.f22621629.Handlers.CommandHandler;
import bg.tu_varna.f22621629.Handlers.XMLFileHandler;
import bg.tu_varna.f22621629.Models.Session;

import java.io.*;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoadCommand implements CommandHandler {
  private XMLFileHandler fileHandler;

  public LoadCommand() {
    this.fileHandler = XMLFileHandler.getInstance();
  }

  @Override
  public void execute(String[] args) throws IOException {
    Set<Session> sessions = fileHandler.getSessions();
    String filePath = args[1];
    File file = new File(filePath);
    boolean found = false;

    for (Session session : sessions) {
      if (filePath.equals(session.getFileName())) {
        found = true;
        break;
      }
    }
    if (found) {
      System.out.println("File found!");
      BufferedReader reader = new BufferedReader(new FileReader(fileHandler.getFileName()));
      getSessionContent(reader, filePath);
    } else {
      System.out.println("File not found!");
      return;
    }
  }

  public void getSessionContent(BufferedReader reader, String filePath) throws IOException {
    StringBuilder xmlContent = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
      xmlContent.append(line).append("\n");
    }
    String regex = "<session\\s+id=\"1\"[^>]*>.*?<image\\s+name\\s*=\\s*\"" + filePath + "\"\\s+.*?>.*?</image>.*?</session>";

    Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
    Matcher matcher = pattern.matcher(xmlContent.toString());

    if (matcher.find()) {
      fileHandler.setSessionLoaded(true);
      String matchedText = matcher.group();
      System.out.println("\n\n" + matchedText);
    } else {
      System.out.println("No match found for filename: " + filePath);
    }
  }
}
