package Commands;

import collection.ProductManager;
import commands.CommandImplements;
import commands.CommandType;
import data.Product;

public class AddIfMinCommand extends CommandImplements {
    private ProductManager collectionManager;
    public AddIfMinCommand(ProductManager cm) {
        super("add_if_min", CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute() {
        collectionManager.addIfMin(getProductArg());
        return ("Added element: " + getProductArg().toString());
    }
}