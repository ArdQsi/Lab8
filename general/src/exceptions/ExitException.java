package exceptions;

public class ExitException extends CommandException{
    public ExitException() {
        super("shutting down");
    }
}