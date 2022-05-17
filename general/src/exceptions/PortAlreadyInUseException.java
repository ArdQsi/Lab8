package exceptions;

public class PortAlreadyInUseException extends ConnectionException{
    public PortAlreadyInUseException() {
        super("port already in use");
    }
}
