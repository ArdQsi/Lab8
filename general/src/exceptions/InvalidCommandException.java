package exceptions;

/**
 * thrown when command argument is invalid
 */
public class InvalidCommandException extends CommandException{
    public InvalidCommandException(String message) {
        super(message);
    }
}
