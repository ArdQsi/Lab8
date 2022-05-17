package exceptions;

import java.io.IOException;

public class ClosedChannelException extends IOException {
    public ClosedChannelException() {
        super("cannot close channel");
    }
}
