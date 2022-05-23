package Commands;

import collection.ProductManager;
import commands.CommandImplements;
import commands.CommandType;
import connection.AnswerMsg;
import connection.CollectionOperation;
import connection.Response;
import data.Product;

import java.util.Arrays;

public class AddIfMinCommand extends CommandImplements {
    private ProductManager collectionManager;
    public AddIfMinCommand(ProductManager cm) {
        super("add_if_min", CommandType.NORMAL, CollectionOperation.ADD);
        collectionManager = cm;
    }

    @Override
    public Response run() {
        collectionManager.addIfMin(getProductArg());
        return new AnswerMsg().info("Added element: " + getProductArg().toString()).setCollection(Arrays.asList(getProductArg()));
    }

}