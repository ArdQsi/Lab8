package commands;

import connection.Request;
import connection.Response;
import exceptions.FileException;

public interface Commandable {
    void addCommand(Command cmd);

    Response runCommand(Request req);

    Command getCommand(String key);

    default Command getCommand(Request req) {
        return getCommand(req.getCommandName());
    }

    boolean hasCommand(String s);

    default boolean hasCommand(Request req) {
        return hasCommand(req.getCommandName());
    }

    void consoleMode();

    void fileMode(String path) throws FileException;
}