package exceptions;

/**
 * thrown when date format is invalid
 */
public class InvalidFormatDateException extends InvalidDataException {
    public InvalidFormatDateException() {
        super("date format must be dd/mm/yyyy");
    }
}
