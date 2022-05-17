package commands;

import exceptions.CommandException;
import exceptions.FileException;
import exceptions.MissedCommandArgumentException;
import exceptions.RecursiveScriptException;

public class ExecuteScriptCommand extends CommandImplements {
    private ClientCommandManager commandManager;
    public ExecuteScriptCommand(ClientCommandManager cm) {
        super("execute_script", CommandType.NORMAL);
        commandManager = cm;
    }

    @Override
    public String execute() {
        if(!hasStringArg()) throw new MissedCommandArgumentException();
        if (commandManager.getStack().contains(getStringArg())) throw new RecursiveScriptException();
        commandManager.getStack().push(getStringArg());
        ClientCommandManager process = new ClientCommandManager(commandManager.getClient());
        try {
            process.fileMode(getStringArg());
        } catch (FileException e) {
            throw new CommandException("cannot find script file");
        }
        commandManager.getStack().pop();
        return "script successfully executed";
    }
}