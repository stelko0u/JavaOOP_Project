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
   * Constructs a new Session object with the specified ID.
   *
   * @param id The ID of the session.
   */
  public Session(int id) {
    this.id = id;
    this.fileNames = new ArrayList<>();
    this.transformations = null;
  }

  /**
   * Retrieves the ID of the session.
   *
   * @return The ID of the session.
   */
  public int getId() {
    return id;
  }

  /**
   * Retrieves the list of file names associated with the session.
   *
   * @return The list of file names.
   */
  public List<String> getFileNames() {
    return fileNames;
  }

  /**
   * Retrieves the transformations applied to the files in the session.
   *
   * @return The transformations applied to the files.
   */
  public String getTransformations() {
    return transformations;
  }

  /**
   * Sets the transformations applied to the files in the session.
   *
   * @param transformations The transformations to be set.
   */
  public void setTransformations(String transformations) {
    this.transformations = transformations;
  }

  /**
   * Adds a single file name to the session.
   *
   * @param fileName The file name to be added.
   */
  public void addFileName(String fileName) {
    fileNames.add(fileName);
  }

  /**
   * Adds a list of file names to the session.
   *
   * @param fileNames The list of file names to be added.
   */
  public void addFileNames(List<String> fileNames) {
    this.fileNames.addAll(fileNames);
  }

  /**
   * Returns a string representation of the session, including its ID and the list of file names.
   *
   * @return A string representation of the session.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(" - Session ID: ").append(getId()).append(", Files in session: ").append(getFileNames());
    return sb.toString();
  }
}
