/**
 * The FileExceptionHandler class represents an exception specific to file handling.
 * It is used to indicate exceptional conditions that occur during file-related operations.
 */
package bg.tu_varna.f22621629.handlers;

public class FileExceptionHandler extends Exception {

  /**
   * Constructs a new FileExceptionHandler with the specified detail message.
   * @param message The detail message (which is saved for later retrieval by the getMessage() method).
   */
  public FileExceptionHandler(String message) {
    super(message);
  }

  /**
   * Constructs a new FileExceptionHandler with the specified detail message and cause.
   * @param message The detail message (which is saved for later retrieval by the getMessage() method).
   * @param cause The cause (which is saved for later retrieval by the getCause() method).
   */
  public FileExceptionHandler(String message, Throwable cause) {
    super(message, cause);
  }
}
