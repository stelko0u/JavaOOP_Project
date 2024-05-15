package bg.tu_varna.f22621629.handlers;

import java.io.IOException;

public interface CommandHandler {
  void execute(String[] args ) throws IOException, FileExceptionHandler;
}
