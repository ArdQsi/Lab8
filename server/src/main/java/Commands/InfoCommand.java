package Commands;

import collection.CollectionManager;
import commands.CommandImplements;
import commands.CommandType;
import data.Product;
import exceptions.InvalidDataException;

public class InfoCommand extends CommandImplements {
    private CollectionManager<Product> collectionManager;

    public InfoCommand(CollectionManager<Product> cm) {
        super("info", CommandType.NORMAL);
        collectionManager = cm;
    }


    @Override
    public String execute() throws InvalidDataException {
        return collectionManager.getInfo();
    }
}