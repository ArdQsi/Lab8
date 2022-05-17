package exceptions;

public class InvalidPortException extends ConnectionException {
    public InvalidPortException() {
        super("invalid port");
    }
}
