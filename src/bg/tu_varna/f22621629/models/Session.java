package bg.tu_varna.f22621629.models;

import java.util.ArrayList;
import java.util.List;

/**
 * The Session class represents a session in the application.
 * A session contains an ID, a list of file names, and transformations applied to the files.
 */
public class Session {
  private int id;
  private List<String> fileNames;
  private String transformations;

  /**
   * Constructs a Session object with the specified ID.
   *
   * @param id the ID of the session
   */
  public Session(int id) {
    this.id = id;
    this.fileNames = new ArrayList<>();
    this.transformations = null;
  }

  /**
   * Gets the ID of the session.
   *
   * @return the ID of the session
   */
  public int getId() {
    return id;
  }

  /**
   * Gets the list of file names associated with the session.
   *
   * @return the list of file names
   */
  public List<String> getFileNames() {
    return fileNames;
  }

  /**
   * Gets the transformations applied to the files in the session.
   *
   * @return the transformations applied
   */
  public String getTransformations() {
    return transformations;
  }

  /**
   * Sets the transformations applied to the files in the session.
   *
   * @param transformations the transformations applied
   */
  public void setTransformations(String transformations) {
    this.transformations = transformations;
  }

  /**
   * Adds a file name to the session.
   *
   * @param fileName the file name to add
   */
  public void addFileName(String fileName) {
    fileNames.add(fileName);
  }

  /**
   * Adds multiple file names to the session.
   *
   * @param fileNames the list of file names to add
   */
  public void addFileNames(List<String> fileNames) {
    this.fileNames.addAll(fileNames);
  }

  /**
   * Returns a string representation of the session.
   *
   * @return a string representation of the session
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(" - Session ID: ").append(getId()).append(", Files in session: ").append(getFileNames());
    return sb.toString();
  }
}
