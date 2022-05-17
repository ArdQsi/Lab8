package Commands;

import commands.CommandImplements;
import commands.CommandType;
import exceptions.FileException;
import exceptions.MissedCommandArgumentException;
import exceptions.RecursiveScriptException;

public class ExecuteScriptCommand extends CommandImplements {
    private ServerCommandManager commandManager;
    public ExecuteScriptCommand(ServerCommandManager cm) {
        super("execute_script", CommandType.SPECIAL);
        commandManager = cm;
    }

    @Override
    public String execute() throws FileException {
        if (!hasStringArg()) throw new MissedCommandArgumentException();
        if (commandManager.getStack().contains(getStringArg())) throw new RecursiveScriptException();
        commandManager.getStack().push(getStringArg());
        ServerCommandManager process = new ServerCommandManager(commandManager.getServer());
        process.fileMode(getStringArg());

        commandManager.getStack().pop();
        return "script successfully executed";
    }
}

