package Commands;

import collection.CollectionManager;
import commands.CommandImplements;
import commands.CommandType;
import data.Product;
import exceptions.EmptyCollectionException;
import exceptions.InvalidDataException;
import exceptions.MissedCommandArgumentException;

import java.util.List;

public class FilterLessThanManufactureCostCommand extends CommandImplements {
    private CollectionManager<Product> collectionManager;
    public FilterLessThanManufactureCostCommand(CollectionManager<Product> cm) {
        super("filter_less_than_manufacture_cost", CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute() throws InvalidDataException {
        if (!hasStringArg()) throw new MissedCommandArgumentException();
        if (collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
        try {
            Float cost = Float.valueOf(getStringArg());
            List<Product> list = collectionManager.filterLessThanManufactureCost(cost);
            if (list.isEmpty()) return "none of elements have manufacture cost less than " + cost;
            return list.toString();
        } catch (NumberFormatException e) {
            throw new InvalidDataException("cost must be number");
        }
    }
}