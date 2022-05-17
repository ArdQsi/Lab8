package commands;

import connection.CollectionOperation;
import connection.Request;
import connection.Response;
import exceptions.*;

public interface Command {
    Response run() throws InvalidDataException, CommandException, FileException, ConnectionException, CollectionException;
    String getName();
    CommandType getType();
    CollectionOperation getOperation();
    void setArgument(Request a);
}
