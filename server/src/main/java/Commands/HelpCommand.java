package Commands;

import commands.CommandImplements;
import commands.CommandManager;
import commands.CommandType;

public class HelpCommand extends CommandImplements {
    public HelpCommand() {
        super("help", CommandType.NORMAL);
    }
    @Override
    public String execute() {
        return CommandManager.getHelp();
    }
}
