package exceptions;

/**
 * thrown when user input is empty
 */
public class EmptyStringException extends InvalidDataException {
    public EmptyStringException() {
        super("string can't be empty");
    }
}
