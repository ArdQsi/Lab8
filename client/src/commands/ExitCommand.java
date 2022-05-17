package commands;

import exceptions.ExitException;

public class ExitCommand extends CommandImplements {
    public ExitCommand() {
        super("exit", CommandType.NORMAL);
    }
    @Override
    public String execute() {
        throw new ExitException();
    }
}
