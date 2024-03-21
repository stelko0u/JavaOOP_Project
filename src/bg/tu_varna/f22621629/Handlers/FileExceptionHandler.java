package bg.tu_varna.f22621629.Handlers;

public class FileExceptionHandler extends Exception{
  public FileExceptionHandler(String message) {
    super(message);
  }
  public FileExceptionHandler(String message, Throwable cause) {
    super(message, cause);
  }
}
