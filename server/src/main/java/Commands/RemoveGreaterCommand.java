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
import exceptions.AuthException;
import exceptions.EmptyCollectionException;
import exceptions.InvalidDataException;
import exceptions.MissedCommandArgumentException;

import java.util.List;

public class RemoveGreaterCommand extends CommandImplements {
    private ProductDatabaseManager collectionManager;

    public RemoveGreaterCommand(ProductManager cm) {
        super("remove_greater", CommandType.NORMAL, CollectionOperation.REMOVE);
        collectionManager = (ProductDatabaseManager) cm;
    }

    @Override
    public Response run() throws InvalidDataException, AuthException {
        if (collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
        if (!hasStringArg()) throw new MissedCommandArgumentException();
        User user = getArgument().getUser();
        try {
            long id = Long.parseLong(getStringArg());

            List<Product> products = collectionManager.removeGreater(id, user);
            //return "Elements with id greater than " + id + " was successfully deleted by "+user.getLogin();

            //List<Product> products = collectionManager.removeLower(id,user);


            //return new AnswerMsg().info(List<Product> products = collectionManager.removeLower(id,user);
//            for (Product product1 : products) {
//                System.out.println(product1.toString());
//                System.out.println("hello");
//
//            }
//            System.out.println("helo");


            return new AnswerMsg().info("Elements with id lower than " + id + " was successfully deleted by " + user.getLogin()).setCollection(products).setCollection(products);

        } catch (NumberFormatException e) {
            throw new InvalidDataException("id must be number");
        }
    }
}
