package commands;

import exceptions.*;

public class ExecuteScriptCommand extends CommandImplements {
    private ClientCommandManager commandManager;
    public ExecuteScriptCommand(ClientCommandManager cm) {
        super("execute_script", CommandType.NORMAL);
        commandManager = cm;
    }

    @Override
    public String execute()  throws FileException, InvalidDataException, ConnectionException {
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