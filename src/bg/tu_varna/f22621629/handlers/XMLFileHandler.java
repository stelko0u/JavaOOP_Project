package bg.tu_varna.f22621629.handlers;

import bg.tu_varna.f22621629.models.Image;
import bg.tu_varna.f22621629.models.Session;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * The XMLFileHandler class is responsible for handling XML file operations such as opening, reading, and
 * saving sessions. This class follows the singleton pattern to ensure only one instance is created.
 */
public class XMLFileHandler {
  private StringBuilder content;
  private static XMLFileHandler instance;
  private boolean isFileOpened = false;
  private Set<Session> sessions;
  private BufferedReader reader;
  private String fileName;
  private boolean isSessionLoaded = false;
  private File currentFile;
  private String nextLocalImage;
  private Image loadedImage;
  private String fileNameLoadedImage;
  private Session currentSession;
  private int currentSessionNumber;
  /**
   * Private constructor to prevent direct instantiation.
   */
  private XMLFileHandler() {
    this.isFileOpened = false;
    this.sessions = new LinkedHashSet<>();
  }

  /**
   * Returns the single instance of XMLFileHandler.
   *
   * @return the single instance of XMLFileHandler
   */
  public static XMLFileHandler getInstance() {
    if (instance == null) {
      instance = new XMLFileHandler();
    }
    return instance;
  }

