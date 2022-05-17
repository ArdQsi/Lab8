package exceptions;

public class NoSuchCommandException extends CommandException{
    public NoSuchCommandException() {
        super("wrong command");
    }
}
