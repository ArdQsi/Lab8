package Commands;

import collection.CollectionManager;
import commands.CommandImplements;
import commands.CommandType;
import data.Product;
import exceptions.CommandException;
import exceptions.InvalidDataException;

public class AddCommand extends CommandImplements {
    private CollectionManager<Product> collectionManager;

    public AddCommand(CollectionManager<Product> cm) {
        super("add", CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute() throws InvalidDataException, CommandException {
        collectionManager.add(getProductArg());
        return "Added element: " + getProductArg().toString();
    }
}
