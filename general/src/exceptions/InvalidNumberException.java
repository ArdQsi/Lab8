package exceptions;

/**
 * thrown when input number is incorrect
 */
public class InvalidNumberException extends InvalidDataException{
    public InvalidNumberException() {
        super("invalid number format");
    }
    public InvalidNumberException(String msg) {
        super(msg);
    }
}
