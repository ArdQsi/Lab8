package exceptions;

/**
 * base class for all exception caused by invalid input
 */
public class InvalidDataException extends Exception {
    public InvalidDataException(String message) {
        super(message);
    }
}
