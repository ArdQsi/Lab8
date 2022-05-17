package collection;

import auth.User;
import data.Product;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public interface CollectionManager<T> {

    void sort();

    Product getById(long id);

    void add(T element);

    String getInfo();

    Collection<T> getCollection();

    boolean chekID(long id);

    void updateById(long id, Product newProduct);

    void removeById(long id);

    void clear();

    void addIfMin(Product product);

    void removeGreater(Collection<Long> ids);

    List<Product> filterLessThanManufactureCost(Float cost);

    List<String> getUniqueOwner();

    void removeLower(Collection<Long> ids);

    void deserializeCollection();

}
