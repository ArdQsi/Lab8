package exceptions;

/**
 * thrown when user doesn't enter argument of command
 */
public class MissedCommandArgumentException extends InvalidCommandException {
    public MissedCommandArgumentException() {
        super("missed command argument");
    }
}

