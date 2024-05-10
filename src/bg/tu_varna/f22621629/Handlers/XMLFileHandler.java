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
  private String content;
  private static XMLFileHandler instance;
  private boolean isFileOpened = false;
  private Set<Session> sessions;
  private BufferedReader reader;
  private String fileName;
  private boolean isSessionLoaded = false;
  private File currentFile;
  private String nextLocalImage;
  private String loadedImage;
  private String fileNameLoadedImage;



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

    if (!filePath.endsWith(".xml")) {
      throw new FileExceptionHandler("Only XML files can be opened!");
    }

    setFileName(filePath);
  sessions.clear();

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

  public void setNextLocalImage(String nextLocalImage) {
    this.nextLocalImage = nextLocalImage;
  }

  public String getNextLocalImage() {
    return nextLocalImage;
  }

  public void setSessions(Set<Session> sessions) {
    this.sessions = sessions;
  }

  public Set<Session> getSessions() {
    return sessions;
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

  public void setLoadedImage(String loadedImage) {
    this.loadedImage = loadedImage;
  }

  public String getLoadedImage() {
    return loadedImage;
  }

  public void setFileNameLoadedImage(String fileNameLoadedImage) {
    this.fileNameLoadedImage = fileNameLoadedImage;
  }

  public String getFileNameLoadedImage() {
    return fileNameLoadedImage;
  }

  public String getContent() throws IOException {
    StringBuilder fileContent = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
      String line;
      while ((line = reader.readLine()) != null) {
        fileContent.append(line).append("\n");
      }
    }
    return fileContent.toString();
  }

  public void close() {
      try {
        if (reader != null) {
          reader.close();
          reader = null;
          setContent("");

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
