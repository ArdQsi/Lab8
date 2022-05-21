package Commands;

import auth.User;
import collection.ProductManager;
import commands.CommandImplements;
import commands.CommandType;
import data.Product;
import exceptions.*;

public class RemoveByIdCommand extends CommandImplements {
    private ProductManager collectionManager;

    public RemoveByIdCommand(ProductManager cm) {
        super("remove_by_id", CommandType.NORMAL);
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
            collectionManager.removeById(id);
            return "element with id \"" + Long.toString(id) + "\" removed";
        } catch (NumberFormatException e) {
            throw new InvalidDataException("id must be long");
        }
    }

}
