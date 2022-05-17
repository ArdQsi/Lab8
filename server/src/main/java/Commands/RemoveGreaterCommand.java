package Commands;

import auth.User;
import collection.CollectionManager;
import commands.CommandImplements;
import commands.CommandType;
import data.Product;
import database.ProductDatabaseManager;
import exceptions.AuthException;
import exceptions.EmptyCollectionException;
import exceptions.InvalidDataException;
import exceptions.MissedCommandArgumentException;

public class RemoveGreaterCommand extends CommandImplements {
    private ProductDatabaseManager collectionManager;

    public RemoveGreaterCommand(CollectionManager<Product> cm) {
        super("remove_greater", CommandType.NORMAL);
        collectionManager = (ProductDatabaseManager) cm;
    }

    @Override
    public String execute() throws InvalidDataException, AuthException {
        if (collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
        if (!hasStringArg()) throw new MissedCommandArgumentException();
        User user = getArgument().getUser();
        try {
            long id = Long.parseLong(getStringArg());

            collectionManager.removeGreater(id, user);
            return "Elements with id greater than " + id + " was successfully deleted by "+user.getLogin();

        } catch (NumberFormatException e) {
            throw new InvalidDataException("id must be number");
        }
    }
}
