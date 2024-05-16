package bg.tu_varna.f22621629.handlers;

import bg.tu_varna.f22621629.models.Session;

import java.io.*;
import java.util.LinkedHashSet;
import java.util.List;
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
  private Session currentSession;
  private int currentSessionNumber;

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
//          sessionId = null;
          fileName = null;
        }
      }
      setFileOpened(true);

    } catch (IOException e) {
      throw new FileExceptionHandler("Error reading the file!");
    }
  }

  private void writeInitialContentToFile(String filePath) throws IOException {
    String initialContent = "<sessions>\n" +
            "   <session id=\"1\">\n" +
            "   </session>\n" +
            "</sessions>";

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
      writer.write(initialContent);
    }
  }

  public void saveSession(int sessionId, String fileName) {
    Session existingSession = findSessionById(sessionId);
    if (existingSession != null) {
      existingSession.addFileName(fileName);
    } else {
      Session session = new Session(sessionId);
      session.addFileName(fileName);
      sessions.add(session);
    }
  }

  private Session findSessionById(int sessionId) {
    for (Session session : sessions) {
      if (session.getId() == sessionId) {
        return session;
      }
    }
    return null;
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

  public int getCurrentSessionNumber() {
    return currentSessionNumber;
  }

  public void setCurrentSessionNumber(int currentSessionNumber) {
    this.currentSessionNumber = currentSessionNumber;
  }

  public void printSessions() {
    Set<Session> sessions = getSessions();
    if (sessions.size() == 0) {
      System.out.println("Empty session!");
      return;
    }
    int prevSessionNumber;
    for (Session session : sessions) {
      System.out.println("Session ID: " + session.getId());
      System.out.println(" * Files in session:");

      List<String> fileNames = session.getFileNames();
      for (String fileName : fileNames) {
        System.out.println("   - " + fileName);

        String[] extensionsToCheck = {"_negative", "_rotated_left", "_rotated_right", "_monochrome", "_grayscale"};

        for (String extension : extensionsToCheck) {
          String alternativeFileName = fileName + extension;
          File alternativeFile = new File("images/" + alternativeFileName);
          if (alternativeFile.exists()) {
            System.out.println("     " + alternativeFileName);
          }
        }
      }

      System.out.println();
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

  public void setCurrentSession(Session session) {
    this.currentSession = session;
  }

  public Session getCurrentSession() {
    return currentSession;
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
          return;
        }
      } catch (IOException e) {
        System.out.println("Error occurred while closing the Sessions: " + e.getMessage());
      } catch (NullPointerException e) {
        System.out.println("NullPointerException occurred: " + e.getMessage());
      }
    }

  public Session findSessionByNumber(int sessionNumber) {
      for(Session session : sessions) {
        if(session.getId() == sessionNumber) {
          return session;
        }
      }
    return null;
    }

  public void setNextLocalImageForSession(int sessionNumber, String newImageElement) {
    Session session = findSessionByNumber(sessionNumber);
    if (session != null) {
      session.setTransformations(newImageElement);
    } else {
      System.out.println("Session with number " + sessionNumber + " not found.");
    }
  }

  public boolean isFileInCurrentSession(String imagePath) {
    Session currentSession = getCurrentSession();
    if (currentSession != null) {
      List<String> fileNames = currentSession.getFileNames();
      for (String fileName : fileNames) {
        if (imagePath.equals("images/" + fileName)) {
          return true;
        }
      }
    }
    return false;
  }
  }
