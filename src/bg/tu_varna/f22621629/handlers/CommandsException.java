package bg.tu_varna.f22621629.handlers;
/**
 * The CommandsException class represents an exception specific to command handling.
 * It is used to indicate exceptional conditions that occur during command execution.
 */
public class CommandsException extends Exception {
    /**
     * Constructs a new CommandsException with the specified detail message.
     *
     * @param message The detail message.
     */

    public CommandsException(String message) {
        super(message);
    }
}
