package connection;

import data.Product;

import java.io.Serializable;
import java.util.Collection;

public interface Response extends Serializable {
    String getMessage();


    Long getIds();

    Status getStatus();

    void setStatus(AnswerMsg status);

    void setStatuss(Status status);

    AnswerMsg setCollectionOperation(CollectionOperation op);

    void setCollectionOperations(CollectionOperation op);

    CollectionOperation getCollectionOperation();

    //void setStatuss(Status status);

    enum Status  {
        ERROR,
        FINE,
        EXIT,
        AUTH_SUCCESS,
        BROADCAST,
        COLLECTION
    }


    Collection<Product> getCollection();

}
