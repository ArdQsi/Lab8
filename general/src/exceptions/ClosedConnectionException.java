package exceptions;

public class ClosedConnectionException extends ConnectionException{
    public ClosedConnectionException() {
        super("The server is down. Try to connect later...");
    }
}
