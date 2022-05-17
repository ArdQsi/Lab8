package Commands;

import auth.User;
import collection.CollectionManager;
import commands.CommandImplements;
import commands.CommandType;
import data.Product;
import exceptions.*;

public class UpdateCommand extends CommandImplements {
    private CollectionManager<Product> collectionManager;

    public UpdateCommand(CollectionManager<Product> cm) {
        super("update", CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute() throws InvalidDataException, AuthException {
        User user = getArgument().getUser();
        if (collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
        if (!hasStringArg()) throw new MissedCommandArgumentException();
        try {
            long id = Long.parseLong(getStringArg());
            if (!collectionManager.chekID(id)) throw new InvalidCommandException("no such id " + getStringArg());

            String owner = collectionManager.getById(id).getUserLogin();
            String login = user.getLogin();
            if (login == null || !login.equals(owner)) {
                throw new AuthException("you don't have permission, element was created by " + owner);
            }
            collectionManager.updateById(id, getProductArg());
            return "element with id: " + Long.toString(id) + " updated";
        } catch (NumberFormatException e) {
            throw new InvalidDataException("id must be number");
        }
    }
}
