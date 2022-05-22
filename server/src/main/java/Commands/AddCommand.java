package Commands;

import collection.ProductManager;
import commands.CommandImplements;
import commands.CommandType;
import connection.AnswerMsg;
import connection.CollectionOperation;
import connection.Response;
import data.Product;
import exceptions.CommandException;
import exceptions.InvalidDataException;

import java.util.Arrays;

public class AddCommand extends CommandImplements {
    private ProductManager collectionManager;

    public AddCommand(ProductManager cm) {
        super("add", CommandType.NORMAL, CollectionOperation.ADD);
        collectionManager = cm;
    }

    @Override
    public Response run() throws InvalidDataException, CommandException {
        collectionManager.add(getProductArg());
        return new AnswerMsg().info("Added element: " + getProductArg().toString()).setCollection(Arrays.asList(getProductArg()));
    }
}
