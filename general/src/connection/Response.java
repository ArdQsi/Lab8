package connection;

import data.Product;

import java.io.Serializable;
import java.util.Collection;

public interface Response extends Serializable {
    String getMessage();


    Long getIds();

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

    Collection<Product> getCollection();

}
