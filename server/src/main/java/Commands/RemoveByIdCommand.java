package Commands;

import auth.User;
import collection.ProductManager;
import commands.CommandImplements;
import commands.CommandType;
import connection.AnswerMsg;
import connection.CollectionOperation;
import connection.Response;
import data.Product;
import exceptions.*;

import java.util.Arrays;
import java.util.List;

public class RemoveByIdCommand extends CommandImplements {
    private ProductManager collectionManager;

    public RemoveByIdCommand(ProductManager cm) {
        super("remove_by_id", CommandType.NORMAL, CollectionOperation.REMOVE);
        collectionManager = cm;
    }

    @Override
    public Response run() throws InvalidDataException, AuthException {
        User user = getArgument().getUser();
        if (collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
        if (!hasStringArg()) throw new MissedCommandArgumentException();
        try {
            long id = Long.parseLong(getStringArg());
            if (!collectionManager.chekID(id)) throw new InvalidCommandException("no such id " + getStringArg());

            String owner = collectionManager.getById(id).getUserLogin();
            String login = user.getLogin();

            if (login == null || !login.equals(owner)) {
                throw new PermissinException(owner);
            }
            Product product = collectionManager.getById(id);
            collectionManager.removeById(id);
            return new AnswerMsg().info("element with id " + id +" removed").setCollection(Arrays.asList(product));
        } catch (NumberFormatException e) {
            throw new InvalidDataException("id must be long");
        }
    }

}
