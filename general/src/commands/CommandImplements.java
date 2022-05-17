package commands;

import connection.AnswerMsg;
import connection.Request;
import connection.Response;
import data.Product;
import exceptions.*;

public abstract class CommandImplements implements Command {
    private final CommandType type;
    private final String name;
    private Request arg;

    public CommandImplements(String n, CommandType t) {
        name = n;
        type = t;
    }

    @Override
    public CommandType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String execute() throws InvalidDataException, CommandException, FileException, ConnectionException, CollectionException {
        return "";
    }

    public Response run() throws InvalidDataException, CommandException, FileException, ConnectionException, CollectionException {
        AnswerMsg res = new AnswerMsg();
        res.info(execute());
        return res;
    }

    public Request getArgument() {
        return arg;
    }

    public void setArgument(Request request) {
        arg = request;
    }

    public boolean hasStringArg() {
        return arg!=null && arg.getStringArg() != null && !arg.getStringArg().equals("");
    }

    public boolean hasProductArg() {
        return arg!=null && arg.getProduct()!=null;
    }

    public boolean hasUserArg() {
        return arg != null && arg.getUser() != null && arg.getUser().getLogin() != null;
    }

    public String getStringArg() {
        return getArgument().getStringArg();
    }

    public Product getProductArg() {
        return getArgument().getProduct();
    }
}
