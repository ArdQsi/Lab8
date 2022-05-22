package Commands;

import auth.User;
import collection.ProductManager;
import commands.CommandImplements;
import commands.CommandType;
import connection.AnswerMsg;
import connection.CollectionOperation;
import connection.Response;
import data.Product;
import database.ProductDatabaseManager;
import exceptions.EmptyCollectionException;
import exceptions.InvalidDataException;

public class ClearCommand extends CommandImplements {
    private final ProductDatabaseManager collectionManager;
    public ClearCommand(ProductManager cm){
        super("clear", CommandType.NORMAL, CollectionOperation.REMOVE);
        collectionManager = (ProductDatabaseManager) cm;
    }

    @Override
    public Response run() {
        AnswerMsg answerMsg = new AnswerMsg();
        if (collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
        User user = getArgument().getUser();
        answerMsg.setCollection(collectionManager.clear(user));
        answerMsg.info("collection cleared");
        return answerMsg;
    }
}