  /**
   * Opens the specified XML file and loads its content.
   *
   * @param filePath the path of the file to be opened.
   * @throws FileExceptionHandler if there is an error opening the file.
   */
  public void open(String filePath) throws FileExceptionHandler {
    if (!filePath.endsWith(".xml")) {
      throw new FileExceptionHandler("Only XML files can be opened!");
    }
    File file = new File(filePath);
    if (!file.exists()) {
      try {
        if (!file.createNewFile()) {
          throw new FileExceptionHandler("Failed to create a new file!");
        } else {
          writeInitialContentToFile(filePath);

        }
      } catch (IOException e) {
        throw new FileExceptionHandler("Error creating a new file.", e);
      }
    }


    setFileName(filePath);
    sessions.clear();

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      String sessionId = null;
      List<String> fileNames = new ArrayList<>();
      Pattern sessionIdPattern = Pattern.compile("<session id=\"(\\d+)\">");
      Pattern fileNamePattern = Pattern.compile("<image name=\"([^\"]+)\"");
      StringBuilder content = new StringBuilder();

      while ((line = reader.readLine()) != null) {
        Matcher sessionIdMatcher = sessionIdPattern.matcher(line);
        Matcher fileNameMatcher = fileNamePattern.matcher(line);
        content.append(line).append("\n");
        if (sessionIdMatcher.find()) {
          if (sessionId != null) {
            saveSession(Integer.parseInt(sessionId), String.join(", ", fileNames));
          }
          sessionId = sessionIdMatcher.group(1);
          fileNames = new ArrayList<>();
        }

        if (fileNameMatcher.find() && sessionId != null) {
          String fileName = fileNameMatcher.group(1);
          fileNames.add(fileName);
        }
      }

      if (sessionId != null) {
        saveSession(Integer.parseInt(sessionId), String.join(", ", fileNames));
      }

      setFileOpened(true);
      setContent(content);
    } catch (IOException e) {
      throw new FileExceptionHandler("Error reading the file!");
    }
  }
  /**
   * Writes initial content to the specified file.
   *
   * @param filePath the path of the file to write to.
   * @throws IOException if there is an error writing to the file.
   */
  private void writeInitialContentToFile(String filePath) throws IOException {
    String initialContent = "<sessions>\n" +
            "   <session id=\"1\">\n" +
            "   </session>\n" +
            "</sessions>"+
            "   <session id=\"2\">\n" +
            "   </session>\n" +
            "</sessions>"
            ;

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
      writer.write(initialContent);
    }

  }
  /**
   * Saves a session with the specified session ID and file name.
   *
   * @param sessionId the ID of the session
   * @param fileName  the name of the file to add to the session
   */
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
  /**
   * Finds a session by its ID.
   *
   * @param sessionId the ID of the session to find.
   * @return the session with the given ID, or null if not found.
   */
  private Session findSessionById(int sessionId) {
    for (Session session : sessions) {
      if (session.getId() == sessionId) {
        return session;
      }
    }
    return null;
  }
  /**
   * Checks if a file is opened.
   *
   * @return true if a file is opened, false otherwise.
   */
  public boolean isFileOpened() {
    return isFileOpened;
  }
  /**
   * Sets the file opened status.
   *
   * @param fileOpened the file opened status to set.
   */
  public void setFileOpened(boolean fileOpened) {
    isFileOpened = fileOpened;
  }
  /**
   * Sets the content of the file handler.
   *
   * @param content the content to set.
   */
  public void setContent(StringBuilder content) {
    this.content = content;
  }
  /**
   * Gets the current session number.
   *
   * @return the current session number.
   */
  public int getCurrentSessionNumber() {
    return currentSessionNumber;
  }
  /**
   * Sets the current session number.
   *
   * @param currentSessionNumber the current session number to set.
   */
  public void setCurrentSessionNumber(int currentSessionNumber) {
    this.currentSessionNumber = currentSessionNumber;
  }
  /**
   * Prints the sessions and their files to the console.
   */
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
      List<String> individualFileNames = new ArrayList<>();
      for (String files : fileNames) {
        individualFileNames.addAll(Arrays.asList(files.split(", ")));
      }
      for (String fileName : individualFileNames) {
        if (fileName.isEmpty()) {
          System.out.println("  - Empty");
        } else {
          System.out.println("   - " + fileName);
        }
      }

      System.out.println();
    }
  }

  /**
   * Sets the next local image.
   *
   * @param nextLocalImage the next local image to set.
   */
  public void setNextLocalImage(String nextLocalImage) {
    this.nextLocalImage = nextLocalImage;
  }
  /**
   * Gets the next local image.
   *
   * @return the next local image.
   */
  public String getNextLocalImage() {
    return nextLocalImage;
  }
  /**
   * Sets the sessions for this instance.
   *
   * @param sessions the set of sessions to be set.
   */
  public void setSessions(Set<Session> sessions) {
    this.sessions = sessions;
  }
  /**
   * Gets the sessions.
   *
   * @return the sessions.
   */
  public Set<Session> getSessions() {
    return sessions;
  }
  /**
   * Sets the file name.
   *
   * @param fileName the file name to set.
   */
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
  /**
   * Gets the file name.
   *
   * @return the file name.
   */
  public String getFileName() {
    return fileName;
  }
  /**
   * Checks if a session is loaded.
   *
   * @return true if a session is loaded, false otherwise.
   */
  public boolean isSessionLoaded() {
    return isSessionLoaded;
  }
  /**
   * Sets the session loaded status.
   *
   * @param sessionLoaded the session loaded status to set.
   */
  public void setSessionLoaded(boolean sessionLoaded) {
    isSessionLoaded = sessionLoaded;
  }
  /**
   * Sets the current session.
   *
   * @param session the session to set.
   */
  public void setCurrentSession(Session session) {
    this.currentSession = session;
  }
  /**
   * Gets the current session.
   *
   * @return the current session.
   */
  public Session getCurrentSession() {
    return currentSession;
  }
  /**
   * Sets the loaded image.
   *
   * @param loadedImage the loaded image to set.
   */
  public void setLoadedImage(Image loadedImage) {
    this.loadedImage = loadedImage;
  }
  /**
   * Gets the loaded image.
   *
   * @return the loaded image.
   */
  public Image getLoadedImage() {
    return loadedImage;
  }
  /**
   * Sets the file name of the loaded image.
   *
   * @param fileNameLoadedImage the file name of the loaded image to set.
   */
  public void setFileNameLoadedImage(String fileNameLoadedImage) {
    this.fileNameLoadedImage = fileNameLoadedImage;
  }
  /**
   * Gets the file name of the loaded image.
   *
   * @return the name of the loaded image file.
   */
  public String getFileNameLoadedImage() {
    return fileNameLoadedImage;
  }
  /**
   * Reads the content of a file specified by the fileName.
   *
   * @return the content of the file as a String.
   * @throws IOException if an I/O error occurs.
   */
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
  /**
   * Closes the current session and resets the content.
   */
  public void close() {
      try {
        if (reader != null) {
          reader.close();
          reader = null;
          this.content.setLength(0);

          System.out.println("Sessions closed successfully.");
          return;
        }
      } catch (IOException e) {
        System.out.println("Error occurred while closing the Sessions: " + e.getMessage());
      } catch (NullPointerException e) {
        System.out.println("NullPointerException occurred: " + e.getMessage());
      }
    }
  /**
   * Finds a session by its number.
   *
   * @param sessionNumber the number of the session.
   * @return the session with the specified number, or null if not found.
   */
  public Session findSessionByNumber(int sessionNumber) {
      for(Session session : sessions) {
        if(session.getId() == sessionNumber) {
          return session;
        }
      }
    return null;
    }
  /**
   * Sets the next local image for the specified session.
   *
   * @param sessionNumber    the number of the session.
   * @param newImageElement  the new image element to be set.
   */
  public void setNextLocalImageForSession(int sessionNumber, Image newImageElement) {
    Session session = findSessionByNumber(sessionNumber);
    if (session != null) {
      session.setTransformations(newImageElement.toXMLString());
    } else {
      System.out.println("Session with number " + sessionNumber + " not found.");
    }
  }
  /**
   * Checks if a file is in the current session.
   *
   * @param imagePath the path of the image file.
   * @return true if the file is in the current session, false otherwise.
   */
  public boolean isFileInCurrentSession(String imagePath) {
    Session currentSession = getCurrentSession();
    if (currentSession != null) {
      List<String> fileNames = currentSession.getFileNames();
      List<String> individualFileNames = new ArrayList<>();

      for (String files : fileNames) {
        individualFileNames.addAll(Arrays.asList(files.split(", ")));
      }
      for (String fileName : individualFileNames) {
        if (imagePath.equals("images/" + fileName)) {
          return true;
        }
      }
    }
    return false;
  }
  }
