package Commands;

import collection.CollectionManager;
import commands.CommandImplements;
import commands.CommandType;
import data.Product;
import exceptions.EmptyCollectionException;

public class ShowCommand extends CommandImplements {
    private CollectionManager<Product> collectionManager;

    public ShowCommand(CollectionManager<Product> cm) {
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
