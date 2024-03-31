package bg.tu_varna.f22621629.Models;

public class Session {
  private int id;
  private String fileName;

  public Session(int id, String fileName) {
    this.id = id;
    this.fileName = fileName;
  }

  public int getId() {
    return id;
  }

  public String getFileName() {
    return fileName;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(" - Session ID: ").append(getId()).append(", File name: ").append(getFileName());
    return sb.toString();
  }

}
