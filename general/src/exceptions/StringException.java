package exceptions;

/**
 * thrown when not a string entered
 */
public class StringException extends InvalidDataException {
    public StringException() {
        super("should not be numbers");
    }
}
