package commands;

import connection.Request;
import connection.Response;
import exceptions.*;

public interface Command {
    public Response run() throws InvalidDataException, CommandException, FileException, ConnectionException, CollectionException;
    public String getName();
    public CommandType getType();
    public void setArgument(Request a);
}
