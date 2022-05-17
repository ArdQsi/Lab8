package collection;

import data.Product;

import java.util.Collection;
import java.util.List;

public interface ProductManager {
    void sort();

    Product getById(long id);

    void add(Product element);

    String getInfo();

    Collection<Product> getCollection();

    boolean chekID(long id);

    void updateById(long id, Product newProduct);

    void removeById(long id);

    void clear();

    void addIfMin(Product product);

    void removeGreater(Collection<Long> ids);

    List<Product> filterLessThanManufactureCost(Float cost);

    List<String> getUniqueOwner();

    void removeLower(Collection<Long> ids);

    void deserializeCollection(String data);
}
