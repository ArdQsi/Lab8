package exceptions;

public class InvalidReceivedDataException extends InvalidDataException{
    public InvalidReceivedDataException() {
        super("received data is damaged");
    }
}