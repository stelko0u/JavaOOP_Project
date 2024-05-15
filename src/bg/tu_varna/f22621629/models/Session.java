package bg.tu_varna.f22621629.models;

import java.util.ArrayList;
import java.util.List;

public class Session {
  private int id;
  private List<String> fileNames;
  private String transformations;

  public Session(int id) {
    this.id = id;
    this.fileNames = new ArrayList<>();
    this.transformations = null;
  }

  public int getId() {
    return id;
  }

  public List<String> getFileNames() {
    return fileNames;
  }

  public String getTransformations() {
    return transformations;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(" - Session ID: ").append(getId()).append(", Files in session: ").append(getFileNames());
    return sb.toString();
  }

  public void setTransformations(String transformations) {
    this.transformations = transformations;
  }

  public void addFileName(String fileName) {
    fileNames.add(fileName);
  }

  public void addFileNames(List<String> fileNames) {
    this.fileNames.addAll(fileNames);
  }
}
