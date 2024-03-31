package bg.tu_varna.f22621629.Handlers;

import bg.tu_varna.f22621629.Models.Session;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLFileHandler {
  private static XMLFileHandler instance;
  private String content;
  private boolean isFileOpened = false;
  private String filePath;
  private int sessionCount;
  private Set<Integer> openedSessions;
  private Set<Session> sessions;

  private XMLFileHandler() {
    this.isFileOpened = false;
    this.content = "";
    this.sessionCount = 0;
    this.openedSessions = new LinkedHashSet<>();
    this.sessions = new LinkedHashSet<>();
  }

  public static XMLFileHandler getInstance() {
    if (instance == null) {
      instance = new XMLFileHandler();
    }
    return instance;
  }

  public void open(String filePath) throws FileExceptionHandler {
    File file = new File(filePath);
    if (!file.exists()) {
      try {
        if (!file.createNewFile()) {
          throw new FileExceptionHandler("Failed to create a new file!");
        }
      } catch (IOException e) {
        throw new FileExceptionHandler("Error creating a new file.", e);
      }
    }
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      StringBuilder sb = new StringBuilder();
      String line;
      String sessionId = null;
      String fileName = null;
      Pattern sessionIdPattern = Pattern.compile("<session id=\"(\\d+)\">");
      Pattern fileNamePattern = Pattern.compile("<image name=\"([^\"]+)\"");
      while ((line = reader.readLine()) != null) {
        Matcher sessionIdMatcher = sessionIdPattern.matcher(line);
        Matcher fileNameMatcher = fileNamePattern.matcher(line);
        if (sessionIdMatcher.find()) {
          sessionId = sessionIdMatcher.group(1);
        }
        if (fileNameMatcher.find()) {
          fileName = fileNameMatcher.group(1);
        }
        if (sessionId != null && fileName != null) {
          saveSession(Integer.parseInt(sessionId), fileName);
          sessionId = null;
          fileName = null;
        }
      }
      setContent(sb.toString());
      setFileOpened(true);
      printSessions();
    } catch (IOException e) {
      throw new FileExceptionHandler("Error reading the file!");
    }
  }

  public void saveSession(int sessionId, String fileName) {
    Session session = new Session(sessionId, fileName);
    sessions.add(session);
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public boolean isFileOpened() {
    return isFileOpened;
  }

  public void setFileOpened(boolean fileOpened) {
    isFileOpened = fileOpened;
  }

  public int getSessionCount() {
    return sessionCount;
  }

  public void printSessions() {
    System.out.println("Sessions:");
    for (Session session : sessions) {
      System.out.println(session.toString());
    }
  }

}
