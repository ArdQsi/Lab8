package Commands;

import auth.User;
import collection.ProductManager;
import commands.CommandImplements;
import commands.CommandType;
import connection.AnswerMsg;
import connection.Response;
import data.Product;
import database.ProductDatabaseManager;
import exceptions.AuthException;
import exceptions.EmptyCollectionException;
import exceptions.InvalidDataException;
import exceptions.MissedCommandArgumentException;

import java.util.Arrays;
import java.util.List;

public class RemoveLowerCommand extends CommandImplements {
    private final ProductDatabaseManager collectionManager;

    public RemoveLowerCommand(ProductManager cm) {
        super("remove_lower", CommandType.NORMAL);
        collectionManager = (ProductDatabaseManager) cm;
    }

    @Override
    public Response run() throws InvalidDataException, AuthException {
        if (collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
        if (!hasStringArg()) throw new MissedCommandArgumentException();
        User user = getArgument().getUser();
        try {
            long id = Long.parseLong(getStringArg());

            collectionManager.removeLower(id, user);
            Product product = collectionManager.getCollection().iterator().next();
            return new AnswerMsg().info("Elements with id lower than \" + id + \" was successfully deleted by \" + user.getLogin()").setCollection(Arrays.asList(product));

        } catch (NumberFormatException e) {
            throw new InvalidDataException("id must be number");
        }
    }
}
