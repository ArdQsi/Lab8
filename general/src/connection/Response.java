package connection;

import data.Product;

import java.io.Serializable;
import java.util.Collection;

public interface Response extends Serializable {
    String getMessage();

    Status getStatus();

    enum Status {
        ERROR,
        FINE,
        EXIT,
        AUTH_SUCCESS,
        BROADCAST,
        COLLECTION
    }

    CollectionOperation getCollectionOperation();

    public Collection<Product> getCollection();
}
