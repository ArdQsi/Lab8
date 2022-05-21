package Commands;

import collection.ProductManager;
import commands.CommandImplements;
import commands.CommandType;
import data.Product;
import exceptions.EmptyCollectionException;

import java.util.List;

public class PrintUniqueOwnerCommand extends CommandImplements {
    private ProductManager collectionManager;

    public PrintUniqueOwnerCommand(ProductManager cm) {
        super("print_unique_owner", CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute() {
        if (collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
        List<String> list = collectionManager.getUniqueOwner();
        return list.stream().reduce("", (a, b) -> a + b + "\n");
    }
}