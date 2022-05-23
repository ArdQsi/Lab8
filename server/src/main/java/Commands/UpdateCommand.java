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

public class UpdateCommand extends CommandImplements {
    private ProductManager collectionManager;

    public UpdateCommand(ProductManager cm) {
        super("update", CommandType.NORMAL, CollectionOperation.UPDATE);
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
            collectionManager.updateById(id, getProductArg());
            return new AnswerMsg().info("element with id: " + id + " updated").setCollection(Arrays.asList(getProductArg())).setCollectionOperation(CollectionOperation.UPDATE);
        } catch (NumberFormatException e) {
            throw new InvalidDataException("id must be number");
        }
    }
}
