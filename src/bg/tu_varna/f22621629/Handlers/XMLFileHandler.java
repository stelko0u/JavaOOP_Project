package bg.tu_varna.f22621629.Handlers;

import bg.tu_varna.f22621629.Handlers.FileExceptionHandler;
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
  private String content;
  private static XMLFileHandler instance;
  private boolean isFileOpened = false;
  private Set<Session> sessions;
  private BufferedReader reader;
  private String fileName;
  private boolean isSessionLoaded = false;



  private XMLFileHandler() {
    this.isFileOpened = false;
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
    setFileName(filePath);
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
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

  public boolean isFileOpened() {
    return isFileOpened;
  }

  public void setFileOpened(boolean fileOpened) {
    isFileOpened = fileOpened;
  }
  public void setContent(String content) {
    this.content = content;
  }

  public void printSessions() {
    System.out.println("Sessions:");
    for (Session session : sessions) {
      System.out.println(session.toString());
    }
  }

  public Set<Session> getSessions() {
    return sessions;
  }

  public String getContent() {
    // You can implement getContent() if needed
    // For now, let's return an empty string
    return "";
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getFileName() {
    return fileName;
  }

  public boolean isSessionLoaded() {
    return isSessionLoaded;
  }

  public void setSessionLoaded(boolean sessionLoaded) {
    isSessionLoaded = sessionLoaded;
  }

  public void close() {
      try {
        if (reader != null) {
          reader.close();
          reader = null;
          System.out.println("Sessions closed successfully.");
        } else {
          System.out.println("Sessions is already closed or empty.");
        }
      } catch (IOException e) {
        System.out.println("Error occurred while closing the Sessions: " + e.getMessage());
      } catch (NullPointerException e) {
        System.out.println("NullPointerException occurred: " + e.getMessage());
      }
    }


  }
