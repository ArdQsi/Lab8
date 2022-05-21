package Commands;

import collection.ProductManager;
import commands.CommandImplements;
import commands.CommandType;
import data.Product;
import exceptions.EmptyCollectionException;

public class ShowCommand extends CommandImplements {
    private ProductManager collectionManager;

    public ShowCommand(ProductManager cm) {
        super("show", CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute() {
        if (collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
        collectionManager.sort();
        return collectionManager.getCollection().toString();
    }
}
