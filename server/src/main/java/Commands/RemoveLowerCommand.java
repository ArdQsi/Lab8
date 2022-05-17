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

public class RemoveLowerCommand extends CommandImplements {
    private final ProductDatabaseManager collectionManager;

    public RemoveLowerCommand(CollectionManager<Product> cm) {
        super("remove_lower", CommandType.NORMAL);
        collectionManager = (ProductDatabaseManager) cm;
    }

    @Override
    public String execute() throws InvalidDataException, AuthException {
        if (collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
        if (!hasStringArg()) throw new MissedCommandArgumentException();
        User user = getArgument().getUser();
        try {
            long id = Long.parseLong(getStringArg());

            collectionManager.removeLower(id, user);
            return "Elements with id lower than " + id + " was successfully deleted by " + user.getLogin();

        } catch (NumberFormatException e) {
            throw new InvalidDataException("id must be number");
        }
    }
}
