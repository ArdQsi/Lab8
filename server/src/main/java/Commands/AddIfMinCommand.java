package Commands;

import collection.CollectionManager;
import commands.CommandImplements;
import commands.CommandType;
import data.Product;
import exceptions.CommandException;

public class AddIfMinCommand extends CommandImplements {
    private CollectionManager<Product> collectionManager;
    public AddIfMinCommand(CollectionManager<Product> cm) {
        super("add_if_min", CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute() {
        collectionManager.addIfMin(getProductArg());
        return ("Added element: " + getProductArg().toString());
    }
}