package Commands;

import auth.User;
import collection.CollectionManager;
import commands.CommandImplements;
import commands.CommandType;
import data.Product;
import database.ProductDatabaseManager;
import exceptions.EmptyCollectionException;
import exceptions.InvalidDataException;

public class ClearCommand extends CommandImplements {
    private final ProductDatabaseManager collectionManager;
    public ClearCommand(CollectionManager<Product> cm){
        super("clear", CommandType.NORMAL);
        collectionManager = (ProductDatabaseManager) cm;
    }

    @Override
    public String execute() throws InvalidDataException {
        if (collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
        User user = getArgument().getUser();
        collectionManager.clear(user);
        return "collection cleared";
    }
}
