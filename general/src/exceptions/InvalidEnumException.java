package exceptions;

/**
 * thrown when input doesn't match enum
 */
public class InvalidEnumException extends InvalidDataException{
    public InvalidEnumException() {
        super("wrong constant (must be KILOGRAMS, METERS, SQUARE_METERS, MILLILITERS");
    }
}