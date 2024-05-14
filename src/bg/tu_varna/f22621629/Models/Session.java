package bg.tu_varna.f22621629.Models;

import java.util.ArrayList;
import java.util.List;

public class Session {
  private int id;
  private String fileName;
  private String transformations;

  public Session(int id, String fileName) {
    this.id = id;
    this.fileName = fileName;
    this.transformations = null;
  }

  public int getId() {
    return id;
  }

  public String getFileName() {
    return fileName;
  }

  public String getTransformations() {
    return transformations;
  }
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(" - Session ID: ").append(getId()).append(", File name: ").append(getFileName());
    return sb.toString();
  }

  public void setTransformations(String transformations) {
    this.transformations = transformations;
  }
}
