package exceptions;

public class InvalidAddressException  extends ConnectionException{
    public InvalidAddressException() {
        super("invalid address");
    }

    public InvalidAddressException(String s) {
        super(s);
    }
}
