package exceptions;

/**
 * base class for all exception caused by executing commands
 */
public class CommandException extends RuntimeException{
    public CommandException(String message) {
        super(message);
    }
}