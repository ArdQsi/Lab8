package Commands;

import collection.ProductManager;
import commands.CommandImplements;
import commands.CommandType;
import data.Product;
import exceptions.InvalidDataException;

public class InfoCommand extends CommandImplements {
    private ProductManager collectionManager;

    public InfoCommand(ProductManager cm) {
        super("info", CommandType.NORMAL);
        collectionManager = cm;
    }


    @Override
    public String execute() throws InvalidDataException {
        return collectionManager.getInfo();
    }
}