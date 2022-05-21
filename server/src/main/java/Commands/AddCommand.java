package Commands;

import collection.ProductManager;
import commands.CommandImplements;
import commands.CommandType;
import data.Product;
import exceptions.CommandException;
import exceptions.InvalidDataException;

public class AddCommand extends CommandImplements {
    private ProductManager collectionManager;

    public AddCommand(ProductManager cm) {
        super("add", CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute() throws InvalidDataException, CommandException {
        collectionManager.add(getProductArg());
        return "Added element: " + getProductArg().toString();
    }
}
